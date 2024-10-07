package projectppin.ppin.Service;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectppin.ppin.DTO.DataLogDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AttendanceService {

    @Autowired
    private DataLogService dataLogService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//    // 출근 기록
//    public void clockIn(String userId) {
//        String newData = "출근 기록: " + LocalDateTime.now();
//        // 출근 기록 로그 남기기 (여기서는 ID를 0으로 처리)
//        dataLogService.logAction("admin",userId, "출근", 0, null, newData, "출근 기록이 저장되었습니다.");
//    }
//
//    // 퇴근 기록
//    public void clockOut(String userId) {
//        String newData = "퇴근 기록: " + LocalDateTime.now();
//        // 퇴근 기록 로그 남기기 (여기서도 ID를 0으로 처리)
//        dataLogService.logAction("admin",userId, "퇴근", 0, null, newData, "퇴근 기록이 저장되었습니다.");
//    }

    // 출근 기록
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public void clockIn(String userId) {
        // DataLogDTO 객체 생성 및 데이터 설정
        DataLogDTO dataLogDTO = new DataLogDTO();
        dataLogDTO.setActionType("출근");
        dataLogDTO.setEntityType("출근 기록");
        dataLogDTO.setEntityId(0); // 출근 기록이므로 엔티티 ID는 0으로 처리
        dataLogDTO.setNewData("출근 기록: " + LocalDateTime.now().format(formatter));  // 포맷 적용
        dataLogDTO.setComments("출근 기록이 저장되었습니다.");

        // 로그 기록 호출
        dataLogService.logAction(dataLogDTO, userId);
    }

    // 퇴근 기록
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public void clockOut(String userId) {
        // DataLogDTO 객체 생성 및 데이터 설정
        DataLogDTO dataLogDTO = new DataLogDTO();
        dataLogDTO.setActionType("퇴근");
        dataLogDTO.setEntityType("퇴근 기록");
        dataLogDTO.setEntityId(0); // 퇴근 기록이므로 엔티티 ID는 0으로 처리
        dataLogDTO.setNewData("퇴근 기록: " + LocalDateTime.now().format(formatter));  // 포맷 적용
        dataLogDTO.setComments("퇴근 기록이 저장되었습니다.");

        // 로그 기록 호출
        dataLogService.logAction(dataLogDTO, userId);
    }
}
