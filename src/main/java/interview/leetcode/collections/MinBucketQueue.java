package interview.leetcode.collections;

import java.util.Arrays;
import java.util.Random;

public class MinBucketQueue extends BucketQueue {

    private static final int SIZE = 77;
    private static final int CYCLES = 30;

    @Override
    protected void updateMinMax(Integer number) {
        if (newValueWasAdded(number) && numberIsSmaller(number)) {
            minMax = number;
        }
        else if (valueWasRemoved(number)) {
            minMax = findNextLargestNumber();
        }
    }

    private boolean newValueWasAdded(int number) {
        return arr[number] == 1;
    }

    private boolean numberIsSmaller(Integer number) {
        return minMax == -1  ||  number < minMax;
    }

    private boolean valueWasRemoved(Integer number) {
        return arr[number] == 0;
    }

    private int findNextLargestNumber() {
        for (int i = minMax+1; i < arr.length; i++) {
            if (arr[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        final Random random = new Random();
        for (int i = 0; i < CYCLES; i++) {
            final MinBucketQueue q = new MinBucketQueue();

            for (int j = 0; j < SIZE; j++) {
                q.add(random.nextInt(100));
            }

            int prev = -1;
            for (int j = 0; j < SIZE; j++) {
                final Integer current = q.poll();
                if (current < prev) {
                    System.out.println(Arrays.toString(q.arr));
                }
                prev = current;
            }

        }

    }
}
