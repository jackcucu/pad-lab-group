package md.pad.service.impl;

import md.pad.model.db.Serial;
import md.pad.repository.SerialRepository;
import md.pad.service.SerialService;
import md.pad.service.abs.EntityServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class SerialServiceImpl extends EntityServiceImpl<Serial, SerialRepository>
        implements SerialService
{
    @Transactional
    @Override public Serial getByName(final String name)
    {
        return repository.findByName(name).orElseGet(Serial::new);
    }

    @Transactional
    @Override public void deleteByName(final String name)
    {
        repository.deleteByName(name);
    }

}
