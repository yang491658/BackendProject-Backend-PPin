package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.Query;
import projectppin.ppin.domain.EmployeeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeList, Long> {
    boolean existsByEmpID(String empID);
    Optional<EmployeeList> findByEmpID(String empID);

    @Query("SELECT e.enb FROM EmployeeList e")
    List<String> findAllEnbValues();
}
