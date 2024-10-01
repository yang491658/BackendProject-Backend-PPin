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
}
