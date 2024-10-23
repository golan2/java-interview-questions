package interview.leetcode.trees;

import interview.leetcode.bst.common.TreeNode;

public class TreeComparator {
    static boolean equals(TreeNode a, TreeNode b) {
        if (a == b) return true;
        if (a == null) return false;
        if (b == null) return false;

        if (a.getVal() != b.getVal()) return false;
        return equals(a.getLeft(), b.getLeft())  &&   equals(a.getRight(), b.getRight());
    }

}
