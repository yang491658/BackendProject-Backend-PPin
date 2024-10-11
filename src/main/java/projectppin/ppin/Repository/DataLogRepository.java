package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectppin.ppin.domain.DataLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataLogRepository extends JpaRepository<DataLog, Long> {
    List<DataLog> findByActionType(String 추가_수정);

    List<DataLog> findByActionTypeIn(List<String> 출근);

    List<DataLog> findByEntityType(String entityType);

    int countByActionType(String 문서_생성);

    Optional<DataLog> findByActionTypeAndEntityId(String 문서_생성, int entityId);
}
