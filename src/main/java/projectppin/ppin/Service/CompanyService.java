package projectppin.ppin.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectppin.ppin.domain.CompanyList;
import projectppin.ppin.Repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // 회사 정보 생성
    public CompanyList createCompany(String position, String department, Long baseSalary) {
        CompanyList company = new CompanyList();
        company.setPosition(position);
        company.setDepartment(department);
        company.setBaseSalary(baseSalary);
        return companyRepository.save(company);
    }

    // 회사 정보 수정
    public CompanyList updateCompany(Long cnb, String position, String department, Long baseSalary) {
        Optional<CompanyList> companyOpt = companyRepository.findById(cnb);
        if (companyOpt.isPresent()) {
            CompanyList company = companyOpt.get();
            company.setPosition(position);
            company.setDepartment(department);
            company.setBaseSalary(baseSalary);
            return companyRepository.save(company);  // 수정된 정보 저장
        } else {
            throw new EntityNotFoundException("정보를 찾을 수 없습니다.");
        }
    }

    // 회사 정보 삭제
    public void deleteCompany(Long cnb) {
        Optional<CompanyList> companyOpt = companyRepository.findById(cnb);
        if (companyOpt.isPresent()) {
            companyRepository.delete(companyOpt.get());
        } else {
            throw new EntityNotFoundException("삭제할 정보를 찾을 수 없습니다.");
        }
    }
}
