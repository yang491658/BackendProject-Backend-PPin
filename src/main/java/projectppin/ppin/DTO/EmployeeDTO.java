package projectppin.ppin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long enb;
    private Long empID;
    private String empPw;
    private String name;
    private String resiNum;
    private String phoneNum;
    private String email;
    private int loginErrCount;
    private boolean del_flag;

    private Long companyId;

    private String customFourDigits;
}
