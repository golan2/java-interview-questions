package interview.leetcode.collections;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value and the median is the mean of the two middle values.
 *
 * For example, for arr = [2,3,4], the median is 3.
 * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 * Implement the MedianFinder class:
 *
 * MedianFinder() initializes the MedianFinder object.
 * void addNum(int num) adds the integer num from the data stream to the data structure.
 * double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 *
 */
public class MedianFinder {
    private final Queue<Integer> min = new PriorityQueue<>();
    private final Queue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());

    public void addNum(int num) {
        if (num > findMedian()) {
            min.add(num);
        }
        else {
            max.add(num);
        }

        if (min.size() > max.size() + 1) {
            max.add( min.poll() );
        }
        if (max.size() > min.size() +1) {
            min.add( max.poll() );
        }
    }

    public double findMedian() {
        if (min.isEmpty() && max.isEmpty()) {
            return -1;
        }
        if (min.isEmpty()) {
            return max.peek();
        }
        if (max.isEmpty()) {
            return min.peek();
        }

        if (min.size() > max.size()) {
            return min.peek();
        }
        if (max.size() > min.size()) {
            return max.peek();
        }
        return (min.peek() + max.peek()) / 2.0;

    }

    public static void main(String[] args) {
        final MedianFinder mf = new MedianFinder();
        mf.addNum(3);
        mf.addNum(5);
        mf.addNum(7);
        if (mf.findMedian() != 5) throw new IllegalStateException();
        mf.addNum(1);
        if (mf.findMedian() != 4) throw new IllegalStateException();
    }
}
