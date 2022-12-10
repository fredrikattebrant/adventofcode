package se.attebrant.aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent09 extends AbstractAdvent {

  public Advent09(boolean... debug) {
    super("2022", debug);
  }

  public static void main(String[] args) {
    var advent = new Advent09(true);
    var dayPart = getDayPart(advent);
    var mode = IS_LIVE;
    // advent.testStuff();
    print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(mode));
  }

  private class Position {
    int row;
    int col;

    public Position(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getCol() {
      return col;
    }

    public void moveCol(int col) {
      this.col += col;
    }

    public int getRow() {
      return row;
    }

    public void moveRow(int row) {
      this.row += row;
    }

    public int distance(Position pos) {
      // return (row - pos.row) + (col - pos.col);
      return Math.abs(row - pos.row) + Math.abs(col - pos.col);
    }

    public boolean isTouching(Position pos) {
      boolean sameRow = row == pos.row;
      boolean sameCol = col == pos.col;
      int rowDelta = Math.abs(row - pos.row);
      int colDelta = Math.abs(col - pos.col);
      if (sameRow) {
        return colDelta < 2;
      } else if (sameCol) {
        return rowDelta < 2;
      }
      // Diagonal
      return rowDelta == 1 && colDelta == 1;
    }

    public boolean differentRowCol(Position pos) {
      return row != pos.row && col != pos.col;
    }

    public void add(Position pos) {
      row = pos.row;
      col = pos.col;
    }

    @Override
    public String toString() {
      return String.format("(%2s, %2s)", row, col);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = prime + Objects.hash(col, row);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Position other = (Position) obj;
      return col == other.col && row == other.row;
    }

  }

  /**
   * <pre>
   * .0123
   * 0....
   * 1C...
   * 2BA..
   * 3.H..
   * </pre>
   */
  private void testStuff() {
    printDistance(new Position(3, 1), new Position(3, 1)); // Same
    printDistance(new Position(3, 1), new Position(2, 1)); // A
    printDistance(new Position(3, 1), new Position(2, 0)); // B
    printDistance(new Position(3, 1), new Position(1, 0)); // C
    printDistance(new Position(1, 2), new Position(3, 1)); //
    log("Equals?" + new Position(1, 2).equals(new Position(1, 2)));
    Set<Position> pset = new HashSet<>();
    pset.add(new Position(0, 0));
    pset.add(new Position(0, 0));
    pset.add(new Position(0, 1));
    pset.add(new Position(0, 1));
    log("=== EOT ===");
  }

  private void printDistance(Position head, Position tail) {
    log(head + ", " + tail + " -> " + head.distance(tail) + ", isTouching = "
        + tail.isTouching(head));
  }

  Set<String> positionsVisitedByTail = new HashSet<>();

  private static String FORMAT = "%8s, %8s: %2s";

  private int solvePart1(boolean isTest) {
    List<String> lines = readData4(isTest);

    // Initial state - head covers tail
    Position head = new Position(4, 0);
    Position tail = new Position(4, 0);
    log("( HEAD ), ( TAIL ): distance");
    log(String.format(FORMAT, head, tail, 0));
    positionsVisitedByTail.add(tail.toString());
    printDots(5, 6, head, tail);
    for (String move : lines) {
      // Line: direction distance
      log("==" + move + " ==");
      String split[] = move.split(" ");
      String direction = split[0];
      int steps = Integer.parseInt(split[1]);
      for (int i = 0; i < steps; i++) {
        // Move the head 1 step in the direction
        switch (direction) {
          case "R":
            head.moveCol(1);
            break;
          case "L":
            head.moveCol(-1);
            break;
          case "D":
            head.moveRow(1);
            break;
          case "U":
            head.moveRow(-1);
            break;
          default:
            break;
        }
        boolean isTouching = tail.isTouching(head);
        if (!isTouching) {
          if (tail.differentRowCol(head)) {
            // Move diagonally towards the head
            int rowDelta = head.row - tail.row;
            int colDelta = head.col - tail.col;
            int rowStep = rowDelta / Math.abs(rowDelta);
            int colStep = colDelta / Math.abs(colDelta);
            tail.moveRow(rowStep);
            tail.moveCol(colStep);
          } else {
            // move tail - use same direction as head
            switch (direction) {
              case "R":
                tail.moveCol(1);
                break;
              case "L":
                tail.moveCol(-1);
                break;
              case "D":
                tail.moveRow(1);
                break;
              case "U":
                tail.moveRow(-1);
                break;
              default:
                break;
            }
          }
          positionsVisitedByTail.add(tail.toString());
          // log(String.format(FORMAT, head, tail, distance));
          printDots(5, 6, head, tail);
        }
      }
      // log(String.format(FORMAT, head, tail, tail.distance(head)));
      printDots(5, 6, head, tail);
    }
    return positionsVisitedByTail.size();
  }

  private void printDots(int height, int width, Position head, Position tail) {
    if (1 == 1) {
      return;
    }
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        if (head.equals(new Position(h, w))) {
          System.out.print("H");
        } else if (tail.equals(new Position(h, w))) {
          System.out.print("T");
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  private int solvePart2(boolean isTest) {
    return -1;
  }

}
