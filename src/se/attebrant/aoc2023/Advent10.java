package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.attebrant.common.AbstractAdvent;

public class Advent10 extends AbstractAdvent {

  public Advent10(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent10 advent = new Advent10(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    parseInput(input);
    return isPart2 ? solvePart2() : solvePart1();
  }

  private static final char START = 'S';
  private static final char VERTICAL = '|';
  private static final char HORIZONTAL = '-';
  private static final char NORTHEASTL = 'L';
  private static final char NORTHWESTJ = 'J';
  private static final char SOUTHWEST7 = '7';
  private static final char SOUTHEASTF = 'F';
  private static final char GROUND = '.';
  private static final char NOKIND = '*';

  private static List<String> diagram = new ArrayList<>();
  private static int width;
  private static int height;
  private static Position startPosition;
  private static char startKind; // S translated into a kind

  record Position(char pipe, int row, int col) {

    boolean atStart() {
      return START == pipe;
    }

    boolean canGoEast() {
      char thePipe = atStart() ? startKind : pipe;
      return col < width
          && (thePipe == HORIZONTAL || thePipe == NORTHEASTL || thePipe == SOUTHEASTF);
    }

    boolean canGoWest() {
      char thePipe = atStart() ? startKind : pipe;
      return col > 0
          && (thePipe == HORIZONTAL || thePipe == NORTHWESTJ || thePipe == SOUTHWEST7);
    }

    boolean canGoNorth() {
      char thePipe = atStart() ? startKind : pipe;
      return row > 0
          && (thePipe == VERTICAL || thePipe == NORTHEASTL || thePipe == NORTHWESTJ);
    }

    boolean canGoSouth() {
      char thePipe = atStart() ? startKind : pipe;
      return row < height
          && (thePipe == VERTICAL || thePipe == SOUTHEASTF || thePipe == SOUTHWEST7);
    }

    public Position goEast() {
      return new Position(diagram.get(row).charAt(col + 1), row, col + 1);
    }

    public Position goWest() {
      return new Position(diagram.get(row).charAt(col - 1), row, col - 1);
    }

    public Position goNorth() {
      return new Position(diagram.get(row - 1).charAt(col), row - 1, col);
    }

    public Position goSouth() {
      return new Position(diagram.get(row + 1).charAt(col), row + 1, col);
    }

    @Override
    public String toString() {
      return pipe + " (" + row + ", " + col + ")";
    }

  }

  /**
   * Determine the kind of pipe for the start position.
   */
  private static char getStartKind(Position position) {
    int row = position.row;
    int col = position.col;

    char kind = NOKIND;
    String r = diagram.get(row);

    if (row == 0) { // Top row
      if (col == 0) { // Left corner
        return SOUTHEASTF; // F
      } else if (col == width - 1) { // Right corner
        return SOUTHWEST7; // 7
      } else {
        // Check start position in the middle of the top row
        char left = r.charAt(col - 1);
        char right = r.charAt(col + 1);
        char below = diagram.get(row + 1).charAt(col);

        boolean isConnectedLeft = left == HORIZONTAL || left == SOUTHEASTF;
        boolean isConnectedRight = right == HORIZONTAL || right == SOUTHWEST7;
        boolean isConnectedDown = below == NORTHWESTJ || below == NORTHEASTL;

        if (isConnectedLeft && isConnectedRight) {
          return HORIZONTAL;
        }

        if (isConnectedLeft && isConnectedDown) {
          return SOUTHWEST7;
        }

        if (isConnectedRight && isConnectedDown) {
          return SOUTHEASTF;
        }
      }
    }

    if (row == height - 1) { // Bottom row
      if (col == 0) { // Left corner
        return NORTHEASTL; // L
      } else if (col == width - 1) { // Right corner
        return NORTHWESTJ; // J
      } else {
        // Check start position in the middle of the bottom row
        char left = r.charAt(col - 1);
        char right = r.charAt(col + 1);
        char above = diagram.get(row - 1).charAt(col);

        boolean isConnectedLeft = left == HORIZONTAL || left == NORTHEASTL;
        boolean isConnectedRight = right == HORIZONTAL || right == NORTHWESTJ;
        boolean isConnectedUp = above == SOUTHWEST7 || above == SOUTHEASTF;

        if (isConnectedLeft && isConnectedRight) {
          return HORIZONTAL;
        }

        if (isConnectedLeft && isConnectedUp) {
          return NORTHWESTJ;
        }

        if (isConnectedRight && isConnectedUp) {
          return NORTHEASTL;
        }
      }
    }

    // A middle row
    char above = diagram.get(row - 1).charAt(col);
    char below = diagram.get(row + 1).charAt(col);

    if (col == 0) {
      // Left column - only up/down/right
      char right = r.charAt(col + 1);

      boolean isConnectedUp = above == VERTICAL || above == SOUTHEASTF;
      boolean isConnectedDown = below == VERTICAL || below == NORTHEASTL;
      boolean isConnectedRight = right == HORIZONTAL || right == NORTHWESTJ || right == SOUTHWEST7;

      if (isConnectedUp && isConnectedDown) {
        return VERTICAL;
      }

      if (isConnectedUp && isConnectedRight) {
        return NORTHEASTL;
      }

      if (isConnectedDown && isConnectedRight) {
        return SOUTHEASTF;
      }

    } else if (col == width - 1) {
      // Right column - only up/down/left
      char left = r.charAt(col - 1);

      boolean isConnectedUp = above == VERTICAL || above == SOUTHWEST7;
      boolean isConnectedDown = below == VERTICAL || below == NORTHWESTJ;
      boolean isConnectedLeft = left == HORIZONTAL || left == NORTHEASTL || left == SOUTHEASTF;

      if (isConnectedUp && isConnectedDown) {
        return VERTICAL;
      }

      if (isConnectedUp && isConnectedLeft) {
        return NORTHWESTJ;
      }

      if (isConnectedDown && isConnectedLeft) {
        return SOUTHWEST7;
      }

    }

    // Can connect in any direction - left/up/down/right/horizontal/vertical
    char left = r.charAt(col - 1);
    char right = r.charAt(col + 1);

    boolean isConnectedUp = above == VERTICAL || above == SOUTHWEST7 || above == SOUTHEASTF;
    boolean isConnectedDown = below == VERTICAL || below == NORTHWESTJ || below == NORTHEASTL;
    boolean isConnectedLeft = left == HORIZONTAL || left == NORTHEASTL || left == SOUTHEASTF;
    boolean isConnectedRight = right == HORIZONTAL || right == NORTHWESTJ || right == SOUTHWEST7;

    if (isConnectedUp && isConnectedDown) {
      return VERTICAL;
    }

    if (isConnectedLeft && isConnectedRight) {
      return HORIZONTAL;
    }

    if (isConnectedUp && isConnectedRight) {
      return NORTHEASTL;
    }

    if (isConnectedDown && isConnectedRight) {
      return SOUTHEASTF;
    }

    if (isConnectedDown && isConnectedLeft) {
      return SOUTHWEST7;
    }

    if (isConnectedLeft && isConnectedUp) {
      return NORTHWESTJ;
    }

    // This is an error - should not happen
    return kind;
  }

  private static void parseInput(List<String> input) {
    diagram = input;
    height = input.size();
    width = input.get(0).length();
    for (int i = 0; i < input.size(); i++) {
      String row = input.get(i);
      for (int j = 0; j < row.length(); j++) {
        char pipe = row.charAt(j);
        if (pipe == START) {
          startPosition = new Position(START, i, j);
          startKind = getStartKind(startPosition);
        }
      }
    }
  }

  private int solvePart1() {
    int distance = 0;
    // START -> go right (or down or left or up) and stop when back at the start
    // The result is numberOfSteps / 2
    Position pos = startPosition;
    Set<Position> previous = new HashSet<>();
    do {
      previous.add(pos);
      if (pos.canGoEast() && !previous.contains(pos.goEast())) {
        pos = pos.goEast();
      } else if (pos.canGoSouth() && !previous.contains(pos.goSouth())) {
        pos = pos.goSouth();
      } else if (pos.canGoWest() && !previous.contains(pos.goWest())) {
        pos = pos.goWest();
      } else if (pos.canGoNorth() && !previous.contains(pos.goNorth())) {
        pos = pos.goNorth();
      }
      distance++;
      if (distance > 2) {
        // remove start pos from the memory so we can go to the start at the end
        previous.remove(startPosition);
      }
    } while (!pos.atStart());
    int halfOfTheSteps = distance / 2;
    return halfOfTheSteps;
  }

  private int solvePart2() {
    return -1;
  }

}
