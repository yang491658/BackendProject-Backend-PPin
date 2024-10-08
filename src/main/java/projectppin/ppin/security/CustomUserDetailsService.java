package projectppin.ppin.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.domain.CompanyRole;
import projectppin.ppin.domain.EmployeeList;
import projectppin.ppin.DTO.EmployeeDTO;
import projectppin.ppin.Repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * CustomUserDetailsService
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final EmployeeRepository employeeRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        log.info("----------------loadUserByUsername-----------------------------");
//
//        // DB에서 사원 정보와 연관된 부서, 직급, 권한 정보를 가져옴
//        EmployeeList employee = employeeRepository.findByEmpID(empID);
//
//        if(employee == null){
//            throw new UsernameNotFoundException("Employee not found");
//        }
//
//        // Employee의 부서와 직급에 따른 권한을 설정
//        EmployeeDTO employeeDTO = new EmployeeDTO(
//                employee.getEmpID(),  // 사원 이메일 (로그인 ID)
//                employee.getEmpPw(),  // 사원 비밀번호
//                employee.getName(),  // 사원이름
//                employee.getCompany().getDepartment(),  // 부서 정보
//                employee.getCompany().getPosition(),  // 직급 정보
//                employee.getCompany().getCompanyRole().name()  // 부서와 직급에 따른 권한
//        );
//
//        log.info(employeeDTO);
//
//        return employeeDTO;
//    }
//}
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String empID) throws UsernameNotFoundException {
        // 1. EmployeeList에서 empID로 사용자 조회
        EmployeeList employee = employeeRepository.findByEmpID(empID)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with empID: " + empID));
        log.info("employee : " + employee);

        // 2. 해당 직원이 속한 회사의 권한(roles) 리스트 조회
        CompanyList company = employee.getCompany();
        List<CompanyRole> companyRoles = company.getCompanyRoleList();

        // 3. 권한 리스트를 String으로 변환 (roleNames)
        List<String> roleNames = companyRoles.stream()
                .map(CompanyRole::name)  // Enum에서 이름만 추출
                .collect(Collectors.toList());

        // 4. EmployeeDTO 생성: 로그인 정보와 권한을 가진 UserDetails로 사용
        EmployeeDTO employeeDTO = new EmployeeDTO(
                employee.getEnb(),
                employee.getEmpID(),      // 사용자 아이디 (empID)
                employee.getEmpPw(),      // 비밀번호 (Spring Security가 자동으로 비교)
                employee.getName(),       // 사용자 이름
                employee.getResiNum(),    // 주민번호
                employee.getPhoneNum(),   // 전화번호
                employee.getEmail(),      // 이메일
                employee.getLoginErrCount(), // 로그인 오류 횟수
                employee.isDel_flag(),
                company.getCnb(),         // 소속 회사 ID
                roleNames                 // 권한 리스트
        );

        // 5. EmployeeDTO를 UserDetails로 반환 (EmployeeDTO는 User를 상속받았으므로 그대로 반환 가능)
        return employeeDTO;
    }
}