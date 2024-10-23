package interview.trees.bst;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    10/06/2015 20:18
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class KthSmallestInBST extends MyIntBST {

  private static final Integer[] VALUES = new Integer[] { 10, 5, 3, 1, 4, 7, 6, 8, 15, 13, 11, 14, 17, 16, 18 };

  public static void main(String[] args) {
    KthSmallestInBST tree = new KthSmallestInBST( createTree(VALUES) );
    Node node = tree.findKthSmallestInBST(54);
    System.out.println(node);

  }


  public KthSmallestInBST(MyIntBST tree) {
    super(tree.root);
  }

  public Node findKthSmallestInBST(int k) {
    return findKthSmallestInBST(this.root, k, new AtomicInteger());
  }

  private Node findKthSmallestInBST(Node root, int k, AtomicInteger counter) {
    if (root == null)
      return null;

    Node leftty = findKthSmallestInBST(root.left, k, counter);
    if (leftty != null) {
      return leftty;
    }
    else if (counter.incrementAndGet() == k) {
      return root;
    }
    else {
      return findKthSmallestInBST(root.right, k, counter);
    }
  }
}
