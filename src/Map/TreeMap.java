package Map;


import Lists.Linked.Doubly.DoublyLinkedList;
import Lists.Linked.Singly.SinglyLinkedList;
import Lists.LinkedList;
import Set.KeySet;
import Trees.BTNode;

import java.util.Comparator;
import java.util.Objects;

public class TreeMap<Key, Value> implements Map<Key, Value>
{
    private final Value defaultValue;
    private final Comparator<Key> comparator;
    private BTNode<KeyValuePair<Key, Value>> head;
    private int size;

    public TreeMap(Comparator<Key> comparator, Value defaultValue)
    {
        this.defaultValue = defaultValue;
        this.comparator = comparator;
        this.size = 0;
    }

    public TreeMap(TreeMap<Key, Value> source)
    {
        this.head = source.head.copy();
        this.comparator = source.comparator;
        this.size = source.size;
        this.defaultValue = source.defaultValue;
    }

    public Value put(Key key, Value value)
    {
        size++;
        if (head != null)
            return put(key, value, head);

        head = new BTNode<>(null, new KeyValuePair<>(key, value));
        return null;
    }

    private Value put(Key key, Value value, BTNode<KeyValuePair<Key, Value>> current)
    {
        if (comparator.compare(key, current.getValue().getKey()) < 1)
        {
            if (current.getValue().getKey() == key)
            {
                Value old = current.getValue().getValue();
                current.getValue().setValue(value);
                return old;
            }

            if (current.getLeft() == null)
            {
                current.setLeft(new BTNode<>(current, new KeyValuePair<>(key, value)));
                return null;
            }

            return put(key, value, current.getLeft());
        }
        else
        {
            if (current.getRight() == null)
            {
                current.setRight(new BTNode<>(current, new KeyValuePair<>(key, value)));
                return null;
            }

            return put(key, value, current.getRight());
        }
    }

    public Value get(Key key)
    {
        BTNode<KeyValuePair<Key, Value>> result = get(key, head);
        return result != null ? result.getValue().getValue() : null;
    }

    private BTNode<KeyValuePair<Key, Value>> get(Key key, BTNode<KeyValuePair<Key, Value>> current)
    {
        if (current == null)
            return null;
        int comparison = comparator.compare(key, current.getValue().getKey());
        if (comparison == 0 && key == current.getValue().getKey())
            return current;
        else if (comparison <= 0)
            return get(key, current.getLeft());
        else
            return get(key, current.getRight());
    }


    public Value remove(Key entry) {
        return removeNode(entry).getValue().getValue();
    }

    protected BTNode<KeyValuePair<Key, Value>> findBiggestNode(BTNode<KeyValuePair<Key, Value>> subRoot) {
        BTNode<KeyValuePair<Key, Value>> current = subRoot;
        while (current.getRight() != null)
            current = current.getRight();
        return current;
    }

    protected BTNode<KeyValuePair<Key, Value>> findSmallestNode(BTNode<KeyValuePair<Key, Value>> subRoot) {
        BTNode<KeyValuePair<Key, Value>> current = subRoot;
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    protected BTNode<KeyValuePair<Key, Value>> getCandidate(Key entry, BTNode<KeyValuePair<Key, Value>> current) {
        if (current.getValue().getKey().equals(entry)) return current;
        int result = comparator.compare(entry, current.getValue().getKey());
        if (result <= 0 && current.getLeft() != null) return getCandidate(entry, current.getLeft());
        else if (result > 0 && current.getRight() != null) return getCandidate(entry, current.getRight());
        return current;
    }

    protected BTNode<KeyValuePair<Key, Value>> removeNode(Key entry) {
        BTNode<KeyValuePair<Key, Value>> candidate = getCandidate(entry, head);

        if (!Objects.equals(candidate.getValue().getKey(), entry)) return null;

        boolean isLeftChild = candidate.getParent() != null && candidate.equals(candidate.getParent().getLeft());

        size--;
        BTNode<KeyValuePair<Key, Value>> parent = candidate.getParent();
        if (candidate.getLeft() == null && candidate.getRight() == null) {
            if (parent == null)
                head = null;
            else if (isLeftChild)
                parent.setLeft(null);
            else
                parent.setRight(null);
            return candidate;
        }

        replace(candidate, isLeftChild);

        return candidate;
    }

    protected void replace(BTNode<KeyValuePair<Key, Value>> toBeReplaced, boolean isLeftChild) {
        BTNode<KeyValuePair<Key, Value>> replacement;

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

        BTNode<KeyValuePair<Key, Value>> left = toBeReplaced.getLeft();
        BTNode<KeyValuePair<Key, Value>> right = toBeReplaced.getRight();

        replacement.setParent(toBeReplaced.getParent());
        replacement.setLeft(!replacement.equals(left) ? left : null);
        replacement.setRight(!replacement.equals(right) ? right : null);

        if(replacement.getParent() == null) head = replacement;
    }

    public boolean containsKey(Key key)
    {
        return containsKey(key, head);
    }

    private boolean containsKey(Key key, BTNode<KeyValuePair<Key, Value>> current)
    {
        if (current == null)
            return false;
        int comparison = comparator.compare(key, current.getValue().getKey());
        if (comparison == 0 && key == current.getValue().getKey())
            return true;
        else if (comparison <= 0)
            return containsKey(key, current.getLeft());
        else
            return containsKey(key, current.getRight());
    }

    public String toString()
    {
        return head != null ? head.traverseInorder().toString() : "";
    }

    public int getSize()
    {
        return size;
    }

    public DoublyLinkedList<Key> getKeys()
    {
        DoublyLinkedList<Key> keys = DoublyLinkedList.of();
        addKey(keys, head);
        return keys;
    }

    private void addKey(DoublyLinkedList<Key> list, BTNode<KeyValuePair<Key, Value>> current) {
        if (current == null) return;
        addKey(list, current.getLeft());
        list.push(current.getValue().getKey());
        addKey(list, current.getRight());
    }

    public DoublyLinkedList<KeyValuePair<Key, Value>> getEntries() {
        DoublyLinkedList<KeyValuePair<Key, Value>> entries = DoublyLinkedList.of();
        addEntry(entries, head);
        return entries;
    }

    private void addEntry(DoublyLinkedList<KeyValuePair<Key, Value>> list, BTNode<KeyValuePair<Key, Value>> current) {
        if (current == null) return;
        addEntry(list, current.getLeft());
        list.push(current.getValue().copy());
        addEntry(list, current.getRight());
    }

    public void putIfAbsent(Key key, Value value) {
        if (containsKey(key)) return;
        put(key, value);
    }

    public int size() {
        return size;
    }

    @Override
    public KeySet<Key, Value> mutableKeySet() {
        return new KeySet<Key, Value>(this, defaultValue);
    }

    public SinglyLinkedList<Key> keys() {
        LinkedList<KeyValuePair<Key, Value>> list = head.traverseInorder();
        SinglyLinkedList<Key> keyList = new SinglyLinkedList<>();
        for (KeyValuePair<Key, Value> entry : list)
            keyList.push(entry.getKey());
        return keyList;
    }

    public TreeMap<Key, Value> copy() {
        return new TreeMap<>(this);
    }
}
