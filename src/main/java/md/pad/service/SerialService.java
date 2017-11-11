package md.pad.service;

import md.pad.model.db.Serial;
import md.pad.service.abs.EntityService;

public interface SerialService extends EntityService<Serial>
{
    Serial getByName(String name);

    void deleteByName(String name);
}
