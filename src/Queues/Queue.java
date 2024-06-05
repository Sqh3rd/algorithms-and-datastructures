package Queues;


import Lists.Linked.Singly.SinglyLinkedList;
import Lists.LinkedList;

public class Queue<T> {
    private LinkedList<T> list;

    public Queue() {
        list = new SinglyLinkedList<>();
    }

    private Queue(Queue<T> q) {
        this.list = q.list.copy();
    }

    private Queue(LinkedList<T> linkedList) {
        this.list = linkedList.copy();
    }

    private Queue(T... elements) {
        list = SinglyLinkedList.of(elements);
    }

    public static <T> Queue<T> of(T... elements) {
        return new Queue<>(elements);
    }

    public static <T> Queue<T> of(LinkedList<T> linkedList) {
        return new Queue<>(linkedList);
    }

    public static <T> Queue<T> of(Queue<T> queue) {
        return new Queue<>(queue);
    }

    public static <T> Queue<T> from(LinkedList<T> linkedList) {
        Queue<T> returnQueue = new Queue<>();
        returnQueue.list = linkedList;
        return returnQueue;
    }

    public void add(T element) {
        list.push(element);
    }

    public T poll() {
        return list.remove(0);
    }

    public Queue<T> clone() {
        return new Queue<>(this);
    }

    public int size() {
        return list.size();
    }
}
