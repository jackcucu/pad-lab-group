package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import md.pad.model.db.Episode;
import md.pad.model.db.Season;
import md.pad.service.EpisodeService;
import md.pad.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/serial/{serialId}/season/{seasonId}/episode")
public class EpisodeController
{
    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private SeasonService seasonService;

    @GetMapping(value = "/{id}")
    public ApiResponse get(@PathVariable final Integer serialId,
                           @PathVariable final Integer seasonId,
                           @PathVariable final Integer id) throws SerialException
    {
        return episodeService.getEpisodeForSeason(seasonId, id)
                .map(ApiResponse::new)
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ApiResponse getAll(@PathVariable final Integer serialId,
                                @PathVariable final Integer seasonId,
                                @RequestParam(required = false) final String search,
                                @PageableDefault final Pageable page)
    {
        return new ApiResponse(episodeService.getEpisodesForSeason(seasonId, search, page));
    }

    @PostMapping(value = "/add")
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

    @DeleteMapping(value = "/delete")
    public void delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer seasonId,
                                    @PathVariable final Integer id) throws SerialException
    {
        episodeService.getEpisodeForSeason(seasonId, id)
                .orElseThrow(() -> new SerialException("Episode not found"));

        episodeService.delete(id);
    }
}
