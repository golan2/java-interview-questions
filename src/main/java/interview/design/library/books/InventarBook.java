package interview.design.library.books;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/11/2014 23:00
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class InventarBook extends Book {

  private int    howManyCopies;

  public InventarBook(String bookTitle, int howManyCopies) {
    super(bookTitle);
    this.howManyCopies = howManyCopies;
  }

  public int getHowManyCopies() {
    return howManyCopies;
  }

  public void setHowManyCopies(int howManyCopies) {
    this.howManyCopies = howManyCopies;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("<InventarBook bookTitle=\"").append(bookTitle).append("\" howManyCopies=\"").append(howManyCopies).append("\" />").toString();
  }

}
