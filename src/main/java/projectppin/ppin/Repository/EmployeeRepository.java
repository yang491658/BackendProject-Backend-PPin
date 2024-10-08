package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectppin.ppin.domain.EmployeeList;

public interface EmployeeRepository extends JpaRepository<EmployeeList, Long> {
    boolean existsByEmpID(String empID);
}
