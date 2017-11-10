package md.pad.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "SEASON")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Season extends AbstractEntity
{
    private Integer season;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "serial_id")
    private Serial serial;

    @OneToMany(mappedBy = "season", fetch = FetchType.EAGER)
    private List<Series> series;
}
