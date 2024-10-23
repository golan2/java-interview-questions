package interview.leetcode.arrays;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.UnexpectedException;
import java.util.*;

/**
 * https://leetcode.com/problems/range-sum-query-mutable/
 * @noinspection unused
 */
@Slf4j
public class NumArray {

    private static final int SIZE = 10_000;

    private static int[] initRandomArray(int size) {
        final Random r = new Random();
        final int[] res = new int[size];
        for (int i = 0; i < res.length; i++) {
            res[i] =r.nextInt(size);
        }
        return res;
    }

    /** @noinspection unused*/
    @SneakyThrows
    private static int[] initFromFile() {
        final String s = Files.readString(Path.of("/tmp/arr.txt"));
        final String[] values = s.split(",");
        return Arrays.stream(values)
                .map(String::trim)
                .map(Integer::parseInt)
                .mapToInt(a -> a)
                .toArray();
    }





    private final RangeTree rt;

    private final static int[] bigArray = initRandomArray(50);

    @SneakyThrows
    public static void main(String[] args) {
        final ArrayList<Integer> arrayList = new ArrayList<>(SIZE);
        final LinkedList<Integer> linkedList = new LinkedList<>();
        int[] array = new int[SIZE];

        for (int i=0 ; i<SIZE ; i++) {
            arrayList.add(i);
            linkedList.add(i);
            array[i] = i;
        }

        final long a = System.nanoTime();
        int sum1 = 0;
        for (Integer v : arrayList) {
            sum1 += v;
        }
        final long b = System.nanoTime();
        int sum2 = 0;
        for (Integer v : linkedList) {
            sum2 += v;
        }
        final long c = System.nanoTime();
        int sum3 = 0;
        for (int j : array) {
            sum3 += j;

        }
        final long d = System.nanoTime();



        System.out.println(sum1 + " " + (b-a));
        System.out.println(sum2 + " " + (c-b));
        System.out.println(sum3 + " " + (d-c));

//        final RangeTree rt = new RangeTree(bigArray);
//        bfsPrint(rt.root);

//        System.out.println(" ---- ");
//        for (int i = 10 ;  i<60_000 ; i+=500) {
//            final int from = 100;
//            final int to = 100 + i;
//            System.out.println( "["+ from +","+ to +"] => " + testRange(rt, from, to) / 1000 );
//        }
//        testRange(rt, 100, 1600);
//        testRange(rt, 100, 15100);
    }

    private static long treeSumRange(RangeTree rt, int from, int to) {
        final long a = System.nanoTime();
        final int actual = rt.sumRange(from, to);
        final long b = System.nanoTime();
        return b-a;
    }

    private static long testRange(RangeTree rt, int from, int to) throws UnexpectedException {
        final long a = System.nanoTime();
        final int actual = rt.sumRange(from, to);
        final long b = System.nanoTime();
        final int expected = rangeSum(from, to);
        final long c = System.nanoTime();

        if (actual != expected) {
            System.out.println(Arrays.toString(bigArray));
            throw new UnexpectedException("Range["+ from +","+ to +"]  --  Expected " + expected + " but was " + actual);
        }

        final long tree = b - a;
        final long naive = c - b;
        final long diff = naive - tree;
//        System.out.println("Range["+ from +","+ to +"] =>  "+diff+"        tree: " + tree + "naive: " + naive);
        return diff;
    }

    private static int rangeSum(int left, int right) {
        int sum = 0;
        for (int i = left ; i<=right ; i++) {
            sum += bigArray[i];
        }
        return sum;
    }

    public NumArray(int[] nums) {
        this.rt = new RangeTree(nums);
    }

    public void update(int index, int val) {
        rt.update(index, val);
    }

    public int sumRange(int left, int right) {
        return rt.sumRange(left,right);
    }


    private static class RangeTree {
        private final int[] arr;
        private final RangeNode root;

        private RangeTree(int[] arr) {
            this.arr = arr;
            this.root = buildTree(arr);
        }

        private RangeNode buildTree(int[] arr) {
            return buildSubTree(arr, 0, arr.length - 1);
        }

        private RangeNode buildSubTree(int[] arr, int left, int right) {
            if (left == right) {
                return new RangeNode(left, right, arr[left], null, null);
            }
            if (right - left == 1) {
                return new RangeNode(left, right, arr[left] + arr[right],
                        new RangeNode(left, left, arr[left], null, null),
                        new RangeNode(right, right, arr[right], null, null));
            }
            int middle = left + (right - left) / 2;
            final RangeNode leftNode = buildSubTree(arr, left, middle);
            final RangeNode rightNode = buildSubTree(arr, middle + 1, right);
            return new RangeNode(left, right, leftNode.sum + rightNode.sum, leftNode, rightNode);
        }

        public void update(int index, int val) {
            update(root, index, val);
        }

        private void update(RangeNode node, int index, int val) {
            if (node.from == node.to) {
                if (node.from != index) throw new IllegalStateException("node = " + node + "  index="+index + " val="+val);
                node.sum = val;
            }
            else if (node.to - node.from == 1) {
                if (node.from != index && node.to != index) throw new IllegalStateException("node = " + node + "  index="+index + " val="+val);
                arr[index] = val;
                node.sum = arr[node.from] + arr[node.to];
            }
            else {
                if (index < node.right.from) {
                    update(node.left, index, val);
                }
                else {
                    update(node.right, index, val);
                }
                node.sum = node.left.sum + node.right.sum;
            }
        }

        public int sumRange(int left, int right) {
            return sumRange(root, left, right);
        }

        private int sumRange(RangeNode node, int left, int right) {
            if (node == null) {
                log.debug("sumRange  null ==> 0");
                return 0;
            }
            if (left <= node.from  &&  node.to <= right) {
                log.debug("sumRange  {} ==> {}", node, node.sum);
                return node.sum;
            }
            if (left > node.to  ||  right < node.from) {
                log.debug("sumRange  {} ==> 0", node);
                return 0;
            }
            final int sum = sumRange(node.left, left, right) + sumRange(node.right, left, right);
            log.debug("sumRange  {} ==> {}", node, sum);
            return sum;
        }
    }

    private static class RangeNode {
        private final int from;
        private final int to;
        private int sum;
        private final RangeNode left;
        private final RangeNode right;

        public RangeNode(int from, int to, int sum, RangeNode left, RangeNode right) {
            this.from = from;
            this.to = to;
            this.sum = sum;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "[" + from + "," + to + "] (" + sum + ")";
        }
    }

    private static void bfsPrint(RangeNode root) {
        Queue<RangeNodeWrapper> queue = new LinkedList<>();

        if (root == null) {
            System.out.println("null");
            return;
        }

        int level = 0;
        queue.add(new RangeNodeWrapper(root, 0));
        while (!queue.isEmpty()) {
            final RangeNodeWrapper nw = queue.poll();
            if (level < nw.level) {
                level = nw.level;
                System.out.println();
            }
            final RangeNode node = nw.node;
            System.out.print(node);
            System.out.print("  |  ");
            if (node.left != null) {
                queue.add(new RangeNodeWrapper(node.left, level + 1));
            }
            if (node.right != null) {
                queue.add(new RangeNodeWrapper(node.right, level + 1));
            }
        }
        System.out.println();
    }

    private static class RangeNodeWrapper {
        private final RangeNode node;
        private final int level;

        public RangeNodeWrapper(RangeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}
