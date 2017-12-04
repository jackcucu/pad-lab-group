package md.jack.service.impl;

import md.jack.GenericException;
import md.jack.accessor.SeasonAccessor;
import md.jack.dto.Dto;
import md.jack.dto.SeasonDto;
import md.jack.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static md.jack.util.ResponseUtil.getResponse;

@Service
public class SeasonServiceImpl implements SeasonService
{
    @Autowired
    private SeasonAccessor seasonAccessor;

    @Cacheable(value = "season", key = "#serialId + #id")
    @Override
    public SeasonDto getById(final Integer serialId, final Integer id) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.get(serialId, id));

        return getSeasonDto(response);
    }

    @Override
    public Dto getSeasons(final Integer serialId, final String search, final Pageable pageable) throws GenericException
    {
        return getResponse(seasonAccessor.getAll(
                serialId,
                search,
                pageable.getPageSize(),
                pageable.getPageNumber()));
    }

    @Override
    public SeasonDto addSeason(final Integer serialId, final SeasonDto seasonDto) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.addSeason(serialId, seasonDto));

        return getSeasonDto(response);
    }

    @Override
    @CachePut(value = "season", key = "#serialId + #id")
    public SeasonDto updateSeason(final Integer serialId, final Integer id, final SeasonDto seasonDto) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.updateSeason(serialId, id, seasonDto));

        return getSeasonDto(response);
    }

    @CacheEvict(value = "season", key = "#serialId + #id")
    @Override
    public void deleteSeason(final Integer serialId, final Integer id) throws GenericException
    {
        getResponse(seasonAccessor.delete(serialId, id));
    }

    private SeasonDto getSeasonDto(final Dto response)
    {
        final SeasonDto season = new SeasonDto();
        season.setDescription(response.getDescription());
        season.setId(response.getId());
        season.setReleaseDate(response.getReleaseDate());
        season.setSeasonNumber(response.getSeasonNumber());
        season.setSerial(response.getSerial());
        return season;
    }
}
