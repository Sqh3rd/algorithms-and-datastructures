package Queues;


import java.util.Iterator;

public class QueueIterator<T> implements Iterator<T> {
    private final Queue<T> queue;

    public QueueIterator(Queue<T> queue) {
        this.queue = queue;
    }

    @Override
    public T next() {
        return queue.poll();
    }

    @Override
    public boolean hasNext() {
        return queue.size() > 0;
    }
}
