package md.pad.resouce;

import lombok.Getter;
import md.pad.exceptions.SerialException;
import md.pad.model.db.Serial;
import md.pad.web.controllers.SerialController;
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
        add(linkTo(SerialController.class).withRel("api/serials"));
        try
        {
            add(linkTo(methodOn(SerialController.class).getSerial(serial.getName())).withSelfRel());
        }
        catch (SerialException e)
        {
            e.printStackTrace();
        }
    }
}
