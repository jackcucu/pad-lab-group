package md.jack.accessor;

import md.jack.dto.EpisodeDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    EpisodeDto get(@PathVariable("serialId") Integer serialId,
                   @PathVariable("seasonId") Integer seasonId,
                   @PathVariable("id") Integer id);

    @GetMapping(value = "/{serialId}/season/{seasonId}/episode")
    Page<EpisodeDto> getAll(@PathVariable("serialId") Integer serialId,
                            @PathVariable("seasonId") Integer seasonId,
                            @RequestParam(value = "search", required = false) String search,
                            @PageableDefault Pageable page);

    @PostMapping(value = "/{serialId}/season/{seasonId}/episode/add")
    EpisodeDto addEpisode(@PathVariable("serialId") Integer serialId,
                          @PathVariable("seasonId") Integer seasonId,
                          @RequestBody @Validated EpisodeDto episode);

    @DeleteMapping(value = "/{serialId}/season/{seasonId}/episode/{id}/delete")
    void delete(@PathVariable("serialId") Integer serialId,
                @PathVariable("seasonId") Integer seasonId,
                @PathVariable("id") Integer id);

}
