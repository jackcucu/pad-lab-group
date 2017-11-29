package md.jack.util;

@FunctionalInterface
public interface ReferencePerformer<T>
{
    void perform(T source);
}
