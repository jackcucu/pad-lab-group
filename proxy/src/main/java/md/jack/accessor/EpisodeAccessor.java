package md.jack.accessor;

import md.jack.dto.EpisodeDto;
import org.springframework.cloud.netflix.feign.FeignClient;
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
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "node", path = "/api/serial")
public interface EpisodeAccessor
{
    @GetMapping(value = "/{serialId}/season/{seasonId}/episode/{id}")
    ResponseEntity<EpisodeDto> get(@PathVariable Integer serialId,
                                   @PathVariable Integer seasonId,
                                   @PathVariable Integer id);

    @GetMapping(value = "/{serialId}/season/{seasonId}/episode")
    ResponseEntity<Page<EpisodeDto>> getAll(@PathVariable Integer serialId,
                                            @PathVariable Integer seasonId,
                                            @RequestParam(value = "search", required = false) String search,
                                            @PageableDefault Pageable page);

    @PostMapping(value = "/{serialId}/season/{seasonId}/episode/add")
    ResponseEntity<EpisodeDto> addEpisode(@PathVariable Integer serialId,
                                          @PathVariable Integer seasonId,
                                          @RequestBody @Validated EpisodeDto episode);

    @DeleteMapping(value = "/{serialId}/season/{seasonId}/episode/{id}/delete")
    ResponseEntity<?> delete(@PathVariable Integer serialId,
                             @PathVariable Integer seasonId,
                             @PathVariable Integer id);

}
