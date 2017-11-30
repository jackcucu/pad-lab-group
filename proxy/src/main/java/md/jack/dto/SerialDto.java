package md.jack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.jack.validation.Put;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SerialDto implements Serializable
{
    private Integer id;

    @NotEmpty(groups = Put.class)
    @NotNull(groups = Put.class)
    private String name;
}
