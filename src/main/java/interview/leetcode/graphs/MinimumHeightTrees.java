package interview.leetcode.graphs;

import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

public class MinimumHeightTrees {

    private static final int N = 10;
    private static final int SEED = 1;
    private static final Random RANDOM = new Random();

    private static final int[][] MATRIX = toMatrix(new int[]{
            0, 1,
            1, 0
    });

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (edges.length == 0) {
            return Collections.singletonList(SEED);
        }
        System.out.println(Arrays.stream(edges).map(t -> "[" + t[0] + "," + t[1] + "]").collect(Collectors.toList()));
        final Graph g = new Graph(n, edges);
        Deque<Graph.Vertex> leaves = new LinkedList<>();
        g.getVertices()
                .values()
                .stream()
                .filter(v -> v.getDegree() == 1)
                .forEach(leaves::add);


        while (!leaves.isEmpty()) {
            System.out.println(g);
            System.out.println(leaves.stream().map(Graph.Vertex::getValue).collect(Collectors.toList()));
            System.out.println("----------");
            if (g.size() == 1) {
                return singleVertexResult(g);
            }
            if (g.size() == 2) {
                return twoVerticesResult(g);
            }

            leaves = removeAllLeaves(g, leaves);
        }
        return Collections.emptyList();
    }

    private Deque<Graph.Vertex> removeAllLeaves(Graph g, Deque<Graph.Vertex> leaves) {
        Deque<Graph.Vertex> newLeaves = new LinkedList<>();
        while (!leaves.isEmpty()) {
            final Graph.Vertex v = leaves.removeFirst();
            g.remove(v);
            v.getNeighbours().forEach(nei -> {
                nei.decDegree();
                nei.getNeighbours().remove(v);
                if (nei.getDegree() == 1) {
                    newLeaves.add(nei);
                }
            });
        }
        return newLeaves;
    }

    private List<Integer> singleVertexResult(Graph g) {
        return Collections.singletonList(g.getVertices().values().iterator().next().getValue());
    }

    private List<Integer> twoVerticesResult(Graph g) {
        return g.getVertices()
                .values()
                .stream()
                .map(Graph.Vertex::getValue)
                .collect(Collectors.toList());
    }

    private static class Graph {
        final Map<Integer, Vertex> vertices = new HashMap<>();

        private Graph(int n, int[][] edges) {
            for (int i = 1; i <= n; i++) {
                vertices.put(i, new Vertex(i));
            }
            for (int[] edge : edges) {
                vertices.get(edge[0]).addNeighbour(edge[1]);
                vertices.get(edge[1]).addNeighbour(edge[0]);
            }
        }

        public int size() {
            return vertices.size();
        }

        public Map<Integer, Vertex> getVertices() {
            return vertices;
        }

        public void remove(Vertex vertex) {
            this.vertices.remove(vertex.getValue());
        }

        @EqualsAndHashCode(of = "value")
        private class Vertex {
            private final int value;
            final Set<Vertex> neighbours = new HashSet<>();
            private int degree = 0;

            private Vertex(int value) {
                this.value = value;
            }

            public void addNeighbour(int a) {
                neighbours.add(vertices.get(a));
                this.degree++;
            }

            public int getValue() {
                return value;
            }

            public int getDegree() {
                return degree;
            }

            public Set<Vertex> getNeighbours() {
                return neighbours;
            }

            public void decDegree() {
                this.degree--;
            }
        }

        @Override
        public String toString() {
            final StringBuilder b = new StringBuilder();
            for (Map.Entry<Integer, Vertex> e : vertices.entrySet()) {
                for (int i = 1; i <= vertices.keySet().size(); i++) {
                    if (e.getValue().neighbours.contains(getVertices().get(i))) {
                        b.append("1,");
                    } else {
                        b.append("0,");
                    }
                }
                b.append("\n");
            }

            return b.toString();
        }
    }


    public static void main(String[] args) {
        System.out.println(
//                new MinimumHeightTrees().findMinHeightTrees(N, toEdges(MATRIX))
                new MinimumHeightTrees().findMinHeightTrees(N, randomTreeEdges(N) )
        );
    }

    /**
     * Generate a random tree with n nodes.
     *
     * @param n number of vertices (0...n-1)
     * @return list of (n-1) edges where each item in the array is a tuple with the ids of the connected vertices
     */
    private static int[][] randomTreeEdges(int n) {
        final int[][] edges = new int[n - 1][];
        for (int i = 2; i <= n; i++) {
            edges[i - 2] = new int[]{
                    i, rand(1, i - 1)
            };
        }
        return edges;
    }

    /**
     * Both inclusive
     */
    private static int rand(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * Convert an adjacency matrix to a list of edges.
     */
    private static int[][] toEdges(int[][] matrix) {
        final ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            int[] ints = matrix[i];
            for (int j = i; j < ints.length; j++) { //start from "i" since an edge from "a" to "b" appears twice in the matrix (in the row of "a" and in the row of "b")
                if (ints[j] == 1) {
                    edges.add(new int[]{i + 1, j + 1});
                }
            }
        }
        System.out.println(edges.stream().map(t -> "[" + t[0] + "," + t[1] + "]").collect(Collectors.toList()));
        return edges.toArray(new int[0][]);
    }

    private static int[][] toMatrix(int[] vals) {
        final int n = (int) Math.sqrt(vals.length);
        final int[][] matrix = new int[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new int[n];
            //noinspection ManualArrayCopy
            for (int j = 0; j < n; j++) {
                matrix[i][j] = vals[i * n + j];
            }
        }
        return matrix;
    }

}