package interview.chess;

/** @noinspection unused*/
public class Rook extends AbsPiece {
  protected Rook(Color color, Point location, Chessboard chessboard) {
    super(color, location, chessboard);
  }

  @Override
  protected boolean isLeapEnabled() {
    return false;
  }

  @Override
  protected boolean isValidMove(Point to) {
    return (location.getRow()==to.getRow() || location.getCol()==to.getCol());
  }

  @Override
  public String toString() {
    return "Rook{}";
  }
}
