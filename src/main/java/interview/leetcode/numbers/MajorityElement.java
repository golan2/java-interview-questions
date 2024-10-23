package interview.leetcode.numbers;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/majority-element/
 *
 * Given an array nums of size n, return the majority element.
 * Do it in O(n) time and with O(1) memory
 *
 * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
 */
public class MajorityElement {

    public static void main(String[] args) {

        final MajorityElement a = new MajorityElement();
//        System.out.println(a.majorityElement(new int[] {1,1,1,1,1,2,2,2}));
//        System.out.println(a.majorityElement(new int[] {2,2,2,1,1,1,1,1}));
//        System.out.println(a.majorityElement(new int[] {2,1,2,1,2,1,1,1}));
//        System.out.println(a.majorityElement(new int[] {1,1,1,2,1,2,1,2}));
//        System.out.println(a.majorityElement(new int[] {1,2,1,2,1,2,1,1}));
        System.out.println(a.majorityElement(new int[] {2,2,3,3,2}));
    }

    public int majorityElement(int[] nums) {
        if (nums == null  ||  nums.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            if (nums[0] == nums[1]) {
                return nums[0];
            } else {
                throw new IllegalArgumentException("There is no majority element");
            }
        }

        int index = 1;
        int scout = nums.length-1;

        while (index < nums.length) {
            System.out.println(Arrays.toString(nums));
            if (nums[index] == nums[index-1]) {
                scout = findReplacement(nums, index, scout);
                if (scout == -1) {
//                    scout = findReplacement(nums, index, scout);
                    if (scout == -1) {
                        //we can't find a number that is different from current
                        return nums[index];
                    }
                }
                int temp = nums[index];
                nums[index] = nums[scout];
                nums[scout] = temp;
            }
            index++;
        }

        return nums[nums.length-1];
    }

    private int findReplacement(int[] nums, int index, int scout) {
        while (scout  > index) {
            if (nums[scout] != nums[index]) {
                return scout;
            }
            scout--;
        }
        return -1;
    }


}
