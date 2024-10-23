package interview.lists.levels;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    07/06/2015 22:49
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 *
 * Source: Wiley - Programming Interviews Exposed - Secrets to Landing Your Next Job (2000).pdf
 *
 * TODO: Still not working!!
 *
 *
 * Problem: List Flattening
 * ========================
 * You are given a special type of a linked list: MultiLevelList
 * You need to write a code that will flatten the list.
 * The result should be a simple linked list with all the elements in the original list.
 * Order is not important.
 *
 * What is a MultiLevelList?
 * =========================
 *  Start with a standard doubly linked list.
 *  Now imagine that in addition to next and previous pointers, each element has a child pointer,
 *  which may or may not point to a separate doubly linked list.
 *  These child lists may have one or more children of their own, and so on, to produce a multilevel data structure.
 *
 *
 * </pre>
 */
public class MultiLevelList {

  private Node head = null;

  public static final String[] FOLDERS = {
      "root;opt;HP",
      "root;opt;Mercury",
      "root;etc;server",
      "root;etc;Mercury",
      "root;opt;Mercury",
      "root;opt;Mercury",
      ""
  };

  public static void main(String[] args) {
    MultiLevelList list = new MultiLevelList();
    for (int i = 0; i < FOLDERS.length ; i++) {
      String folder = FOLDERS[i];
      list.addToList(folder);
      System.out.println(list);

    }

    System.out.println(list);

  }

  private void addToList(String value) {

    String[] arr = value.split(";");

    addToList(head, arr, 0);
  }

  private Node addToList(Node node, String[] arr, int index) {
    Node a;

    if (index>=arr.length)
      return null;

    if (head==null) {
      head = new Node(arr[0]);
      a = head;
    }
    else {
      a = findOrAdd(node, arr[index]);
    }

    //if (a.sublist==null) {
    //  a.sublist = new Node(arr[index]);
    //}
    Node newNode = addToList(a.sublist, arr, index+1);
    addToSubList(a, newNode);
    return a;
  }

  private Node findOrAdd(Node node, String s) {
    while (node!=null) {
      if (node.value.equals(s)) return node;
      node = node.next;
    }
    return new Node(s);
  }

  private void addToSubList(Node a, Node newNode) {
    if (a.sublist==null) {
      a.sublist = newNode;
    }
    else {
      Node it = a.sublist;
      while (it.next!=null) {
        it = it.next;
      }
      a.next = newNode;

    }
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();

    toJsonString(head, res);

    return res.toString();
  }


  private void toJsonString(Node node, StringBuilder buf) {
    if (node==null) {
      buf.append("null");
      return;
    }

    buf.append("{\"Node\":{\"value\": ").append("\""+node.value+"\"").append(" , \"next\" : ");
    toJsonString(node.next, buf);
    buf.append(" , \"sublist\" : ");
    toJsonString(node.sublist, buf);
    buf.append(" }}");
  }

  private static class Node {
    private String value;
    private Node next;
    private Node sublist;

    public Node(String a) {
      value = a;
      next = null;
      sublist = null;
    }
  }
}
