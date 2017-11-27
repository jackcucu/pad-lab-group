package md.pad.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EPISODE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Episode extends AbstractEntity
{
    private String name;

    private String description;

    private Integer ord;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
}
