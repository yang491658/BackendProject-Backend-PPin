package projectppin.ppin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectppin.ppin.domain.DataLog;

import java.util.List;

@Repository
public interface DataLogRepository extends JpaRepository<DataLog, Long> {
    List<DataLog> findByActionType(String 추가_수정);
}
