package md.pad.repository;

import md.pad.model.db.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer>, JpaSpecificationExecutor<Season>
{
    Optional<Season> findBySerial_IdAndId(Integer serialId, Integer id);

    Page<Season> findAllBySerial_Id(Integer serialId, Pageable pageable);
}
