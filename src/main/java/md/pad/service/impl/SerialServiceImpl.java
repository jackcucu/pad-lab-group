package md.pad.service.impl;

import md.pad.model.db.Serial;
import md.pad.repository.SerialRepository;
import md.pad.service.SerialService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SerialServiceImpl extends EntityServiceImpl<Serial, SerialRepository>
        implements SerialService
{
}
