package interview.hackerrank;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * https://www.hackerrank.com/challenges/crush/problem?isFullScreen=true
 * @noinspection unused
 */
public class ArrayManipulation {
    public static void _main(/*String[] args*/) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.arrayManipulation(n, queries);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {

        //  [1,100]

        final long a = Result.arrayManipulation(100,
                List.of(
                        List.of(10, 20, 1),
                        List.of(50, 60, 4),
//                        List.of(15, 40, 1),
//                        List.of(15, 40, 1),
//                        List.of(15, 40, 1),
//                        List.of(15, 40, 1),
                        List.of(15, 40, 1)
                ));

        System.out.println(a);

    }

    /**
     * When we execute an operation we change things in the ranges (usually add more).
     * We can focus on the relevant ranges that are impacted by the operation.
     * Each of the impacted ranges is going to be replaced by 1,2 or 3 ranges depending on the use case.
     * When you see
     *      [10,50;1]
     * It represents a range of numbers, all having the same value.
     * It means that the numbers 10,11,...50 all have the value 1.
     * Instead of having an array of size [n] we have list of such ranges.
     * |
     * Example 1
     * =========
     * the from-to is included completely inside the range:
     * These are the ranges before the operation:
     *      [0,9;5] [10,50;1] [51,90;6]
     * This is the operation:
     *      20,30,3
     * (i.e. add the value 3 to items 20,21,...30)
     * |
     * This range is replaced by 3 ranges:
     *      [10,50;1] ==> [10,19;1] [20,30;4] [31,50;1]
     * The ranges after the operation:
     *      [0,9;5] [10,19;1] [20,30;4] [31,50;1] [51,90;6]
     * |
     * What happened is that the [10,50;1] was split into 3 ranges.
     *  - Left   part with unchanged value (10->19)
     *  - Right  part with unchanged value (31->50)
     *  - Middle part is where we added +3 (20->30)
     * |
     * Example 2
     * =========
     * The from-to is NOT included completely inside the range:
     * It may start before the range or continue after it.
     * |
     * These are the ranges before the operation:
     *      [0,9;5] [10,50;1] [51,55;6] [56,90;2]
     * This is the operation:
     *      40,60,3
     * (i.e. add the value 3 to items 40,41,...60)
     * |
     * This operation impacts more than a single (existing) range.
     * We will handle each range separately.
     *      [10,50;1] ==> [10,39;1] [40,50;4]
     *      [51,55;6] ==> [51,55;9]
     *      [56,90;2] ==> [56,60;5] [61,90;2]
     *
     * =================================
     *
     * Each impacted range can be replaced by 1,2 or 3 ranges depending on the use case.
     * We pass here the range we want to handle.
     * We return an array of ranges that should replace the given range.
     * |
     * Implementation Note:
     * When the range should be replaced with a single range then the only change is to the value field.
     * For performance reasons we will not remove and add a range rather update the value of the range.
     * |
     * OperationRange:
     * If we want to perform the operation [40,60,1]
     * it means that we want to add 1 to all values from 40 to 60.
     * The values 40,41,..60 are the OperationRange.
     *
     *
     *
     * |
     * Use Case 1:
     * The given range is completely inside the operation range.
     * In this case we don't really need to replace the range.
     * We just need to update the range's value by adding it the value given by the operation.
     * For example given this ranges list:
     *      [0,9;5] [10,50;1] [51,55;6] [56,90;2]
     * And this operation:
     *
     * |
     * Use Case 2:
     *
     *
     *
     */
    private static class Result {
        public static long arrayManipulation(int n, List<List<Integer>> queries) {
            // Write your code here
            final SortedRanges sortedRanges = new SortedRanges(n);
            System.out.println(sortedRanges);
            for (List<Integer> query: queries) {
                System.out.println("------");
                System.out.println(query);
                sortedRanges.executeOperation(query.get(0), query.get(1), query.get(2));
                System.out.println(sortedRanges);
            }
            return sortedRanges.max;
        }


        private static class SortedRanges {
            private int max = 0;
            private RangeNode head;
            private final TreeMap<Range, RangeNode> map = new TreeMap<>();

            public SortedRanges(int n) {
                final Range range = new Range(1, n);
                this.head = new RangeNode(range, 0, null, null);
                map.put(range, this.head);
            }

            public void executeOperation(int from, int to, int value) {
                final Range operationRange = new Range(from, to);
                final List<RangeNode> impactedNodes = resolveNodesThatAreImpactedByThisOperation(operationRange);
                impactedNodes.forEach(impactedNode -> {
                    System.out.println("impactedNode: " + impactedNode);
                    final RangeNode[] sequence = createNodeSequenceToReplaceTheImpactedNode(impactedNode, operationRange, value);
                    System.out.println("sequence: " + Arrays.toString(sequence));
                    replaceTheTargetWithSequence(impactedNode, sequence);
                    updateMax(sequence);
                    updateTheMap(impactedNode, sequence);
                });
            }


            private List<RangeNode> resolveNodesThatAreImpactedByThisOperation(Range opRange) {
                final ArrayList<RangeNode> result = new ArrayList<>();
                RangeNode impacted = map.get(new Range(opRange.from));
                while (impacted.range.to < opRange.to) {
                    result.add(impacted);
                    impacted = impacted.next;
                }
                result.add(impacted);
                return result;
            }

            private RangeNode[] createNodeSequenceToReplaceTheImpactedNode(RangeNode impactedNode, Range opRange, int opValue) {
                if (impactedNode.range.from < opRange.from  &&  opRange.to < impactedNode.range.to) {
                    //the OperationRange is included completely inside the given range
                    //in this case we split the range to 3 ranges
                    //  [10,50;1] ==> [10,19;1] [20,30;4] [31,50;1]

                    final RangeNode[] result = new RangeNode[3];
                    result[0] = new RangeNode(impactedNode.range.from, opRange.from-1, impactedNode.value);
                    result[1] = new RangeNode(opRange.from, opRange.to, impactedNode.value + opValue);
                    result[2] = new RangeNode(opRange.to+1, impactedNode.range.to, impactedNode.value);
                    result[0].next = result[1];
                    result[1].next = result[2];
                    result[1].prev = result[0];
                    result[2].prev = result[1];
                    return result;
                }
                else if (opRange.from < impactedNode.range.from  &&  impactedNode.range.to < opRange.to) {
                    //the given range is included completely inside the OperationRange
                    //in this case we return a single range
                    //  [51,55;6] ==> [51,55;9]
                    final RangeNode[] result = new RangeNode[1];
                    result[0] = new RangeNode(impactedNode.range.from, impactedNode.range.to, impactedNode.value + opValue);
                    return result;
                }
                else {
                    //the given range covers the range partially
                    //] Example
                    //  Operation: 40,60,3
                    //  [10,50;1] ==> [10,39;1] [40,50;4]
                    //  [56,90;2] ==> [56,60;5] [61,90;2]

                    final RangeNode[] result = new RangeNode[2];
                    result[0] = new RangeNode(impactedNode.range.from, opRange.from-1, impactedNode.value);
                    result[1] = new RangeNode(opRange.from, impactedNode.range.to, impactedNode.value);
                    result[0].next = result[1];
                    result[1].prev = result[0];

                    if (impactedNode.range.from < opRange.from) {
                        result[1].value += opValue;
                    }
                    else {
                        result[0].value += opValue;
                    }
                    return result;

                }
            }

            private void updateMax(RangeNode[] sequence) {
                for (RangeNode node: sequence) {
                    if (node.value > max) {
                        max = node.value;
                    }
                }
            }

            private void replaceTheTargetWithSequence(RangeNode target, RangeNode[] seq) {
                if (head == target) {
                    head = seq[0];
                }
                if (target.prev != null) {
                    target.prev.next = seq[0];
                }
                seq[0].prev = target.prev;
                seq[seq.length-1].next = target.next;
                if (target.next != null) {
                    target.next.prev = seq[seq.length-1];
                }
            }

            private void updateTheMap(RangeNode target, RangeNode[] seq) {
                map.remove(target.range);
                for (RangeNode rangeNode : seq) {
                    map.put(rangeNode.range, rangeNode);
                }
            }

            @Override
            public String toString() {
                final StringBuilder b = new StringBuilder();
                RangeNode n = this.head;
                while (n != null) {
                    b.append(n).append(" ");
                    n = n.next;
                }
                return b.toString();
            }
        }

        /**
         * The comparator here is unique because we want to check ranges
         * A range is considered smaller if it is completely before (no intersection)
         * If a range is completely contained in an
         */
        private static class Range implements Comparable<Range>{
            int from;
            int to;

            public Range(int single) {
                this(single, single);
            }

            public Range(int from, int to) {
                this.from = from;
                this.to = to;
            }

            @Override
            public int compareTo(Range o) {
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

        static class RangeNode {
            Range range;
            int value;
            RangeNode next;
            RangeNode prev;

            public RangeNode(Range range, int value, RangeNode next, RangeNode prev) {
                this.range = range;
                this.value = value;
                this.next = next;
                this.prev = prev;
            }

            public RangeNode(int from, int to, int value) {
                this(new Range(from, to), value, null, null);
            }

            @Override
            public String toString() {
                return "[" + range.from + "," + range.to + "|" + value + "]";
            }
        }


    }





}

