package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projectppin.ppin.Service.AttendanceService;

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
}
