package interview.design.library.books;

import java.util.*;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/11/2014 23:04
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class DailyRentals {

  private final Map<String, List<Rental>> rentals = new HashMap<>();  //key=bookTitle

  public void addRental(Rental rental) {
    String bookTitle = rental.getBookTitle();
    List<Rental> rentalList = rentals.get(bookTitle);

    if (rentalList==null) {
      rentalList = new LinkedList<>();
      rentals.put(bookTitle, rentalList);
    }

    rentalList.add(rental);
  }

  public Rental removeRental(int customerId, String bookTitle) {
    List<Rental> rentalList = rentals.get(bookTitle);

    if (rentalList==null) return null;

    for (Iterator<Rental> it = rentalList.iterator(); it.hasNext(); ) {
      Rental rental = it.next();
      if (rental.getCustomerId()==customerId && rental.getBookTitle().equals(bookTitle)) {
        it.remove();
        return rental;
      }
    }
    return null;
  }

  public int countBookRentals(String bookTitle) {
    List<Rental> rentalList = rentals.get(bookTitle);
    if (rentalList==null) return 0;
    return rentalList.size();
  }

  //add all rentals in rhs into the rentals in this
  public void merge(DailyRentals rhs) {
    for (String bookTitle : rhs.rentals.keySet()) {
      List<Rental> rhsRentals = rhs.rentals.get(bookTitle);
      if (rhsRentals!=null) {
        for (Rental rhsRental : rhsRentals) {
          addRental(rhsRental);
        }
      }
    }
  }

  public int getRentalsCount() {
    int count=0;
    for (List<Rental> rentalList : rentals.values()) {
      if (rentalList!=null) {
        count += rentalList.size();
      }
    }
    return count;
  }

  public int getRentalsCount(String bookTitle) {
    List<Rental> rentalList = rentals.get(bookTitle);
    if (rentalList==null) return 0;
    return rentalList.size();
  }

  /*package*/Map<String, List<Rental>> getRentals() {
    return rentals;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("<DailyRentals>");
    result.append("<rentals>");
    for (String bookTitle : rentals.keySet()) {
      result.append("<BookRentals bookTitle=\"").append(bookTitle).append("\">");
      List<Rental> rentalList = rentals.get(bookTitle);
      for (Rental rental : rentalList) {
        result.append(rental);
      }
      result.append("</BookRentals>");
    }
    result.append("</rentals>");
    result.append("</DailyRentals>");

    return result.toString();
  }
}
