package interview.Whattim;

import java.util.Arrays;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    21/03/14 07:39
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class WhatFuncDoes {
    public static final int n = 10;

    public static void main(String[] args) {
        int[] a = new int[n+1];


        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        
        a[4] = 6;

        System.out.println(Arrays.toString(a));

        System.out.println(func(a));
    }

    /**
     * find the missing number in the array
     * we expect the array to have all the numbers between ZERO and N
     * @param a array
     * @return the missing number
     */
    private static int func(int a[]) {
        int i, j, flag;
        for (j = 0; j <= n; j++) {
            flag = 0;
            for (i = 0; i <= n; i++) {
                if (a[i] == j) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return j;
            }
        }
        return -1;
    }

}
