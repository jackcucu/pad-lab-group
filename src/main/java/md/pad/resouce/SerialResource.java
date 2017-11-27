package md.pad.resouce;

import lombok.Getter;
import md.pad.exceptions.SerialException;
import md.pad.model.db.Serial;
import md.pad.web.controllers.SeasonController;
import md.pad.web.controllers.SerialController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class SerialResource extends ResourceSupport
{
    private final Serial serial;

    public SerialResource(final Serial serial)
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
        catch (SerialException e)
        {
            e.printStackTrace();
        }
    }
}
