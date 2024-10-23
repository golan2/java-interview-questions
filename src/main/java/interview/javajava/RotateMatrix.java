package interview.javajava;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    11/01/2015 23:03
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class RotateMatrix {

  public static void main(String[] args) {
    int[][] matrix = {
        {11,12,13,14,15},
        {21,22,23,24,25},
        {31,32,33,34,35},
        {41,42,43,44,45},
        {51,52,53,54,55}

    };

    System.out.println(matrixToString(matrix));

    rotate(matrix);

    System.out.println(matrixToString(matrix));

  }

  private static String matrixToString(int[][] matrix) {
    StringBuilder buf = new StringBuilder();
      for (int[] row : matrix) {
          for (int cell : row) {
              buf.append(cell).append(", ");
          }
          buf.append("\n");
      }
    return buf.toString();
  }

  /**
   * Rotate the matrix in 90 degrees.
   * 
   * Before explaining the algorithm we will define terms first.
   * 
   * First Ring:
   * ===========
   * Imagine a matrix of 10x10 that contains the numbers [1..100] ordered by ascending value (first row starts with 1, second row starts with 11, ...)
   *  First Row      [1,2,3..10]
   *  Last Column    [10,20,30..100]
   *  Last Row       [100,99,..91]
   *  First Column   [91,81,..1]
   *
   * Anchor:
   * =======
   * We can represent a ring by stating it's 4 anchors (although 2 are enough we will use 4).
   * The anchors are the cells at the corner.
   * For the "First Ring" described above the anchors are [1,10,100,91]
   *
   * Ring:
   * =====
   * So if we define the first ring we can understand that the second ring is the next inner ring in the matrix.
   * In our matrix of example the anchors of the second ring are [12,19,89,81] and for the third are [23,28,78,73].
   * If numbers are confusing then draw the matrix on a paper or Excel; it should help :-)
   * 
   * The Algorithm:
   * ==============
   * We handle the matrix ring by ring, starting from the outer and going in.
   * Each time we have the 4 anchors held in {leftTop, leftBottom, rightBottom, rightTop}
   * In order to actually rotate the ring we call {@link #rotateRing(int[][], Ring)}
   * The actual rotate work is described there.
   * Here all we do is iterate the rings from the outer to the inner.
   * Each time we move the anchors one step diagonally towards the middle to proceed to the next inner ring.
   *
   * @param matrix the matrix
   */
  private static void rotate(int[][] matrix) {

    Ring ring = new Ring(matrix);    //this is the "First Ring"

    while (ring.leftBottom.col < ring.rightBottom.col) {

      rotateRing(matrix, ring);

      //move the anchors to represent the next ring
      ring.leftTop.goRight().goDown();
      ring.rightTop.goLeft().goDown();
      ring.rightBottom.goLeft().goUp();
      ring.leftBottom.goRight().goUp();


    }
  }


  /**
   * In order to understand what "ring" and "anchor" refer to you should read the docs of {@link #rotate(int[][])}
   *
   * This method will rotate a single ring in the matrix.
   * In order to rotate a ring you start with the corners.
   * You rotate the corners by calling {@link #rotate4points(int[][], Point, Point, Point, Point)}
   * After corners are ok we go to the next 4 cells:
   *  - The leftTop goes one cell down.
   *  - The leftBottom goes one cell right.
   *  - The rightBottom goes one cell up.
   *  - The rightTop goes one cell left.
   *  And again we call {@link #rotate4points(int[][], Point, Point, Point, Point)}
   *  We continue moving the points along the ring until the end of the row/col.
   *
   *  And that's it :)
   *
   * @param matrix the matrix
   */
  private static void rotateRing(int[][] matrix, Ring ring) {

    //we are going to move the points now and we don't want to ruin the ring parameter so we clone it
    ring = ring.clone();

    System.out.println(ring);

    while (ring.leftBottom.col < ring.rightBottom.col) {

      rotate4points(matrix, ring.leftTop, ring.leftBottom, ring.rightBottom, ring.rightTop);

      //move anchors along the ring
      ring.leftTop.goDown();
      ring.leftBottom.goRight();
      ring.rightBottom.goUp();
      ring.rightTop.goLeft();

    }

    System.out.println(matrixToString(matrix));
  }

  /**
   * "a" goes into "b"
   * "b" goes into "c"
   * "c" goes into "d"
   * "d" goes into "a"
   * and we use temp in order not to lose values :-)
   * @param matrix the matrix to be rotated
   * @param a point in the matrix where position to be replaced
   * @param b point in the matrix where position to be replaced
   * @param c point in the matrix where position to be replaced
   * @param d point in the matrix where position to be replaced
   */
  private static void rotate4points(int[][] matrix, Point a, Point b, Point c, Point d) {
    int temp = matrix[d.row][d.col];
    matrix[d.row][d.col] = matrix[c.row][c.col];
    matrix[c.row][c.col] = matrix[b.row][b.col];
    matrix[b.row][b.col] = matrix[a.row][a.col];
    matrix[a.row][a.col] = temp;
  }


  private static class Point {
    int row;
    int col;

    public Point(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public Point goLeft () { col--; return this; }
    public Point goRight() { col++; return this; }
    public Point goUp   () { row--; return this; }
    public Point goDown () { row++; return this; }

    public Point clone() {
      return new Point(row,col);
    }

    @Override
    public String toString() {
      return "(" + row + "," + col + ')';
    }



  }


  private static class Ring {
    private final Point leftTop;        //the 4 anchors of the ring
    private final Point leftBottom;
    private final Point rightBottom;
    private final Point rightTop;

    public Ring(Point leftTop, Point leftBottom, Point rightBottom, Point rightTop) {
      this.leftTop = leftTop;
      this.leftBottom = leftBottom;
      this.rightBottom = rightBottom;
      this.rightTop = rightTop;
    }

    public Ring(int[][] matrix) {
      this.leftTop = new Point(0,0);
      this.leftBottom = new Point(matrix.length -1, 0);
      this.rightBottom = new Point(matrix.length -1, matrix.length -1);
      this.rightTop = new Point(0, matrix.length -1);
    }


    public Ring clone() {
      return new Ring(leftTop.clone(), leftBottom.clone(), rightBottom.clone(), rightTop.clone());
    }

    @Override
    public String toString() {
      return "{" + leftTop + " # " + leftBottom + " # " + rightBottom + " # " + rightTop + "}";
    }
  }
}
