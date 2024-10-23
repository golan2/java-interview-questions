package interview.trees.bst;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    11/01/2015 08:18
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * A simple Binary Search Tree to play with trees algorithms
 * I download BST and Queue online which are the concrete implementation.
 *
 * </pre>
 */
public class IsBalancedBST extends BST<Integer, Integer> {





  /**
   * A tree is balanced if all of the below applies:
   * [1] Left subtree is balanced
   * [2] Right subtree is balanced
   * [3] The max-depth diff between left and right is maximum 1

   *
   * @return balanced or not :-)
   */
  public boolean isBalanced() {
    return isBalanced(root).balanced;
  }


  private BalanceState isBalanced(Node root) {
    if (root==null) {
      return new BalanceState(0,true);
    }

    BalanceState leftState = isBalanced(root.left);
    BalanceState rightState = isBalanced(root.right);



    if (checkBalance(leftState, rightState)) {
      System.out.println(root.key + " TRUE  leftState="+leftState + " rightState="+rightState);
      return new BalanceState(Math.max(leftState.maxDepth,rightState.maxDepth)+1, true);
    }

    System.out.println(root.key + " FALSE leftState="+leftState + " rightState="+rightState);
    return BalanceState.NOT_BALANCED;
  }

  /**
   * The balance is true if 3 conditions hold:
   * [1] Left subtree is balanced
   * [2] Right subtree is balanced
   * [3] The maxDepth diff between left and right is maximum 1
   *
   * @param leftState the state of the left subtree
   * @param rightState the state of the right subtree
   * @return true if the tree is balanced
   */
  private boolean checkBalance(BalanceState leftState, BalanceState rightState) {
    return (leftState.balanced && rightState.balanced) && (Math.abs(leftState.maxDepth-rightState.maxDepth)<=1);
  }

  private static class BalanceState {
    public static final BalanceState NOT_BALANCED = new BalanceState(-1,false);

    final int maxDepth;
    final boolean balanced;

    public BalanceState(int maxDepth, boolean balanced) {
      this.maxDepth = maxDepth;
      this.balanced = balanced;
    }

    @Override
    public String toString() {
      return "BalanceState{" +
          "maxDepth=" + maxDepth +
          ", balanced=" + balanced +
          '}';
    }
  }
}
