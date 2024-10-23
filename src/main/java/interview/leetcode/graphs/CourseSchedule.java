package interview.leetcode.graphs;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where
 *      prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses.
 * If there are many valid answers, return any of them.
 * If it is impossible to finish all courses, return an empty array.
 *
 */
public class CourseSchedule {

    public static void main(String[] args) {
        System.out.println(
                Arrays.toString(
                        new CourseSchedule().findOrder(7, new int[][]{
                                            new int[]{1,0},
                                            new int[]{2,0},
                                            new int[]{3,0},
                                            new int[]{5,1},
                                            new int[]{6,1},
                                            new int[]{4,2},
                                            new int[]{2,3},
                                            new int[]{4,3},
                                            new int[]{1,4},
                                            new int[]{4,6}
                        }))
        );
    }

    /**
     * The provided param is an array of tuples
     * Each tuple is actually an array of 2 (hence the 2 dimensional array)
     * Each tuple (x,y) represents a dependency from x to y  (you need to do Y before X)
     * @return can we do it or do we have a cyclic dependency
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        final Map<Integer, Collection<Integer>> graph = buildGraph(numCourses, prerequisites);
        final Map<Integer, AtomicInteger> inDegree = calculateInDegreeForEachVertex(numCourses, prerequisites);

        final int[] ordered = new int[numCourses];
        int orderIndex = 0;
        final Deque<Integer> zeroNodes = findAllZeroNodes(inDegree);
        while( !zeroNodes.isEmpty() ) {
            final Integer node = zeroNodes.poll();
            ordered[orderIndex++] = node;
            final Collection<Integer> neighbours = graph.get(node);
            neighbours.forEach(v -> {
                final int newInValue = inDegree.get(v).decrementAndGet();
                if (newInValue == 0) {
                    zeroNodes.add(v);
                }
            });
            graph.remove(node);
        }

        if (graph.isEmpty()) {
            return ordered;
        }
        else {
            return new int[0];
        }
    }

    /**
     * Convert the prerequisites array of tuples to a Graph.
     * A tuple [x,y] means "X needs Y"
     * For that we create an edge from Y to X which represents the order to take the courses (first Y and then X)
     * @return map from vertex to a collection of its adjacent-vertices
     */
    private Map<Integer, Collection<Integer>> buildGraph(int numCourses, int[][] prerequisites) {
        final Map<Integer, Collection<Integer>> graph = new HashMap<>();
        IntStream.range(0, numCourses).forEach(a -> graph.put(a, new LinkedList<>()));    // we want the map to have all vertices (including ones that don't have edges)
        for (int[] tuple : prerequisites) {
            graph.get(tuple[1]).add(tuple[0]);
        }
        return graph;
    }

    private Map<Integer, AtomicInteger> calculateInDegreeForEachVertex(int numCourses, int[][] prerequisites) {
        final HashMap<Integer, AtomicInteger> res = new HashMap<>();
        IntStream.range(0, numCourses).forEach(a -> res.put(a, new AtomicInteger(0)));
        for (int[] tuple : prerequisites) {
            res.get(tuple[0]).incrementAndGet();
        }
        return res;
    }

    private Deque<Integer> findAllZeroNodes(Map<Integer, AtomicInteger> inDegree) {
        final LinkedList<Integer> res = new LinkedList<>();
        inDegree
                .entrySet()
                .stream()
                .filter(e -> e.getValue().get() == 0)
                .map(Map.Entry::getKey)
                .forEach(res::add);
        return res;
    }

}
