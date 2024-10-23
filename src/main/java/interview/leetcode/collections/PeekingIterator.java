package interview.leetcode.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/peeking-iterator/
 *
 * Design an iterator that supports the peek operation on an existing iterator in addition to the hasNext and the next operations.
 *
 * Implement the PeekingIterator class:
 *
 * PeekingIterator(Iterator<int> nums) Initializes the object with the given integer iterator iterator.
 * int next() Returns the next element in the array and moves the pointer to the next element.
 * boolean hasNext() Returns true if there are still elements in the array.
 * int peek() Returns the next element in the array without moving the pointer.
 */
public class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    Integer next = null;
    boolean hasNext;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.hasNext = iterator.hasNext();
        if (hasNext) {
            this.next = iterator.next();
        }
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (iterator.hasNext()) {
            final Integer r = next;
            next = iterator.next();
            return r;
        }
        else {
            hasNext = false;
            return next;
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }


    public static void main(String[] args) {
        final PrimitiveIterator.OfInt it = IntStream
                .range(0, 10)
                .iterator();

        final PeekingIterator pit = new PeekingIterator(it);
        while (pit.hasNext()) {
            System.out.println(pit.peek());
            Integer next = pit.next();
            System.out.println(next);
            System.out.println(pit.peek());
            System.out.println("--");
        }
    }
}
