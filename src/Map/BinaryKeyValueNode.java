package Map;

import Other.Copy;

class BinaryKeyValueNode<Key, Value> implements Copy<BinaryKeyValueNode<Key, Value>>
{
    private final Key key;
    private Value value;

    private BinaryKeyValueNode<Key, Value> left;
    private BinaryKeyValueNode<Key, Value> right;

    BinaryKeyValueNode(Key key, Value value)
    {
        this(key, value, null, null);
    }

    private BinaryKeyValueNode(Key key, Value value, BinaryKeyValueNode<Key, Value> left, BinaryKeyValueNode<Key, Value> right)
    {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
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

    public BinaryKeyValueNode<Key, Value> getLeft()
    {
        return left;
    }

    public void setLeft(BinaryKeyValueNode<Key, Value> left)
    {
        this.left = left;
    }

    public BinaryKeyValueNode<Key, Value> getRight()
    {
        return right;
    }

    public void setRight(BinaryKeyValueNode<Key, Value> right)
    {
        this.right = right;
    }

    public BinaryKeyValueNode<Key, Value> copy()
    {
        return new BinaryKeyValueNode<Key, Value>(key,
            value,
            left == null ? null : left.copy(),
            right == null ? null : right.copy());
    }

    public String toString()
    {
        String ownRepresentation = String.format("%s: %s", key.toString(), value.toString());
        String leftRepresentation = left == null ? "" : left.toString() + ", ";
        String rightRepresentation = right == null ? "" : ", " + right.toString();

        return String.format("{ %s%s%s }", leftRepresentation, ownRepresentation, rightRepresentation);
    }
}
