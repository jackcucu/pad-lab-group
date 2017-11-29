package md.jack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Dto implements Serializable
{
    private Integer id;

    private String name;

    private String description;

    private Integer ord;

    private SeasonDto season;

    private Integer seasonNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date releaseDate;

    private SerialDto serial;

    private List<SerialDto> serials;

    private List<EpisodeDto> episodes;

    private List<SeasonDto> seasons;

    private long totalElements;
}
