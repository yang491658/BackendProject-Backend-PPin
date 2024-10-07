package projectppin.ppin.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Cnb;

    private String position;
    private String department;
    private Long baseSalary;
}
