package md.pad.web.controllers;

import md.pad.model.db.Serial;
import md.pad.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/serial")
public class SerialController
{
    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/get", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Serial getSerial(@RequestParam final String name)
    {
        return serialService.getByName(name);
    }

    @GetMapping(value = "/getAll")
    public @ResponseBody List<Serial> getAllSerials(@RequestParam(required = false) final String search, @PageableDefault final Pageable page)
    {
        return serialService.getAll(search, page).getContent();
    }

    @PostMapping(value = "/add", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody String addSerial(@RequestBody final Serial serial)
    {
        serialService.add(serial);
        return "Success";
    }

    @DeleteMapping(value = "/delete", produces = APPLICATION_JSON_VALUE)
    public String delete(@RequestParam final String name)
    {
        serialService.deleteByName(name);
        return "Success";
    }
}
