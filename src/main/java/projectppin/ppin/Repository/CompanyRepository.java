package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectppin.ppin.domain.CompanyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyList, Long> {
    @EntityGraph(attributePaths = {"companyRoleList"})
    @Query("select c from CompanyList c where c.cnb = :cnb")
    CompanyList getWithRoles(@Param("cnb") Long cnb);

}


