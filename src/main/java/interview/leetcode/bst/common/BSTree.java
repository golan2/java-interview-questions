package interview.leetcode.bst.common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BSTree {
    protected TreeNode root;

    public BSTree add(int val) {
        if (root == null) {
            root = new TreeNode(val);
        } else {
            add(root, val);
        }
        return this;
    }

    private void add(TreeNode node, int val) {
        if (val < node.val) {
            if (node.left == null) {
                node.left = new TreeNode(val);
            } else {
                add(node.left, val);
            }
        } else {
            if (node.right == null) {
                node.right = new TreeNode(val);
            } else {
                add(node.right, val);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        traverse(root, n -> b.append(n.val).append(", "));
        return b.toString();
    }

    public void print() {
        traverse(root, System.out::println);

    }

    private static void traverse(TreeNode root, Consumer<TreeNode> consumer) {
        final Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            final TreeNode node = q.poll();
            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
            consumer.accept(node);
        }

    }
}
