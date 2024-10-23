package interview.trees.bst;

import interview.trees.bst.BST.Node;

public class MirrorTree {

    public static void main(String[] args) {
        final MyIntBST t = createTree1();
        System.out.println("BEFORE: \n" + t);
        mirror(t.root);
        System.out.println("AFTER: \n" + t);
    }

    /**
     * I use a BST because the order of insertion defines the structure of the tree.
     * I don't care about the search capabilities just to get a tree with a desired shape.
     */
    private static MyIntBST createTree1() {
        final MyIntBST tree = new MyIntBST();
        tree.put(10);
        tree.put(8);
        tree.put(7);
        tree.put(9);
        tree.put(20);
        tree.put(30);
        tree.put(25);
        tree.put(35);
        return tree;
    }

    private static void mirror(Node root) {
        if (root==null) return;

        Node t = root.left;
        root.left = root.right;
        root.right = t;

        mirror(root.left);
        mirror(root.right);
    }

}
