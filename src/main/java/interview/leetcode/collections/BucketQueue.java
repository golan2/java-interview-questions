package interview.leetcode.collections;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public abstract class BucketQueue implements Queue<Integer> {
    protected int minMax = -1;
    protected final int[] arr = new int[101];

    @Override
    public int size() {
        return Arrays.stream(arr)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public Integer peek() {
        return minMax;
    }

    @Override
    public boolean add(Integer number) {
        arr[number]++;
        if (arr[number] == 1) {
            //this is the first time we add this number, so it might be the new min/max
            updateMinMax(number);
        }
        return true;
    }

    @Override
    public Integer poll() {
        if (minMax == -1) {
            return null;
        }
        arr[minMax]--;
        int res = minMax;
        if (arr[minMax] == 0) {
            //we've just removed the last minMax
            updateMinMax(minMax);
        }
        return res;
    }

        @Override
    public boolean remove(Object o) {
        if (o instanceof Integer) {
            final int index = (Integer) o;
            if (arr[index] > 0) {
                arr[index]--;
                if (arr[index]==0 && index==minMax) {
                    //we've just removed the last minMax
                    updateMinMax(index);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Update the value of {@link #minMax}.
     * We call this method after add/remove; but only if update is needed.
     * Update is needed if
     *  - We added a new number for the first time so arr[number]=1
     *  - We removed the last {@link #minMax} so arr[number]=0
     */
    protected abstract void updateMinMax(Integer number);

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Integer> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Integer remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer element() {
        throw new UnsupportedOperationException();
    }

}
