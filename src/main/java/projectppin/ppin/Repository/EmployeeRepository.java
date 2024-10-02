package projectppin.ppin.Repository;

import projectppin.ppin.domain.EmployeeList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeList, Long> {
}
