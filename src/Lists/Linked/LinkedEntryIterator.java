package Lists.Linked;


import java.util.Iterator;

public class LinkedEntryIterator<T, E extends LinkedEntry<T, E>> implements Iterator<T>
{
    private LinkedEntry<T, E> current;

    LinkedEntryIterator(LinkedEntry<T, E> current) {
        this.current = current;
    }

    @Override
    public T next() {
        T value = current.getValue();
        current = current.getNext();
        return value;
    }

    @Override
    public boolean hasNext()
    {
        return current != null;
    }
}
