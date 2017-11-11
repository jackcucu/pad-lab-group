package md.pad.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "SERIAL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Serial extends AbstractEntity
{
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "serial", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Season> seasons;
}