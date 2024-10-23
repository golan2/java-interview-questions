package interview.trees.bst;

import java.util.Arrays;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    10/01/2015 21:40
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class IsBalancedTree {

  public static void main(String[] args) {

    System.out.println( isBalanced( constructBalancedTree()) );
//    System.out.println( isBalanced( constructNonBalancedTree()) );

  }

  private static boolean isBalanced(BST tree) {
    int[] mmd = minMaxDepth(tree.root);
    return (Math.abs(mmd[0]-mmd[1])<2);
  }

  /**
   *
   * @param node sub root of the tree to check for balanced
   * @return a tuple in the form of an array. index 0 holds the minimum and index 1 holds the maximum
   */
  private static int[] minMaxDepth(BST.Node node) {
    if (node==null) return new int[]{0,0};
    final int[] l = minMaxDepth(node.left);
    final int[] r = minMaxDepth(node.right);
    final int min = Math.min(l[0], r[0]) + 1;
    final int max = Math.max(l[1], r[1]) + 1;
    final int[] result = {min, max};
    System.out.println("Node["+node.key+"] Return["+ Arrays.toString(result) +"]");
    return result;
  }

  private static IsBalancedBST constructBalancedTree() {
    IsBalancedBST tree = new IsBalancedBST();

    tree.put(10,1);
    tree.put(6,1);
    tree.put(4,1);
    tree.put(8,1);
    tree.put(16,1);
    tree.put(12,1);
    tree.put(18,1);

    return tree;
  }

  private static IsBalancedBST constructNonBalancedTree() {
    IsBalancedBST tree = new IsBalancedBST();

    tree.put(20,1);
    tree.put(25,1);
    tree.put(23,1);
    tree.put(28,1);
    tree.put(10,1);
    tree.put(5,1);
    tree.put(2,1);
    tree.put(7,1);
    tree.put(6,1);
    tree.put(11,1);
    tree.put(15,1);
    tree.put(14,1);

    return tree;
  }

}
