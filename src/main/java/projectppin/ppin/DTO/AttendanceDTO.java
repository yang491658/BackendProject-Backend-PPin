package projectppin.ppin.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDTO {
    private String userId;
    private String entityType;
    private String actionType;
    private String newData;
    private LocalDateTime timestamp;
}
