package projectppin.ppin.Repository;

import projectppin.ppin.domain.EmployeeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeList, Long> {
    boolean existsByEmpID(String empID);
    Optional<EmployeeList> findByEmpID(String empID);
}
