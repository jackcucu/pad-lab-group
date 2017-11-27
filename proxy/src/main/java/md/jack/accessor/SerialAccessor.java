package md.jack.accessor;

import md.jack.dto.EpisodeDto;
import md.jack.dto.SerialDto;
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
public interface SerialAccessor
{
    @GetMapping(value = "/{id}")
    ResponseEntity<SerialDto> get(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<Page<SerialDto>> getAll(@RequestParam(value = "search", required = false) String search,
                                           @PageableDefault Pageable page);

    @PostMapping(value = "/add")
    ResponseEntity<EpisodeDto> addSerial(@RequestBody @Validated SerialDto serial);

    @DeleteMapping(value = "/{id}/delete")
    ResponseEntity<?> delete(@PathVariable Integer id);
}
