package interview.recursion.permutations;

import java.util.Arrays;

public class SumsetNthElement {

    public static void main(String[] args) {
        int[] a = {2,4,6,8,12};
        int[] b = {1,10,16,17};

        int[] sums = sumset(a,b);
        Arrays.sort(sums);
        System.out.println(Arrays.toString(sums));

        System.out.println(findSumsetNthElement(a,b,5));

//        for (int i = 1; i <= a.length+b.length; i++) {
//            System.out.printf("%d, ", findSumsetNthElement(a,b,i));
//        }


    }

    private static int findSumsetNthElement(int[] a, int[] b, int n) {
        int i=0,j=0;
        for (int c = 1; c < n; c++) {
            if (j==b.length)
                i++;
            else if (i==a.length)
                j++;
            else if (a[i+1] < b[j+1])
                i++;
            else
                j++;
        }
        return a[i]+b[j];
    }

    private static int[] sumset(int[] a, int[] b) {
        int[] result = new int[a.length*b.length];
        int index=0;
        for (int itemA : a) {
            for (int itemB : b) {
                result[index++] = itemA + itemB;
            }
        }
        return result;
    }
}
