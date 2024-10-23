package interview.leetcode.arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 */
@Slf4j
public class MedianTwoSortedArrays {

    public static void main(String[] args) {
        System.out.println(
                new MedianTwoSortedArrays().findMedianSortedArrays(new int[]{1,2,3,4,109,110,111}, new int[]{5,6,7})

                //-1 1 2 3

                //  1 2 3 4           9 10 11
                //           5 6 7
        );

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // log.debug("nums1 = {}", nums1);
        // log.debug("nums2 = {}", nums2);
        final double median;

        if (nums1.length == 0  &&  nums2.length == 0) {
            return Double.NaN;
        }
        if (nums1.length == 0) {
            return median(nums2, 0, nums2.length - 1);
        }
        if (nums2.length == 0) {
            return median(nums1, 0, nums1.length - 1);
        }

        if (nums1.length > nums2.length) {
            median = find(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1);
        }
        else {
            median = find(nums2, 0, nums2.length - 1, nums1, 0, nums1.length - 1);
        }
        // log.debug("MEDIAN: {}", median);
        return median;
    }

    private double find(int[] a, int aleft, int aright, int[] b, int bleft, int bright) {
        if (a.length < b.length) throw new IllegalArgumentException("The [a] should be the larger array ");
        log.info("[find] a = ({} , {})   b = ({} , {})", a[aleft], a[aright], b[bleft], b[bright]);

        //termination condition - the shorter array (or both) is over (i.e. has a single item)
        if (aleft == aright  &&  bleft == bright) {
            return (a[aleft] + b[bleft]) / 2.0;
        }
        if (bleft == bright) {
            return medianPlusOne(a, aleft, aright, b[bleft]);
        }
        if (aleft == aright) {
            throw new IllegalArgumentException("The [a] should be the larger array ");
        }

        double amedian = median(a, aleft, aright);
        double bmedian = median(b, bleft, bright);

        log.info("amedian={}   bmedian={}", amedian, bmedian);

        final int howMuchToCut = (bright - bleft + 1) / 2;
        if (amedian < bmedian) {
            //a is the "left" array, so we cut off its left-side
            //b is the "right" one, so we cut off its right-side
            //how much to cut? half of the smaller array
            log.info("howMuchToCut={}", howMuchToCut);
            return find(a, aleft + howMuchToCut, aright, b, bleft, bright - howMuchToCut);
        }
        else if (amedian > bmedian) {
            //b is the "left" array and a is the "right" one
            log.info("howMuchToCut={}", howMuchToCut);
            return find(a, aleft, aright - howMuchToCut, b, bleft + howMuchToCut, bright);
        }
        else {
            return amedian;
        }
    }

    /**
     * The median of the array assuming we add to it an additional number.
     * We look at the "center" of the array to decide.
     * The median may be shifted to the left or the right depending on the value of the additional.
     * Or it may be the additional itself.
     * The logic below is fairly simple, but it depends on if the array is of even or odd length.
     */
    private double medianPlusOne(int[] arr, int left, int right, int additional) {
        // log.debug("[medianPlusOne] arr = ({} , {}) / {}", arr[left], arr[right], additional);
        int len = right - left + 1;

        if (len % 2 == 0) {
            //the median of the array is the average of these 2 candidates
            //adding another number will shift it left or right
            //or... it may be exactly between them two.
            final int leftCandidate = arr[left + len / 2 - 1];
            final int rightCandidate = arr[left + len / 2];
            if (additional < leftCandidate) {
                return leftCandidate;
            }
            //noinspection ManualMinMaxCalculation
            if (additional > rightCandidate) {
                return rightCandidate;
            }
            else {
                return additional;
            }
        }
        else {
            //the median now is the number in the middle
            //the result will be an average of this middle with something else:
            //either the number before it or the number after
            //or... the additional
            int middle = arr[len/2];
            int before = arr[len/2-1];
            int after = arr[len/2+1];
            if (additional < before) {
                return average(before, middle);
            }
            if (additional > after) {
                return average(middle, after);
            }
            //so additional should be a neighbour of middle
            if (additional < middle) {
                return average(additional, middle);
            }
            else {
                return average(middle, additional);
            }
        }

    }

    private double median(int arr[], int left, int right) {
        final int len = right - left + 1;
        if (len == 0) {
            return -1;
        }
        if (len % 2 == 0) {
            return average(arr[left + len/2], arr[left + len/2-1]);
        }
        else {
            return arr[left + len/2];
        }
    }

    private double average(int a, int b) {
        return (a+b) / 2.0;
    }
}
