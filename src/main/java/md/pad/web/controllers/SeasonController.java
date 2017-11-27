package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Season;
import md.pad.model.db.Serial;
import md.pad.resouce.SeasonResource;
import md.pad.service.SeasonService;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
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
import java.util.List;

@RestController
@RequestMapping("api/serial/{serialId}/season")
public class SeasonController
{
    @Autowired
    private SeasonService seasonService;

    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> get(@PathVariable final Integer serialId,
                                              @PathVariable final Integer id) throws SerialException
    {
        return seasonService.getSeasonForSerial(serialId, id)
                .map(it -> ResponseEntity.ok(new SeasonResource(it)))
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ResponseEntity<PagedResources<SeasonResource>> getAll(@PathVariable final Integer serialId,
                                                                 @RequestParam(required = false) final String search,
                                                                 @PageableDefault final Pageable page)
    {
        final Page<Season> all = seasonService.getAll(search, page);

        final List<SeasonResource> seasons = all
                .map(SeasonResource::new)
                .getContent();

        final PagedResources<SeasonResource> resources = new PagedResources<>(seasons,
                new PagedResources.PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<SeasonResource> addSerial(@PathVariable final Integer serialId,
                                                    @RequestBody @Validated final Season season) throws Exception
    {
        final Serial serial = serialService.getById(serialId)
                .orElseThrow(() -> new SerialException("Serial Not Found"));

        season.setSerial(serial);

        seasonService.add(season);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, season.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new SeasonResource(season));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer seasonId) throws SerialException
    {
        return seasonService.getSeasonForSerial(serialId, seasonId)
                .map(it -> {
                    seasonService.delete(seasonId);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new SerialException("Season not found"));
    }
}
