package md.pad.util;

import java.util.function.Consumer;
import java.util.function.Function;

public final class FunctionalUtils
{
    public static <T, V> V safeGet(final T source, final Function<T, V> getter)
    {
        return source != null
                ? getter.apply(source)
                : null;
    }

    public static <T, V> void safeSet(final Consumer<V> setter, final T source, final Function<T, V> getter)
    {
        final V t = safeGet(source, getter);

        if (t != null)
        {
            if (!t.equals(0))
            {
                setter.accept(t);
            }
        }
    }
}
