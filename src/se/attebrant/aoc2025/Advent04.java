package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent04 extends AbstractAdvent {

  public Advent04(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent04 advent = new Advent04(isTest);
    advent.debug = false;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve1(day, false, isTest));
    print("Result day " + day + ", part2: " + advent.solve2(day, true, isTest));
  }

  private long solve1(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> grid = readData(day, false, isTest);

    int rows = grid.size();
    int cols = grid.get(0).length();

    int numberOfRollsThatCanBeMoved = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if ('@' == grid.get(row).charAt(col)) {
          if (numberOfAdjacentRolls(grid, row, col) <= 4) {
            numberOfRollsThatCanBeMoved++;
          } else {
          }
        } else {
        }
      }
    }
    log("");
    return numberOfRollsThatCanBeMoved;
  }

  private List<String> grid;

  private long solve2(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    grid = readData(day, false, isTest);

    int rows = grid.size();
    int cols = grid.get(0).length();

    int totalNumberOfRollsThatCanBeMoved = 0;

    boolean done = false;
    do {
      int numberOfRollsThatCanBeMoved = removeRollsFromGrid();
      totalNumberOfRollsThatCanBeMoved += numberOfRollsThatCanBeMoved;
      done = numberOfRollsThatCanBeMoved == 0;
    } while (!done);
    return totalNumberOfRollsThatCanBeMoved;
  }

  private int removeRollsFromGrid() {
    List<String> grid2 = new ArrayList<>();
    int numberOfRollsThatCanBeMoved = 0;
    int rows = grid.size();
    int cols = grid.get(0).length();
    for (int row = 0; row < rows; row++) {
      String newRow = "";
      for (int col = 0; col < cols; col++) {
        if ('@' == grid.get(row).charAt(col)) {
          if (numberOfAdjacentRolls(grid, row, col) <= 4) {
            numberOfRollsThatCanBeMoved++;
            newRow += ".";
          } else {
            newRow += grid.get(row).charAt(col);
          }
        } else {
          newRow += grid.get(row).charAt(col);
        }
      }
      grid2.add(newRow);
    }
    grid = grid2;
    return numberOfRollsThatCanBeMoved;
  }


  private Integer numberOfAdjacentRolls(List<String> grid, int row, int col) {
    Integer sum = hasPaperRoll(grid, row, col);
    sum += hasPaperRoll(grid, row - 1, col);
    sum += hasPaperRoll(grid, row + 1, col);
    sum += hasPaperRoll(grid, row, col - 1);
    sum += hasPaperRoll(grid, row, col + 1);
    sum += hasPaperRoll(grid, row - 1, col - 1);
    sum += hasPaperRoll(grid, row + 1, col + 1);
    sum += hasPaperRoll(grid, row + 1, col - 1);
    sum += hasPaperRoll(grid, row - 1, col + 1);
    return sum;
  }

  private Integer hasPaperRoll(List<String> grid, int row, int col) {
    if (row < 0 || row >= grid.size()) {
      // Outside of grid
      return 0;
    }
    if (col < 0 || col >= grid.get(0).length()) {
      // Outside of grid
      return 0;
    }
    return grid.get(row).charAt(col) == '@' ? 1 : 0;
  }


}
