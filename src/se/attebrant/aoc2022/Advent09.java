package se.attebrant.aoc2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent09 extends AbstractAdvent {

  public Advent09(boolean... debug) {
    super("2022", debug);
  }

  private static boolean printDots = true;

  public static void main(String[] args) {
    var advent = new Advent09(true);
    var dayPart = getDayPart(advent);
    var mode = IS_TEST;
    printDots = true;
    // advent.testStuff();
    // print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
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

  private void printDistance(Position head, Position tail) {
    log(head + ", " + tail + " -> " + head.distance(tail) + ", isTouching = "
        + tail.isTouching(head));
  }

  Set<String> positionsVisitedByTail = new HashSet<>();

  private static String FORMAT = "%8s, %8s: %2s";
  private static int DOTS_ROWS = 21; // 5;
  private static int DOTS_COLS = 27; // 6;

  private int solvePart1(boolean isTest) {
    List<String> lines = readData4(isTest);

    // Initial state - head covers tail
    Position head = new Position(10, 10);
    Position tail = new Position(10, 10);
    log("( HEAD ), ( TAIL ): distance");
    log(String.format(FORMAT, head, tail, 0));
    positionsVisitedByTail.add(tail.toString());
    printDots(DOTS_ROWS, DOTS_COLS, head, tail);
    for (String move : lines) {
      // Line: direction distance
      log("==" + move + " ==");
      String split[] = move.split(" ");
      String direction = split[0];
      int steps = Integer.parseInt(split[1]);
      for (int i = 0; i < steps; i++) {
        moveHead(head, direction);
        moveTail(head, tail, direction);
        positionsVisitedByTail.add(tail.toString());
      }
      // log(String.format(FORMAT, head, tail, tail.distance(head)));
      printDots(DOTS_ROWS, DOTS_COLS, head, tail);
    }
    return positionsVisitedByTail.size();
  }

  private void moveTail(Position head, Position tail, String direction) {
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
        moveHead(tail, direction);
      }
      // positionsVisitedByTail.add(tail.toString());
      // log(String.format(FORMAT, head, tail, distance));
      // printDots(DOTS_ROWS, DOTS_COLS, head, tail);
    }
  }

  private void printDots(int height, int width, Position head, Position tail) {
    if (printDots) {
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

  private void printDots(int height, int width, List<Position> rope) {
    if (!printDots) {
      return;
    }
    List<String> matrix = new ArrayList<>();
    for (int h = 0; h < height; h++) {
      String prefix = String.format("%02d: ", h) + "";
      String line = prefix;
      matrix.add(line);
      for (int w = 0; w < width; w++) {
        line += ".";
      }
      for (int i = rope.size() - 1; i >= 0; i--) {
        String marker = i == 0 ? "H" : String.valueOf(i);
        Position knot = rope.get(i);
        if (knot.row == h) {
          int offset = prefix.length() + knot.col;
          line = line.substring(0, offset) + marker + line.substring(offset + 1);
        }
      }
      System.out.println(line);
    }
    System.out.println();
  }

  private int solvePart2(boolean isTest) {
    List<String> lines = readData3("Advent09test2.txt");// readData4(isTest);
    // List<String> lines = readData4(isTest);

    List<Position> rope = new ArrayList<>();
    int knots = 10;
    for (int i = 0; i < knots; i++) {
      rope.add(new Position(15, 11));
    }
    // TODO *** Refactor to allow iterating over all the knots in the rope

    // Initial state - head covers tail
    Position head = rope.get(0);
    Position tail = rope.get(1);
    log("( HEAD ), ( TAIL ): distance");
    log(String.format(FORMAT, head, tail, 0));
    positionsVisitedByTail.add(tail.toString());
    printDots(DOTS_ROWS, DOTS_COLS, rope);
    for (String move : lines) {
      // Line: direction distance
      log("==" + move + " ==");
      String split[] = move.split(" ");
      String direction = split[0];
      int steps = Integer.parseInt(split[1]);
      for (int i = 0; i < steps; i++) {
        moveHead(head, direction);
        Position previous = head;
        for (int j = 1; j < knots; j++) {
          Position next = rope.get(j);
          moveTail(previous, next, direction);
          if (j == rope.size() - 1) {
            positionsVisitedByTail.add(next.toString());
          }
          previous = next;
        }
        if (debug) {
          // printDots(DOTS_ROWS, DOTS_COLS, rope);
        }
      }
      // log(String.format(FORMAT, head, tail, tail.distance(head)));
      printDots(DOTS_ROWS, DOTS_COLS, rope);
    }
    return positionsVisitedByTail.size();
  }

  private void moveHead(Position head, String direction) {
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



}
