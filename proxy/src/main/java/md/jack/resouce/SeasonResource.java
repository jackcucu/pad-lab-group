package md.jack.resouce;

import lombok.Getter;
import md.jack.dto.SeasonDto;
import md.jack.web.controllers.EpisodeController;
import md.jack.web.controllers.SeasonController;
import md.jack.web.controllers.SerialController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class SeasonResource extends ResourceSupport
{
    private final SeasonDto season;

    public SeasonResource(final SeasonDto season)
    {
        this.season = season;

        final Integer seasonId = season.getId();
        final Integer serialId = season.getSerial().getId();

        add(linkTo(methodOn(SeasonController.class).get(serialId, seasonId)).withSelfRel());

        add(linkTo(methodOn(SeasonController.class).getAll(serialId, "", null)).withRel("seasons"));

        final Link episodes = linkTo(methodOn(EpisodeController.class).getAll(serialId, seasonId, "", null))
                .withRel("episodes");
        add(episodes);

        add(linkTo(methodOn(SerialController.class).get(serialId)).withRel("serial"));
    }
}
