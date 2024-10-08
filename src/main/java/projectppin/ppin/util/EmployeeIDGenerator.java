package projectppin.ppin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EmployeeIDGenerator {
    public static String generateEmployeeID(Long enb, String customFourDigits) {
        // 1. 현재 연도 네 자리 (예: "2410" -> 24년 10월)
        String yearMonth = DateTimeFormatter.ofPattern("yyMM").format(LocalDateTime.now());

        // 2. 고정된 4자리 숫자 (사용자가 입력한 값, 없으면 입력 요청)
        if (customFourDigits == null || customFourDigits.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("4자리 숫자를 입력해주세요:");
            customFourDigits = scanner.nextLine();
        }

        // 3. 자동 생성된 사번 (사원 번호)
        String employeeNumber = enb.toString();

        // 4. 최종 사원 ID 생성: 연도+월 - 사번
        return yearMonth + "-" + employeeNumber + customFourDigits;
    }
}
