package projectppin.ppin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
// 어노테이션 추가
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private String userId;
    private String actionType;
    private String entityType;
    private int entityId;

    @Column(columnDefinition = "TEXT")
    private String oldData;

    @Column(columnDefinition = "TEXT")
    private String newData;

    private LocalDateTime timestamp;
    private String comments;
}
