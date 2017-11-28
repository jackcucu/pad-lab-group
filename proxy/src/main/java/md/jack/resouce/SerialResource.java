package md.jack.resouce;

import lombok.Getter;
import md.jack.GenericException;
import md.jack.dto.SerialDto;
import md.jack.web.controllers.SeasonController;
import md.jack.web.controllers.SerialController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class SerialResource extends ResourceSupport
{
    private final SerialDto serial;

    public SerialResource(final SerialDto serial)
    {
        this.serial = serial;

        try
        {
            add(linkTo(methodOn(SerialController.class).get(serial.getId())).withSelfRel());
            add(linkTo(SerialController.class).withRel("serials"));

            final Link seasons = linkTo(methodOn(SeasonController.class).getAll(serial.getId(), "", null))
                    .withRel("seasons");
            add(seasons);
        }
        catch (GenericException e)
        {
            e.printStackTrace();
        }
    }
}
