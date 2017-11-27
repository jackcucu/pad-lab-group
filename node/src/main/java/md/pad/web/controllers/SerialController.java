package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Serial;
import md.pad.resouce.SerialResource;
import md.pad.service.SerialService;
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
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SerialResource> get(@PathVariable final Integer id) throws SerialException
    {
        return serialService.getById(id)
                .map(it -> ResponseEntity.ok(new SerialResource(it)))
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<SerialResource> getSerial(@RequestParam final String name) throws SerialException
    {
        return serialService.getByName(name)
                .map(it -> ResponseEntity.ok(new SerialResource(it)))
                .orElseThrow(() -> new SerialException("Serial not found"));

    }

    @GetMapping
    public ResponseEntity<PagedResources<SerialResource>> getAllSerials(@RequestParam(required = false) final String search,
                                                                        @PageableDefault final Pageable page)
    {
        final Page<Serial> all = serialService.getAll(search, page);

        final List<SerialResource> serials = all
                .map(SerialResource::new)
                .getContent();

        final PagedResources<SerialResource> resources = new PagedResources<>(serials, new PageMetadata(all
                .getSize(), all.getNumber(), all.getTotalElements()));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<SerialResource> addSerial(@RequestBody @Validated final Serial serial)
    {
        serialService.add(serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serial.getName())
                .toUri();

        return ResponseEntity.created(uri).body(new SerialResource(serial));
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable final Integer id) throws SerialException
    {
        return serialService.getById(id)
                .map(it -> {
                    serialService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new SerialException("Serial not found"));
    }
}
