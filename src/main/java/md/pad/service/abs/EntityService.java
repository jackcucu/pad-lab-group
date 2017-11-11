package md.pad.service.abs;

import md.pad.model.db.abs.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface EntityService<E extends AbstractEntity>
{
    void add(E entity);

    void edit(E entity);

    void delete(Integer id);

    void deleteAll();

    E get(Integer id);

    List<E> getAll();

    List<E> getAll(String query);

    Page<E> getAll(String query, Pageable page);

    Page<E> getAll(Pageable page);

    Long getCount();
}
