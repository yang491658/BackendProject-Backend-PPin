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
    private Long Enb;

    private Long EmpID;
    private String EmpPw;
    private String Name;
    @Pattern(regexp = "^\\d{6}-\\d{7}$",
            message = "주민등록번호를 똑바로 입력하세요.(XXXXXX-XXXXXXX")
    private String ResiNum;
    private String PhoneNum;
    @Email(message = "이메일을 똑바로 입력하세요.")
    private String Email;
    private int LoginErrCount;
    private boolean del_flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyList company;
}

