package Trees;


import Lists.LinkedList;

public interface Tree<T> extends Iterable<T> {
    void add(T entry);
    T remove(T entry);
    LinkedList<T> traverseInorder();
    LinkedList<T> traversePreorder();
}
