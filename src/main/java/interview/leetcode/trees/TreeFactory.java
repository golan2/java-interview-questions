package interview.leetcode.trees;

import interview.leetcode.bst.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

class TreeFactory {

    /**
     * Each node has the value that is the sum of its children
     * The leafs are 1,2,3...
     */
    @SuppressWarnings("SameParameterValue")
    static TreeNode sumTree(final int leafCount) {
        final Queue<TreeNode> queue = new LinkedList<>();
        for (int i = 0; i < leafCount; i++) {
            queue.add(new TreeNode(i));
        }

        while (!queue.isEmpty()) {
            final TreeNode left = queue.remove();
            if (queue.isEmpty()) {
                return left;
            }
            final TreeNode right = queue.remove();
            final TreeNode node = new TreeNode(right.val + left.val);
            node.left = left;
            node.right = right;
            queue.add(node);
        }
        return null;
    }
}
