package Lists.Linked.Doubly;

import Lists.Linked.LinkedEntry;

public class DoublyLinkedEntry<T> implements LinkedEntry<T, DoublyLinkedEntry<T>>
{
    private T value;
    private DoublyLinkedEntry<T> next;
    private DoublyLinkedEntry<T> previous;

    DoublyLinkedEntry(T value)
    {
        this(value, null, null);
    }

    private DoublyLinkedEntry(T value, DoublyLinkedEntry<T> next, DoublyLinkedEntry<T> previous)
    {
        this.value = value;
        this.next = next;
        this.previous = previous;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public DoublyLinkedEntry<T> getNext()
    {
        return next;
    }

    public void setNext(DoublyLinkedEntry<T> next)
    {
        this.next = next;
        if (next != null)
            next.previous = this;
    }

    public DoublyLinkedEntry<T> getPrevious()
    {
        return previous;
    }

    public void setPrevious(DoublyLinkedEntry<T> previous)
    {
        this.previous = previous;
        if (previous != null)
            previous.next = this;
    }

    @Override
    public DoublyLinkedEntry<T> copy() {
        return new DoublyLinkedEntry<>(value, next == null ? null : next.copy(), previous == null ? null : previous.copy());
    }
}
