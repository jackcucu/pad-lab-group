package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import md.pad.model.db.Serial;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/serial")
public class SerialController
{
    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse get(@PathVariable final Integer id) throws SerialException
    {
        return serialService.getById(id)
                .map(ApiResponse::new)
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ApiResponse getAll(@RequestParam(required = false) final String search,
                              @PageableDefault final Pageable page)
    {
        return new ApiResponse(serialService.getAll(search, page));
    }

    @PostMapping(value = "/add")
    public ApiResponse addSerial(@RequestBody @Validated final Serial serial)
    {
        serialService.add(serial);

        return new ApiResponse(serial);
    }

    @DeleteMapping(value = "/{id}/delete")
    public void delete(@PathVariable final Integer id) throws SerialException
    {
        final Serial serial = serialService.getById(id)
                .orElseThrow(() -> new SerialException("Serial not found"));

        serialService.delete(id);
    }
}
