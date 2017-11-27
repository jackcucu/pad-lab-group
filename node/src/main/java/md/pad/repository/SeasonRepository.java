package md.pad.repository;

import md.pad.model.db.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer>, JpaSpecificationExecutor<Season>
{
    Optional<Season> findBySerial_IdAndId(Integer serialId, Integer id);
}
