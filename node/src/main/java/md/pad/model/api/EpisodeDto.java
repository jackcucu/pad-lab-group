package md.pad.model.api;

import lombok.Data;
import md.pad.model.db.Episode;

import java.util.List;

@Data
public class EpisodeDto
{
    private List<Episode> episodes;

    private long totalElements;
}
