package Map;


import Lists.Linked.Singly.SinglyLinkedList;
import Lists.LinkedList;
import Set.HashSet;
import Set.KeySet;

public class HashMap<Key, Value> implements Map<Key, Value> {
    private final Value defaultValue;
    private int hashedPlaces = 97;
    protected SinglyLinkedList<KeyValuePair<Key, Value>>[] arr;
    private int usedSize = 0;

    /**
     * Initializes this map as an empty map.
     */
    public HashMap() {
        this((Value) null);
    }

    public HashMap(Value defaultValue) {
        arr = new SinglyLinkedList[hashedPlaces];
        this.defaultValue = defaultValue;
    }

    /**
     * Initializes this map as an independent copy of the specified map. Later changes of 'this'
     * will not affect 'map' and vice versa.
     */
    public HashMap(HashMap<Key, Value> map) {
        defaultValue = map.defaultValue;
        usedSize = map.usedSize;
        arr = new SinglyLinkedList[hashedPlaces];
        for (int i = 0; i < hashedPlaces; i++) {
            if (map.arr[i] == null)
                continue;
            arr[i] = map.arr[i].deepCopy();
        }
    }

    private int hashKey(Key key) {
        return key.hashCode() % hashedPlaces;
    }

    @Override
    public Value get(Key key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return null;
        for (KeyValuePair<Key, Value> next : arr[pos]) {
            if (next.getKey() == key)
                return next.getValue();
        }
        return null;
    }

    @Override
    public Value put(Key key, Value value) {
        int pos = hashKey(key);
        if (arr[pos] == null) {
            arr[pos] = new SinglyLinkedList<>();
            arr[pos].push(new KeyValuePair<>(key, value));
            usedSize++;
            return null;
        }

        for (KeyValuePair<Key, Value> next : arr[pos]) {
            if (next.getKey() != key)
                continue;
            Value oldValue = next.getValue();
            next.setValue(value);
            return oldValue;
        }

        arr[pos].push(new KeyValuePair<>(key, value));
        usedSize++;
        if (usedSize / hashedPlaces == 1) rehash();
        return null;
    }

    @Override
    public Value remove(Key key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return null;

        var iterator = arr[pos].iterator();
        for (int i = 0; i < arr[pos].size(); i++) {
            var next = iterator.next();
            if (next.getKey() != key)
                continue;
            usedSize--;
            return arr[pos].remove(i).getValue();
        }
        return null;
    }

    @Override
    public boolean containsKey(Key key) {
        int pos = hashKey(key);
        if (arr[pos] == null)
            return false;

        for (KeyValuePair<Key, Value> next : arr[pos]) {
            if (next.getKey() == key)
                return true;
        }

        return false;
    }

    @Override
    public int size() {
        return usedSize;
    }

    @Override
    public KeySet<Key, Value> mutableKeySet() {
        return new KeySet<Key, Value>(this, defaultValue);
    }

    public HashSet<Key> keysAsSet() {
        HashSet<Key> result = new HashSet<Key>();
        for (var entry : arr) {
            if (entry == null)
                continue;
            for (KeyValuePair<Key, Value> keyValuePair : entry) {
                result.add(keyValuePair.getKey());
            }
        }
        return result;
    }

    @Override
    public LinkedList<Key> keys() {
        LinkedList<Key> list = new SinglyLinkedList<>();
        for (var entry : arr) {
            if (entry == null) continue;
            for (var keyValuePair : entry)
                list.push(keyValuePair.getKey());
        }
        return list;
    }

    private void rehash() {
        this.hashedPlaces = Math.min(hashedPlaces*2, Integer.MAX_VALUE);
        SinglyLinkedList<KeyValuePair<Key, Value>>[] oldEntries = arr;
        arr = new SinglyLinkedList[hashedPlaces];
        for (SinglyLinkedList<KeyValuePair<Key, Value>> list : oldEntries)
            for (KeyValuePair<Key, Value> kvp : list) put(kvp.getKey(), kvp.getValue());
    }

    public HashMap<Key, Value> copy() {
        return new HashMap<Key, Value>(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) sb.append(arr[i].toString());
            else if (!sb.isEmpty() && i + 1 < arr.length && arr[i] != null) sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;
        HashMap<Key, Value> map = (HashMap<Key, Value>) obj;
        if (map.usedSize != usedSize) return false;
        for (Key var : keysAsSet())
            if (!get(var).equals(map.get(var))) return false;
        return true;
    }
}
