package md.pad.service.impl;

import md.pad.model.db.Season;
import md.pad.repository.SeasonRepository;
import md.pad.service.SeasonService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SeasonServiceImpl extends EntityServiceImpl<Season, SeasonRepository>
        implements SeasonService
{
    @Override
    public Optional<Season> getSeasonForSerial(final Integer serialId, final Integer seasonId)
    {
        return repository.findBySerial_IdAndId(serialId, seasonId);
    }

    @Override
    public Page<Season> getSeasonsForSerial(final Integer serialId, final String query, final Pageable pageable)
    {
        return repository.findAllBySerial_Id(serialId, pageable);

//        final long count = getAll().stream().filter(it -> it.getSerial().getId().equals(serialId)).count();
//
//        final List<Season> collect = getAll(query).stream()
//                .filter(it -> it.getSerial().getId().equals(serialId))
//                .collect(toList());
//
//        return new PageImpl<>(collect, pageable, count);
    }
}
