package interview.leetcode.trees;

import interview.leetcode.bst.common.TreeNode;

class TreePrinter {
    static void print(TreeNode root) {
        final StringBuilder buf = new StringBuilder();
        traversePreOrder(buf, "", "", root);
        System.out.println(buf);
    }

    private static void traversePreOrder(StringBuilder sb, String padding, String pointer, TreeNode node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.val);
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);
        }
    }
}
