package md.pad.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "SERIES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Series extends AbstractEntity
{
    private String name;

    private String description;

    private Integer ord;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
}
