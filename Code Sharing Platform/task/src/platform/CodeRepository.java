package platform;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends CrudRepository<CodeEntity, Long> {
    public Optional<CodeEntity> findById(String id);
    public List<CodeEntity> findAll();
    public List<CodeEntity> findAllByRestrictedViewsAndTime(boolean isRestricted, long time);
}

