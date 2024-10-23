package interview.lists;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SinglyLinkedListTest {



  @Test
  public void testAddFirst()  {
    LList list = new LList();
    assertNull(list.head);
    list.addFirst(4);
    assertNotNull(list.head);
    assertEquals("UnexpectedDifferentValue", 4, list.head.getValue().longValue());

    list.addFirst(3);
    assertNotNull(list.head);
    assertEquals("UnexpectedDifferentValue", 3, list.head.getValue().longValue());
    assertNotNull(list.head.getNext());
    assertEquals("UnexpectedDifferentValue", 4, list.head.getNext().getValue().longValue());

    list.addFirst(2);
    assertNotNull(list.head);
    assertEquals("UnexpectedDifferentValue", 2, list.head.getValue().longValue());
    assertNotNull(list.head.getNext());
    assertEquals("UnexpectedDifferentValue", 3, list.head.getNext().getValue().longValue());
    assertNotNull(list.head.getNext().getNext());
    assertEquals("UnexpectedDifferentValue", 4, list.head.getNext().getNext().getValue().longValue());


    System.out.println(list);
  }

  private static class LList extends SinglyLinkedList<Integer> { }

}