package interview.lists;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    25/03/14 10:41
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class SinglyLinkedList<T> implements MyList<T> {
  protected Node<T> head;

  public SinglyLinkedList() {}

  public SinglyLinkedList(T[] values) {
    for (int i = values.length-1; i >=0 ; i--) {
      addFirst(values[i]);
    }
    checkIntegrity();
  }

  @Override
  public void addFirst(T value) {
    addFirst(new Node<>(value));
    checkIntegrity();
  }

  protected void addFirst(Node<T> newNode) {
    newNode.setNext(head);  //if empty list then root is null
    head = newNode;
    checkIntegrity();
  }

  protected Node<T> removeFirst() {
    if (head==null) return null;

    Node<T> result = head;
    head = head.getNext();
    result.setNext(null);
    checkIntegrity();
    return result;
  }

  /**
   * @return true if there is a cycle in the list
   */
  @Override
  public boolean hasCycle() {

    if (head==null || head.getNext()==null) return false;

    Node<T> singleSpeed = head;
    Node<T> doubleSpeed = head;

    while (doubleSpeed!=null && doubleSpeed.getNext()!=null) {
      singleSpeed = singleSpeed.getNext();
      doubleSpeed = doubleSpeed.getNext().getNext();

      if (singleSpeed==doubleSpeed) return true;
    }

    return false;
  }

  public T findMiddleNode() {

    if (head==null) return null;
    if (head.getNext()==null) return head.getValue();

    Node<T> singleSpeed = head;
    Node<T> doubleSpeed = head;

    while (doubleSpeed!=null && doubleSpeed.getNext()!=null) {
      singleSpeed = singleSpeed.getNext();
      doubleSpeed = doubleSpeed.getNext().getNext();
    }

    return singleSpeed.getValue();
  }

  @Override
  public boolean contains(MyList<T> list) {
    SinglyLinkedList rhs = (SinglyLinkedList) list;
    if (rhs==null) return true;
    if (rhs.isEmpty()) return true;
    if (this.isEmpty()) return false;

    Node a = this.head;

    while (a.hasNext()) {
      if (beginsWith(a, rhs.head)) {
        return true;
      }
      else {
        a = a.getNext();
      }
    }
    return false;
  }

  @Override
  public void addLast(T value) {
    if (isEmpty()) {
      addFirst(value);
    }

    Node<T> last = head;
    while (last.getNext()!=null) {
      last = last.getNext();
    }
    Node<T> newNode = new Node<>(value);
    last.setNext(newNode);
    newNode.setNext(null);
  }

  /**
   * Print in Reverse.
   * For Linked List 1->2->3->4->5, fun1() prints 5->4->3->2->1.
   * @param head the begining of the list
   */
  private void what1(Node head)
  {
    if(head == null)
      return;

    what1(head.getNext());
    System.out.println(head.getValue());
  }

  /**
   * prints alternate nodes of the given Linked List,
   * first from head to end, and then from end to head.
   * If Linked List has even number of nodes, then what2() skips the last node.
   * For Linked List 1->2->3->4->5, fun2() prints 1 3 5 5 3 1. For Linked List 1->2->3->4->5->6, fun2() prints 1 3 5 5 3 1
   * @param head
   */
  void what2(Node head)
  {
    if(head== null)
      return;
    System.out.println(head.getValue()+ ", ");

    if(head.getValue() != null )
      what2(head.getNext().getNext());
    System.out.println(head.getValue() + ", ");
  }



  /**
   * Each Node is the first element in a sub-list.
   * Check if "longer" contains the "shorter" in its beginning.
   * Examples:
   * {1,2,3,4,5} begins with {1,2,3} but doesn't begin with {3,4,5}
   * {1,2,3} does NOT begin with {1,2,3,4,5}
   *
   * @param longer the longer list
   * @param shorter the shorter list
   * @return
   */
  private boolean beginsWith(Node longer, Node shorter) {

    while (longer!=null && shorter!=null) {
      if (longer.getValue()!=shorter.getValue()) return false;
      longer = longer.getNext();
      shorter = shorter.getNext();
    }

    //if we are here then either "longer" or "shorter" has ended.... or both... so we check which case....

    //noinspection RedundantIfStatement
    if (longer!=shorter) {
      return false; //one has ended while the other not so they are not the same
    }
    else {
      return true; //both ended (and all values are the same since this is what the loop ensures)
    }
  }

  @Override
  public void checkIntegrity() {
    if (hasCycle())  {
      throw new RuntimeException("Has Cycle");
    }
  }

  @Override
  public boolean isEmpty() {
    return (head==null);
  }

  @Override
  public boolean remove(T value) {

    if (head==null) return false;

    Node<T> prev = head;
    while (prev.getNext()!=null) {
      if (prev.getNext().getValue()==value){
        prev.setNext(prev.getNext().getNext());
        return true;
      }
      prev = prev.getNext();
    }
    return false;
  }

  @Override
  public void reverse() {

    if (isEmpty()) return;

    //build a new list [result] to be the reversed list
    Node<T> newHead = null;

    while (head!=null) {

      //remove the first item from the original list
      Node<T> node = head;
      head = head.getNext();


      //add [node] to the beginning of the result list
      node.setNext(newHead);
      newHead = node;

    }

    //set root to point the new list
    head = newHead;
    checkIntegrity();
  }

  @Override
  public void recursiveReverse() {
    internalRecursiveReverse();
  }

  private Node<T> internalRecursiveReverse() {
    if (head==null) return null;
    if (!head.hasNext()) return head;

    Node<T> first = removeFirst();
    Node<T> last = internalRecursiveReverse();
    last.setNext(first);
    first.setNext(null);
    checkIntegrity();
    return first;
  }

  @Override
  public String toString() {
    if (isEmpty()) return "{EMPTY}";
    final StringBuilder result = new StringBuilder();
    Node it = head;
    while (it!=null) {
      result.append(it.getValue());
      if (it.getNext()!=null) result.append(",");
      it = it.getNext();
    }
    return result.toString();
  }
}
