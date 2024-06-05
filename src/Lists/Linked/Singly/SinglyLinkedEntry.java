package Lists.Linked.Singly;


import Lists.Linked.LinkedEntry;
import Other.Copy;

public class SinglyLinkedEntry<T> implements LinkedEntry<T, SinglyLinkedEntry<T>>
{
    private T value;
    private SinglyLinkedEntry<T> next;

    SinglyLinkedEntry(T value) {
        this(value, null);
    }

    SinglyLinkedEntry(T value, SinglyLinkedEntry<T> next)
    {
        this.value = value;
        this.next = next;
    }

    public void setValue(T value) { this.value = value; }

    public T getValue()
    {
        return value;
    }

    public SinglyLinkedEntry<T> getNext()
    {
        return next;
    }

    public void setNext(SinglyLinkedEntry<T> singlyLinkedEntry)
    {
        this.next = singlyLinkedEntry;
    }

    public SinglyLinkedEntry<T> deepCopy()
    {
        return new SinglyLinkedEntry<>(value instanceof Copy ? (T)((Copy)value).copy() : value,
            next == null ? null :
            next.deepCopy());
    }

    public SinglyLinkedEntry<T> copy() {
        return new SinglyLinkedEntry<>(value, next != null ? next.copy() : null);
    }
}
