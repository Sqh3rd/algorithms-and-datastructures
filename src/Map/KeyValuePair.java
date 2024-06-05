package Map;


import Other.Copy;

public class KeyValuePair<Key, Value> implements Copy<KeyValuePair<Key, Value>>
{
    private final Key key;
    private Value value;

    public KeyValuePair(Key key, Value value)
    {
        this.key = key;
        this.value = value;
    }

    public Key getKey()
    {
        return key;
    }

    public Value getValue()
    {
        return value;
    }

    public void setValue(Value value)
    {
        this.value = value;
    }

    @Override
    public KeyValuePair<Key, Value> copy() {
        return new KeyValuePair<>(key, value);
    }

    @Override
    public String toString() {
        return String.format("{ %s: %s }", key.toString(), value.toString());
    }
}
