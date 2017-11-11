package md.pad.repository;

import md.pad.model.db.Serial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerialRepository extends JpaRepository<Serial, Integer>, JpaSpecificationExecutor<Serial>
{
    Optional<Serial> findByName(String name);

    void deleteByName(String name);
}
