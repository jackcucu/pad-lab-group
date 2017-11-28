package md.pad.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SEASON")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Season extends AbstractEntity
{
    @Min(value = 1)
    @NotNull
    @Column(unique = true, name = "season_number")
    private Integer seasonNumber;

    @Column(name = "release_date")
    @NotNull
    private Date releaseDate;

    @NotNull
    @NotEmpty
    private String description;

    @ManyToOne
    @JoinColumn(name = "serial_id")
    private Serial serial;

    @JsonIgnore
    @OneToMany(mappedBy = "season", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Episode> episodes;
}


