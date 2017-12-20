package md.jack.web.controllers;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.dto.SerialDto;
import md.jack.resouce.SerialResource;
import md.jack.service.SerialService;
import md.jack.validation.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.PagedResources.PageMetadata;

@RestController
@RequestMapping("/api/serials")
@CrossOrigin(origins = "http://localhost:80")
public class SerialController extends AbstractController
{
    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SerialResource get(@PathVariable final Integer id) throws GenericException
    {
        final SerialDto serialDto = serialService.getById(id);

        return new SerialResource(serialDto);
    }

    @GetMapping
    public PagedResources<SerialResource> getAll(@RequestParam(required = false) final String search,
                                                 @PageableDefault final Pageable page) throws GenericException
    {
        final Dto response = serialService.getSerials(search, page);

        final List<SerialDto> list = response.getSerials();

        final Page<SerialDto> all = new PageImpl<>(list, page, response.getTotalElements());

        final List<SerialResource> serials = all
                .map(SerialResource::new)
                .getContent();

        return new PagedResources<>(serials,
                new PageMetadata(all
                        .getSize(), all.getNumber(), all.getTotalElements()));
    }

    @PutMapping
    public ResponseEntity<SerialResource> addSerial(@RequestBody
                                                    @Validated(Put.class) final SerialDto serial) throws GenericException
    {
        final SerialDto serialDto = serialService.addSerial(serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serialDto.getName())
                .toUri();

        return ResponseEntity.created(uri).body(new SerialResource(serialDto));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<SerialResource> updateSerial(@PathVariable final Integer id,
                                                       @RequestBody @Validated final SerialDto serial) throws GenericException
    {
        final SerialDto serialDto = serialService.updateSerial(id, serial);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("get")
                .buildAndExpand(serialDto.getName())
                .toUri();

        return ResponseEntity.created(uri).body(new SerialResource(serialDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) throws GenericException
    {
        serialService.deleteSerial(id);

        return ResponseEntity.noContent().build();
    }
}
