package projectppin.ppin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CompanyList")
public class CompanyList {

    @Id
    private Long Cnb;

    private String Position;
    private String Department;
    private Long BaseSalary;
}
