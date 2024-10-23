package interview.trees.bst;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    08/06/2015 08:32
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * Source: Wiley - Programming Interviews Exposed - Secrets to Landing Your Next Job (2000).pdf
 *
 * Problem:Lowest Common Ancestor
 *
 * Given the value of two nodes in a BST, find the lowest common ancestor.
 * Hint: You should not assume that both values already exist in the tree.
 * Required in worst log(n)
 *
 * </pre>
 */
public class LowestCommonAncestor {

  public static void main(String[] args) {
    MyIntBST tree = MyIntBST.createTree( new Integer[] { 10, 5, 3, 1, 4, 7, 6, 8, 15, 13, 11, 14, 17, 16, 18 } );

    System.out.println(tree);             //this is a json so we can see what we've got

    System.out.println(findLowestCommonAncestor(tree, 9, 11).key);

  }


  public static MyIntBST.Node findLowestCommonAncestor(MyIntBST tree, int a, int b) {
    return findLowestCommonAncestor(tree.root, Integer.min(a,b), Integer.max(a, b));
  }

  /**
   * Given the value of two nodes in a BST, find the lowest common ancestor.
   *
   * The idea is that this is a BST so we don't need to traverse all tree in order to find them.
   * We start going down the tree.
   * At each point we decide if to go left or right (if both are smaller or both are bigger)
   * When we reach to the node where one is on the left and one is on the right we stop and declare this node as the lowest ancestor.
   * Before we declare it as the lowest ancestor we do 2 simple lookup calls to make sure these values are indeed in the tree.
   *
   * @param node root of relevant subtree in the reclusive call
   * @param small smaller value to look for
   * @param big bigger value to look for
   * @return the node of the lowest ancestor or null if the numbers are not in the tree
   */
  private static MyIntBST.Node findLowestCommonAncestor(MyIntBST.Node node, int small, int big) {
    if (node==null || node.left==null || node.right==null) {
      return null;
    }

    if (small>node.key) {
      //go write
      return findLowestCommonAncestor(node.right, small, big);
    }
    else if (big<node.key) {
      //go left
      return findLowestCommonAncestor(node.left, small, big);
    }
    else {
      //one here and one there
      if (lookup(node, small)==null) return null;
      if (lookup(node, big)  ==null) return null;
      return node;
    }

  }

  private static MyIntBST.Node lookup(MyIntBST.Node root, int key) {
    if (root==null) return null;
    if (key<root.key) return lookup(root.left, key);
    if (key>root.key) return lookup(root.right, key);
    return root;
  }

}
