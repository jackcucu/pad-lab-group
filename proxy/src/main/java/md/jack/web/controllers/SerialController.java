package md.jack.web.controllers;

import md.jack.accessor.SerialAccessor;
import md.jack.dto.SerialDto;
import md.jack.resouce.SerialResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/serial")
public class SerialController
{
    @Autowired
    private SerialAccessor serialAccessor;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SerialResource> get(@PathVariable final Integer id)
    {
        final SerialDto serial = serialAccessor.get(id).getBody();

        return ResponseEntity.ok(new SerialResource(serial));
    }

    @GetMapping
    public ResponseEntity<PagedResources<SerialResource>> getAll(@RequestParam(required = false) final String search,
                                                                 @PageableDefault final Pageable page)
    {
        final Page<SerialDto> all = serialAccessor.getAll(search, page).getBody();

        final List<SerialResource> serials = all
                .map(SerialResource::new)
                .getContent();

        final PagedResources<SerialResource> resources = new PagedResources<>(serials,
                new PageMetadata(all
                        .getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<SerialResource> addSerial(@RequestBody @Validated final SerialDto serial)
    {
        serialAccessor.addSerial(serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serial.getName())
                .toUri();

        return ResponseEntity.created(uri).body(new SerialResource(serial));
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer id)
    {
        serialAccessor.delete(id);

        return ResponseEntity.noContent().build();
    }
}
