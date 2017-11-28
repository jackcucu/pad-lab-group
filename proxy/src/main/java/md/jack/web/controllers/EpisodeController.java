package md.jack.web.controllers;

import md.jack.GenericException;
import md.jack.accessor.EpisodeAccessor;
import md.jack.dto.Dto;
import md.jack.dto.EpisodeDto;
import md.jack.resouce.EpisodeResource;
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

import static org.springframework.hateoas.PagedResources.PageMetadata;

@RestController
@RequestMapping("/api/serial/{serialId}/season/{seasonId}/episode")
public class EpisodeController extends AbstractController
{
    @Autowired
    private EpisodeAccessor episodeAccessor;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EpisodeResource> get(@PathVariable final Integer serialId,
                                               @PathVariable final Integer seasonId,
                                               @PathVariable final Integer id) throws GenericException
    {
        final Dto response = getResponse(episodeAccessor.get(serialId, seasonId, id));

        final EpisodeDto episodeDto = getEpisodeDto(response);

        return ResponseEntity.ok(new EpisodeResource(episodeDto));
    }

    @GetMapping
    public ResponseEntity<PagedResources<EpisodeResource>> getAll(@PathVariable final Integer serialId,
                                                                  @PathVariable final Integer seasonId,
                                                                  @RequestParam(required = false) final String search,
                                                                  @PageableDefault final Pageable page) throws GenericException
    {
        final Dto response = getResponse(episodeAccessor.getAll(
                serialId,
                seasonId,
                search,
                page.getPageSize(),
                page.getPageNumber()));

        final List<EpisodeDto> list = response.getEpisodes();

        final Page<EpisodeDto> all = new PageImpl<>(list, page, response.getTotalElements());

        final List<EpisodeResource> serials = all
                .map(EpisodeResource::new)
                .getContent();

        final PagedResources<EpisodeResource> resources = new PagedResources<>(serials,
                new PageMetadata(all.getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<EpisodeResource> addEpisode(@PathVariable final Integer serialId,
                                                      @PathVariable final Integer seasonId,
                                                      @RequestBody @Validated final EpisodeDto episode) throws GenericException
    {
        final Dto response = getResponse(episodeAccessor.addEpisode(serialId, seasonId, episode));

        final EpisodeDto episodeDto = getEpisodeDto(response);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("")
                .buildAndExpand(serialId, seasonId, episodeDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new EpisodeResource(episodeDto));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer serialId,
                                    @PathVariable final Integer seasonId,
                                    @PathVariable final Integer id) throws GenericException
    {
        getResponse(episodeAccessor.delete(serialId, seasonId, id));

        return ResponseEntity.noContent().build();
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
