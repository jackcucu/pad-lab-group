package md.jack.accessor;

import md.jack.dto.SeasonDto;
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
public interface SeasonAccessor
{
    @GetMapping(value = "/{serialId}/seasons/{id}")
    ApiResponse get(@PathVariable("serialId") Integer serialId,
                    @PathVariable("id") Integer id);

    @GetMapping(value = "/{serialId}/seasons")
    ApiResponse getAll(@PathVariable("serialId") Integer serialId,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "size") int size,
                       @RequestParam(value = "page") int page);

    @PutMapping(value = "/{serialId}/seasons")
    ApiResponse addSeason(@PathVariable("serialId") Integer serialId,
                          @RequestBody @Validated SeasonDto season);

    @PutMapping(value = "/{serialId}/seasons/{id}")
    ApiResponse updateSeason(@PathVariable("serialId") Integer serialId,
                             @PathVariable("id") Integer id,
                             @RequestBody @Validated SeasonDto season);

    @DeleteMapping(value = "/{serialId}/seasons/{id}")
    ApiResponse delete(@PathVariable("serialId") Integer serialId,
                       @PathVariable("id") Integer id);
}
