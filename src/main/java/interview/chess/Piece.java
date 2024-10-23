package interview.chess;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    05/06/2015 15:19
 * <B>Since:</B>       Neptune 1.0
 * <B>Description:</B>
 *
 * </pre>
 */
public interface Piece {

  Piece EMPTY = new EmptySquare();

  Color getColor();

  /** @noinspection unused*/
  boolean isLegalMove(Point to);

  class EmptySquare implements Piece {
    @Override
    public Color getColor() {
      return null;
    }

    @Override
    public boolean isLegalMove(Point to) {
      return false;
    }
  }
}
