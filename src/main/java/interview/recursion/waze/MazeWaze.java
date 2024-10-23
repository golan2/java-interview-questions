package interview.recursion.waze;

import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    07/01/2015 20:37
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * https://www.cs.bu.edu/teaching/alg/maze/
 * http://www.geeksforgeeks.org/practice-questions-for-linked-list-and-recursion/
 *
 *
 * </pre>
 */
public class MazeWaze extends AbsMazeWaze {
  private static final String MAZE_FILE = "C:\\Users\\golaniz\\Documents\\Izik\\Dropbox\\Java\\Projects\\123Test\\static_content\\Maze3.txt";

  public MazeWaze() {super(MAZE_FILE);}

  public static void main(String[] args) throws IOException {
    MazeWaze mazeWaze = new MazeWaze();
    mazeWaze.readFromFile();
    mazeWaze.printMatrix();
    Pair<Point,Point> startEnd = mazeWaze.findStartAndEnd();
    System.out.println(startEnd);
    Path path = mazeWaze.findShortestPath(startEnd.getKey(), startEnd.getValue());
    System.out.println(path);
    mazeWaze.markPath(path);
    mazeWaze.printMatrix();
  }

  private Path findShortestPath(Point start, Point end) {
    if (start.equals(end)) {
      return Path.createSinglePointPath(end);
    }
    if (outOfBoundaries(start) || isWall(start) || isMark(start)) {
      return Path.DEAD_END;
    }

    byte prevValue = this.matrix[start.row][start.col];         //a corner case is where we are in the "real" start and we MARK it so when we finish we want to put back the START and not FREE so we store the value here to know what to put later.
    this.matrix[start.row][start.col] = MARK;                   //MARK this cell to not repeat it later

    ArrayList<Path> paths = new ArrayList<>(4);
    paths.add(findShortestPath(start.moveNorth(), end));
    paths.add(findShortestPath(start.moveSouth(), end));
    paths.add(findShortestPath(start.moveEast(), end));
    paths.add(findShortestPath(start.moveWest(), end));
    Path shortest = Collections.min(paths);                    //Try {North, South, East, West} and return the shortest of all

    this.matrix[start.row][start.col] = prevValue;              //restore MARKed value

    if (shortest == Path.DEAD_END)
      return Path.DEAD_END;          //if the shortest is DEAD_END (i.e. all paths are DEAD_END) then it is indeed a DEAD_END

    shortest.add(start);                                        //if not DEAD_END then add this Point and return the new path

    return shortest;
  }

  private boolean isMark(Point p) {
    return (matrix[p.row][p.col] == MARK);
  }

  private boolean isWall(Point p) {
    return (matrix[p.row][p.col] == WALL);
  }

  @SuppressWarnings("RedundantIfStatement")
  private boolean outOfBoundaries(Point point) {
    if (point.getRow()<0 || point.getCol()<0) return true;
    if (point.getRow() >= matrix.length) return true;
    if (point.getCol() >= matrix[point.getRow()].length) return true;
    return false;

  }

}
