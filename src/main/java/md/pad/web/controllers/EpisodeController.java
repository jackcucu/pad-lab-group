package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Episode;
import md.pad.resouce.EpisodeResource;
import md.pad.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

import static org.springframework.hateoas.PagedResources.PageMetadata;

@RestController
@RequestMapping("/api/serial/{serialId}/season/{seasonId}/episode")
public class EpisodeController
{
    @Autowired
    private EpisodeService episodeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EpisodeResource> get(@PathVariable final Integer serialId,
                                               @PathVariable final Integer seasonId,
                                               @PathVariable final Integer id) throws SerialException
    {
        return episodeService.getEpisodeForSeason(seasonId, id)
                .map(it -> ResponseEntity.ok(new EpisodeResource(it)))
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ResponseEntity<PagedResources<EpisodeResource>> getAll(@PathVariable final Integer serialId,
                                                                  @PathVariable final Integer seasonId,
                                                                  @RequestParam(required = false) final String search,
                                                                  @PageableDefault final Pageable page)
    {
        final Page<Episode> all = episodeService.getEpisodesForSeason(seasonId, search, page);

        final List<EpisodeResource> serials = all
                .map(EpisodeResource::new)
                .getContent();

        final PagedResources<EpisodeResource> resources = new PagedResources<>(serials,
                new PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<EpisodeResource> addSerial(@PathVariable final Integer serialId,
                                                     @PathVariable final Integer seasonId,
                                                     @RequestBody final Episode episode)
    {
        episodeService.add(episode);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(episode.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new EpisodeResource(episode));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer seasonId,
                                    @PathVariable final Integer episodeId) throws SerialException
    {
        return episodeService.getEpisodeForSeason(seasonId, episodeId)
                .map(it -> {
                    episodeService.delete(episodeId);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new SerialException("Episode not found"));
    }
}
