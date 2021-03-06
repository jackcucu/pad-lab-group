package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import md.pad.model.api.SerialDto;
import md.pad.model.db.Serial;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static md.pad.util.FunctionalUtils.safeSet;

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
        final Page<Serial> all = serialService.getAll(search, page);

        final List<Serial> content = all.getContent();

        final SerialDto serialDto = new SerialDto();

        serialDto.setSerials(content);
        serialDto.setTotalElements(all.getTotalElements());

        return new ApiResponse(serialDto);
    }

    @PutMapping
    public ApiResponse addSerial(@RequestBody @Validated final Serial serial)
    {
        serialService.add(serial);

        return new ApiResponse(serial);
    }

    @PutMapping("/{id}")
    public ApiResponse updateSerial(@PathVariable final Integer id,
                                    @RequestBody @Validated final Serial serial) throws SerialException
    {
        final Serial serialLocal = serialService.getById(id)
                .orElseThrow(() -> new SerialException("Serial not found"));

        safeSet(serialLocal::setName, serial, Serial::getName);

        serialService.edit(serialLocal);

        return new ApiResponse(serialLocal);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable final Integer id) throws SerialException
    {
        serialService.getById(id)
                .orElseThrow(() -> new SerialException("Serial not found"));

        serialService.delete(id);
    }
}
