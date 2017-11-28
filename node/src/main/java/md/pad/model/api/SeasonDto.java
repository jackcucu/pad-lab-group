package md.pad.model.api;

import lombok.Data;
import md.pad.model.db.Season;

import java.util.List;

@Data
public class SeasonDto
{
    private List<Season> seasons;

    private long totalElements;
}
