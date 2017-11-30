package md.jack.accessor;

import md.jack.dto.SerialDto;
import md.jack.model.api.ApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "node", path = "/api/serial")
public interface SerialAccessor
{
    @GetMapping(value = "/{id}")
    ApiResponse get(@PathVariable("id") Integer id);

    @GetMapping
    ApiResponse getAll(@RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "size") int size,
                       @RequestParam(value = "page") int page);

    @PutMapping
    ApiResponse addSerial(@RequestBody @Validated SerialDto serial);

    @PatchMapping(value = "/{id}")
    ApiResponse updateSerial(@PathVariable("id") Integer id, @RequestBody @Validated SerialDto serial);

    @DeleteMapping(value = "/{id}")
    ApiResponse delete(@PathVariable("id") Integer id);
}
