package interview.design.library.books;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/11/2014 23:02
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class BookLibrary {

  private static final int MAX_DAYS_RENTAL = 7;

  private final Map<String, InventarBook> inventar; //key=bookTitle
  private final DailyRentals[]    rentals;

  public BookLibrary() {
    inventar = new HashMap<>();
    rentals = new DailyRentals[MAX_DAYS_RENTAL+1];
    for (int i = 0; i < rentals.length; i++) {
      rentals[i] = new DailyRentals();
    }
  }

  public void addOrUpdateToInventar(InventarBook inventarBook) {
    inventar.put(inventarBook.getBookTitle(), inventarBook);
  }

  public void rentBook(int customerId, String bookTitle) {

    //make sure we have this book in the library
    InventarBook book = inventar.get(bookTitle);
    if (book==null) throw new IllegalArgumentException("There is no book with this title in the library. Title="+bookTitle);

    //count how many copies of it are already rented
    int rentCount = 0;
    for (DailyRentals daily : rentals) {
      rentCount += daily.countBookRentals(bookTitle);
    }

    //do we have a free copy?
    if (rentCount>=book.getHowManyCopies()) throw new IllegalStateException("All "+book.getHowManyCopies()+" copies of this book are rented. Title="+bookTitle);

    rentals[0].addRental(new Rental(customerId, bookTitle));
  }

  public boolean returnBook(int customerId, String bookTitle) {
    for (int i=MAX_DAYS_RENTAL-1 ; i>0 ; i--) {
      Rental rental = rentals[i].removeRental(customerId, bookTitle);
      if (rental != null) {
        return true;
      }
    }
    return false;
  }

  public void midnightOp() {
    rentals[MAX_DAYS_RENTAL].merge(rentals[MAX_DAYS_RENTAL-1]);
    for (int i=MAX_DAYS_RENTAL-1 ; i>0 ; i--) {
      rentals[i] = rentals[i-1];
    }
    rentals[0] = new DailyRentals();
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("<BookLibrary>");

    result.append("<inventar>");
    for (InventarBook book : inventar.values()) {
      result.append(book);
    }
    result.append("</inventar>");


    result.append("<rentals>");
    for (int i = 0; i < rentals.length; i++) {
      result.append("<RentalBucket index=\""+i+"\">").append(rentals[i]).append("</RentalBucket>");
    }
    result.append("</rentals>");


    result.append("</BookLibrary>");

    return result.toString();
  }

  /*package*/ Map<String, InventarBook> getInventar() {
    return inventar;
  }

  /*package*/ DailyRentals[] getRentals() {
    return rentals;
  }
}
