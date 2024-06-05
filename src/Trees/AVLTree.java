package Trees;


import Lists.LinkedList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

public class AVLTree<T> extends BinaryTree<T> implements Tree<T>  {
    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    public AVLTree(Comparator<T> comparator, T rootValue) {
        super(comparator, rootValue);
    }

    @Override
    public void add(T entry) {
        BTNode<T> addedNode = addNode(entry);
    }

    @Override
    public T remove(T entry) {
        return null;
    }

    @Override
    public LinkedList<T> traverseInorder() {
        return null;
    }

    @Override
    public LinkedList<T> traversePreorder() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }
}
