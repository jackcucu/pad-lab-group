package md.pad.resouce;

import lombok.Getter;
import md.pad.exceptions.SerialException;
import md.pad.model.db.Episode;
import md.pad.web.controllers.EpisodeController;
import md.pad.web.controllers.SeasonController;
import md.pad.web.controllers.SerialController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class EpisodeResource extends ResourceSupport
{
    private final Episode episode;

    public EpisodeResource(final Episode episode)
    {
        this.episode = episode;

        final Integer episodeId = episode.getId();
        final Integer seasonId = episode.getSeason().getId();
        final Integer serialId = episode.getSeason().getSerial().getId();

        try
        {
            add(linkTo(methodOn(EpisodeController.class).get(serialId, seasonId, episodeId)).withSelfRel());
            add(linkTo(methodOn(EpisodeController.class).getAll(serialId, seasonId, "", null)).withRel("episodes"));
            add(linkTo(methodOn(SeasonController.class).get(serialId, seasonId)).withRel("season"));
            add(linkTo(methodOn(SerialController.class).get(serialId)).withRel("serial"));
        }
        catch (SerialException exception)
        {
            exception.printStackTrace();
        }
    }
}
