package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Serial;
import md.pad.resouce.SerialResource;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/serials")
public class SerialController
{
    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/get")
    public ResponseEntity<SerialResource> getSerial(@RequestParam final String name) throws SerialException
    {
        return serialService.getByName(name)
                .map(it -> ResponseEntity.ok(new SerialResource(it)))
                .orElseThrow(() -> new SerialException("Serial not found"));

    }

    @GetMapping
    public ResponseEntity<Resources<SerialResource>> getAllSerials(@RequestParam(required = false) final String search,
                                                                   @PageableDefault final Pageable page)
    {
        final List<SerialResource> serials = serialService.getAll(search, page)
                .getContent()
                .stream()
                .map(SerialResource::new)
                .collect(toList());

        final Resources<SerialResource> resources = new Resources<>(serials);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();

        resources.add(new Link(uriString, "self"));

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<SerialResource> addSerial(@RequestBody final Serial serial)
    {
        serialService.add(serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serial.getName())
                .toUri();

        return ResponseEntity.created(uri).body(new SerialResource(serial));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestParam final String name) throws SerialException
    {
        return serialService.getByName(name)
                .map(it -> {
                    serialService.deleteByName(name);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new SerialException("Serial not found"));
    }
}
