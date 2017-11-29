package md.jack.service;

import md.jack.GenericException;
import md.jack.dto.Dto;
import md.jack.dto.SerialDto;
import org.springframework.data.domain.Pageable;

public interface SerialService
{
    SerialDto getById(Integer id) throws GenericException;

    Dto getSerials(String search, Pageable pageable) throws GenericException;

    SerialDto addSerial(SerialDto serialDto) throws GenericException;

    void deleteSerial(Integer id) throws GenericException;
}
