package Lists.Array;


import Lists.List;
import Lists.Scaling;
import Queues.Queue;
import Queues.QueueIterator;
import Utils.ArrayUtils;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

abstract class ProtoList<T> implements List<T>
{
    protected final int multiplicativeScaling;
    protected final int additiveScaling;
    protected T[] internalArray;
    protected int nextIndex = 0;

    protected ProtoList(T[] internalArray, int multiplicativeScaling, int additiveScaling)
    {
        this.internalArray = internalArray;
        this.additiveScaling = additiveScaling;
        this.multiplicativeScaling = multiplicativeScaling;
        this.nextIndex = determineNextIndex();
    }

    protected int determineNextIndex()
    {
        for (int i = 0; i < internalArray.length; i++)
        {
            if (internalArray[i] == null)
                return i;
        }
        return internalArray.length;
    }

    protected void extendInternalArray()
    {
        int extendBy = internalArray.length * multiplicativeScaling + additiveScaling;
        internalArray = ArrayUtils.General.extend(internalArray, extendBy);
    }

    protected void insert(T element, int index)
    {
        if (nextIndex == internalArray.length)
            extendInternalArray();
        ArrayUtils.General.insertAtIndexIP(internalArray, element, convertIndex(index));
        nextIndex++;
    }

    public T pop()
    {
        return remove(nextIndex);
    }

    public T remove(int index)
    {
        int convertedIndex = convertIndex(index);
        T removed = internalArray[convertedIndex];
        for (int i = convertedIndex; i < internalArray.length - 1; i++)
        {
            if (internalArray[i] == null)
                break;
            internalArray[i] = internalArray[i + 1];
        }
        nextIndex--;
        return removed;
    }

    public void lshift(int numberOfElements)
    {
        if (numberOfElements <= 0) return;
        for (int i = numberOfElements; i < internalArray.length; i++)
            internalArray[i - numberOfElements] = internalArray[i];
        for (int i = internalArray.length - numberOfElements; i < nextIndex; i++)
            internalArray[i] = null;
        nextIndex -= numberOfElements;
    }

    public T get(int index)
    {
        return internalArray[convertIndex(index)];
    }

    /**
     * @return The number of items added to the list
     */
    public int count()
    {
        return nextIndex;
    }

    /**
     * @return The length of the internal array used to store elements
     */
    public int size()
    {
        return internalArray.length;
    }

    public int indexOf(T element)
    {
        for (int i = 0; i < nextIndex; i++)
        {
            if (Objects.equals(internalArray[i], element))
                return i;
        }
        return -1;
    }

    public boolean contains(T element)
    {
        return indexOf(element) != -1;
    }

    protected int convertIndex(int index)
    {
        int convertedIndex = index;
        if (index < 0)
            convertedIndex += internalArray.length;
        return convertedIndex;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(Queue.of(internalArray));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (var element : internalArray) action.accept(element);
    }

    public static abstract class ProtoListBuilder<T, B extends ProtoListBuilder<T, B>>
    {
        protected T[] initialArray;
        protected int multiplicativeScaling;
        protected int additiveScaling;

        protected ProtoListBuilder()
        {
            this.initialArray = (T[]) new Object[0];
            this.multiplicativeScaling = Scaling.Multiplicative.DOUBLE;
            this.additiveScaling = 0;
        }

        protected ProtoListBuilder(T[] initialArray, int multiplicativeScaling, int additiveScaling)
        {
            this.initialArray = initialArray.clone();
            this.multiplicativeScaling = multiplicativeScaling;
            this.additiveScaling = additiveScaling;
        }

        public B withInitialArray(T[] initialArray)
        {
            this.initialArray = initialArray.clone();
            return (B) this;
        }

        public B withMultiplicativeScaling(int multiplicativeScaling)
        {
            this.multiplicativeScaling = multiplicativeScaling;
            return (B) this;
        }

        public B withAdditiveScaling(int additiveScaling)
        {
            this.additiveScaling = additiveScaling;
            return (B) this;
        }
    }
}
