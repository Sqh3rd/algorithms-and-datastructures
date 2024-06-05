package Lists.Array;


import Utils.ArrayUtils;

import java.util.Comparator;

public class SortedList<T> extends ProtoList<T>
{
    private final Comparator<T> comparator;

    public static <T> SortedListBuilder<T> builder() {
        return new SortedListBuilder<>();
    }

    private SortedList(T[] sortedArray, int multiplicativeScaling, int additiveScaling,
        Comparator<T> comparator)
    {
        super(sortedArray, multiplicativeScaling, additiveScaling);
        this.comparator = comparator;
    }

    public void push(T element)
    {
        int index = binarySearch(element, true);
        super.insert(element, Math.max(0, index));
    }

    @Override
    public void insert(T element, int index)
    {
        super.insert(element, index);
    }

    @Override
    public T pop()
    {
        return super.pop();
    }

    @Override
    public T remove(int index)
    {
        return super.remove(index);
    }

    @Override
    public int indexOf(T element)
    {
        return binarySearch(element, false);
    }

    public boolean contains(T element)
    {
        return indexOf(element) != -1;
    }

    public int binarySearch(T element, boolean returnNext)
    {
        return ArrayUtils.Search.binarySearch(internalArray, comparator, element, nextIndex - 1, returnNext);
    }

    public static class SortedListBuilder<T>
        extends ProtoListBuilder<T, SortedListBuilder<T>>
    {
        private Comparator<T> comparator;

        public SortedListBuilder<T> withComparator(Comparator<T> comparator)
        {
            this.comparator = comparator;
            return this;
        }

        public SortedList<T> build()
        {
            T[] sortedArray = ArrayUtils.Sort.quicksort(initialArray, comparator);
            return new SortedList<>(sortedArray, multiplicativeScaling, additiveScaling, comparator);
        }
    }
}
