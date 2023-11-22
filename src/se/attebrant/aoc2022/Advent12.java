package se.attebrant.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import se.attebrant.common.AbstractAdvent;

public class Advent12 extends AbstractAdvent {

  public Advent12() {
    super("2022");
    debug = true;
  }

  public static void main(String[] args) {
    var advent = new Advent12();
    var dayPart = getDayPart(advent);
    print("Result " + dayPart + " part 1: " + advent.solvePart1(IS_LIVE));
    // print("Result " + dayPart + " part 2: " + advent.solvePart2(IS_LIVE));
  }

  private HeightMap map;
  private HeightMap markedMap;


  /**
   * <pre>
   * ^ Up   : row - 1
   * v Down : row + 1
   * < Left : col - 1
   * > Right: col + 1
   * </pre>
   */
  private enum Direction {
    UP, DOWN, LEFT, RIGHT, DONE
  }

  int steps = 0;
  int currentRow = 0;
  int currentCol = 0;

  class HeightMap {

    List<List<Character>> map;
    int rows;
    int cols;

    HeightMap(int rows, int cols) {
      this.map = new ArrayList<>();
      this.rows = rows;
      this.cols = cols;
      for (int r = 0; r < rows; r++) {
        List<Character> row = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
          row.add('.');
        }
        map.add(row);
      }
    }

    HeightMap(List<String> input) {
      map = new ArrayList<>();
      int r = 0;
      for (String line : input) {
        List<Character> row = new ArrayList<>();
        for (int c = 0; c < line.length(); c++) {
          char marker = line.charAt(c);
          if (marker == 'S') {
            // Save start
            currentRow = r;
            currentCol = c;
          }
          row.add(marker);
        }
        map.add(row);
        r++;
      }
      rows = map.size();
      cols = map.get(0).size();
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      for (int r = 0; r < rows; r++) {
        List<Character> row = map.get(r);
        for (int c = 0; c < cols; c++) {
          text.append(row.get(c));
        }
        text.append(System.lineSeparator());
      }
      text.append(System.lineSeparator());
      return text.toString();
    }

    public char get(int theRow, int theCol) {
      return map.get(theRow).get(theCol);
    }

    public void set(int theRow, int theCol, char c) {
      map.get(theRow).set(theCol, c);
    }
  }

  private char getMarker(int row, int col) {
    char marker = map.get(row, col);
    if (marker == 'E') {
      return 'z';
    }
    return marker;
  }


  private Map<Direction, Integer> getPossibleDirections(int row, int col) {
    Map<Direction, Integer> stepDirMap = new HashMap<>();
    //    List<Direction> directions = new ArrayList<>();
    char marker = map.get(row, col);

    if (marker == 'S') {
      // Start
      marker = 'a';
    }

    boolean isE = false;
    // Up
    if (row - 1 >= 0 && markedMap.get(row - 1, col) == '.') {
      char up = getMarker(row - 1, col);
      isE = up == 'E';
      int diff = up - marker;
      if (isE || diff >= 0 && diff <= 1) {
        //        directions.add(Direction.UP);
        stepDirMap.put(Direction.UP, diff);
      }
    }
    // Down
    if (row + 1 < map.rows && markedMap.get(row + 1, col) == '.') {
      char down = getMarker(row + 1, col);
      isE = down == 'E';
      int diff = down - marker;
      if (isE || diff >= 0 && diff <= 1) {
        // directions.add(Direction.DOWN);
        stepDirMap.put(Direction.DOWN, diff);
      }
    }
    // Right
    if (col + 1 < map.cols && markedMap.get(row, col + 1) == '.') {
      char right = getMarker(row, col + 1);
      isE = right == 'E';
      int diff = right - marker;
      if (isE || diff >= 0 && diff <= 1) {
        // directions.add(Direction.RIGHT);
        stepDirMap.put(Direction.RIGHT, diff);
      }
    }
    // Left
    if (col - 1 >= 0 && markedMap.get(row, col - 1) == '.') {
      char left = getMarker(row, col - 1);
      isE = left == 'E';
      int diff = left - marker;
      if (isE || diff >= 0 && diff <= 1) {
        // directions.add(Direction.LEFT);
        stepDirMap.put(Direction.LEFT, diff);
      }
    }

    if (isE) {
      // directions.add(Direction.DONE);
      stepDirMap.put(Direction.DONE, 0);
    }
    // return directions;
    return stepDirMap;
  }

  private boolean move() {
    Map<Direction, Integer> possibleDirections = getPossibleDirections(currentRow, currentCol);
    if (possibleDirections.keySet().contains(Direction.DONE)) {
      // Done
      return true;
    }

    if (possibleDirections.isEmpty()) {
      // TODO Backtrack a retry from previous fork
      System.err.println("*** NO DIRECTIONS ***");
    }

    // Try first the direction with highest step
    List<Direction> list = possibleDirections.keySet().stream().sorted().toList();

    Direction first = list.get(0);
    for (Entry<Direction, Integer> entry : possibleDirections.entrySet()) {
      if (entry.getValue() > 0) {
        first = entry.getKey();
        break;
      }
    }

    char marker = '?';
    switch (first) {
      case RIGHT:
        marker = '>';
        markedMap.set(currentRow, currentCol, marker);
        currentCol++;
        break;
      case DOWN:
        marker = 'v';
        markedMap.set(currentRow, currentCol, marker);
        currentRow++;
        break;
      case LEFT:
        marker = '<';
        markedMap.set(currentRow, currentCol, marker);
        currentCol--;
        break;
      case UP:
        marker = '^';
        markedMap.set(currentRow, currentCol, marker);
        currentRow--;
        break;
      default:
        break;
    }

    char newCurrent = map.get(currentRow, currentCol);
    steps++;
    log("(" + currentRow + ", " + currentCol + ") " + marker + " " + steps);
    if (newCurrent == 'E') {
      return true;
    }

    return false;
  }

  private HeightMap loadMap(boolean isTest) {
    HeightMap aMap = new HeightMap(readData4(isTest));
    return aMap;
  }

  private int solvePart1(boolean isTest) {
    map = loadMap(isTest);
    markedMap = new HeightMap(map.rows, map.cols);

    while (!move()) {
      // System.out.println(markedMap);
    }
    return steps;
  }

  private int solvePart2(boolean isTest) {
    List<String> lines = readData4(isTest);

    var steps = 0;
    return steps;
  }

}
