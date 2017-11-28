package md.jack.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Dto
{
    private Integer id;

    private String name;

    private String description;

    private Integer ord;

    private SeasonDto season;

    private Integer seasonNumber;

    private Date releaseDate;

    private SerialDto serial;
}
