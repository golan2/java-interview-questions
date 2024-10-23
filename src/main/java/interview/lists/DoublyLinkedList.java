package interview.lists;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    21/12/2011 11:41:42
 * <B>Since:</B>       BSM 9.1
 * <B>Description:</B>
 * <p/>
 * </pre>
 */
public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
  private Node<T> tail;

  public DoublyLinkedList(T[] values) {
    super(values);
  }

  @Override
  protected void addFirst(Node<T> newNode) {
    if (isEmpty()) tail = newNode;  //only if empty we set the last
    super.addFirst(newNode);        //otherwise it is the same as parent
  }

  protected Node<T> removeFirst() {
    if (head==tail) tail = null;    //only if remove last item then update "last"
    return super.removeFirst();     //otherwise it is the same as parent
  }

  @Override
  public void addLast(T value) {
    addLast(new Node<>(value));
    checkIntegrity();
  }

  private void addLast(Node<T> node) {
    if (isEmpty()) {
      addFirst(node);
      checkIntegrity();
      return;
    }

    tail.setNext(node);
    tail = node;
    checkIntegrity();
  }

  @Override
  public void checkIntegrity() {
    super.checkIntegrity();
    if (isEmpty()) {
      if (tail==null) {
        return;
      }
      else {
        throw new RuntimeException("Empty list but tail!=null");
      }
    }

    Node<T> it = head;
    while (it.hasNext()) {
      it = it.getNext();
    }
    if (tail!=it) {
      throw new RuntimeException("Last item is ["+it+"] while tail is ["+tail+"]");
    }
  }

  @Override
  public boolean remove(T value) {
    if (head==null) return false;

    if (head.getValue().equals(value)) {
      removeFirst();
      return true;
    }

    Node<T> prev = head;
    Node<T> cur = head.getNext();
    while (prev.hasNext()) {
      if (cur.getValue().equals(value)) {
        prev.setNext(cur.getNext());
        if (!prev.hasNext()) {    //if we removed the last item update "tail"
          tail = prev;
        }
        checkIntegrity();
        return true;
      }
      prev = prev.getNext();
      cur = cur.getNext();
    }
    return false;
  }

  @Override
  public void reverse() {
    tail = head;
    super.reverse();
  }

  public void recursiveReverse() {
    if (head==null) return;
    if (!head.hasNext()) return;

    Node<T> first = removeFirst();
    recursiveReverse();
    addLast(first);
    checkIntegrity();
  }

}
