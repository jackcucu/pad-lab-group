package md.pad.web.controllers;

import md.pad.exceptions.SerialException;
import md.pad.model.api.ApiResponse;
import md.pad.model.api.SeasonDto;
import md.pad.model.db.Season;
import md.pad.model.db.Serial;
import md.pad.service.SeasonService;
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
@RequestMapping("api/serials/{serialId}/seasons")
public class SeasonController
{
    @Autowired
    private SeasonService seasonService;

    @Autowired
    private SerialService serialService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse get(@PathVariable final Integer serialId,
                           @PathVariable final Integer id) throws SerialException
    {
        return seasonService.getSeasonForSerial(serialId, id)
                .map(ApiResponse::new)
                .orElseThrow(() -> new SerialException("Serial not found"));
    }

    @GetMapping
    public ApiResponse getAll(@PathVariable final Integer serialId,
                              @RequestParam(required = false) final String search,
                              @PageableDefault final Pageable page) throws SerialException
    {
        serialService.getById(serialId).orElseThrow(() -> new SerialException("Serial not found"));

        final Page<Season> all = seasonService.getSeasonsForSerial(serialId, search, page);

        final List<Season> content = all.getContent();

        final SeasonDto seasonDto = new SeasonDto();

        seasonDto.setSeasons(content);
        seasonDto.setTotalElements(all.getTotalElements());

        return new ApiResponse(seasonDto);
    }

    @PutMapping
    public ApiResponse addSeason(@PathVariable final Integer serialId,
                                 @RequestBody @Validated final Season season) throws Exception
    {
        final Serial serial = serialService.getById(serialId)
                .orElseThrow(() -> new SerialException("Serial Not Found"));

        season.setSerial(serial);

        seasonService.add(season);

        return new ApiResponse(season);
    }

    @PutMapping("/{id}")
    public ApiResponse updateSeason(@PathVariable final Integer serialId,
                                    @PathVariable final Integer id,
                                    @RequestBody @Validated final Season season) throws Exception
    {
        final Serial serial = serialService.getById(serialId)
                .orElseThrow(() -> new SerialException("Serial Not Found"));

        final Season seasonLocal = serial.getSeasons().stream()
                .filter(it -> it.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new SerialException("Season Not Found"));

        safeSet(seasonLocal::setDescription, season, Season::getDescription);
        safeSet(seasonLocal::setReleaseDate, season, Season::getReleaseDate);
        safeSet(seasonLocal::setSeasonNumber, season, Season::getSeasonNumber);
        safeSet(seasonLocal::setName, season, Season::getName);

        seasonService.edit(seasonLocal);

        return new ApiResponse(seasonLocal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Integer serialId,
                       @PathVariable final Integer id) throws SerialException
    {
        serialService.getById(serialId).orElseThrow(() -> new SerialException("Serial not found"));

        seasonService.getSeasonForSerial(serialId, id)
                .orElseThrow(() -> new SerialException("Season not found"));

        seasonService.delete(id);
    }
}
