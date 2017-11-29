package md.jack;

import md.jack.dto.SerialDto;

public interface SerialService
{
    SerialDto getById(Integer id) throws GenericException;
}
