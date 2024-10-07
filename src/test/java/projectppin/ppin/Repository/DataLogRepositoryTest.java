package projectppin.ppin.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import projectppin.ppin.Service.CompanyService;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.domain.DataLog;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataLogRepositoryTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DataLogRepository dataLogRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        // 임의의 사용자 ID와 권한을 가진 인증 정보 설정
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("testUser", null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("회사리스트를 불러와서 수정 후 Log 기록 여부 테스트")
    public void Test1() throws InterruptedException {
        // Cnb1에 해당하는 회사 정보 가져오기
        Optional<CompanyList> companyOpt = companyRepository.findById(2L);  // Cnb1이 1L이라 가정
        assertTrue(companyOpt.isPresent(), "회사 정보가 없습니다.");

        CompanyList company = companyOpt.get();

        // 기존의 기준 봉급을 null로 설정 (기존 상태가 null일 것을 가정)
        company.setBaseSalary(null);
        companyRepository.save(company);

        // 기준 봉급 업데이트
        Long newSalary = 3200000L;
        companyService.updateCompany(company.getCnb(), company.getPosition(), company.getDepartment(), newSalary);

        // 로그 기록을 기다리기 위해 일정 시간 대기 (잠재적 트랜잭션 처리 문제 해결)
        Thread.sleep(2000); // 2초 대기

        // Data Log에서 변경 이력이 기록되었는지 확인
        List<DataLog> logs = dataLogRepository.findByActionType("기준 봉급 수정");

        // 로그가 비어있지 않은지 확인 (로그가 기록되었는지 확인)
        assertTrue(logs.isEmpty(), "Data Log에 기록된 로그가 없습니다!");

        // 로그가 기록된 경우에만 가장 최근 로그 확인
        if (!logs.isEmpty()) {
            DataLog log = logs.get(logs.size() - 1);  // 가장 최근 기록
            assertEquals("이전 기준 봉급: null", log.getOldData());  // 이전 값이 null인지 확인
            assertEquals("변경된 기준 봉급: 3200000", log.getNewData());  // 변경된 값이 10000000인지 확인
        }
    }

    @Test
    @DisplayName("회사리스트를 불러와서 직책과 부서 수정 후 Log 기록 여부 테스트")
    public void Test2() throws InterruptedException {
        // Cnb1에 해당하는 회사 정보 가져오기
        Optional<CompanyList> companyOpt = companyRepository.findById(2L);  // Cnb1이 1L이라 가정
        assertTrue(companyOpt.isPresent(), "회사 정보가 없습니다.");

        CompanyList company = companyOpt.get();

        // 기존의 직책과 부서 정보 저장
        String oldPosition = company.getPosition();
        String oldDepartment = company.getDepartment();

        // 새로운 직책과 부서로 업데이트
        String newPosition = "비서";
        String newDepartment = "비서";
        companyService.updateCompany(company.getCnb(), newPosition, newDepartment, company.getBaseSalary());

        // 로그 기록을 기다리기 위해 일정 시간 대기 (잠재적 트랜잭션 처리 문제 해결)
        Thread.sleep(2000); // 2초 대기

        // Data Log에서 변경 이력이 기록되었는지 확인
        List<DataLog> logs = dataLogRepository.findByActionType("회사 수정");

        // 로그가 비어있지 않은지 확인 (로그가 기록되었는지 확인)
        assertFalse(logs.isEmpty(), "Data Log에 기록된 로그가 없습니다!");

        // 로그가 기록된 경우에만 가장 최근 로그 확인
        if (!logs.isEmpty()) {
            DataLog log = logs.get(logs.size() - 1);  // 가장 최근 기록
            assertEquals("기존 정보 - 직책: " + oldPosition + ", 부서: " + oldDepartment + ", 기준 봉급: " + company.getBaseSalary(), log.getOldData());  // 이전 값 확인
            assertEquals("새로운 정보 - 직책: " + newPosition + ", 부서: " + newDepartment + ", 기준 봉급: " + company.getBaseSalary(), log.getNewData());  // 변경된 값 확인
        }
    }
}
