package projectppin.ppin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectppin.ppin.DTO.CompanyDTO;
import projectppin.ppin.Service.CompanyService;
import projectppin.ppin.domain.CompanyList;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    // 회사 정보 생성
    @PostMapping("/create")
    public ResponseEntity<CompanyList> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyList createdCompany = companyService.createCompany(
                companyDTO.getPosition(),
                companyDTO.getDepartment(),
                companyDTO.getBaseSalary()
        );
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // 회사 정보 수정
    @PutMapping("/update/{cnb}")
    public ResponseEntity<CompanyList> updateCompany(
            @PathVariable Long cnb,
            @RequestBody CompanyDTO companyDTO) {
        CompanyList updatedCompany = companyService.updateCompany(
                cnb,
                companyDTO.getPosition(),
                companyDTO.getDepartment(),
                companyDTO.getBaseSalary()
        );
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    // 회사 정보 삭제
    @DeleteMapping("/delete/{cnb}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long cnb) {
        companyService.deleteCompany(cnb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
