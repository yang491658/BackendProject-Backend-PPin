package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projectppin.ppin.Service.VacationService;

@RestController
@RequestMapping("/api/vacation")
public class VacationController {
    @Autowired
    private VacationService vacationService;

    // 연차 사용 기록 API
    @PostMapping("/use")
    public void useVacation(@RequestParam String userId, @RequestParam int usedDays) {
        vacationService.logVacationUsage(userId, usedDays);
    }

    // 연차 이월 API
    @PostMapping("/carryover/{userId}")
    public void carryOverVacation(@PathVariable String userId) {
        vacationService.carryOverAnnualLeave(userId);
    }
}
