package md.jack.service.impl;

import md.jack.GenericException;
import md.jack.accessor.SerialAccessor;
import md.jack.dto.Dto;
import md.jack.dto.SerialDto;
import md.jack.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static md.jack.util.ResponseUtil.getResponse;

@Service
public class SerialServiceImpl implements SerialService
{
    @Autowired
    private SerialAccessor serialAccessor;

    @Cacheable(value = "serial", key = "#id")
    @Override
    public SerialDto getById(final Integer id) throws GenericException
    {
        final Dto response = getResponse(serialAccessor.get(id));

        return getSerialDto(response);
    }

    @Cacheable(value = "serials", key = "#pageable.pageNumber + #pageable.pageSize")
    @Override
    public Dto getSerials(final String search, final Pageable pageable) throws GenericException
    {

        return getResponse(serialAccessor.getAll(
                search,
                pageable.getPageSize(),
                pageable.getPageNumber()));
    }

    @Override
    public SerialDto addSerial(final SerialDto serialDto) throws GenericException
    {
        final Dto response = getResponse(serialAccessor.addSerial(serialDto));

        return getSerialDto(response);
    }

    @CacheEvict(value = "serial", key = "#id")
    @Override
    public void deleteSerial(final Integer id) throws GenericException
    {
        getResponse(serialAccessor.delete(id));
    }

    private SerialDto getSerialDto(final Dto response)
    {
        final SerialDto serialDto = new SerialDto();

        serialDto.setId(response.getId());
        serialDto.setName(response.getName());

        return serialDto;
    }
}
