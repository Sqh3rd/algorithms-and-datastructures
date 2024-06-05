package Trees;


import Lists.Linked.Singly.SinglyLinkedList;
import Lists.LinkedList;

public class BTNode<T> extends TNode<T, BTNode<T>> {
    private BTNode<T> left;
    private BTNode<T> right;

    private int balance = 0;
    private int height = 0;

    public BTNode(BTNode<T> parent, T value) {
        this(parent, value, null, null);
    }

    BTNode(BTNode<T> parent, T value, BTNode<T> left, BTNode<T> right) {
        super(parent, value);
        this.left = left;
        this.right = right;
        height = calculateHeight();
        recalculateBalance();
    }

    public int recalculateBalance() {
        if (right != null) right.calculateHeight();
        if (left != null) left.calculateHeight();
        balance = Math.abs(right != null ? right.height : 0) - Math.abs(left != null ? left.height : 0);
        return balance;
    }

    public BTNode<T> getLeft() {
        return left;
    }

    public void setLeft(BTNode<T> left) {
        this.left = left;
    }

    public BTNode<T> getRight() {
        return right;
    }

    public void setRight(BTNode<T> right) {
        this.right = right;
    }

    @Override
    public LinkedList<T> traverseInorder() {
        LinkedList<T> list = new SinglyLinkedList<>();
        traverseInorder(list);
        return list;
    }

    @Override
    public void traverseInorder(LinkedList<T> linkedList) {
        if (left != null) left.traverseInorder(linkedList);
        linkedList.push(getValue());
        if (right != null) right.traverseInorder(linkedList);
    }

    @Override
    public LinkedList<T> traversePreorder() {
        LinkedList<T> list = new SinglyLinkedList<>();
        traversePreorder(list);
        return list;
    }

    @Override
    public void traversePreorder(LinkedList<T> linkedList) {
        linkedList.push(getValue());
        if (left != null) left.traversePreorder(linkedList);
        if (right != null) right.traversePreorder(linkedList);
    }

    @Override
    public int calculateHeight() {
        return Math.max(left != null ? left.height : 0, right != null ? right.height : 0) + 1;
    }

    @Override
    public void updateHeight() {
        height = calculateHeight();
    }

    @Override
    public BTNode<T> copy() {
        return new BTNode<>(getParent(), getValue(), left != null ? left.copy() : null, right != null ? right.copy()
            : null);
    }
}
