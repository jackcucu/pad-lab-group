package md.jack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.jack.validation.Put;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EpisodeDto implements Serializable
{
    private Integer id;

    @NotEmpty(groups = Put.class)
    @NotNull(groups = Put.class)
    private String name;

    @NotEmpty(groups = Put.class)
    @NotNull(groups = Put.class)
    private String description;

    @NotNull(groups = Put.class)
    @Min(value = 1, groups = Put.class)
    private Integer ord;

    private SeasonDto season;
}
