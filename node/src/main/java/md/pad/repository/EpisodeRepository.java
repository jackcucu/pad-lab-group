package md.pad.repository;

import md.pad.model.db.Episode;
import md.pad.model.db.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer>, JpaSpecificationExecutor<Episode>
{
    Optional<Episode> findBySeason_Serial_IdAndSeason_IdAndId(Integer serialId, Integer seasonId, Integer id);

    Page<Episode> findAllBySeason_Serial_IdAndSeason_Id(Integer serialId, Integer seasonId, Pageable pageable);
}
