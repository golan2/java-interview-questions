package interview.lists;

/**
* <pre>
* <B>Copyright:</B>   Izik Golan
* <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
* <B>Creation:</B>    21/12/2011 11:41:32
* <B>Since:</B>       BSM 9.1
* <B>Description:</B>
* <p/>
* </pre>
*/
public class Node<T> {
  protected T value;
  protected Node<T> next;

  public Node(T value) {
    this.value = value;
    this.next = null;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public Node<T> getNext() {
    return next;
  }

  public void setNext(Node<T> next) {
    this.next = next;
  }

  public boolean hasNext() {
    return (this.next!=null);
  }

  @Override public String toString() {
    return ""+value;
  }
}
