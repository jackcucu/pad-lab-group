package md.pad.service.impl;

import md.pad.model.db.Series;
import md.pad.repository.SeriesRepository;
import md.pad.service.SeriesService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl extends EntityServiceImpl<Series, SeriesRepository>
        implements SeriesService
{
}
