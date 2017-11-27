package md.pad.resouce;

import lombok.Getter;
import md.pad.exceptions.SerialException;
import md.pad.model.db.Season;
import md.pad.web.controllers.EpisodeController;
import md.pad.web.controllers.SeasonController;
import md.pad.web.controllers.SerialController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class SeasonResource extends ResourceSupport
{
    private final Season season;

    public SeasonResource(final Season season)
    {
        this.season = season;

        final Integer seasonId = season.getId();
        final Integer serialId = season.getSerial().getId();

        try
        {
            add(linkTo(methodOn(SeasonController.class).get(serialId, seasonId)).withSelfRel());

            add(linkTo(methodOn(SeasonController.class).getAll(serialId, "", null)).withRel("seasons"));

            final Link episodes = linkTo(methodOn(EpisodeController.class).getAll(serialId, seasonId, "", null))
                    .withRel("episodes");
            add(episodes);

            add(linkTo(methodOn(SerialController.class).get(serialId)).withRel("serial"));
        }
        catch (SerialException exception)
        {
            exception.printStackTrace();
        }
    }
}
