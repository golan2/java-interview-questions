package interview.javajava;

import java.util.Arrays;

/**
 * You are given an array of integers. The array is ordered in an ascending order except for one place.
 * You can think about 2 sorted arrays (different sizes) that we concatenated.
 * You should find the index where it happens (concatenation place)
 * Example: {5,6,7,8,9,1,2,3}   ==> "1"
 *
 * Solution is Binary Search where each time we check which half of the array is "not good" and we dive there.
 * In the above example we start with {5,8} and {9,3}
 * {5,8} is in the correct order but {9,3} is not so it is where we continue searching.
 *
 */
public class FindArrayConcat {
    public static void main(String[] args) {
        final int[][] input = {
                {5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 1, 2, 3},
                {5, 1},
                {5, 6},
        };

        for (final int[] arr : input) {
            final int pivot = findPivot(arr, 0, arr.length - 1);
            System.out.println(arr[pivot] + " ==> " + Arrays.toString(arr));
        }

    }

    private static int findPivot(int[] arr, int left, int right) {
        if (right-left<=1) {
            if (arr[left]<=arr[right])
                return left;
            else
                return right;
        }

        int middle = left+(right-left)/2;

        if (arr[middle] > arr[right]) {
            return findPivot(arr, middle, right);
        }
        else {
            return findPivot(arr, left, middle);
        }
    }
}
