package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import md.pad.model.api.EpisodeDto;
import md.pad.model.db.Episode;
import md.pad.model.db.Season;
import md.pad.service.EpisodeService;
import md.pad.service.SeasonService;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static md.pad.util.FunctionalUtils.safeSet;

@RestController
@RequestMapping("/api/serials/{serialId}/seasons/{seasonId}/episodes")
public class EpisodeController
{
    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}")
    public ApiResponse get(@PathVariable final Integer serialId,
                           @PathVariable final Integer seasonId,
                           @PathVariable final Integer id) throws SerialException
    {
        serialService.getById(serialId).orElseThrow(() -> new SerialException("Serial not found"));

        seasonService.getSeasonForSerial(serialId, seasonId).orElseThrow(() -> new SerialException("Season not found"));

        return episodeService.getEpisodeForSeason(serialId, seasonId, id)
                .map(ApiResponse::new)
                .orElseThrow(() -> new SerialException("Episode not found"));
    }

    @GetMapping
    public ApiResponse getAll(@PathVariable final Integer serialId,
                              @PathVariable final Integer seasonId,
                              @RequestParam(required = false) final String search,
                              @PageableDefault final Pageable page) throws SerialException
    {
        serialService.getById(serialId).orElseThrow(() -> new SerialException("Serial not found"));

        seasonService.getSeasonForSerial(serialId, seasonId).orElseThrow(() -> new SerialException("Season not found"));

        final Page<Episode> all = episodeService.getEpisodesForSeason(serialId, seasonId, search, page);

        final List<Episode> content = all.getContent();

        final EpisodeDto episodeDto = new EpisodeDto();

        episodeDto.setEpisodes(content);
        episodeDto.setTotalElements(all.getTotalElements());

        return new ApiResponse(episodeDto);
    }

    @PutMapping
    public ApiResponse addEpisode(@PathVariable final Integer serialId,
                                  @PathVariable final Integer seasonId,
                                  @RequestBody @Validated final Episode episode) throws Exception
    {
        final Season season = seasonService.getSeasonForSerial(serialId, seasonId)
                .orElseThrow(() -> new SerialException("Serial or Season Not Found"));

        episode.setSeason(season);

        episodeService.add(episode);

        return new ApiResponse(episode);
    }

    @PutMapping("/{id}")
    public ApiResponse updateEpisode(@PathVariable final Integer serialId,
                                     @PathVariable final Integer seasonId,
                                     @PathVariable final Integer id,
                                     @RequestBody @Validated final Episode episode) throws Exception
    {
        final Season season = seasonService.getSeasonForSerial(serialId, seasonId)
                .orElseThrow(() -> new SerialException("Serial or Season Not Found"));

        final Episode episodeLocal = season.getEpisodes().stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SerialException("Episode Not Found"));

        safeSet(episodeLocal::setDescription, episode, Episode::getDescription);
        safeSet(episodeLocal::setName, episode, Episode::getName);
        safeSet(episodeLocal::setOrd, episode, Episode::getOrd);

        episodeService.edit(episodeLocal);

        return new ApiResponse(episodeLocal);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable final Integer serialId,
                       @PathVariable final Integer seasonId,
                       @PathVariable final Integer id) throws SerialException
    {
        serialService.getById(serialId).orElseThrow(() -> new SerialException("Serial not found"));

        episodeService.getEpisodeForSeason(serialId, seasonId, id)
                .orElseThrow(() -> new SerialException("Episode not found"));

        episodeService.delete(id);
    }
}
