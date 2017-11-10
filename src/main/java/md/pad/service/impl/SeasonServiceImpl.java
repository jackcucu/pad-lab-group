package md.pad.service.impl;

import md.pad.model.db.Season;
import md.pad.repository.SeasonRepository;
import md.pad.service.SeasonService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SeasonServiceImpl extends EntityServiceImpl<Season, SeasonRepository>
        implements SeasonService
{
}
