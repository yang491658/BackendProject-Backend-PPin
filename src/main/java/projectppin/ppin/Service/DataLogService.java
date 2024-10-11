package projectppin.ppin.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectppin.ppin.DTO.AttendanceDTO;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.DTO.DocumentDTO;
import projectppin.ppin.Repository.DataLogRepository;
import projectppin.ppin.Repository.EmployeeRepository;
import projectppin.ppin.domain.DataLog;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DataLogService {

    @Autowired
    private DataLogRepository dataLogRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void logAction(DataLogDTO dataLogDTO, String userId) {
        DataLog log = new DataLog();
        log.setUserId(userId);
        log.setActionType(dataLogDTO.getActionType());
        log.setEntityType(dataLogDTO.getEntityType());
        log.setEntityId(dataLogDTO.getEntityId());
        log.setOldData(dataLogDTO.getOldData());
        log.setNewData(dataLogDTO.getNewData());
        log.setComments(dataLogDTO.getComments());
        log.setTimestamp(LocalDateTime.now());

        dataLogRepository.save(log);
    }

    // 모든 출퇴근 기록을 가져오는 메서드
    public List<AttendanceDTO> findAllAttendanceRecords() {
        // employee 테이블에서 모든 enb 값을 가져옴
        List<String> validUserIds = employeeRepository.findAllEnbValues();

        // 출근 및 퇴근 로그를 가져오고, validUserIds와 일치하는 것만 필터링
        List<DataLog> dataLogs = dataLogRepository.findByActionTypeIn(List.of("출근", "퇴근")).stream()
                .filter(dataLog -> validUserIds.contains(dataLog.getUserId())) // userId 필터링
                .collect(Collectors.toList());

        // DataLog 객체를 AttendanceDTO로 변환
        return dataLogs.stream()
                .map(this::entityToDTO) // 변환 메서드 호출
                .collect(Collectors.toList());
    }

    // DataLog를 AttendanceDTO로 변환하는 메서드
    private AttendanceDTO entityToDTO(DataLog dataLog) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        // DataLog의 정보를 AttendanceDTO에 설정
        attendanceDTO.setUserId(dataLog.getUserId());
        attendanceDTO.setEntityType(dataLog.getEntityType());
        attendanceDTO.setActionType(dataLog.getActionType());
        attendanceDTO.setNewData(dataLog.getNewData());
        attendanceDTO.setTimestamp(dataLog.getTimestamp());
        return attendanceDTO;
    }

    public List<DocumentDTO> findAllDocumentRecords() {
        // actionType이 '문서 생성'인 로그 데이터를 가져오기
        List<DataLog> dataLogs = dataLogRepository.findByActionType("문서 생성");
        // actionType이 '문서 삭제'인 로그 데이터를 가져오기
        List<DataLog> deletedDataLogs = dataLogRepository.findByActionType("문서 삭제");

        // deletedDataLogs의 entityId를 Set으로 수집
        Set<Integer> deletedEntityIds = deletedDataLogs.stream()
                .map(DataLog::getEntityId) // entityId 추출
                .collect(Collectors.toSet()); // Set으로 변환

        // DataLog의 newData 문자열을 파싱하여 DocumentDTO로 변환
        return dataLogs.stream()
                .filter(dataLog -> !deletedEntityIds.contains(dataLog.getEntityId())) // 삭제된 문서 제외
                .map(dataLog -> {
                    // newData 필드 파싱
                    String newData = dataLog.getNewData();
                    DocumentDTO documentDTO = parseNewDataToDocumentDTO(dataLog.getEntityId(), newData, dataLog.getTimestamp());
                    documentDTO.setWriter(dataLog.getUserId()); // 작성자(userId) 설정
                    return documentDTO;
                })
                .sorted(Comparator.comparingLong(DocumentDTO::getDno).reversed()) // dno 기준 내림차순 정렬
                .collect(Collectors.toList());
    }

    // newData 문자열을 파싱하여 DocumentDTO로 변환하는 메서드
    private DocumentDTO parseNewDataToDocumentDTO(int entityId, String newData, LocalDateTime creatTime) {
        // 제목, 작성자, 내용, 첨부파일, 삭제 여부를 파싱하는 로직
        String title = extractValue(newData, "제목");
        String content = extractValue(newData, "내용");

        // 첨부파일 리스트 파싱
        String filesString = extractValue(newData, "첨부파일");
        List<String> uploadFileNames = parseFiles(filesString);

        // DocumentDTO 생성
        return DocumentDTO.builder()
                .dno((long) entityId)
                .title(title)
                .content(content)
                .createDate(creatTime)
                .uploadFileNames(uploadFileNames)
                .build();
    }

    // 특정 키워드에 해당하는 값을 추출하는 유틸리티 메서드
    private String extractValue(String data, String key) {
        String[] parts = data.split(", ");
        for (String part : parts) {
            if (part.contains(key)) {
                int cut = part.split(": ").length;
                return part.split(": ")[cut - 1].trim();
            }
        }
        return "";
    }

    // 첨부파일 문자열을 파싱하여 리스트로 변환하는 메서드
    private List<String> parseFiles(String filesString) {
        filesString = filesString.replace("[", "").replace("]", "").trim(); // 대괄호 제거
        if (filesString.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(filesString.split(", "));
    }

    public DocumentDTO findByEntityId(int entityId) {
        // entityId에 해당하는 DataLog 조회
        Optional<DataLog> dataLogOptional = dataLogRepository.findByActionTypeAndEntityId("문서 생성", entityId);

        if (!dataLogOptional.isPresent()) {
            log.info("해당 문서가 존재하지 않습니다.");
            return null; // 또는 예외 처리
        }

        DataLog dataLog = dataLogOptional.get();

        // newData 필드 파싱하여 DocumentDTO 생성
        String newData = dataLog.getNewData();
        DocumentDTO documentDTO = parseNewDataToDocumentDTO(dataLog.getEntityId(), newData, dataLog.getTimestamp());
        documentDTO.setWriter(dataLog.getUserId()); // 작성자(userId) 설정

        return documentDTO;
    }
}
