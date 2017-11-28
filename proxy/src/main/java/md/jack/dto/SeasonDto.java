package md.jack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeasonDto
{
    private Integer id;

    @Min(value = 1)
    @NotNull
    private Integer season;

    @NotNull
    private Date releaseDate;

    @NotNull
    @NotEmpty
    private String description;

    private SerialDto serial;
}
