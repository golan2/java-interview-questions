package interview.leetcode.numbers;

import java.util.Arrays;

public interface SummaryRanges {
    void addNum(int value);

    int[][] getIntervals();

    static void main(String[] args) {


        final SummaryRanges r1 = new SummaryRangesWithRangeCompare();
        final SummaryRanges r2 = new SummaryRangesWithIntegers();

        System.out.println("--- 1 ---");
        run(r1, 40000, 3, 500);
        System.out.println("--- 2 ---");
        run(r2, 40000, 3, 500);

        System.out.println("--- 2 ---");
        run(r2, 20000, 2, 1000);
        System.out.println("--- 1 ---");
        run(r1, 20000, 2, 1000);


    }

    private static void run(SummaryRanges r, final int bulkSize, final int gapJump, final int cycles) {
        final long before = System.nanoTime();
        System.nanoTime();


        for (int i = 0; i < cycles; i++) {
            for (int j = 0; j < bulkSize; j++) {
                if (j % gapJump != 0) {
                    add(r, i* bulkSize + j);
                }
            }
            for (int j = 0; j < bulkSize; j++) {
                if (j % gapJump == 0) {
                    add(r, i* bulkSize + j);
                }
            }
        }

        final long after = System.nanoTime();
        System.out.printf("%s\n", Arrays.deepToString(r.getIntervals()));
        System.out.println((after-before)/1_000_000);
    }

    private static void add(SummaryRanges r, int value) {
        r.addNum(value);
//        System.out.printf("%d => %s\n", value, Arrays.deepToString(r.getIntervals()));
    }

}
