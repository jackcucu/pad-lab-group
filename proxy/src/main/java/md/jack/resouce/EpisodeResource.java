package md.jack.resouce;

import lombok.Getter;
import md.jack.dto.EpisodeDto;
import md.jack.web.controllers.EpisodeController;
import md.jack.web.controllers.SeasonController;
import md.jack.web.controllers.SerialController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class EpisodeResource extends ResourceSupport
{
    private final EpisodeDto episode;

    public EpisodeResource(final EpisodeDto episode)
    {
        this.episode = episode;

        final Integer episodeId = episode.getId();
        final Integer seasonId = episode.getSeason().getId();
        final Integer serialId = episode.getSeason().getSerial().getId();

        add(linkTo(methodOn(EpisodeController.class).get(serialId, seasonId, episodeId)).withSelfRel());
        add(linkTo(methodOn(EpisodeController.class).getAll(serialId, seasonId, "", null)).withRel("episodes"));
        add(linkTo(methodOn(SeasonController.class).get(serialId, seasonId)).withRel("season"));
        add(linkTo(methodOn(SerialController.class).get(serialId)).withRel("serial"));
    }
}
