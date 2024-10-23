package interview.leetcode.numbers;

import java.util.Map;
import java.util.TreeMap;

/**
 * Same like {@link SummaryRangesWithRangeCompare} but a little more efficient.
 * We use {@link TreeMap#ceilingEntry(Object)} and {@link TreeMap#floorEntry(Object)}
 */
public class SummaryRangesWithIntegers implements SummaryRanges {
    private final TreeMap<Integer, Integer> ranges = new TreeMap<>();    //key -> from     value -> to

    public void addNum(int value) {
        if (ranges.containsKey(value)) return;

        final Map.Entry<Integer, Integer> before = ranges.floorEntry(value);
        if (before != null  &&  before.getKey() == value) {
            //the value is in the map already
            return;
        }

        final Map.Entry<Integer, Integer> after = ranges.ceilingEntry(value);
        if (before == null) {
            if (after != null && after.getKey() == value + 1) {
                //after is adjacent to value so we merge/extend
                ranges.remove(after.getKey());
                ranges.put(value, after.getValue());
            } else {
                ranges.put(value, value);
            }
        }
        else {
            if (before.getValue() == value - 1) {
                if (after != null && after.getKey() == value + 1) {
                    //value connects before to after, so they become a single long range
                    ranges.remove(after.getKey());
                    ranges.put(before.getKey(), after.getValue());
                }
                else {
                    //before is adjacent to value so we merge/extend
                    ranges.put(before.getKey(), value);
                }
            } else {
                if (after != null && after.getKey() == value + 1) {
                    //after is adjacent to value so we merge/extend
                    ranges.remove(after.getKey());
                    ranges.put(value, after.getValue());
                } else {
                    ranges.put(value, value);
                }
            }
        }
    }

    public int[][] getIntervals() {
        final int[][] res = new int[ranges.size()][];
        int i = 0;
        for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
            res[i++] = new int[] {range.getKey(), range.getValue()};
        }
        return res;
    }



}
