package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.attebrant.common.AbstractAdvent;

public class Advent03 extends AbstractAdvent {

  public Advent03(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent03 advent = new Advent03(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts

    if (!isPart2) {
      return solvePart1(input);
    }

    return solvePart2(input);

  }

  record Pos(int row, int col) {
  }

  private int solvePart1(List<String> input) {
    List<Integer> connectedNumbers = new ArrayList<>();
    for (int row = 0; row < input.size(); row++) {
      String line = input.get(row);
      int number = 0;
      boolean isConnected = false;
      for (int col = 0; col < line.length(); col++) {
        char d = line.charAt(col);
        if (isDigit(d)) {
          number = number * 10 + (d - '0');
          if (!isConnected && isAdjacent(input, row, col, false) != null) {
            isConnected = true;
          }
          // last char in line?
          if (col + 1 == line.length()) {
            if (isConnected) {
              // save number
              connectedNumbers.add(number);
            }
          }
        } else {
          if (isConnected) {
            // save number
            connectedNumbers.add(number);
          } // reset and scan for next number
          number = 0;
          isConnected = false;
        }
      }
    }
    return connectedNumbers.stream().mapToInt(Integer::intValue).sum();
  }

  private int solvePart2(List<String> input) {
    Map<Pos, List<Integer>> connectedGears = new HashMap<>();

    for (int row = 0; row < input.size(); row++) {
      String line = input.get(row);
      int number = 0;
      Pos posForConnectedGear = null;
      for (int col = 0; col < line.length(); col++) {
        char d = line.charAt(col);
        if (isDigit(d)) {
          number = number * 10 + (d - '0');
          if (posForConnectedGear == null) {
            Pos pos = isAdjacent(input, row, col, true);
            if (pos != null) {
              posForConnectedGear = pos;
            }
          }
          // last char in line?
          if (col + 1 == line.length()) {
            if (posForConnectedGear != null) {
              // save number and the gear pos
              List<Integer> numbers = connectedGears.get(posForConnectedGear);
              if (numbers == null) {
                numbers = new ArrayList<>();
              }
              numbers.add(number);
              connectedGears.put(posForConnectedGear, numbers);
            }
          }
        } else {
          if (posForConnectedGear != null) {
            // save number and the gear pos
            List<Integer> numbers = connectedGears.get(posForConnectedGear);
            if (numbers == null) {
              numbers = new ArrayList<>();
            }
            numbers.add(number);
            connectedGears.put(posForConnectedGear, numbers);
          } // reset and scan for next number
          number = 0;
          posForConnectedGear = null;
        }
      }
    }
    int gearRatio = 0;
    for (Entry<Pos, List<Integer>> gearInfo : connectedGears.entrySet()) {
      if (gearInfo.getValue().size() == 2) {
        gearRatio += gearInfo.getValue().get(0) * gearInfo.getValue().get(1);
      }
    }
    return gearRatio;
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private boolean isSymbol(char c, boolean checkGear) {
    if (checkGear) {
      return c == '*';
    }
    return !isDigit(c) && c != '.';
  }

  /**
   * Returns the {@link Pos} of a connected symbol or <code>null</code> if not connected.
   */
  private Pos isAdjacent(List<String> input, int row, int col, boolean checkGear) {
    String line = input.get(row);
    // check line above
    if (row > 0) {
      String lineAbove = input.get(row - 1);
      // check one before (if not first col)
      if (col > 0) {
        char before = lineAbove.charAt(col - 1);
        if (isSymbol(before, checkGear)) {
          return new Pos(row - 1, col - 1);
        }
      }
      // check right above
      char above = lineAbove.charAt(col);
      if (isSymbol(above, checkGear)) {
        return new Pos(row - 1, col);
      }
      // check one after
      if (col + 1 < line.length()) {
        char after = lineAbove.charAt(col + 1);
        if (isSymbol(after, checkGear)) {
          return new Pos(row - 1, col + 1);
        }
      }
    }

    // check previous column
    if (col > 0 && isSymbol(line.charAt(col - 1), checkGear)) {
      return new Pos(row, col - 1);

    }
    // check next column
    if (col + 1 < line.length()) {
      char after = line.charAt(col + 1);
      if (isSymbol(after, checkGear)) {
        return new Pos(row, col + 1);
      }
    }
    // check line below
    if (row + 1 < input.size()) {
      String lineBelow = input.get(row + 1);
      // check one before (if not first col)
      if (col > 0) {
        char before = lineBelow.charAt(col - 1);
        if (isSymbol(before, checkGear)) {
          return new Pos(row + 1, col - 1);
        }
      }
      // check right below
      char below = lineBelow.charAt(col);
      if (isSymbol(below, checkGear)) {
        return new Pos(row + 1, col);
      }
      // check one after
      if (col + 1 < line.length()) {
        char after = lineBelow.charAt(col + 1);
        if (isSymbol(after, checkGear)) {
          return new Pos(row + 1, col + 1);
        }
      }
      return null;
    }
    return null;
  }

}
