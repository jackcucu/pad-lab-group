package md.jack.accessor;

import md.jack.dto.SeasonDto;
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
public interface SeasonAccessor
{
    @GetMapping(value = "/{serialId}/season/{id}")
    ResponseEntity<SeasonDto> get(@PathVariable Integer serialId,
                                  @PathVariable Integer id);

    @GetMapping(value = "/{serialId}/season")
    ResponseEntity<Page<SeasonDto>> getAll(@PathVariable Integer serialId,
                                           @RequestParam(value = "search", required = false) String search,
                                           @PageableDefault Pageable page);

    @PostMapping(value = "/{serialId}/season/add")
    ResponseEntity<SeasonDto> addSeason(@PathVariable Integer serialId,
                                        @RequestBody @Validated SeasonDto episode);

    @DeleteMapping(value = "/{serialId}/season/{id}/delete")
    ResponseEntity<?> delete(@PathVariable Integer serialId,
                             @PathVariable Integer id);
}
