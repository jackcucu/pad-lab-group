package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Episode;
import md.pad.model.db.Season;
import md.pad.service.EpisodeService;
import md.pad.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/serial/{serialId}/season/{seasonId}/episode")
public class EpisodeController
{
    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private SeasonService seasonService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Episode> get(@PathVariable final Integer serialId,
                                       @PathVariable final Integer seasonId,
                                       @PathVariable final Integer id) throws SerialException
    {
        return episodeService.getEpisodeForSeason(seasonId, id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ResponseEntity<Page<Episode>> getAll(@PathVariable final Integer serialId,
                                                @PathVariable final Integer seasonId,
                                                @RequestParam(required = false) final String search,
                                                @PageableDefault final Pageable page)
    {
        final Page<Episode> all = episodeService.getEpisodesForSeason(seasonId, search, page);

        return ResponseEntity.ok(all);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Episode> addEpisode(@PathVariable final Integer serialId,
                                              @PathVariable final Integer seasonId,
                                              @RequestBody @Validated final Episode episode) throws Exception
    {
        final Season season = seasonService.getSeasonForSerial(serialId, seasonId)
                .orElseThrow(() -> new SerialException("Serial or Season Not Found"));

        episode.setSeason(season);

        episodeService.add(episode);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, seasonId, episode.getId())
                .toUri();

        return ResponseEntity.created(uri).body(episode);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer seasonId,
                                    @PathVariable final Integer id) throws SerialException
    {
        return episodeService.getEpisodeForSeason(seasonId, id)
                .map(it -> {
                    episodeService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new SerialException("Episode not found"));
    }
}
