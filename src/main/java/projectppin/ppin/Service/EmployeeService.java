package projectppin.ppin.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectppin.ppin.DTO.EmployeeDTO;
import projectppin.ppin.Repository.CompanyRepository;
import projectppin.ppin.Repository.EmployeeRepository;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.domain.EmployeeList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // 사원 추가 및 수정
    public EmployeeDTO saveOrUpdateEmployee(EmployeeDTO employeeDTO) {
        EmployeeList employee = new EmployeeList();

        // 먼저 사원을 저장하여 자동 증가된 ID를 얻음
        EmployeeList savedEmployee = employeeRepository.save(employee);

        // 자동 증가된 ID를 기반으로 사원 ID 생성
        String generatedEmpID = generateEmployeeID(savedEmployee.getEnb(), employeeDTO.getCustomFourDigits());
        savedEmployee.setEmpID(Long.parseLong(generatedEmpID));  // 생성된 사원 ID를 EmpID에 저장

        // 나머지 정보 설정
        savedEmployee.setEmpPw(employeeDTO.getEmpPw());
        savedEmployee.setName(employeeDTO.getName());
        savedEmployee.setResiNum(employeeDTO.getResiNum());
        savedEmployee.setPhoneNum(employeeDTO.getPhoneNum());
        savedEmployee.setEmail(employeeDTO.getEmail());
        savedEmployee.setLoginErrCount(employeeDTO.getLoginErrCount());
        savedEmployee.setDel_flag(employeeDTO.isDel_flag());

        // 회사 정보 설정
        CompanyList company = companyRepository.findById(employeeDTO.getCompanyId()).orElse(null);
        savedEmployee.setCompany(company);

        // 최종적으로 사원 정보 저장
        EmployeeList finalSavedEmployee = employeeRepository.save(savedEmployee);

        return convertToDTO(finalSavedEmployee);
    }

    // 사원 ID 생성 함수 (사용자가 입력한 4자리 숫자 사용)
    private String generateEmployeeID(Long enb, String customFourDigits) {
        // 1. 현재 연도 두 자리
        String year = DateTimeFormatter.ofPattern("yy").format(LocalDateTime.now());

        // 2. 현재 월 두 자리
        String month = DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now());

        // 3. 고정된 4자리 숫자 (사용자가 입력한 값, 없으면 기본값 "1234")
        String fixedNumber = (customFourDigits != null && !customFourDigits.isEmpty()) ? customFourDigits : "1234";

        // 4. 자동 생성된 사원 ID (enb 값을 숫자로 그대로 사용)
        String idWithoutZeros = enb.toString();  // 자동 증가된 사원 ID

        // 5. 최종 사원 ID 생성: 연도 + 월 + 생성된 ID + 4자리 고정 숫자
        return year + month + idWithoutZeros + fixedNumber;
    }

    // 엔티티를 DTO로 변환
    public EmployeeDTO convertToDTO(EmployeeList employee) {
        return new EmployeeDTO(
                employee.getEnb(),
                employee.getEmpID(),
                employee.getEmpPw(),
                employee.getName(),
                employee.getResiNum(),
                employee.getPhoneNum(),
                employee.getEmail(),
                employee.getLoginErrCount(),
                employee.isDel_flag(),
                (employee.getCompany() != null) ? employee.getCompany().getCnb() : null,  // 회사 ID 반환
                null  // 4자리 숫자는 반환할 필요가 없음
        );
    }

    // 특정 사원 조회
    public EmployeeDTO findEmployeeByEnb(Long enb) {
        EmployeeList employee = employeeRepository.findById(enb).orElse(null);
        return (employee != null) ? convertToDTO(employee) : null;
    }

    // 모든 사원 조회
    public List<EmployeeDTO> findAllEmployees() {
        List<EmployeeList> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 사원 삭제
    public boolean deleteEmployee(Long enb) {
        EmployeeList employee = employeeRepository.findById(enb).orElse(null);

        if (employee != null) {
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }

    public List<CompanyList> findAllCompanies() {
        return companyRepository.findAll();
    }
}