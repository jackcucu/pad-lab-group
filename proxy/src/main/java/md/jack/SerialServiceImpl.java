package md.jack;

import md.jack.accessor.SerialAccessor;
import md.jack.dto.Dto;
import md.jack.dto.SerialDto;
import md.jack.model.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    private Dto getResponse(final ApiResponse apiResponse) throws GenericException
    {
        if (apiResponse.isStatus())
        {
            return apiResponse.getResult();
        }
        else
        {
            throw new GenericException(apiResponse.getMessage());
        }
    }

    private SerialDto getSerialDto(final Dto response)
    {
        final SerialDto serialDto = new SerialDto();

        serialDto.setId(response.getId());
        serialDto.setName(response.getName());

        return serialDto;
    }
}
