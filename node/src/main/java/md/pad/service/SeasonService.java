package md.pad.service;

import md.pad.model.db.Season;
import md.pad.service.abs.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SeasonService extends EntityService<Season>
{
    Optional<Season> getSeasonForSerial(Integer serialId, Integer seasonId);

    Page<Season> getSeasonsForSerial(Integer serialId, String query, Pageable pageable);
}
