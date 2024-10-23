package interview.design.library.books;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/11/2014 23:56
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    BookLibrary bookLibrary = new BookLibrary();
    bookLibrary.addOrUpdateToInventar(new InventarBook("Book1", 10));
    bookLibrary.addOrUpdateToInventar(new InventarBook("Book2", 10));
    bookLibrary.addOrUpdateToInventar(new InventarBook("Book3", 10));
    bookLibrary.addOrUpdateToInventar(new InventarBook("Book4", 10));

    bookLibrary.rentBook(1, "Book1");
    Thread.sleep(5);
    bookLibrary.rentBook(2, "Book1");
    Thread.sleep(5);
    bookLibrary.rentBook(1, "Book2");
    Thread.sleep(5);
    bookLibrary.rentBook(2, "Book2");

    System.out.print("<Run>");

    System.out.print(bookLibrary);

    bookLibrary.midnightOp();

    System.out.print(bookLibrary);

    //bookLibrary.rent(3, "Book1");
    //Thread.sleep(5);
    //bookLibrary.rent(4, "Book1");
    //Thread.sleep(5);
    //bookLibrary.rent(5, "Book2");
    //Thread.sleep(5);
    //bookLibrary.rent(6, "Book2");
    //
    //
    //
    //System.out.println(bookLibrary);

    System.out.print("</Run>");
  }
}
