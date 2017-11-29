package md.jack.service.impl;

import md.jack.GenericException;
import md.jack.accessor.EpisodeAccessor;
import md.jack.dto.Dto;
import md.jack.dto.EpisodeDto;
import md.jack.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static md.jack.util.ResponseUtil.getResponse;

@Service
public class EpisodeServiceImpl implements EpisodeService
{
    @Autowired
    private EpisodeAccessor episodeAccessor;

    @Cacheable(value = "episode", key = "#serialId + #seasonId + #id")
    @Override
    public EpisodeDto getById(final Integer serialId, final Integer seasonId, final Integer id) throws GenericException
    {
        final Dto response = getResponse(episodeAccessor.get(serialId, seasonId, id));

        return getEpisodeDto(response);
    }


    @Cacheable(value = "episodes", key = "#serialId + #seasonId + #pageable.pageNumber + #pageable.pageSize + #search")
    @Override
    public Dto getEpisodes(final Integer serialId, final Integer seasonId, final String search, final Pageable pageable) throws GenericException
    {
        return getResponse(episodeAccessor.getAll(
                serialId,
                seasonId,
                search,
                pageable.getPageSize(),
                pageable.getPageNumber()));
    }

    @CachePut(value = "episode", key = "#serialId + #seasonId + #episodeDto.id")
    @Override
    public EpisodeDto addEpisode(final Integer serialId, final Integer seasonId, final EpisodeDto episodeDto) throws GenericException
    {
        final Dto response = getResponse(episodeAccessor.addEpisode(serialId, seasonId, episodeDto));

        return getEpisodeDto(response);
    }

    @CacheEvict(value = "episode", key = "#serialId + #seasonId + #id")
    @Override
    public void deleteEpisode(final Integer serialId, final Integer seasonId, final Integer id) throws GenericException
    {
        getResponse(episodeAccessor.delete(serialId, seasonId, id));
    }

    private EpisodeDto getEpisodeDto(final Dto response)
    {
        final EpisodeDto episodeDto = new EpisodeDto();

        episodeDto.setDescription(response.getDescription());
        episodeDto.setId(response.getId());
        episodeDto.setName(response.getName());
        episodeDto.setOrd(response.getOrd());
        episodeDto.setSeason(response.getSeason());
        return episodeDto;
    }
}
