package md.jack.web.controllers;

import md.jack.GenericException;
import md.jack.accessor.SeasonAccessor;
import md.jack.dto.Dto;
import md.jack.dto.SeasonDto;
import md.jack.resouce.SeasonResource;
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
public class SeasonController extends AbstractController
{
    @Autowired
    private SeasonAccessor seasonAccessor;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SeasonResource> get(@PathVariable final Integer serialId,
                                              @PathVariable final Integer id) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.get(serialId, id));

        final SeasonDto season = getSeasonDto(response);

        return ResponseEntity.ok(new SeasonResource(season));
    }


    @GetMapping
    public ResponseEntity<PagedResources<SeasonResource>> getAll(@PathVariable final Integer serialId,
                                                                 @RequestParam(required = false) final String search,
                                                                 @PageableDefault final Pageable page) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.getAll(
                serialId,
                search,
                page.getPageSize(),
                page.getPageNumber()));

        final List<SeasonDto> list = response.getSeasons();

        final Page<SeasonDto> all = new PageImpl<>(list, page, response.getTotalElements());

        final List<SeasonResource> seasons = all
                .map(SeasonResource::new)
                .getContent();

        final PagedResources<SeasonResource> resources = new PagedResources<>(seasons,
                new PagedResources.PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<SeasonResource> addSeason(@PathVariable final Integer serialId,
                                                    @RequestBody @Validated final SeasonDto season) throws GenericException
    {
        final Dto response = getResponse(seasonAccessor.addSeason(serialId, season));

        final SeasonDto seasonDto = getSeasonDto(response);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, seasonDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new SeasonResource(seasonDto));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer id) throws GenericException
    {
        getResponse(seasonAccessor.delete(serialId, id));

        return ResponseEntity.noContent().build();
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