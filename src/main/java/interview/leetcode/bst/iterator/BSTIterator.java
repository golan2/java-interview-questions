package interview.leetcode.bst.iterator;

import interview.leetcode.bst.common.TreeNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * https://leetcode.com/problems/binary-search-tree-iterator/
 *
 * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):
 *
 * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
 * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
 * int next() Moves the pointer to the right, then returns the number at the pointer.
 * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST.
 *
 * You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order traversal when next() is called.
 *
 *
 * SOLUTION
 * ========
 *
 * The idea is to know for each node who is the next node we need to go to.
 *
 * If the node has a "right" son then we should "dive" to this child and then all the way to the left.
 * This is what {@link #findMinimum(TreeNode)} does.
 *
 * If there is no child to the right then we are done with this sub-tree but where should we go from here?
 * This is what {@link #nextHop} is for.
 * For each node we map the ancestor that we need to go to.
 */
public class BSTIterator implements Iterator<Integer> {
    private TreeNode current;
    private final Map<TreeNode, TreeNode> nextHop = new HashMap<>();

    public BSTIterator(TreeNode root) {
        TreeNode node = root;
        while (node.getLeft() != null) {
            nextHop.put(node.getLeft(), node);
            node = node.getLeft();
        }
        current = new TreeNode(-1);
        nextHop.put(current, node);
    }


    @Override
    public Integer next() {
        if (current.getRight() == null) {
            final TreeNode next = nextHop.get(current);
            if (next == null) throw new NoSuchElementException();
            nextHop.remove(current);
            current = next;
        }
        else {
            nextHop.put(current.getRight(), nextHop.get(current));
            nextHop.remove(current);
            current = findMinimum(current.getRight());
        }
        return current.getVal();
    }

    private TreeNode findMinimum(TreeNode node) {
        while (node.getLeft() != null) {
            nextHop.put(node.getLeft(), node);
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public boolean hasNext() {
        if (current.getRight() == null) {
            return nextHop.get(current) != null;
        }
        return true;
    }


}
