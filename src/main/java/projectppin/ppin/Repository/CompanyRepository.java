package projectppin.ppin.Repository;

import projectppin.ppin.domain.CompanyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyList, Long> {
}


