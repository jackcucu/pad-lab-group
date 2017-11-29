package md.jack.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class FunctionalUtils
{
    public static <T, V> V safeGet(final T source, final Function<T, V> getter)
    {
        return source != null
                ? getter.apply(source)
                : null;
    }

    public static <T, V> V safeGet(final T source, final Function<T, V> getter, final Supplier<V> defaultValue)
    {
        return source != null
                ? getter.apply(source)
                : defaultValue.get();
    }

    public static <T, V> V safeGet(final T source, final Predicate<T> isSafe, final Function<T, V> getter)
    {
        return isSafe.test(source)
                ? getter.apply(source)
                : null;
    }

    public static <T, V> void safeSet(final Consumer<V> setter, final T source, final Function<T, V> getter)
    {
        setter.accept(safeGet(source, getter));
    }

    public static void executeIf(final Premise premise, final Performer action)
    {
        Optional.of(action).filter(performer -> premise.test()).ifPresent(Performer::perform);
    }

    public static <T> void executeIf(final T source,
                                     final Predicate<T> predicate,
                                     final ReferencePerformer<T> action)
    {
        Optional.of(action)
                .filter(performer -> predicate.test(source))
                .ifPresent(performer -> performer.perform(source));
    }

    public static void executeIfElse(final Premise premise, final Performer action, final Performer elseAction)
    {
        final Performer performer = premise.test()
                ? action
                : elseAction;
        performer.perform();
    }

    public static <T> void executeIfElse(final T source,
                                         final Predicate<T> predicate,
                                         final ReferencePerformer<T> action,
                                         final ReferencePerformer<T> elseAction)
    {
        final ReferencePerformer<T> performer = predicate.test(source)
                ? action
                : elseAction;

        performer.perform(source);
    }

    public static void throwableExecuteIf(final Premise premise, final ThrowablePerformer action)
    {
        if (premise.test())
        {
            try
            {
                action.perform();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void tryExecute(final ThrowablePerformer action)
    {
        try
        {
            action.perform();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public static void tryThenExecute(final ThrowablePerformer throwableAction, final Performer action)
    {
        try
        {
            throwableAction.perform();
        }
        catch (Exception exception)
        {
            action.perform();
        }
    }

    public static <T> Function<T, T> doAndReturn(final Consumer<T> action)
    {
        return it ->
        {
            action.accept(it);
            return it;
        };
    }
}
