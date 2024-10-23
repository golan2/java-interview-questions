package interview.leetcode.numbers;

import java.util.TreeMap;

/**
 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/
 *
 * 352. Data Stream as Disjoint Intervals
 *
 * Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen so far as a list of disjoint intervals.
 *
 * Implement the SummaryRanges class:
 *
 * SummaryRanges() Initializes the object with an empty stream.
 * void addNum(int value) Adds the integer value to the stream.
 * int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals [starti, endi]. The answer should be sorted by starti.
 *
 * Constraints:
 *
 * 0 <= value <= 104
 * At most 3 * 104 calls will be made to addNum and getIntervals.
 *
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class SummaryRangesWithRangeCompare implements SummaryRanges {
    private final TreeMap<RangeKey, Range> ranges = new TreeMap<>();

    public void addNum(int value) {
        final Range singleton = new Range(value);
        if (ranges.containsKey(singleton)) {
            return; //we've already seen this number, so it is contained in one of the ranges
        }

        final Range before = ranges.remove(new RangeKey(value-1));
        final Range after  = ranges.remove(new RangeKey(value+1));
        if (before == null) {
            if (after == null) {
                //so there is no adjacent range
                ranges.put(singleton, singleton);
            }
            else {
                //extend after to include value
                final Range extended = new Range(value, after.to);
                ranges.put(extended, extended);
            }
        }
        else {
            //it is adjacent to before (but maybe it is adjacent to the after as well?)
            if (after == null) {
                //extend before
                final Range extended = new Range(before.from, value);
                ranges.put(extended, extended);
            }
            else {
                //this value connects before and after to a single long range
                final Range connected = new Range(before.from, after.to);
                ranges.put(connected, connected);
            }
        }
    }

    public int[][] getIntervals() {
        final int[][] res = new int[ranges.size()][];
        int i = 0;
        for (Range range : ranges.values()) {
            res[i++] = new int[] {range.from, range.to};
        }
        return res;
    }

    /**
     * The comparator here is unique because we want to check ranges
     * A range is considered smaller if it is completely before (no intersection)
     * If a range is contained in
     */
    private static class RangeKey implements Comparable<RangeKey>{
        int from;
        int to;

        public RangeKey(int single) {
            this(single, single);
        }

        public RangeKey(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int compareTo(RangeKey o) {
            if (o.to < this.from) return +1;
            if (this.to < o.from) return -1;
            if (this.from < o.from && this.to > o.from && this.to < o.to) throw new IllegalStateException("Intersecting ranges:  this=" + this + " o = " + o);
            return 0;
        }

        @Override
        public String toString() {
            return String.format("{%d,%d}", from, to);
        }
    }

    private static class Range extends RangeKey {
        public Range(int from, int to) {
            super(from, to);
        }

        public Range(int singleton) {
            super(singleton);
        }
    }
}
