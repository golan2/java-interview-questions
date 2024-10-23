package interview.trees.bst;

import interview.recursion.permutations.Permutations;

import java.util.Arrays;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    06/06/2015 20:42
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 *
 * The Question:
 * =============
 * Reconstruct a binary search tree (BST) from a list (or array) that contains the results of a traversal of the tree. You may use any traversal you wish.
 *
 * Friendly reminder #1
 * ====================
 * BST is a Binary Search Tree.
 * For each node in the tree the following holds:
 * [1] All values on the left sub-tree are smaller than the node's value.
 * [2] All values on the right sub-tree are grater than the node's value.
 *
 * Friendly reminder #2
 * ====================
 * There are 3 ways to traverse a BST (in-order/pre-order/post-order).
 *
 * Still don't understand?
 * =======================
 * So we will do it like a "cards magic" :-)
 * [1] I build a BST but I don't show it to you.
 * [2] You choose the "traversal type" you want.
 * [3] I give you the array which is the result of the traversal you chose.
 * [4] You need to run your magic code that will give me the BST that I built.
 * [5] The answer to this question is "your magic code"
 *
 * </pre>
 */
public class ReconstructTreeFromArray {

  private static final Integer[] ALL_VALUES = {10, 5, 3, 1, 4, 7, 6, 8};

  public static void main(String[] args) {

    Integer[][] allValues = Permutations.allPermutations(ALL_VALUES);

    for (Integer[] values : allValues) {

      System.out.println(Arrays.toString(values));

      //Create a BST with all values
      MyIntBST original = new MyIntBST();
      for (Integer value : values) {
        original.put(value);
      }


      Integer[] preOrder = toArrayPreOrder(original);
      MyIntBST reconstructed1 = reconstruct_by_adding_to_a_new_tree(preOrder);
      MyIntBST reconstructed2 = reconstruct_by_recursively_pivot_left_right(preOrder);

      if ( !original.equals(reconstructed1) && !original.equals(reconstructed2) ) {
        System.out.println("NOT THE SAME");
        System.out.println("NOT THE SAME");
        System.out.println("NOT THE SAME");
        System.out.println("original:\n"+original);
        System.out.println("reconstructed1:\n"+reconstructed1);
        System.out.println("reconstructed2:\n"+reconstructed2);
        System.out.println("NOT THE SAME");
        System.out.println("NOT THE SAME");
        System.out.println("NOT THE SAME");
        return;
      }
    }
    System.out.println("Done!");

  }

  private static Integer[] toArrayPreOrder(MyIntBST tree) {
    Integer[] result = new Integer[tree.size()];
    recursiveToArrayPreOrder(tree.root, result, 0);
    return result;
  }

  private static int recursiveToArrayPreOrder(MyIntBST.Node node, Integer[] array, int index) {
    if (node==null) return index;

    array[index++] = node.key;
    index = recursiveToArrayPreOrder(node.left, array, index);
    index = recursiveToArrayPreOrder(node.right, array, index);
    return index;
  }

  /**
   * We choose "pre-order".
   * We create a new BST.
   * We go over the array and insert the values into the BST.
   * Eventually we will get a BST the same like the original.
   * @param preOrder array of values that are a result of pre-order traversal on the original tree
   * @return the reconstructed tree
   */
  public static MyIntBST reconstruct_by_adding_to_a_new_tree(Integer[] preOrder) {
    MyIntBST result = new MyIntBST();
    for (Integer val : preOrder) {
      result.put(val);
    }
    return result;
  }


  public static MyIntBST reconstruct_by_recursively_pivot_left_right(Integer[] preOrder) {
    BST<Integer, Integer>.Node node = reconstruct_by_recursively_pivot_left_right(preOrder, 0, preOrder.length - 1);
    return new MyIntBST(node);

  }

  /**
   * The idea is to understand that:
   * ===============================
   * [1] The first is the root.
   * [2] All items after it are "left sub-tree" until... the pivot.
   * [2] First item that is larger than the first is the pivot.
   * [3] All items from pivot and until the end are the "right sub-tree".
   *
   * And the most important thing is that all of the above is correct recursively to the sub arrays as well.
   *
   * So what we do:
   * ==============
   * Create a node for the root.
   * Find the pivot
   * Perform 2 recursive calls to handle the 2 sub-arrays (from pivot left and from pivot right).
   *
   * http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversa/
   *
   *
   * @param preOrder array of preOrder
   * @return the root node
   */
  private static MyIntBST.Node reconstruct_by_recursively_pivot_left_right(Integer[] preOrder, int left, int right) {

    if (left>right) {
      return null;        //if left==right then this node is a leaf and is handled ok here below by setting its left and right to null in the recursive call
    }

    MyIntBST.Node node = new MyIntBST().createNode(preOrder[left]);

    if (left==right) {
      return node;
    }

    int pivot = left+1;

    while (pivot<right && preOrder[pivot]<preOrder[left]) {
      pivot++;
    }
    if (pivot==right) pivot++;

    node.left = reconstruct_by_recursively_pivot_left_right (preOrder, left+1, pivot-1);
    node.right = reconstruct_by_recursively_pivot_left_right(preOrder, pivot, right);

    return node;


  }

}
