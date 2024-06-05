package Trees;


import Lists.LinkedList;

abstract class TNode<T, E extends TNode<T, E>> {
    private E parent;
    private final T value;
    private int height;

    protected TNode(E parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    public int calculateHeight() {
        int calculatedHeight = 0;
        E ancestor = parent;
        while (ancestor != null) {
            calculatedHeight++;
            ancestor = ancestor.getParent();
        }
        return calculatedHeight;
    }

    public void updateHeight() {
        height = calculateHeight();
    }

    public void setParent(E parent) {
        this.parent = parent;
    }

    public E getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    public int getHeight() {
        return height;
    }

    abstract public E copy();

    // TRAVERSALS

    abstract public LinkedList<T> traverseInorder();
    abstract public void traverseInorder(LinkedList<T> linkedList);

    abstract public LinkedList<T> traversePreorder();
    abstract public void traversePreorder(LinkedList<T> linkedList);
}
