package interview.design.library.books;

import junit.framework.TestCase;
import org.junit.Test;

public class InventarBookLibraryTest extends TestCase {

  private static final String BOOK_1 = "Book1";
  private static final int CUSTOMER_ID_1 = 1;
  private static final int DAY_ZERO = 0;
  BookLibrary bookLibrary = new BookLibrary();

  @Test
  public void testAddToInventar() throws Exception {
    assertEquals(0, bookLibrary.getInventar().size());
    InventarBook book1 = new InventarBook(BOOK_1, 10);
    bookLibrary.addOrUpdateToInventar(book1);
    InventarBook book2 = new InventarBook("Book2", 10);
    bookLibrary.addOrUpdateToInventar(book2);
    assertEquals(2, bookLibrary.getInventar().size());
    assertEquals(book1, bookLibrary.getInventar().get(BOOK_1));
    assertEquals(book2, bookLibrary.getInventar().get("Book2"));
  }

  @Test
  public void testRent() throws Exception {
    boolean exceptionThrown;

    //Scenario: No such Book
    try {
      exceptionThrown = false;
      bookLibrary.rentBook(CUSTOMER_ID_1, BOOK_1);
    } catch (IllegalArgumentException ignored) {
      exceptionThrown = true;
    }
    assertTrue("The [rent] should have failed since there is no [Book1] in the inventar.", exceptionThrown);


    //Scenario: Zero copies of this Book
    try {
      exceptionThrown = false;
      InventarBook book1 = new InventarBook(BOOK_1, 0);
      bookLibrary.addOrUpdateToInventar(book1);
      bookLibrary.rentBook(CUSTOMER_ID_1, BOOK_1);
    } catch (IllegalStateException ignored) {
      exceptionThrown = true;
    }
    assertTrue("The [rent] should have failed since there are [0] copies of [Book1] in the inventar.", exceptionThrown);

    //Scenario: Rent the single copy of this Book
    bookLibrary.getInventar().get(BOOK_1).setHowManyCopies(1);
    bookLibrary.rentBook(1, BOOK_1);
    assertEquals(bookLibrary.getRentals()[DAY_ZERO].countBookRentals(BOOK_1), 1);   //verify successful rental

    //Scenario: Try to rent another copy (but there is only one)
    try {
      exceptionThrown = false;
      bookLibrary.rentBook(CUSTOMER_ID_1, BOOK_1);
    } catch (IllegalStateException ignored) {
      exceptionThrown = true;
    }
    assertTrue("The [rent] should have failed since there are [0] copies of [Book1] in the inventar.", exceptionThrown);




  }

  @Test
  public void testMidnightOp() throws Exception {

  }
}