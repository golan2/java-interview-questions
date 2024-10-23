package interview.leetcode.collections;

import java.util.NoSuchElementException;

/**
 *
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 *
 * @noinspection unused
 */
public class MedianFinder100 {

    static class BucketArray {
        int [] arr = new int[101];

        public void add(int num) {
            arr[num]++;
        }

        public BucketArrayIterator iterator() {
            return new BucketArrayIterator(this.arr);
        }
    }

    /**
     * Iterate the values in {@link BucketArray} as if it was a simple array.
     * For example if we have a {@link BucketArray} that looks like this:
     *   arr[0] = 3
     *   arr[1] = 2
     *   arr[2] = 0
     *   arr[3] = 1
     * Then the corresponding regular array is:
     * 0,0,0,1,1,3
     * And this is what we expect this iterator to return.
     *
     * We do it by keeping track on which {@link #bucketIndex} we are now and what {@link #placeInsideBucket} we stand.
     * The {@link #bucketIndex} is an index of the array, so it starts with zero
     * The {@link #placeInsideBucket} represents which number in the bucket hence starts with 1
     */
    static class BucketArrayIterator {
        private static final int FIRST_PLACE = 1;

        private final int [] arr;      //pointer to the array we traverse
        private int bucketIndex = 0;
        private int placeInsideBucket = FIRST_PLACE;


        public BucketArrayIterator(int[] arr) {
            this.arr = arr;
        }

        /**
         * This is not a "classic" has next
         * It will change the position of the {@link #bucketIndex} if it sees that it all zeroes.
         * But it is ok because the {@link #next()} will "enjoy" this implicit increment as well
         *
         * When this method ends
         *      - The {@link #bucketIndex} will point to the bucket where next item reside
         *      - The {@link #placeInsideBucket} will point to the item BEFORE the next one (so the {@link #next()} will promote it)
         * UNLESS
         * There is no next and in this cse it will be after the last bucket
         */
        public boolean hasNext() {
            if (bucketIndex >= arr.length) {
                return false;
            }
            if (currentBucketIsNotUtilized()) {
                return true;
            }
            skipToTheNextNonEmptyBucket();
            placeInsideBucket = FIRST_PLACE - 1;    //why -1?  because we want to be before the next
            return bucketIndex < arr.length;
        }

        public int next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            placeInsideBucket++;
            return bucketIndex;
        }

        public boolean hasPrevious() {
            if (bucketIndex < 0) {
                return false;
            }
            if (currentBucketIsNotUtilized()) {
                return true;
            }
            skipToThePreviousNonEmptyBucket();
            if (bucketIndex > 0) {
                placeInsideBucket = FIRST_PLACE - 1;    //why -1?  because we want to be after the previous
            }
            return bucketIndex > 0;

        }

        public int previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            placeInsideBucket++;
            return bucketIndex;
        }

        private boolean currentBucketIsNotUtilized() {
            return bucketIndex >= 0 &&
                    bucketIndex < arr.length &&
                    placeInsideBucket < arr[bucketIndex];
        }

        private void skipToTheNextNonEmptyBucket() {
            bucketIndex++;
            while (bucketIndex < arr.length  &&  arr[bucketIndex] == 0) {
                bucketIndex++;
            }
        }

        private void skipToThePreviousNonEmptyBucket() {
            bucketIndex--;
            while (bucketIndex > 0  &&  arr[bucketIndex] == 0) {
                bucketIndex--;
            }
        }
    }
}
