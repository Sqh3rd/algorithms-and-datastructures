package Lists.Linked;

import Other.Copy;

public interface LinkedEntry<T, E extends LinkedEntry<T, E>> extends Copy<E>
{
    T getValue();
    void setValue(T value);
    E getNext();
}
