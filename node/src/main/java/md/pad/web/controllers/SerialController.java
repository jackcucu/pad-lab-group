package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.db.Serial;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping("/api/serial")
public class SerialController
{
    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Serial> get(@PathVariable final Integer id) throws SerialException
    {
        return serialService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ResponseEntity<Page<Serial>> getAll(@RequestParam(required = false) final String search,
                                               @PageableDefault final Pageable page)
    {
        final Page<Serial> all = serialService.getAll(search, page);

        return ResponseEntity.ok(all);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Serial> addSerial(@RequestBody @Validated final Serial serial)
    {
        serialService.add(serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serial.getName())
                .toUri();

        return ResponseEntity.created(uri).body(serial);
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
