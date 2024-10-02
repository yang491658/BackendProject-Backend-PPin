package projectppin.ppin.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.domain.EmployeeList;
import projectppin.ppin.util.EmployeeIDGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 데이터베이스 사용
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        // 회사가 이미 데이터베이스에 존재하는지 확인하여 사전 작업을 건너뜀
        Optional<CompanyList> companyOpt = companyRepository.findById(1L);
        assertTrue(companyOpt.isPresent(), "회사 정보가 데이터베이스에 있어야 합니다. (Cnb=1)");
    }

    @Test
    public void Test1() {

        EmployeeList employee = new EmployeeList();
        employee.setName("홍길동");
        employee.setPhoneNum("010-2323-2311");
        employee.setEmail("123123@example.com");

        Long enb = 1L;
        employee.setEnb(enb);

        String customFourDigits = "6789";

        String generatedEmpID = EmployeeIDGenerator.generateEmployeeID(employee.getEnb(), customFourDigits);
        employee.setEmpID(generatedEmpID);

        Long companyId = 1L;
        Optional<CompanyList> companyOpt = companyRepository.findById(companyId);
        assertTrue(companyOpt.isPresent(), "회사 정보가 올바르게 설정되어 있어야 합니다.");
        employee.setCompany(companyOpt.get());

        EmployeeList savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertEquals(generatedEmpID, savedEmployee.getEmpID());
        assertEquals("홍길동", savedEmployee.getName());
        assertEquals("010-2323-2311", savedEmployee.getPhoneNum());
        assertEquals(1L, savedEmployee.getCompany().getCnb());
    }

    @Test
    public void Test2() {

        EmployeeList employee = new EmployeeList();
        employee.setName("이동동");
        employee.setPhoneNum("010-3232-3232");
        employee.setEmail("3332221@example.com");

        Long enb = 2L;
        employee.setEnb(enb);

        String customFourDigits = "9999";

        String generatedEmpID = EmployeeIDGenerator.generateEmployeeID(employee.getEnb(), customFourDigits);
        employee.setEmpID(generatedEmpID);

        Long companyId = 2L;
        Optional<CompanyList> companyOpt = companyRepository.findById(companyId);
        assertTrue(companyOpt.isPresent(), "회사 정보가 올바르게 설정되어 있어야 합니다.");
        employee.setCompany(companyOpt.get());

        EmployeeList savedEmployee = employeeRepository.save(employee);

    }
}