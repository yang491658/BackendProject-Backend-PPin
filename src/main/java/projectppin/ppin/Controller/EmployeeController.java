package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectppin.ppin.DTO.EmployeeDTO;
import projectppin.ppin.Service.EmployeeService;
import projectppin.ppin.domain.CompanyList;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 사원 추가 및 수정 API
    @PostMapping("/save")
    public ResponseEntity<EmployeeDTO> saveOrUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.saveOrUpdateEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    // 특정 사원 조회 API
    @GetMapping("/{enb}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEnb(@PathVariable Long enb) {
        EmployeeDTO employeeDTO = employeeService.findEmployeeByEnb(enb);
        return (employeeDTO != null) ? ResponseEntity.ok(employeeDTO) : ResponseEntity.notFound().build();
    }

    // 모든 사원 조회 API
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
    @GetMapping("/companies")
    public ResponseEntity<List<CompanyList>> getAllCompanies() {
        List<CompanyList> companies = employeeService.findAllCompanies();
        return ResponseEntity.ok(companies);
    }
}
