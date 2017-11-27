package md.jack.accessor;

import md.jack.dto.SerialDto;
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
public interface SerialAccessor
{
    @GetMapping(value = "/{id}")
    SerialDto get(@PathVariable("id") Integer id);

    @GetMapping
    Page<SerialDto> getAll(@RequestParam(value = "search", required = false) String search,
                           @PageableDefault Pageable page);

    @PostMapping(value = "/add")
    SerialDto addSerial(@RequestBody @Validated SerialDto serial);

    @DeleteMapping(value = "/{id}/delete")
    void delete(@PathVariable("id") Integer id);
}
