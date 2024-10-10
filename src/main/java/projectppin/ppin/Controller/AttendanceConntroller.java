package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projectppin.ppin.DTO.AttendanceDTO;
import projectppin.ppin.Service.AttendanceService;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceConntroller {

    @Autowired
    private AttendanceService attendanceService;

    // 출근 기록 API
    @PostMapping("/clockin")
    public void clockIn(@RequestParam String userId) {
        attendanceService.clockIn(userId);
    }

    // 퇴근 기록 API
    @PostMapping("/clockout")
    public void clockOut(@RequestParam String userId) {
        attendanceService.clockOut(userId);
    }

    // 출퇴근 기록
    @GetMapping("/all")
    public List<AttendanceDTO> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }
}
