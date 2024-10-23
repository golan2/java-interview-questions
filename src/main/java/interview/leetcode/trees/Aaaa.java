package interview.leetcode.trees;

import java.util.ArrayList;
import java.util.List;

public class Aaaa {
    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    static class BinaryTree {
        Node root;

        // Function to construct the tree from inorder traversal
        Node buildTree(List<Integer> inorder, int start, int end) {
            if (start > end)
                return null;

            // Find the index of the root node
            int rootIndex = findRootIndex(inorder, start, end);

            // Create the root node
            Node root = new Node(inorder.get(rootIndex));

            // Recursively build the left and right subtrees
            root.left = buildTree(inorder, start, rootIndex - 1);
            root.right = buildTree(inorder, rootIndex + 1, end);

            return root;
        }

        // Function to find the index of the root node
        int findRootIndex(List<Integer> inorder, int start, int end) {
            int i;
            for (i = start; i < end; i++) {
                if (inorder.get(i) > inorder.get(end))
                    break;
            }
            return i;
        }

        // Function to print the tree with indentation
        void printTree(String s) {
            List<Integer> inorder = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                inorder.add(Integer.parseInt(s.substring(i, i + 1)));
            }

            root = buildTree(inorder, 0, inorder.size() - 1);

            printTreeHelper(root, 0);
        }

        private void printTreeHelper(Node node, int level) {
            if (node == null) {
                return;
            }

            // Print right subtree with indentation
            printTreeHelper(node.right, level + 1);

            // Print current node with indentation and line
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println(node.data);
            if (node.left != null || node.right != null) {
                for (int i = 0; i < level; i++) {
                    System.out.print("  ");
                }
                System.out.println("|");
            }

            // Print left subtree with indentation and line
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            if (node.left != null || node.right != null) {
                System.out.print("|");
            }
            printTreeHelper(node.left, level + 1);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        String inorder = "011100110110001";
        tree.printTree(inorder);
    }
}
