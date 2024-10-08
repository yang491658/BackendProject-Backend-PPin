package projectppin.ppin.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import projectppin.ppin.Repository.DataLogRepository;
import projectppin.ppin.domain.DataLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AttendanceServiceTest {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private DataLogRepository dataLogRepository;

    @BeforeEach
    public void setUp() {
        // 사용자 인증 정보 설정 (testUser)
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("testUser", null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("출근 기록 로그 저장 여부 테스트")
    public void testClockInLog() {
        String userId = "이제희";

        // 출근 기록 호출하기 전에 시간을 미리 기록
        LocalDateTime clockInTime = LocalDateTime.now();  // 이 시점에서 시간을 미리 설정

        // 출근 기록 호출
        attendanceService.clockIn(userId);

        // DataLog에서 출근 기록 이력이 저장되었는지 확인
        List<DataLog> logs = dataLogRepository.findByActionType("출근");

        // 로그가 비어있지 않은지 확인 (로그가 기록되었는지 확인)
        assertFalse(logs.isEmpty(), "출근 기록이 로그에 저장되지 않았습니다.");

        // 로그가 기록된 경우에만 가장 최근 로그 확인
        if (!logs.isEmpty()) {
            DataLog log = logs.get(logs.size() - 1);  // 가장 최근 기록

            // 로그에서 날짜 부분 비교 (로그된 시간과 미리 기록된 시간이 일치하는지 확인)
            String expectedDate = "출근 기록: " + clockInTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            assertEquals(expectedDate, log.getNewData(), "로그된 날짜가 예상과 일치하지 않습니다.");

            assertEquals("출근 기록이 저장되었습니다.", log.getComments());  // 코멘트 확인
            assertEquals(userId, log.getUserId());  // 사용자 ID 확인
        }
    }

    @Test
    @DisplayName("퇴근 기록 로그 저장 여부 테스트")
    public void testClockOutLog() {
        String userId = "이제희";

        // 퇴근 기록 호출하기 전에 시간을 미리 기록
        LocalDateTime clockOutTime = LocalDateTime.now();  // 이 시점에서 시간을 미리 설정

        // 퇴근 기록 호출
        attendanceService.clockOut(userId);

        // DataLog에서 퇴근 기록 이력이 저장되었는지 확인
        List<DataLog> logs = dataLogRepository.findByActionType("퇴근");

        // 로그가 비어있지 않은지 확인 (로그가 기록되었는지 확인)
        assertFalse(logs.isEmpty(), "퇴근 기록이 로그에 저장되지 않았습니다.");

        // 로그가 기록된 경우에만 가장 최근 로그 확인
        if (!logs.isEmpty()) {
            DataLog log = logs.get(logs.size() - 1);  // 가장 최근 기록

            // 로그에서 날짜 부분 비교 (로그된 시간과 미리 기록된 시간이 일치하는지 확인)
            String expectedDate = "퇴근 기록: " + clockOutTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            assertEquals(expectedDate, log.getNewData(), "로그된 날짜가 예상과 일치하지 않습니다.");

            assertEquals("퇴근 기록이 저장되었습니다.", log.getComments());  // 코멘트 확인
            assertEquals(userId, log.getUserId());  // 사용자 ID 확인
        }
    }
}