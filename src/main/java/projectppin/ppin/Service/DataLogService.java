package projectppin.ppin.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectppin.ppin.DTO.AttendanceDTO;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.Repository.DataLogRepository;
import projectppin.ppin.Repository.EmployeeRepository;
import projectppin.ppin.domain.DataLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
}
