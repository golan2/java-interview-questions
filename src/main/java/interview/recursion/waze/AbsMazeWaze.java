package interview.recursion.waze;

import javafx.util.Pair;
import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    12/01/2015 21:35
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public abstract class AbsMazeWaze {

  public static final byte MARK = '+';
  private static final int    MAX_SIZE  = 1000;
  public  static final byte   EMPTY     = ' ';
  public  static final byte   WALL      = '#';
  public  static final byte   START     = 'S';
  public  static final byte   END       = 'E';


  protected byte[][] matrix = new byte[MAX_SIZE][];
  private final String mazeFile;

  protected AbsMazeWaze(String mazeFile) {this.mazeFile = mazeFile;}

  protected Pair<Point, Point> findStartAndEnd() {
    Point start = null;
    Point end = null;
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (matrix[row][col]=='S') {
          start = new Point(row,col);
        }
        else if (matrix[row][col]=='E') {
          end = new Point(row,col);
        }
      }
    }
    if (start==null) throw new IllegalStateException("Start is null");
    if (end==null) throw new IllegalStateException("End is null");
    return new Pair<>(start, end);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  protected void readFromFile() throws IOException {
    InputStream inputStream = new FileInputStream(new File(mazeFile));
    byte[] bytes = IOUtils.toByteArray(inputStream);

    byte[] line = new byte[MAX_SIZE];
    int row=0;
    int col=0;

    for (byte bb : bytes) {

      if (bb == 10) {
        //end of line - put whatever we have in "line" into the relevant row in the matrix
        matrix[row] = Arrays.copyOf(line, col);
        row++;
        col = 0;
      }

      else if (bb == 13) {
        //skip this one since "newline" is 10 and 13 so we handle it only once
      }

      else {
        line[col] = readChar(bb);
        col++;
      }
    }

    if (matrix[row]==null) {
      matrix[row] = Arrays.copyOf(line, col);
    }
    matrix = Arrays.copyOf(matrix, row + 1);
  }

  private byte readChar(byte b) {
    if (b==EMPTY || b==WALL || b==START || b==END) {
      return b;
    }
    else {
      throw new IllegalArgumentException("Byte=["+b+"]");
    }
  }

  @SuppressWarnings("ForLoopReplaceableByForEach")
  protected void printMatrix() {
    for (int row = 0; row < matrix.length; row++) {
      StringBuilder buf = new StringBuilder();
      for (int col = 0; col < matrix[row].length && matrix[row][col]!=0 ; col++) {
        buf.append((char)matrix[row][col]);
      }
      System.out.println(buf.toString());
    }
  }

  /**
   * Given a path this method will mark it in the matrix.
   * Each cell in the path will be overwritten in the matrix to contain {@link #MARK}
   *
   * @param path the path on matrix to be marked
   * @return a map of previous values. A map from each point to its previous value (before we marked it)
   */
  public Map<Point, Byte> markPath(Path path) {
    Map<Point, Byte> result = new HashMap<>();
    for (Point point : path.getPointList()) {
      result.put(point, this.matrix[point.row][point.col]);
      this.matrix[point.row][point.col] = MARK;
    }
    return result;
  }

  /**
   * <pre>
   * <B>Copyright:</B>   Izik Golan
   * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
   * <B>Creation:</B>    12/01/2015 21:36
   * <B>Since:</B>       BSM 9.21
   * <B>Description:</B>
   *
   * </pre>
   */
  public static class Point {
    int row;
    int col;

    public Point(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }

    public Point moveNorth() {
      return new Point(row - 1, col);
    }

    public Point moveSouth() {
      return new Point(row + 1, col);
    }

    public Point moveEast() {
      return new Point(row, col + 1);
    }

    public Point moveWest() {
      return new Point(row, col - 1);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Point)) {
        return false;
      }

      Point point = (Point) o;

      if (col != point.col) {
        return false;
      }
      //noinspection RedundantIfStatement
      if (row != point.row) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = row;
      result = 31 * result + col;
      return result;
    }

    @Override
    public String toString() {
      return "(" + row + "," + col + ')';
    }

  }

  /**
   * <pre>
   * <B>Copyright:</B>   Izik Golan
   * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
   * <B>Creation:</B>    12/01/2015 21:36
   * <B>Since:</B>       BSM 9.21
   * <B>Description:</B>
   *
   * </pre>
   */
  public static class Path implements Comparable<Path> {
    public static final Path DEAD_END = new Path() {
      public int length() {
        return Integer.MAX_VALUE;
      }

      @Override
      public void add(Point point) {
        throw new IllegalStateException("DEAD_END: " + point.toString());
      }
    };

    private final List<Point> pointList;

    public Path() {pointList = new ArrayList<>();}

    public List<Point> getPointList() {
      return pointList;
    }

    public int length() {
      return pointList.size();
    }

    public static Path createSinglePointPath(Point point) {
      Path path = new Path();
      path.add(point);
      return path;
    }

    public void add(Point point) {
      this.pointList.add(point);
    }

    @Override
    public String toString() {
      return "Path{" + Arrays.toString(pointList.toArray()) + '}';
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Path rhs) {
      return this.length() - rhs.length();
    }
  }
}
