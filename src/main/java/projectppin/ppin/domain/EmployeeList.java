package projectppin.ppin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EmployeeList")
public class EmployeeList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enb;

    @Column(name = "empID")
    private String empID;
    private String empPw;
    private String name;
    @Pattern(regexp = "^\\d{6}-\\d{7}$",
            message = "주민등록번호를 똑바로 입력하세요.(XXXXXX-XXXXXXX")
    private String resiNum;
    private String phoneNum;
    @Email(message = "이메일을 똑바로 입력하세요.")
    private String email;
    private int loginErrCount;
    private boolean del_flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyList company;
}

