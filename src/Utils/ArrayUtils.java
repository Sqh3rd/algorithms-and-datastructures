package Utils;

import java.util.Comparator;

public class ArrayUtils
{
    public static class General
    {
        public static <T> T[] extend(T[] source, int extendBy)
        {
            T[] target = newArray(source.length + extendBy);
            for (int i = 0; i < source.length; i++)
            {
                target[i] = source[i];
            }
            return target;
        }

        public static <T> T[] insertAtIndex(T[] source, T element, int index)
        {
            if (index < 0 || index >= source.length)
                throw new IllegalArgumentException(String.format("Index %s out of bonds for length %s", index, source.length-1));
            T[] target = newArray(source.length);
            for (int i = target.length - 2; i >= 0; i--)
            {
                if (i > index)
                    target[i + 1] = source[i];
                else if (i == index)
                    target[i] = element;
                else
                    target[i] = source[i];
            }
            return target;
        }

        public static <T> void insertAtIndexIP(T[] source, T element, int index)
        {
            if (index < 0 || index >= source.length)
                throw new IllegalArgumentException(String.format("Index %s out of bonds for length %s", index, source.length - 1));
            for (int i = source.length - 2; i >= index; i--)
            {
                source[i + 1] = source[i];
                if (i == index)
                    source[i] = element;
            }
        }

        public static <T> T[] newArray(int length)
        {
            @SuppressWarnings("unchecked")
            T[] target = (T[]) new Object[length];
            return target;
        }

        /**
         * Returns a new array containing only entries of the original array from index start (inclusive) to index end (exclusive).
         * <br>
         * <pre>
         * {@code
         *     Integer[] array = new Integer[5];
         *     for (int i = 0; i < 5; i++)
         *          array[i] = i;
         *     splice(array, 1, 3) // returns [1, 2]
         * }
         * </pre>
         *
         * @param array the array to splice
         * @param start the start index (inclusive)
         * @param end   the end index (exclusive)
         * @return the spliced array
         */
        public static <T> T[] splice(T[] array, int start, int end)
        {
            T[] spliced = newArray(end - start);
            for (int i = start; i < end; i++)
            {
                spliced[i - start] = array[i];
            }
            return spliced;
        }
    }

    public static class Search
    {
        public static <T> int binarySearch(T[] sortedArray, Comparator<T> comparator, T query, int maxIndex,
            boolean returnNext)
        {
            if (sortedArray.length == 0 || maxIndex < 0)
                return -1;
            if (comparator.compare(query, sortedArray[maxIndex]) > 0)
                return returnNext ? maxIndex + 1 : -1;
            else if (comparator.compare(query, sortedArray[0]) < 0)
                return returnNext ? 0 : -1;

            int min = 0;
            int max = maxIndex;
            int mid = 0;
            while (min <= max)
            {
                mid = min + ((max - min) / 2);
                if (comparator.compare(query, sortedArray[mid]) > 0)
                {
                    min = mid + 1;
                }
                else if (comparator.compare(query, sortedArray[mid]) < 0)
                {
                    max = mid - 1;
                }
                else
                {
                    return mid;
                }
            }
            return returnNext ? mid + Math.max(comparator.compare(query, sortedArray[mid]), 0) : -1;
        }
    }

    public static class Sort
    {
        /**
         * Clones the given array, sorts the copy and then returns the sorted copy
         *
         * @param array      the array to sort
         * @param comparator a comparator to compare the transformed values
         * @param <T>        the type of the array entries
         * @return a sorted copy of the original array
         */
        public static <T> T[] quicksort(T[] array, Comparator<T> comparator)
        {
            T[] target = array.clone();
            quicksort(target, comparator, 0, target.length - 1);
            return target;
        }

        private static <T> void quicksort(T[] array, Comparator<T> comparator, int begin, int end)
        {
            if (begin >= end)
                return;
            int partitionIndex = partition(array, comparator, begin, end);

            quicksort(array, comparator, begin, partitionIndex - 1);
            quicksort(array, comparator, partitionIndex + 1, end);
        }

        private static <T> int partition(T[] array, Comparator<T> comparator, int begin, int end)
        {
            T pivot = array[end];
            int i = begin - 1;

            for (int j = begin; j < end; j++)
            {
                if (array[j] == null || pivot == null || comparator.compare(array[j], pivot) > 0)
                    continue;
                i++;
                swap(array, i, j);
            }
            swap(array, i + 1, end);
            return i + 1;
        }

        private static <T> void swap(T[] array, int i, int j)
        {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
