package Lists;


import Other.Copy;

public interface LinkedList<T> extends CommonList<T>, Copy<LinkedList<T>>
{
    void insert(int index, T element);
    T replace(int index, T element);
    int lastIndexOf(T element);
}
