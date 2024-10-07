package projectppin.ppin.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.DTO.EmployeeDTO;
import projectppin.ppin.Repository.CompanyRepository;
import projectppin.ppin.Repository.EmployeeRepository;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.domain.EmployeeList;
import projectppin.ppin.util.EmployeeIDGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DataLogService dataLogService; // 로그 기록을 위한 서비스 주입

//    // 사원 추가 및 수정
//    public EmployeeDTO saveOrUpdateEmployee(EmployeeDTO employeeDTO) {
//
//        // 고유번호의 중복 여부 확인
//        boolean uniqueCodeExists = employeeRepository.existsByEmpID(employeeDTO.getEmpID());
//
//        if (uniqueCodeExists) {
//            throw new IllegalArgumentException("고유번호가 이미 존재합니다.");
//        }
//
//        EmployeeList employee = new EmployeeList();
//
//        // 먼저 사원을 저장하여 자동 증가된 ID를 얻음
//        EmployeeList savedEmployee = employeeRepository.save(employee);
//
//        // 자동 증가된 ID를 기반으로 사원 ID 생성
//        String generatedEmpID = EmployeeIDGenerator.generateEmployeeID(savedEmployee.getEnb(), employeeDTO.getCustomFourDigits());
//        savedEmployee.setEmpID(generatedEmpID);  // 생성된 사원 ID를 EmpID에 저장
//
//        // 비밀번호 설정: 비밀번호가 없으면 주민등록번호 앞 6자리로 설정
//        if (employeeDTO.getEmpPw() == null || employeeDTO.getEmpPw().isEmpty()) {
//            String resiNum = employeeDTO.getResiNum();
//            if (resiNum != null && resiNum.length() >= 6) {
//                // 주민등록번호 앞 6자리로 비밀번호 설정
//                savedEmployee.setEmpPw(resiNum.substring(0, 6));
//            } else {
//                throw new IllegalArgumentException("주민등록번호 형식이 잘못되었습니다.");
//            }
//        } else {
//            // 사용자가 입력한 비밀번호로 설정
//            savedEmployee.setEmpPw(employeeDTO.getEmpPw());
//        }
//
//        // 나머지 정보 설정
//        savedEmployee.setName(employeeDTO.getName());
//        savedEmployee.setResiNum(employeeDTO.getResiNum());
//        savedEmployee.setPhoneNum(employeeDTO.getPhoneNum());
//        savedEmployee.setEmail(employeeDTO.getEmail());
//        savedEmployee.setLoginErrCount(employeeDTO.getLoginErrCount());
//
//        // 회사 정보 설정
//        if (employeeDTO.getCompanyId() != null) {
//            CompanyList company = companyRepository.findById(employeeDTO.getCompanyId()).orElse(null);
//            if (company != null) {
//                savedEmployee.setCompany(company);
//            }
//        }
//
//        // 사원 정보를 다시 저장
//        EmployeeList updatedEmployee = employeeRepository.save(savedEmployee);
//
//        // 로그 기록 - 사원 추가 또는 수정
//        String action = (employeeDTO.getEnb() == null) ? "사원 생성" : "사원 수정";
//        String newData = "사원 ID: " + updatedEmployee.getEmpID() + ", 이름: " + updatedEmployee.getName() +
//                ", 전화번호: " + updatedEmployee.getPhoneNum() + ", 이메일: " + updatedEmployee.getEmail();
//
//        // DataLogDTO 객체 생성
//        DataLogDTO dataLogDTO = new DataLogDTO();
//        dataLogDTO.setActionType(action);
//        dataLogDTO.setEntityType("사원");
//        dataLogDTO.setEntityId(updatedEmployee.getEnb().intValue());
//        dataLogDTO.setNewData(newData);
//        dataLogDTO.setComments(action + "이 완료되었습니다.");
//
//        // 로그 기록
//        dataLogService.logAction(dataLogDTO, "admin");
//
//        return convertToDTO(updatedEmployee);
//    }
//
//    // 엔티티를 DTO로 변환
//    public EmployeeDTO convertToDTO(EmployeeList employee) {
//        return new EmployeeDTO(
//                employee.getEnb(),
//                employee.getEmpID(),
//                employee.getEmpPw(),
//                employee.getName(),
//                employee.getResiNum(),
//                employee.getPhoneNum(),
//                employee.getEmail(),
//                employee.getLoginErrCount(),
//                employee.isDel_flag(),
//                (employee.getCompany() != null) ? employee.getCompany().getCnb() : null,  // 회사 ID 반환
//                null  // 4자리 숫자는 반환할 필요가 없음
//        );
//    }
//
//    // 특정 사원 조회
//    public EmployeeDTO findEmployeeByEnb(Long enb) {
//        EmployeeList employee = employeeRepository.findById(enb).orElse(null);
//        return (employee != null) ? convertToDTO(employee) : null;
//    }
//
//    // 모든 사원 조회
//    public List<EmployeeDTO> findAllEmployees() {
//        List<EmployeeList> employees = employeeRepository.findAll();
//        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    // 사원 삭제
//    public boolean deleteEmployee(Long enb) {
//        Optional<EmployeeList> employeeOpt = employeeRepository.findById(enb);
//        if (employeeOpt.isPresent()) {
//            EmployeeList employee = employeeOpt.get();
//
//            // 삭제 전 정보 기록
//            String oldData = "삭제된 사원 정보 - 사원 ID: " + employee.getEmpID() + ", 이름: " + employee.getName();
//
//            // DataLogDTO 객체 생성
//            DataLogDTO dataLogDTO = new DataLogDTO();
//            dataLogDTO.setActionType("사원 삭제");
//            dataLogDTO.setEntityType("사원");
//            dataLogDTO.setEntityId(employee.getEnb().intValue());
//            dataLogDTO.setOldData(oldData);
//            dataLogDTO.setComments("사원 정보 삭제");
//
//            // 로그 기록
//            dataLogService.logAction(dataLogDTO, "admin");
//
//            // 사원 삭제
//            employeeRepository.delete(employee);
//            return true;
//        } else {
//            throw new EntityNotFoundException("사원을 찾을 수 없습니다.");
//        }
//    }
//
//    // 모든 회사 조회
//    public List<CompanyList> findAllCompanies() {
//        return companyRepository.findAll();
//    }

    // 사원 추가
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        // 고유번호 중복 여부 확인
        boolean uniqueCodeExists = employeeRepository.existsByEmpID(employeeDTO.getEmpID());

        if (uniqueCodeExists) {
            throw new IllegalArgumentException("고유번호가 이미 존재합니다.");
        }

        EmployeeList newEmployee = new EmployeeList();
        EmployeeList savedEmployee = employeeRepository.save(newEmployee);

        // 자동 증가된 ID를 기반으로 사원 ID 생성
        String generatedEmpID = EmployeeIDGenerator.generateEmployeeID(savedEmployee.getEnb(), employeeDTO.getCustomFourDigits());
        savedEmployee.setEmpID(generatedEmpID);

        // 비밀번호 설정 (주민등록번호 앞 6자리)
        setPasswordForEmployee(savedEmployee, employeeDTO);

        // 나머지 정보 설정
        updateEmployeeDetails(savedEmployee, employeeDTO);

        // 사원 정보 저장
        EmployeeList updatedEmployee = employeeRepository.save(savedEmployee);

        // 로그 기록
        logData("사원 생성", updatedEmployee);

        return convertToDTO(updatedEmployee);
    }

    // 사원 수정
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        EmployeeList existingEmployee = employeeRepository.findById(employeeDTO.getEnb())
                .orElseThrow(() -> new IllegalArgumentException("해당 사원을 찾을 수 없습니다."));

        // 사원 정보 업데이트
        if (employeeDTO.getEmpPw() != null && !employeeDTO.getEmpPw().isEmpty()) {
            existingEmployee.setEmpPw(employeeDTO.getEmpPw());
        }

        updateEmployeeDetails(existingEmployee, employeeDTO);
        EmployeeList updatedEmployee = employeeRepository.save(existingEmployee);

        // 로그 기록
        logData("사원 수정", updatedEmployee);

        return convertToDTO(updatedEmployee);
    }

    // 공통: 사원 정보 업데이트
    private void updateEmployeeDetails(EmployeeList employee, EmployeeDTO employeeDTO) {
        employee.setName(employeeDTO.getName());
        employee.setResiNum(employeeDTO.getResiNum());
        employee.setPhoneNum(employeeDTO.getPhoneNum());
        employee.setEmail(employeeDTO.getEmail());
        employee.setLoginErrCount(employeeDTO.getLoginErrCount());

        if (employeeDTO.getCompanyId() != null) {
            CompanyList company = companyRepository.findById(employeeDTO.getCompanyId()).orElse(null);
            employee.setCompany(company);
        }
    }

    // 공통: 비밀번호 설정 로직
    private void setPasswordForEmployee(EmployeeList employee, EmployeeDTO employeeDTO) {
        if (employeeDTO.getEmpPw() == null || employeeDTO.getEmpPw().isEmpty()) {
            String resiNum = employeeDTO.getResiNum();
            if (resiNum != null && resiNum.length() >= 6) {
                employee.setEmpPw(resiNum.substring(0, 6));
            } else {
                throw new IllegalArgumentException("주민등록번호 형식이 잘못되었습니다.");
            }
        } else {
            employee.setEmpPw(employeeDTO.getEmpPw());
        }
    }

    // 공통: 로그 기록
    private void logData(String action, EmployeeList employee) {
        String newData = "사원 ID: " + employee.getEmpID() + ", 이름: " + employee.getName() +
                ", 전화번호: " + employee.getPhoneNum() + ", 이메일: " + employee.getEmail();

        DataLogDTO dataLogDTO = new DataLogDTO();
        dataLogDTO.setActionType(action);
        dataLogDTO.setEntityType("사원");
        dataLogDTO.setEntityId(employee.getEnb().intValue());
        dataLogDTO.setNewData(newData);
        dataLogDTO.setComments(action + "이 완료되었습니다.");

        dataLogService.logAction(dataLogDTO, "admin");
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
                (employee.getCompany() != null) ? employee.getCompany().getCnb() : null,
                null  // 4자리 숫자는 반환할 필요 없음
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
        Optional<EmployeeList> employeeOpt = employeeRepository.findById(enb);
        if (employeeOpt.isPresent()) {
            EmployeeList employee = employeeOpt.get();

            // 삭제 전 정보 기록
            String oldData = "삭제된 사원 정보 - 사원 ID: " + employee.getEmpID() + ", 이름: " + employee.getName();
            DataLogDTO dataLogDTO = new DataLogDTO();
            dataLogDTO.setActionType("사원 삭제");
            dataLogDTO.setEntityType("사원");
            dataLogDTO.setEntityId(employee.getEnb().intValue());
            dataLogDTO.setOldData(oldData);
            dataLogDTO.setComments("사원 정보 삭제");

            dataLogService.logAction(dataLogDTO, "admin");

            employeeRepository.delete(employee);
            return true;
        } else {
            throw new EntityNotFoundException("사원을 찾을 수 없습니다.");
        }
    }

    // 모든 회사 조회
    public List<CompanyList> findAllCompanies() {
        return companyRepository.findAll();
    }
}