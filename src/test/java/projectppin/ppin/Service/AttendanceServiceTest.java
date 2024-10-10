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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("근태관리용 임시 데이터")
    public void temp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();

        int[] startDays = {20, 14, 7, 2}; // 각 사용자별 출근 시작일

        for (int i = 0; i < startDays.length; i++) {
            String userID = String.valueOf(i + 1);
            int days = startDays[i];

            for (int j = days; j >= 0; j--) {
                LocalDate date = LocalDate.now().minusDays(j);

                // 주말은 제외
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }

                // 출근 시간 08:30 ~ 09:30 사이
                LocalTime randomClockInTime = LocalTime.of(8, 30).plusMinutes(random.nextInt(61)); // 0 ~ 60분 추가
                LocalDateTime clockInTime = date.atTime(randomClockInTime);

                // 퇴근 시간 17:00 ~ 20:00 사이
                LocalTime randomClockOutTime = LocalTime.of(17, 0).plusMinutes(random.nextInt(181)); // 0 ~ 180분 추가
                LocalDateTime clockOutTime = date.atTime(randomClockOutTime);
                // 출근 로그
                String clockInComment = "출근 기록이 저장되었습니다.";
                if (clockInTime.toLocalTime().isAfter(LocalTime.of(9, 0))) {
                    clockInComment = "출근 기록이 저장되었습니다. / 지각";
                }
                if (i == 1 && j == 2) {
                    String comment = "무단 결근 / " + clockInTime.toLocalDate();
                    DataLog clockInLog = DataLog.builder()
                            .entityId(0)
                            .entityType("출근 기록")
                            .actionType("기타")
                            .comments(comment)
                            .timestamp(clockInTime.toLocalDate().atStartOfDay())
                            .userId(userID)
                            .build();
                    dataLogRepository.save(clockInLog);
                } else {
                    DataLog clockInLog = DataLog.builder()
                            .entityId(0)
                            .entityType("출근 기록")
                            .actionType("출근")
                            .comments(clockInComment)
                            .newData("출근 기록: " + clockInTime.format(formatter))
                            .timestamp(clockInTime)
                            .userId(userID)
                            .build();
                    dataLogRepository.save(clockInLog);
                }

                // 퇴근 로그
                String clockOutComment = "퇴근 기록이 저장되었습니다.";
                if (clockOutTime.toLocalTime().isBefore(LocalTime.of(18, 0))) {
                    clockOutComment = "퇴근 기록이 저장되었습니다. / 조퇴";
                }
                if (i == 0 && j == 6) {
                    String comment = "퇴근 미체크 / " + clockOutTime.toLocalDate();
                    DataLog clockInLog = DataLog.builder()
                            .entityId(0)
                            .entityType("퇴근 기록")
                            .actionType("기타")
                            .comments(comment)
                            .timestamp(clockOutTime.toLocalDate().atStartOfDay())
                            .userId(userID)
                            .build();
                    dataLogRepository.save(clockInLog);
                } else if (i == 1 && j == 2) {
                    String comment = "무단 결근 / " + clockInTime.toLocalDate();
                    DataLog clockOutLog = DataLog.builder()
                            .entityId(0)
                            .entityType("퇴근 기록")
                            .actionType("기타")
                            .comments(comment)
                            .timestamp(clockOutTime.toLocalDate().atStartOfDay())
                            .userId(userID)
                            .build();
                    dataLogRepository.save(clockOutLog);
                } else {
                    DataLog clockOutLog = DataLog.builder()
                            .entityId(0)
                            .entityType("퇴근 기록")
                            .actionType("퇴근")
                            .comments(clockOutComment)
                            .newData("퇴근 기록: " + clockOutTime.format(formatter))
                            .timestamp(clockOutTime)
                            .userId(userID)
                            .build();
                    dataLogRepository.save(clockOutLog);
                }
            }
        }
    }
}