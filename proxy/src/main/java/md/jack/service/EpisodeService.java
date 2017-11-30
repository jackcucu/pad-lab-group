package md.jack.service;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.dto.EpisodeDto;
import org.springframework.data.domain.Pageable;

public interface EpisodeService
{
    EpisodeDto getById(Integer serialId, Integer seasonId, Integer id) throws GenericException;

    Dto getEpisodes(Integer serialId, Integer seasonId, String search, Pageable pageable) throws GenericException;

    EpisodeDto addEpisode(Integer serialId, Integer seasonId, EpisodeDto episodeDto) throws GenericException;

    EpisodeDto updateEpisode(Integer serialId, Integer seasonId, Integer id, EpisodeDto episodeDto) throws GenericException;

    void deleteEpisode(Integer serialId, Integer seasonId, Integer id) throws GenericException;
}
