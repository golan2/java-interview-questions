package interview.recursion.permutations;

import com.google.gson.Gson;
import interview.trees.bst.Queue;

public class RobotAndMatrix {
    public static void main(String[] args) {
        System.out.println("A(3,3) = " + a(3,3));

        final Node root = new Node(3, 3);
        expand(root);

        System.out.println(binaryTreeToJsonString(root));

        


    }

    private static void binaryTreeByLevels(Node root) {
        final StringBuilder buf = new StringBuilder();
        Queue<NodeDepth> queue = new Queue<>();
        queue.enqueue(new NodeDepth(root, 0));
        int depth = 0;
        while (!queue.isEmpty()) {
            final NodeDepth nd = queue.dequeue();
            buf.append(nd.node);
            if (nd.depth>depth) {
                buf.append("\n");
                depth++;
            }
            queue.enqueue(new NodeDepth(nd.node.left, nd.depth+1));

        }
    }

    private static class NodeDepth {
        final Node node;
        final int depth;

        public NodeDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    private static String binaryTreeToJsonString(Node root) {
        return new Gson().toJson(root);
    }

    private static void expand(Node node) {
        if (node.n>1) {
            node.left = new Node(node.n-1, node.m);
            expand(node.left);
        }
        else {
            node.left = null;
        }
        if (node.m>1) {
            node.right = new Node(node.n, node.m-1);
            expand(node.right);
        }
        else {
            node.right = null;
        }
    }


    private static int a(int n, int m) {
        if ( n==1 && m==1) {
            System.out.println("a(1,1)");
            return 1;
        }
        if (n==1) {
            System.out.println("1 + a("+n+","+(m-1)+")");
            return 1 + a(n,m-1);
        }
        if (m==1) {
            System.out.println("1 + a("+(n-1)+","+m+")");
            return 1 + a(n-1,m);
        }
        System.out.println("2 + a("+(n-1)+","+m+") + a("+n+","+(m-1)+")");
        return 2 + a(n-1,m) + a(n,m-1);
    }


    private static class Node {
        final int n;
        final int m;
        Node left;
        Node right;

        private Node(int n, int m) {
            this.n = n;
            this.m = m;
        }

        @Override
        public String toString() {
            return "["+n+","+m+"]";
        }
    }
}
