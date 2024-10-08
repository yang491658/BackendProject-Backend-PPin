package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectppin.ppin.domain.CompanyList;

public interface CompanyRepository extends JpaRepository<CompanyList, Long> {
}


