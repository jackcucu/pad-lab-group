package md.jack.service;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.dto.SeasonDto;
import org.springframework.data.domain.Pageable;

public interface SeasonService
{
    SeasonDto getById(Integer serialId, Integer id) throws GenericException;

    Dto getSeasons(Integer serialId, String search, Pageable pageable) throws GenericException;

    SeasonDto addSeason(Integer serialId, SeasonDto serialDto) throws GenericException;

    void deleteSeason(Integer serialId, Integer id) throws GenericException;
}
