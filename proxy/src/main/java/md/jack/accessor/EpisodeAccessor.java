package md.jack.accessor;

import md.jack.dto.EpisodeDto;
import md.jack.model.api.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "node", path = "/api/serial")
public interface EpisodeAccessor
{
    @GetMapping(value = "/{serialId}/season/{seasonId}/episode/{id}")
    ApiResponse get(@PathVariable("serialId") Integer serialId,
                    @PathVariable("seasonId") Integer seasonId,
                    @PathVariable("id") Integer id);

    @GetMapping(value = "/{serialId}/season/{seasonId}/episode")
    ApiResponse getAll(@PathVariable("serialId") Integer serialId,
                       @PathVariable("seasonId") Integer seasonId,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "size") int size,
                       @RequestParam(value = "page") int page);

    @PostMapping(value = "/{serialId}/season/{seasonId}/episode/add")
    ApiResponse addEpisode(@PathVariable("serialId") Integer serialId,
                           @PathVariable("seasonId") Integer seasonId,
                           @RequestBody @Validated EpisodeDto episode);

    @PostMapping(value = "/{serialId}/season/{seasonId}/episode/{id}/delete")
    ApiResponse delete(@PathVariable("serialId") Integer serialId,
                       @PathVariable("seasonId") Integer seasonId,
                       @PathVariable("id") Integer id);
}
