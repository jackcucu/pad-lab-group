package md.pad.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SEASON")
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Season extends AbstractEntity
{
    private Integer season;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "serial_id")
    private Serial serial;

    @JsonIgnore
    @OneToMany(mappedBy = "season", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Episode> episodes;
}
