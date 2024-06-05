package Map;

import Lists.Array.SortedList;

import java.util.Comparator;


public class SortedMap<Key, Value>
{
    private final SortedList<KeyValuePair<Key, Value>> list;
    private final Comparator<Key> comparator;

    protected SortedMap(int initialCapacity, Comparator<Key> comparator)
    {
        this(new KeyValuePair[initialCapacity], comparator);
    }

    protected SortedMap(KeyValuePair<Key, Value>[] initialEntries, Comparator<Key> comparator)
    {
        list = SortedList.<KeyValuePair<Key, Value>>builder()
            .withInitialArray(initialEntries)
            .withComparator(convertComparator(comparator))
            .build();
        this.comparator = comparator;
    }

    public Value put(Key key, Value value)
    {
        int index = find(key);
        if (index == -1)
        {
            list.push(new KeyValuePair<>(key, value));
            return null;
        }
        else {
            KeyValuePair<Key, Value> keyValuePair = list.get(index);
            Value oldValue = keyValuePair.getValue();
            keyValuePair.setValue(value);
            return oldValue;
        }
    }

    public Value get(Key key)
    {
        int index = find(key);
        if (index == -1) return null;
        else return list.get(index).getValue();
    }

    private int find(Key key)
    {
        int index = list.binarySearch(new KeyValuePair<Key, Value>(key, null), false);
        if (index == -1 || list.get(index).getKey() == key) return index;

        int i = index - 1;
        while(i > 0 && comparator.compare(key, list.get(i).getKey()) == 0)
        {
            if (list.get(i).getKey() == key) return i;
            i--;
        }
        i = index + 1;
        while (i < list.count() && comparator.compare(key, list.get(i).getKey()) == 0)
        {
            if (list.get(i).getKey() == key) return i;
            i++;
        }
        return -1;
    }

    private Comparator<KeyValuePair<Key, Value>> convertComparator(Comparator<Key> originalComparator)
    {
        return (KeyValuePair<Key, Value> pair1, KeyValuePair<Key, Value> pair2) ->
            originalComparator.compare(pair1.getKey(), pair2.getKey());
    }
}
