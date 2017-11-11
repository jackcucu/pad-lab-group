package md.pad.service.abs;

import md.pad.model.db.Serial;
import md.pad.model.db.abs.AbstractEntity;
import md.pad.query.GenericSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public abstract class EntityServiceImpl<E extends AbstractEntity, R extends JpaRepository<E, Integer> & JpaSpecificationExecutor<E>>
    implements EntityService<E>
{
    @Autowired
    protected R repository;

    @Override
    public void add(final E entity)
    {
        repository.save(entity);
    }

    @Override
    public void edit(final E entity)
    {
        repository.save(entity);
    }

    @Override
    public void delete(final Integer id)
    {
        repository.delete(id);
    }

    @Override
    public void deleteAll()
    {
        repository.deleteAll();
    }

    @Override
    public E get(final Integer id)
    {
        return repository.getOne(id);
    }

    @Override
    public List<E> getAll()
    {
        return repository.findAll();
    }

    @Override public List<E> getAll(final String query)
    {
        final String search = Optional.ofNullable(query).orElse("");

        final Specification<E> specification = new GenericSpecificationBuilder<E>(search).build();

        return repository.findAll(specification);
    }

    @Override public Page<E> getAll(final String query, final Pageable pageable)
    {
        final String search = Optional.ofNullable(query).orElse("");

        final Specification<E> specification = new GenericSpecificationBuilder<E>(search).build();

        return repository.findAll(specification, pageable);
    }

    @Override
    public Page<E> getAll(final Pageable page)
    {
        return repository.findAll(page);
    }

    @Override
    public Long getCount()
    {
        return repository.count();
    }
}
