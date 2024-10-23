package interview.chess;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    05/06/2015 15:31
 * <B>Since:</B>       Neptune 1.0
 * <B>Description:</B>
 *
 * </pre>
 */
public abstract class AbsPiece implements Piece {
  protected final Color color;
  protected final Point location;
  protected final Chessboard chessboard;

  protected AbsPiece(Color color, Point location, Chessboard chessboard) {this.color = color;
    this.location = location;
    this.chessboard = chessboard;
  }

  @Override
  public boolean isLegalMove(Point destination) {
    return legalDestination(destination) && isValidMove(destination) && freeRoutToPoint(destination);


  }

  /**
   * Check if we can move to the destination.
   * We need it to be within the boundaries of the board.
   * And we need the destination to be one of 2 options:
   * [1] Empty
   * [2] Containing an opponent's piece
   *
   * If the destination is occupied by our color we can't go there.
   * @param destination square
   * @return true if we can go there
   */
  private boolean legalDestination(Point destination) {
    return
        chessboard.checkBoundaries(destination)
            &&
            ( chessboard.square(destination)==EMPTY  ||  chessboard.square(destination).getColor()!=getColor() );
  }


  /**
   * Check that the way from [this.location] to [destination] is free.
   * We don't care about the fact if this is a valid move from this specific Piece. This is what we have {@link #isValidMove(Point)} for.
   * All we want to check is that the chessboard doesn't contain Pieces along the route.
   *
   * Take into account
   * [1] If the [destination] if it is occupied by opponent then it is Ok
   * [2] If there is any Piece along the way it is a blocker unless {@link #isLeapEnabled()} is true.
   * @param destination the point on board where we want to move this piece
   * @return if free route or not
   */
  private boolean freeRoutToPoint(Point destination) {
    if (isLeapEnabled()) return true; //like the Knight which doesn't need the "route" to be clear.

    if (location.getRow()==destination.getRow()) {
      //This is a movement of something like a rock which trying to move from left to right or vice versa
      throw new RuntimeException("UNIMPLEMENTED");

    }
    throw new RuntimeException("UNIMPLEMENTED");

  }

  /**
   * If this Piece is allowed to leap above others like the Knight
   * @return
   */
  protected abstract boolean isLeapEnabled();

  protected abstract boolean isValidMove(Point to);

  @Override
  public Color getColor() {
    return color;
  }

}
