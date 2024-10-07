package projectppin.ppin.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CompanyRepositoryTest {
    @BeforeEach
    public void setUp() {
        // 임의의 사용자 인증 정보 설정
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DataLogRepository dataLogRepository;

    @Autowired
    private CompanyService companyService;

    @Test
    @DisplayName("회사 저장 테스트")
    public void test1() {
        CompanyList company = new CompanyList();
        company.setCnb(1L);
        company.setPosition("인사기획");
        company.setDepartment("부장");
        company.setBaseSalary(3000000L);

        CompanyList savedCompany = companyRepository.save(company);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getCnb()).isEqualTo(1L);  // Cnb 확인
    }

    @Test
    @DisplayName("회사 저장 테스트2")
    public void test2() {
        CompanyList company = new CompanyList();
        company.setCnb(2L);
        company.setPosition("인사기획");
        company.setDepartment("정사원");
        company.setBaseSalary(2120000L);

        CompanyList savedCompany = companyRepository.save(company);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getCnb()).isEqualTo(2L);  // Cnb 확인
    }

    @Test
    @DisplayName("CompanyList 초기데이터 저장 테스트 ")
    public void Test3() {
        // 1. 인사기획 (HR Planning)
        companyRepository.save(new CompanyList(null, "부서장", "인사기획", 8000000L));
        companyRepository.save(new CompanyList(null, "팀장", "인사기획", 6000000L));
        companyRepository.save(new CompanyList(null, "담당자", "인사기획", 5000000L));
        companyRepository.save(new CompanyList(null, "사원", "인사기획", 4000000L));
        companyRepository.save(new CompanyList(null, "인턴", "인사기획", 2000000L));
        companyRepository.save(new CompanyList(null, "계약직", "인사기획", 3000000L));

        // 2. 인사관리 (HR Management)
        companyRepository.save(new CompanyList(null, "부서장", "인사관리", 8000000L));
        companyRepository.save(new CompanyList(null, "팀장", "인사관리", 6000000L));
        companyRepository.save(new CompanyList(null, "담당자", "인사관리", 5000000L));
        companyRepository.save(new CompanyList(null, "사원", "인사관리", 4000000L));
        companyRepository.save(new CompanyList(null, "인턴", "인사관리", 2000000L));
        companyRepository.save(new CompanyList(null, "계약직", "인사관리", 3000000L));

        // 3. 영업 (Sales)
        companyRepository.save(new CompanyList(null, "부서장", "영업", 9000000L));
        companyRepository.save(new CompanyList(null, "팀장", "영업", 7000000L));
        companyRepository.save(new CompanyList(null, "담당자", "영업", 6000000L));
        companyRepository.save(new CompanyList(null, "사원", "영업", 5000000L));
        companyRepository.save(new CompanyList(null, "인턴", "영업", 2500000L));
        companyRepository.save(new CompanyList(null, "계약직", "영업", 3500000L));

        // 4. 기획 (Planning)
        companyRepository.save(new CompanyList(null, "부서장", "기획", 8500000L));
        companyRepository.save(new CompanyList(null, "팀장", "기획", 6500000L));
        companyRepository.save(new CompanyList(null, "담당자", "기획", 5500000L));
        companyRepository.save(new CompanyList(null, "사원", "기획", 4500000L));
        companyRepository.save(new CompanyList(null, "인턴", "기획", 2200000L));
        companyRepository.save(new CompanyList(null, "계약직", "기획", 3200000L));
    }

    @Test
    @DisplayName("회사 테이블 수정 테스트")
    public void Test4() {
        // Given: Cnb 1과 2의 기존 데이터를 조회
        Optional<CompanyList> company1Opt = companyRepository.findById(1L);
        Optional<CompanyList> company2Opt = companyRepository.findById(2L);

        // 테스트를 위해 데이터가 존재하는지 먼저 확인
        assertTrue(company1Opt.isPresent(), "Cnb 1의 회사 데이터가 존재해야 합니다.");
        assertTrue(company2Opt.isPresent(), "Cnb 2의 회사 데이터가 존재해야 합니다.");

        CompanyList company1 = company1Opt.get();
        CompanyList company2 = company2Opt.get();

        // When: Cnb 1과 2에 대한 수정 작업
        company1.setPosition("대표이사");
        company1.setDepartment("대표");
        company1.setBaseSalary(null);
        companyRepository.save(company1);

        company2.setPosition("비서");
        company2.setDepartment("비서");
        company2.setBaseSalary(3850000L);
        companyRepository.save(company2);

        // Then: 수정된 내용을 확인
        Optional<CompanyList> updatedCompany1 = companyRepository.findById(1L);
        Optional<CompanyList> updatedCompany2 = companyRepository.findById(2L);

        assertTrue(updatedCompany1.isPresent(), "수정된 Cnb 1의 데이터가 존재해야 합니다.");
        assertTrue(updatedCompany2.isPresent(), "수정된 Cnb 2의 데이터가 존재해야 합니다.");

        assertEquals("대표이사", updatedCompany1.get().getPosition());
        assertEquals(null, updatedCompany1.get().getBaseSalary());

        assertEquals("비서", updatedCompany2.get().getPosition());
        assertEquals(3850000L, updatedCompany2.get().getBaseSalary());
    }

    @Test
    @DisplayName("회사 테이블 수정 테스트2")
    public void Test5() {
        // Given: Cnb 1과 2의 기존 데이터를 조회
        Optional<CompanyList> company2Opt = companyRepository.findById(2L);

        // 테스트를 위해 데이터가 존재하는지 먼저 확인
        assertTrue(company2Opt.isPresent(), "Cnb 2의 회사 데이터가 존재해야 합니다.");

        CompanyList company2 = company2Opt.get();

        company2.setPosition("경리");
        company2.setDepartment("경리");
        company2.setBaseSalary(1850000L);
        companyRepository.save(company2);

        // Then: 수정된 내용을 확인
        Optional<CompanyList> updatedCompany2 = companyRepository.findById(2L);

        assertTrue(updatedCompany2.isPresent(), "수정된 Cnb 2의 데이터가 존재해야 합니다.");

        assertEquals("경리", updatedCompany2.get().getPosition());
        assertEquals(1850000L, updatedCompany2.get().getBaseSalary());
    }

    @Test
    @DisplayName("CompanyList에 대한 수정이 DataLog에 기록되는지 확인")
    public void Test6() {
        // Given: Cnb2에 해당하는 기존 데이터를 조회
        Optional<CompanyList> companyOpt = companyRepository.findById(3L);

        // 테스트를 위해 데이터가 존재하는지 먼저 확인
        assertTrue(companyOpt.isPresent(), "Cnb 3의 회사 데이터가 존재해야 합니다.");

        CompanyList company = companyOpt.get();

        // 원래 값을 확인
        assertEquals("부서장", company.getPosition());
        assertEquals(8000000L, company.getBaseSalary());

        // When: CompanyService.updateCompany를 사용하여 직책과 부서, 기준 봉급을 수정
        companyService.updateCompany(company.getCnb(), "부서장", "인사기획", 7400000L);

        // Then: DataLog에서 "회사 수정" 액션 타입이 기록되었는지 확인
        List<DataLog> logs = dataLogRepository.findByActionType("회사 수정");

        // 로그가 비어있지 않은지 확인 (로그가 기록되었는지 확인)
        assertFalse(logs.isEmpty(), "Data Log에 회사 수정 기록이 없습니다.");

        // 가장 최근 로그 확인
        if (!logs.isEmpty()) {
            DataLog log = logs.get(logs.size() - 1);  // 가장 최근 기록
            assertEquals("기존 정보 - 직책: 부서장, 부서: 인사기획, 기준 봉급: 8000000", log.getOldData());  // 이전 값 확인
            assertEquals("새로운 정보 - 직책: 부서장, 부서: 인사기획, 기준 봉급: 7400000", log.getNewData());  // 변경된 값 확인
            assertEquals("회사 정보 수정", log.getComments());  // 코멘트 확인
        }
    }

    @Test
    public void testCompanyUpdateLog() {
        CompanyService companyService = null;
        CompanyList company = companyService.updateCompany(55L, "새 직책", "새 부서", 5000000L);

        List<DataLog> logs = dataLogRepository.findAll();
        assertFalse(logs.isEmpty());

        DataLog log = logs.get(logs.size() - 1);
        assertEquals("회사 수정", log.getActionType());
        assertEquals("user1", log.getUserId());
    }
}
