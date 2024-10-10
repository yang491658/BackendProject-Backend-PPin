package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectppin.ppin.DTO.CompanyDTO;
import projectppin.ppin.DTO.EmployeeDTO;
import projectppin.ppin.Service.EmployeeService;
import projectppin.ppin.domain.CompanyList;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 사원 추가 API
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.addEmployee(employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    // 사원 수정 API
    @PutMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

//    // 특정 사원 조회 API
//    @GetMapping("/{enb}")
//    public ResponseEntity<EmployeeDTO> getEmployeeByEnb(@PathVariable Long enb) {
//        EmployeeDTO employeeDTO = employeeService.findEmployeeByEnb(enb);
//        return (employeeDTO != null) ? ResponseEntity.ok(employeeDTO) : ResponseEntity.notFound().build();
//    }

    // 모든 사원 조회 API
    // 2024 지금 react에서 쓰고 있는 로직
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // 사원 삭제 API
    @DeleteMapping("/{enb}/delete")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long enb) {
        boolean isDeleted = employeeService.deleteEmployee(enb);
        return (isDeleted) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // 모든 회사 정보 조회 API (회사 선택 시)
    // 2024 지금 react에서 쓰고 있는 로직
    @GetMapping("/companies")
    public ResponseEntity<List<CompanyList>> getAllCompanies() {
        List<CompanyList> companies = employeeService.findAllCompanies();
        return ResponseEntity.ok(companies);
    }

    //20241010부터 최진석이 만들고 있는 코드

    // 2024 지금 react에서 쓰고 있는 로직
    // 해당 사원에 대한 정보만 employeeList에서 뽑아와주기
    @GetMapping("/{empID}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable("empID") String empID) {
        EmployeeDTO employee = employeeService.findEmployeeByEnb(empID);

        if (employee == null) {
            // 데이터가 없을 때 404 Not Found를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(employee);
    }

}
