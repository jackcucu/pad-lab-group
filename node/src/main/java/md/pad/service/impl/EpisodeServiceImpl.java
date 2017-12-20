package md.pad.service.impl;

import md.pad.model.db.Episode;
import md.pad.repository.EpisodeRepository;
import md.pad.service.EpisodeService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class EpisodeServiceImpl extends EntityServiceImpl<Episode, EpisodeRepository>
        implements EpisodeService
{
    @Override
    public Optional<Episode> getEpisodeForSeason(final Integer serialId, final Integer seasonId, final Integer episodeId)
    {
        return repository.findBySeason_Serial_IdAndSeason_IdAndId(serialId, seasonId, episodeId);
    }

    @Override
    public Page<Episode> getEpisodesForSeason(final Integer serialId, final Integer seasonId, final String query, final Pageable pageable)
    {
        final long count = getAll().stream().filter(it -> it.getSeason().getId().equals(seasonId)).count();

        final List<Episode> collect = getAll(query).stream()
                .filter(it -> it.getSeason().getSerial().getId().equals(serialId))
                .filter(it -> it.getSeason().getId().equals(seasonId))
                .collect(toList());

        return new PageImpl<>(collect, pageable, count);
    }
}
