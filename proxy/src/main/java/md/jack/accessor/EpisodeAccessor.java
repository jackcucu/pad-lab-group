package md.jack.accessor;

import md.jack.dto.EpisodeDto;
import md.jack.model.api.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "node", path = "/api/serials")
public interface EpisodeAccessor
{
    @GetMapping(value = "/{serialId}/seasons/{seasonId}/episodes/{id}")
    ApiResponse get(@PathVariable("serialId") Integer serialId,
                    @PathVariable("seasonId") Integer seasonId,
                    @PathVariable("id") Integer id);

    @GetMapping(value = "/{serialId}/seasons/{seasonId}/episodes")
    ApiResponse getAll(@PathVariable("serialId") Integer serialId,
                       @PathVariable("seasonId") Integer seasonId,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "size") int size,
                       @RequestParam(value = "page") int page);

    @PutMapping(value = "/{serialId}/seasons/{seasonId}/episodes")
    ApiResponse addEpisode(@PathVariable("serialId") Integer serialId,
                           @PathVariable("seasonId") Integer seasonId,
                           @RequestBody @Validated EpisodeDto episode);

    @PutMapping(value = "/{serialId}/seasons/{seasonId}/episodes/{id}")
    ApiResponse updateEpisode(@PathVariable("serialId") Integer serialId,
                              @PathVariable("seasonId") Integer seasonId,
                              @PathVariable("id") Integer id,
                              @RequestBody @Validated EpisodeDto episode);

    @DeleteMapping(value = "/{serialId}/seasons/{seasonId}/episodes/{id}")
    ApiResponse delete(@PathVariable("serialId") Integer serialId,
                       @PathVariable("seasonId") Integer seasonId,
                       @PathVariable("id") Integer id);
}
