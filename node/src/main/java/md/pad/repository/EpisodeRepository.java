package md.pad.repository;

import md.pad.model.db.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer>, JpaSpecificationExecutor<Episode>
{
    Optional<Episode> findBySeason_IdAndId(Integer seasonId, Integer id);
}
