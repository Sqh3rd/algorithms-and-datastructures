package Set;


import Map.Map;
import Queues.Queue;
import Queues.QueueIterator;

import java.util.Iterator;

public class KeySet<Key, Value> implements Set<Key> {
    private final Map<Key, Value> map;
    private final Value defaultValue;

    public KeySet(Map<Key, Value> map, Value defaultValue) {
        this.map = map;
        this.defaultValue = defaultValue;
    }

    @Override
    public void add(Key key) {
        if (map.containsKey(key)) return;
        map.put(key, defaultValue);
    }

    @Override
    public boolean contains(Key key) {
        return map.containsKey(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterator<Key> iterator() {
        return new QueueIterator<>(Queue.of(map.keys()));
    }
}
