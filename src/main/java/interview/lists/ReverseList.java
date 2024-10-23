package interview.lists;


/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    21/12/2011 11:50:19
 * <B>Since:</B>       BSM 9.1
 * <B>Description:</B>
 * <p/>
 * </pre>
 */
public class ReverseList extends SinglyLinkedList<Integer> {
  public static void main(String[] args) {
    final SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    list.addFirst(1);
    list.addFirst(2);
    list.addFirst(3);
    list.addFirst(4);
    list.addFirst(5);
    System.out.println("Original = "+list);
    list.recursiveReverse();
    System.out.println("After recursiveReverse = "+list);
    recursive_reverse(list.head);
    System.out.println("After reverse = "+list);
  }

  static Node<Integer> recursive_reverse(Node<Integer> node) {
    if (node==null) {
      return null;
    }

    if (node.next==null) {
      return node;
    }

    Node<Integer> lastNode = recursive_reverse(node.next);
    lastNode.next = node;
    node.next = null;
    return lastNode;
  }

  //private static class RList {
  //  RNode head;
  //
  //  void add() {}
  //}
  //
  //private static class RNode {
  //  int value;
  //  RNode next;
  //}

}