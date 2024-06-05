package Set;


import Lists.Linked.Singly.SinglyLinkedList;
import Queues.Queue;
import Queues.QueueIterator;

import java.util.Iterator;

public class HashSet<T> implements Set<T>, Iterable<T> {
    private int maxSize = 97;
    protected SinglyLinkedList<T>[] arr = new SinglyLinkedList[maxSize];
    private int size = 0;

    public HashSet() {}

    public HashSet(Iterator<T> iter) {
        while (iter.hasNext()) {
            add(iter.next());
        }
    }

    public HashSet(T ...elements) {
        for (T element : elements) {
            add(element);
        }
    }

    public HashSet(Iterator<T> ...iterators) {
        addAll(iterators);
    }

    public HashSet(Iterable<T> ...iterables) {
        addAll(iterables);
    }

    private int hashedPos(T value) {
        return value.hashCode() % maxSize;
    }

    @Override
    public void add(T value) {
        if (value == null) return;
        int pos = hashedPos(value);
        if (arr[pos] == null) {
            arr[pos] = new SinglyLinkedList<>();
            arr[pos].push(value);
            size++;
            return;
        }
        for (T next : arr[pos]) {
            if (next.equals(value))
                return;
        }
        arr[pos].push(value);
        size++;
        if (size / maxSize == 1) rehash();
    }

    private void rehash() {
        maxSize = Math.min(maxSize * 2, Integer.MAX_VALUE);
        SinglyLinkedList<T>[] oldEntries = arr;
        arr = new SinglyLinkedList[maxSize];
        addAll(oldEntries);
    }

    public void addAll(Iterator<T> ...iterators) {
        for (Iterator<T> iter : iterators) {
            while (iter.hasNext())
                add(iter.next());
        }
    }

    public void addAll(Iterable<T> ...iterables) {
        Iterator<T>[] iterators = new Iterator[iterables.length];
        for (int i = 0; i < iterables.length; i++) {
            iterators[i] = iterables[i].iterator();
        }
        addAll(iterators);
    }

    @Override
    public boolean contains(T value) {
        if (value == null) return false;
        int pos = hashedPos(value);
        if (arr[pos] == null)
            return false;
        for (var iter = arr[pos].iterator(); iter.hasNext();) {
            var next = iter.next();
            if (next.equals(value))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        Queue<T> result = new Queue<>();
        for (SinglyLinkedList<T> t : arr) {
            if (t == null) continue;
            for (T e : t)
                result.add(e);
        }
        return new QueueIterator<>(result);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) return false;
        HashSet<Object> ot = (HashSet<Object>) obj;
        if (ot.size != size) return false;
        for (T value : this)
            if (!ot.contains(value)) return false;
        return true;
    }
}
