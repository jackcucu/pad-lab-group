package md.pad.service;

import md.pad.model.db.Episode;
import md.pad.service.abs.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EpisodeService extends EntityService<Episode>
{
    Optional<Episode> getEpisodeForSeason(Integer serialId, Integer seasonId, Integer episodeId);

    Page<Episode> getEpisodesForSeason(Integer serialId, Integer seasonId, String query, Pageable pageable);
}
