package md.pad.service;

import md.pad.model.db.Serial;
import md.pad.service.abs.EntityService;

import java.util.Optional;

public interface SerialService extends EntityService<Serial>
{
    Optional<Serial> getByName(String name);

    void deleteByName(String name);
}
