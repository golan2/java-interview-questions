package interview.lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    03/01/2012 00:51:36
 * <B>Since:</B>       BSM 9.1
 * <B>Description:</B>
 * <p/>
 * </pre>
 * @noinspection unused
 */
public class Main {

  public static void main(String[] args) {
    singletonList();
    System.out.println("================");

  }

  private static void printList() {
    final DoublyLinkedList<Integer> myList = new DoublyLinkedList<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

    System.out.println(myList);
  }


  private static void listContainsList() {

    DoublyLinkedList<Integer> a = new DoublyLinkedList<>(new Integer[] {1,2,1,2,1,2,3,6});
    DoublyLinkedList<Integer> b = new DoublyLinkedList<>(new Integer[] {1,2,1,2,3});
    System.out.println("a="+a);
    System.out.println("b="+b);
    System.out.println(a.contains(b));

    a.reverse();
    b.reverse();
    System.out.println("a="+a);
    System.out.println("b="+b);
    System.out.println(a.contains(b));
  }

  private static void singletonList() {
    final List<String> list = new ArrayList<>(Collections.singletonList("A"));
    list.add("B");
    System.out.println(String.join(",", list));
  }
}
