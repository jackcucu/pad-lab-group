package md.pad.query;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericSpecificationBuilder<T>
{
    private final List<SearchCriteria> params = new ArrayList<>();

    public GenericSpecificationBuilder (final String query)
    {
        final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");

        final Matcher matcher = pattern.matcher(query + ",");

        while (matcher.find())
        {
            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
    }

    public Specification<T> build()
    {
        if (params.size() == 0)
        {
            return null;
        }

        final List<Specification<T>> specs = new ArrayList<>();

        for (SearchCriteria param : params)
        {
            specs.add(new GenericSpecification<T>(param));
        }

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++)
        {
            result = Specifications.where(result).and(specs.get(i));
        }

        return result;
    }
}