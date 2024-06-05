package Trees;


import Lists.Linked.Singly.SinglyLinkedList;
import Lists.LinkedList;
import Queues.Queue;
import Queues.QueueIterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public class BinaryTree<T> implements Tree<T> {
    protected final Comparator<T> comparator;
    protected BTNode<T> root;

    public BinaryTree(Comparator<T> comparator) {
        this(comparator, null);
    }

    public BinaryTree(Comparator<T> comparator, T rootValue) {
        this.comparator = comparator;
        if (rootValue != null)
            this.root = new BTNode<>(null, rootValue);
    }

    @Override
    public void add(T entry) {
        addNode(entry);
    }

    protected BTNode<T> addNode(T entry) {
        BTNode<T> candidate = getCandidate(entry);
        if (candidate == null) {
            root = new BTNode<>(null, entry);
            return root;
        }

        BTNode<T> newNode = new BTNode<>(candidate, entry);
        int comparison = comparator.compare(entry, candidate.getValue());

        if (comparison <= 0) candidate.setLeft(new BTNode<>(candidate, entry));
        else candidate.setRight(new BTNode<>(candidate, entry));
        return newNode;
    }

    public BTNode<T> getCandidate(T entry) {
        if (root == null) return null;

        BTNode<T> current = root;
        while (true) {
            int result = comparator.compare(entry, current.getValue());
            if (result == 0 && Objects.equals(entry, current.getValue()))
                return current;
            if (result < 0 || result == 0) {
                if (current.getLeft() == null)  return current;
                else current = current.getLeft();
            } else {
                if (current.getRight() == null) return current;
                else current = current.getRight();
            }
        }
    }

    @Override
    public T remove(T entry) {
        return removeNode(entry).getValue();
    }

    protected BTNode<T> findBiggestNode(BTNode<T> subRoot) {
        BTNode<T> current = subRoot;
        while (current.getRight() != null)
            current = current.getRight();
        return current;
    }

    protected BTNode<T> findSmallestNode(BTNode<T> subRoot) {
        BTNode<T> current = subRoot;
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    protected BTNode<T> removeNode(T entry) {
        BTNode<T> candidate = getCandidate(entry);

        if (!Objects.equals(candidate.getValue(), entry)) return null;

        boolean isLeftChild = candidate.getParent() != null && candidate.equals(candidate.getParent().getLeft());

        BTNode<T> parent = candidate.getParent();
        if (candidate.getLeft() == null && candidate.getRight() == null) {
            if (parent == null)
                root = null;
            else if (isLeftChild)
                parent.setLeft(null);
            else
                parent.setRight(null);
            return candidate;
        }

        replace(candidate, isLeftChild);

        return candidate;
    }

    protected void replace(BTNode<T> toBeReplaced, boolean isLeftChild) {
        BTNode<T> replacement;

        if (toBeReplaced.getLeft() != null) {
            replacement = findBiggestNode(toBeReplaced.getLeft());

            if (!toBeReplaced.equals(replacement.getParent()))
                replacement.getParent().setRight(null);
        } else {
            replacement = findSmallestNode(toBeReplaced.getRight());

            if (!toBeReplaced.equals(replacement.getParent()))
                replacement.getParent().setLeft(null);
        }

        if (toBeReplaced.getParent() != null) {
            if (isLeftChild)
                toBeReplaced.getParent().setLeft(replacement);
            else
                toBeReplaced.getParent().setRight(replacement);
        }

        BTNode<T> left = toBeReplaced.getLeft();
        BTNode<T> right = toBeReplaced.getRight();

        replacement.setParent(toBeReplaced.getParent());
        replacement.setLeft(!replacement.equals(left) ? left : null);
        replacement.setRight(!replacement.equals(right) ? right : null);

        if(replacement.getParent() == null) root = replacement;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(Queue.of(root.traverseInorder()));
    }

    @Override
    public LinkedList<T> traverseInorder() {
        return root != null ? root.traverseInorder() : new SinglyLinkedList<>();
    }

    @Override
    public LinkedList<T> traversePreorder() {
        return root != null ? root.traversePreorder() : new SinglyLinkedList<>();
    }

    @Override
    public String toString() {
        return traverseInorder().toString();
    }
}
