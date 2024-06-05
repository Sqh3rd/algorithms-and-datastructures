package Lists;

public interface CommonList<T> extends Iterable<T>
{
    void push(T element);
    T pop();
    T remove(int index);
    int size();
    int indexOf(T element);
    boolean contains(T element);
    T get(int index);
}
