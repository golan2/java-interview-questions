package interview.design.library.books;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DailyRentalsTest extends TestCase {

  private static final String BOOK_1 = "Book1";
  private static final String BOOK_2 = "Book2";
  private static final int CUSTOMER_ID_1 = 1;
  DailyRentals dailyRentals = new DailyRentals();

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testAddRental() throws Exception {
    assertEquals(0, dailyRentals.getRentalsCount());

    Rental newBook = new Rental(CUSTOMER_ID_1, BOOK_1);
    dailyRentals.addRental(newBook);
    assertEquals(1, dailyRentals.getRentalsCount());
    Rental bookFromList = dailyRentals.getRentals().get(BOOK_1).iterator().next();
    assertEquals(newBook, bookFromList);

    dailyRentals.addRental(newBook);
    assertEquals(2, dailyRentals.getRentalsCount());
  }

  @Test
  public void testRemoveRental() throws Exception {
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_1));
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_2));
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_2));

    Rental removedItem;

    removedItem = dailyRentals.removeRental(CUSTOMER_ID_1, BOOK_1+"_");      //different bookTitle - no such rental
    assertNull(removedItem);

    removedItem = dailyRentals.removeRental(CUSTOMER_ID_1, BOOK_1);          //legal return
    assertNotNull(removedItem);

    removedItem = dailyRentals.removeRental(CUSTOMER_ID_1, BOOK_2);          //legal return
    assertNotNull(removedItem);

    removedItem = dailyRentals.removeRental(CUSTOMER_ID_1, BOOK_2);          //legal return (2 copies of BOOK_2 were rented)
    assertNotNull(removedItem);

    removedItem = dailyRentals.removeRental(CUSTOMER_ID_1, BOOK_2);          //this book was already returned
    assertNull(removedItem);

  }

  @Test
  public void testGetRentalsCount() throws Exception {
    assertEquals(0, dailyRentals.getRentalsCount());
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_1));
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_2));
    assertEquals(2, dailyRentals.getRentalsCount());
  }

  @Test
  public void testMerge() throws Exception {
    //Add 6 rentals
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_1));
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, BOOK_2));
    dailyRentals.addRental(new Rental(CUSTOMER_ID_1, "Book3"));
    dailyRentals.addRental(new Rental(2, BOOK_1));
    dailyRentals.addRental(new Rental(2, BOOK_2));
    dailyRentals.addRental(new Rental(2, "Book3"));

    //Create a new Rental
    Rental newlyAddedRental = new Rental(CUSTOMER_ID_1, "Book4");
    DailyRentals rhs = new DailyRentals();
    rhs.addRental(newlyAddedRental);

    //Merge
    dailyRentals.merge(rhs);

    //Verify size first
    assertEquals(7, dailyRentals.getRentalsCount());

    //Verify that the newlyAddedRental is in the corresponding rentalList
    List<Rental> rentalList = dailyRentals.getRentals().get(newlyAddedRental.getBookTitle());
    assertNotNull(rentalList);
    assertTrue(rentalList.contains(newlyAddedRental));
  }
}