package interview.design.library.books;

import lombok.Getter;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/11/2014 23:03
 * </pre>
 */
@Getter
public class Rental {
  private final long date;
  private final int customerId;
  private final String bookTitle;

  public Rental(int customerId, String bookTitle) {
    this.date = System.currentTimeMillis();
    this.customerId = customerId;
    this.bookTitle = bookTitle;
  }


  @Override
  public String toString() {
    return "<Rental customerId=\"" + customerId + "\" bookTitle=\"" + bookTitle + "\" date=\"" + date + "\" />";
  }

}
