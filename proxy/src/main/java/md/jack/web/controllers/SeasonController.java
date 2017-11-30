package md.jack.web.controllers;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.dto.SeasonDto;
import md.jack.resouce.SeasonResource;
import md.jack.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/serial/{serialId}/season")
public class SeasonController extends AbstractController
{
    @Autowired
    private SeasonService seasonService;

    @GetMapping(value = "/{id}")
    public SeasonResource get(@PathVariable final Integer serialId,
                              @PathVariable final Integer id) throws GenericException
    {
        final SeasonDto season = seasonService.getById(serialId, id);

        return new SeasonResource(season);
    }


    @GetMapping
    public PagedResources<SeasonResource> getAll(@PathVariable final Integer serialId,
                                                 @RequestParam(required = false) final String search,
                                                 @PageableDefault final Pageable page) throws GenericException
    {
        final Dto response = seasonService.getSeasons(serialId, search, page);

        final List<SeasonDto> list = response.getSeasons();

        final Page<SeasonDto> all = new PageImpl<>(list, page, response.getTotalElements());

        final List<SeasonResource> seasons = all
                .map(SeasonResource::new)
                .getContent();

        return new PagedResources<>(seasons,
                new PagedResources.PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()));
    }

    @PutMapping
    public ResponseEntity<SeasonResource> addSeason(@PathVariable final Integer serialId,
                                                    @RequestBody @Validated final SeasonDto season) throws GenericException
    {
        final SeasonDto seasonDto = seasonService.addSeason(serialId, season);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, seasonDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new SeasonResource(seasonDto));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<SeasonResource> updateSeason(@PathVariable final Integer serialId,
                                                       @PathVariable final Integer id,
                                                       @RequestBody @Validated final SeasonDto season) throws GenericException
    {
        final SeasonDto seasonDto = seasonService.updateSeason(serialId, id, season);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, seasonDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new SeasonResource(seasonDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer id) throws GenericException
    {
        seasonService.deleteSeason(serialId, id);

        return ResponseEntity.noContent().build();
    }
}
