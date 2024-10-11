package projectppin.ppin.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.DTO.DocumentDTO;
import projectppin.ppin.Repository.DataLogRepository;
import projectppin.ppin.domain.DataLog;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
public class DocumentService {
    @Autowired
    private DataLogService dataLogService;
    @Autowired
    private DataLogRepository dataLogRepository;

    public Long register(DocumentDTO documentDTO, String userId) {
        String newData
                = "게시판 : 제목: " + documentDTO.getTitle()
                + ", 작성자: " + documentDTO.getWriter()
                + ", 내용: " + documentDTO.getContent()
                + ", 첨부파일: " + documentDTO.getUploadFileNames();

        // actionType이 '문서 생성'인 로그의 개수를 가져옴
        int entityCount = dataLogRepository.countByActionType("문서 생성");


        DataLogDTO dataLogDTO = new DataLogDTO();
        dataLogDTO.setEntityId(entityCount + 1);
        dataLogDTO.setEntityType("문서함");
        dataLogDTO.setActionType("문서 생성");
        dataLogDTO.setNewData(newData);
        dataLogDTO.setComments("새로운 문서가 등록되었습니다.");

         dataLogService.logAction(dataLogDTO, userId);
        
        return (long) (entityCount + 1);
    }

    public List<DocumentDTO> getList() {
        return dataLogService.findAllDocumentRecords();
    }

    public DocumentDTO get(Long entityId) {
        // 삭제된 문서인지 확인
        List<DataLog> deletedDataLogs = dataLogRepository.findByActionType("문서 삭제");
        boolean isAlreadyDeleted = deletedDataLogs.stream()
                .anyMatch(deletedLog -> Long.valueOf(deletedLog.getEntityId()).equals(entityId));

        if (isAlreadyDeleted) {
            return DocumentDTO.builder().dno((long) entityId).title("이미 삭제된 문서입니다.").build();
        }

        return dataLogService.findByEntityId(Math.toIntExact(entityId));
    }

    public void remove(Long entityId, String userId) {
        // 삭제된 문서인지 확인
        List<DataLog> deletedDataLogs = dataLogRepository.findByActionType("문서 삭제");
        boolean isAlreadyDeleted = deletedDataLogs.stream()
                .anyMatch(deletedLog -> Long.valueOf(deletedLog.getEntityId()).equals((long) entityId));

        // entityId에 해당하는 DataLog를 조회
        Optional<DataLog> dataLogOptional = dataLogRepository.findByActionTypeAndEntityId("문서 생성", Math.toIntExact(entityId));
        DataLog dataLog = dataLogOptional.orElseThrow();

        if (isAlreadyDeleted) {
            log.info("해당 문서는 존재하지 않습니니다."); // 메시지 출력
        } else {
            DataLogDTO dataLogDTO = new DataLogDTO();
            dataLogDTO.setEntityId(dataLog.getEntityId());
            dataLogDTO.setEntityType("문서함");
            dataLogDTO.setActionType("문서 삭제");
            dataLogDTO.setOldData(dataLog.getNewData());
            dataLogDTO.setComments(dataLog.getEntityId() + "번 문서가 삭제되었습니다.");

            dataLogService.logAction(dataLogDTO, userId);
        }
    }

    public void modify(Long entityId, DocumentDTO documentDTO, String userId) {
        remove(entityId,userId);
        register(documentDTO,userId);
    }
}
