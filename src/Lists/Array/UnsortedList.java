package Lists.Array;

public class UnsortedList<T> extends ProtoList<T>
{

    protected UnsortedList(T[] array, int multiplicativeScaling, int additiveScaling)
    {
        super(array, multiplicativeScaling, additiveScaling);
    }

    public static <T> UnsortedListBuilder<T> builder()
    {
        return new UnsortedListBuilder<>();
    }

    public T replace(T newElement, int index)
    {
        int convertedIndex = convertIndex(index);
        if (convertedIndex >= nextIndex)
            throw new IndexOutOfBoundsException(String.format("No element to replace at index %s!", convertedIndex));
        T replacedElement = internalArray[convertedIndex];
        internalArray[convertedIndex] = newElement;
        return replacedElement;
    }

    public void push(T element)
    {
        if (nextIndex >= internalArray.length) extendInternalArray();
        internalArray[nextIndex++] = element;
    }

    @Override
    public void lshift(int numberOfElements)
    {
        super.lshift(numberOfElements);
    }

    @Override
    public int size()
    {
        return super.size();
    }

    @Override
    public int indexOf(T element)
    {
        return super.indexOf(element);
    }

    @Override
    public boolean contains(T element)
    {
        return super.contains(element);
    }

    public UnsortedListBuilder<T> toBuilder()
    {
        return new UnsortedListBuilder<>(internalArray, multiplicativeScaling, additiveScaling);
    }

    public static class UnsortedListBuilder<T> extends ProtoListBuilder<T, UnsortedListBuilder<T>>
    {
        UnsortedListBuilder()
        {
            super();
        }
        UnsortedListBuilder(T[] initialArray, int multiplicativeScaling, int additiveScaling)
        {
            super(initialArray, multiplicativeScaling, additiveScaling);
        }

        public UnsortedList<T> build()
        {
            return new UnsortedList<>(initialArray, multiplicativeScaling, additiveScaling);
        }
    }
}
