package md.pad.model.api;

import lombok.Data;
import md.pad.model.db.Serial;

import java.util.List;

@Data
public class SerialDto
{
    private List<Serial> serialDtos;
}
