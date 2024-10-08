package projectppin.ppin.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.Repository.DataLogRepository;
import projectppin.ppin.domain.DataLog;

import java.time.LocalDateTime;

@Service
public class DataLogService {
    @Autowired
    private DataLogRepository dataLogRepository;

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
}
