package Map;


import Lists.LinkedList;
import Set.KeySet;

public interface Map<Key, Value> {
    Value get(Key key);
    Value put(Key key, Value value);
    Value remove(Key key);
    boolean containsKey(Key key);
    int size();
    KeySet<Key, Value> mutableKeySet();
    LinkedList<Key> keys();
}
