package interview.trees.bst;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    06/06/2015 21:10
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 * Stores only integer values in the tree.
 * We basically use the "key" and put "1" in the values.
 *
 * </pre>
 */
public class MyIntBST extends BST<Integer, Integer> {

  private static final Integer SOME_CONSTANT_VALUE = 1;

  public MyIntBST() {}

  public MyIntBST(Node node) {
    root = node;
  }

  public static MyIntBST createTree(Integer[] preOrder) {
    return ReconstructTreeFromArray.reconstruct_by_adding_to_a_new_tree(preOrder);
  }

  public void put(Integer integer) {
    super.put(integer, SOME_CONSTANT_VALUE);
  }

  protected Node createNode(int key) {
    return super.createNode(key, SOME_CONSTANT_VALUE);
  }

  public String toString() {

    StringBuilder res = new StringBuilder();

    toJsonString(root, res);

    return res.toString();
  }

  private void toJsonString(Node node, StringBuilder buf) {
    if (node==null) {
      buf.append("null");
      return;
    }

    buf.append("{\"Node\":{\"key\": ").append(node.key).append(" , \"left\" : ");
    toJsonString(node.left, buf);
    buf.append(" , \"right\" : ");
    toJsonString(node.right, buf);
    buf.append(" }}");
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof MyIntBST) ) return false;

    MyIntBST rhs = (MyIntBST) obj;

    return recursiveCompare(this.root, rhs.root);
  }

  static boolean recursiveCompare(Node lhs, Node rhs) {
    if (lhs==null) return rhs==null;
    if (rhs==null) return lhs==null;

    if (!lhs.key.equals(rhs.key)) return false;

    //I know less efficient calling right if left is false but I am lazy now... :-)

    boolean left  = recursiveCompare(lhs.left, rhs.left);
    boolean right = recursiveCompare(lhs.right, rhs.right);

    return left && right;
  }

  @Override
  public int hashCode() {
    return recursiveHashCode(this.root);
  }

  private int recursiveHashCode(Node root) {
    if (root==null) return 0;

    return root.key.hashCode() + recursiveHashCode(root.left) + recursiveHashCode(root.right);
  }

}
