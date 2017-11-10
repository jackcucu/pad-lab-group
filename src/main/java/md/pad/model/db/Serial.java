package md.pad.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import md.pad.model.db.abs.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "SERIAL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Serial extends AbstractEntity
{
    private String name;

    @OneToMany(mappedBy = "serial", fetch = FetchType.EAGER)
    private List<Season> seasons;
}