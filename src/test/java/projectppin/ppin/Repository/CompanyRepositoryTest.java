package projectppin.ppin.Repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectppin.ppin.domain.CompanyList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

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
}
