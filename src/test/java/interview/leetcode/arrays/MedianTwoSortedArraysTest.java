package interview.leetcode.arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MedianTwoSortedArraysTest {
    private static final MedianTwoSortedArrays TT = new MedianTwoSortedArrays();

    public static class MedianPlusOne {
        @SuppressWarnings("NewClassNamingConvention")
        public static class ArrayOddSize {
            private static final int MIDDLE = 20;   // the one that is currently the median
            int[] arr1 = new int[]{11, 12, 13, 14, MIDDLE, 22, 25, 26, 28};

            @Test
            public void whenAdditionalIsSmallerThenAll() {
                int[] arr2 = new int[]{9};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(average(14, MIDDLE));
                assertThat(actual2).isEqualTo(average(14, MIDDLE));
            }

            @Test
            public void whenAdditionalIsLargerThanAll() {
                int[] arr2 = new int[]{100};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(average(MIDDLE, 22));
                assertThat(actual2).isEqualTo(average(MIDDLE, 22));
            }

            @Test
            public void whenAdditionalIsLeftNeighbourOfMiddle() {
                int[] arr2 = new int[]{19};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(average(19, MIDDLE));
                assertThat(actual2).isEqualTo(average(19, MIDDLE));
            }

            @Test
            public void whenAdditionalIsRightNeighbourOfMiddle() {
                int[] arr2 = new int[]{21};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(average(MIDDLE, 21));
                assertThat(actual2).isEqualTo(average(MIDDLE, 21));
            }
        }

        @SuppressWarnings("NewClassNamingConvention")
        public static class ArrayEvenSize {
            private static final int LC = 14;       //left candidate
            private static final int RC = 22;       //right candidate
            int[] arr1 = new int[]{11, 12, 13,     LC, RC,    25, 26, 28};

            @Test
            public void whenAdditionalIsSmallerThanLeftCandidate() {
                int[] arr2 = new int[]{9};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(LC);
                assertThat(actual2).isEqualTo(LC);
            }

            @Test
            public void whenAdditionalIsLargerThanRightCandidate() {
                int[] arr2 = new int[]{92};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(RC);
                assertThat(actual2).isEqualTo(RC);
            }

            @Test
            public void whenAdditionalIsBetweenCandidate() {
                int[] arr2 = new int[]{20};
                final double actual1 = TT.findMedianSortedArrays(arr1, arr2);
                final double actual2 = TT.findMedianSortedArrays(arr2, arr1);
                assertThat(actual1).isEqualTo(20);
                assertThat(actual2).isEqualTo(20);
            }

        }

    }

    @SuppressWarnings("NewClassNamingConvention")
    public static class Median {
        public static class BigArrayIsOddSize {
            private static final int MEDIAN = 20;   // the one that is currently the median
            int[] longArray = new int[]{11, 12, 13, 14, MEDIAN, 25, 26, 27, 28};    

            @Test
            public void whenHalfOfTheShortArrayIsSmallerThanTheMedian_andHalfBigger_thenMedianStaysTheSame() {
                int[] shortArray1 = new int[]{1,2,3  ,  98,99,100};
                final double actual1 = TT.findMedianSortedArrays(longArray, shortArray1);
                assertThat(actual1).isEqualTo(MEDIAN);

                int[] shortArray2 = new int[]{15,16,17  ,  22,23,24};
                final double actual2 = TT.findMedianSortedArrays(longArray, shortArray2);
                assertThat(actual2).isEqualTo(MEDIAN);
            }

            @Test
            public void whenAllTheShortArrayValuesAre_SmallerThan_TheMedian_thenMedianShiftsToTheLeft() {
                int[] shortArray1 = new int[]{1,2,3,4,5,6};
                final double actual1 = TT.findMedianSortedArrays(longArray, shortArray1);
                assertThat(actual1).isEqualTo(12);

                int[] shortArray2 = new int[]{4,5,6 , 17,18,19};
                final double actual2 = TT.findMedianSortedArrays(longArray, shortArray2);
                assertThat(actual2).isEqualTo(17);
            }

            @Test
            public void whenAllTheShortArrayValuesAre_LargerThan_TheMedian_thenMedianShiftsToTheLeft() {
                //11, 12, 13, 14, 20, 25,   26, 27, 28,   31,32,33,34,35,36
                int[] shortArray1 = new int[]{31,32,33,34,35,36};
                final double actual1 = TT.findMedianSortedArrays(longArray, shortArray1);
                assertThat(actual1).isEqualTo(27);

                //11, 12, 13,   14,20,21,22,   23,   25,26,27,28,  34,35,36
                int[] shortArray2 = new int[]{21,22,23  ,  34,35,36};
                final double actual2 = TT.findMedianSortedArrays(longArray, shortArray2);
                assertThat(actual2).isEqualTo(23);
            }
        }

    }

    private static double average(int a, int b) {
        return (a + b) / 2.0;
    }

}