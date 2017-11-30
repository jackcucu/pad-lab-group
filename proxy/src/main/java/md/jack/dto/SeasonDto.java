package md.jack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.jack.validation.Put;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeasonDto implements Serializable
{
    private Integer id;

    @Min(value = 1, groups = Put.class)
    @NotNull(groups = Put.class)
    private Integer seasonNumber;

    @NotNull(groups = Put.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @NotNull(groups = Put.class)
    @NotEmpty(groups = Put.class)
    private String description;

    private SerialDto serial;
}
