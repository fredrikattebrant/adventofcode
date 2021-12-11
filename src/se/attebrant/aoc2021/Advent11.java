package se.attebrant.aoc2021;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent11 extends AbstractAdvent {

  public Advent11(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent11 advent = new Advent11(false);
    boolean useTestInput = false;
    print("Day 11, part 1: " + advent.solvePart1(useTestInput));
    print("Day 11, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent11.txt");
  private List<String> testInput = readData3("Advent11test.txt");

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  class Grid {

    int[][] grid;
    int rows;
    int cols;
    boolean[][] flashed;
    int flashCount = 0;

    public Grid(List<String> input) {
      rows = input.size();
      cols = input.get(0).length();
      grid = new int[rows][cols];
      flashed = new boolean[rows][cols];
      for (int r = 0; r < rows; r++) {
        String row = input.get(r).trim();
        if (row.isBlank()) {
          continue;
        }
        for (int c = 0; c < cols; c++) {
          grid[r][c] = Integer.parseInt(row.charAt(c) + "");
          flashed[r][c] = false;
        }
      }
    }

    public boolean step(int step, boolean resetEachStep) {
      // Increase energy level in all cells by 1
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          grid[r][c] += 1;
        }
      }
      // Flash if energy > 9
      while (flash()) {
        log("Flashed new ");
        log(this);
      }

      // Reset flashed
      boolean allFlashed = flashCount == rows * cols;
      if (resetEachStep) {
        flashCount = 0;
      }
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          if (flashed[r][c]) {
            flashed[r][c] = false;
            grid[r][c] = 0;
          }
        }
      }
      log("Step " + step);
      return allFlashed;
    }

    public boolean flash() {
      boolean flashedNew = false;
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          if (!flashed[r][c]) {
            int energyLevel = grid[r][c];
            if (energyLevel > 9) {
              // Flash, if not already flashed
              flashed[r][c] = true;
              flashCount++;
              flashedNew = true;
              increaseAdjacent(r, c);
            }
          }
        }
      }
      return flashedNew;
    }

    public void increaseAdjacent(int row, int col) {
      for (int r = row - 1; r <= row + 1; r++) {
        for (int c = col - 1; c <= col + 1; c++) {
          if (r >= 0 && r < rows && c >= 0 && c < cols) {
            grid[r][c] += 1;
          }
        }
      }
    }

    public boolean hasFlashed() {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          if (flashed[r][c]) {
            return true;
          }
        }
      }
      return false;
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          int energyLevel = grid[r][c];
          String eLvl = switch (energyLevel) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> energyLevel + "";
            case 10 -> "a";
            case 11 -> "b";
            case 12 -> "c";
            case 13 -> "d";
            case 14 -> "e";
            case 15 -> "f";
            default -> energyLevel + "";
          };
          text.append(eLvl);
        }
        text.append("\n");
      }
      text.append("\n");
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          text.append(flashed[r][c] ? "F" : ".");
        }
        text.append("\n");
      }
      return text.toString();
    }

    public int getFlashCount() {
      return flashCount;
    }
  }

  private int solve(boolean useTestInput, boolean isPart1) {
    var input = useTestInput ? testInput : puzzleInput;
    Grid grid = new Grid(input);
    return isPart1 ? solve1(grid) : solve2(grid);
  }

  private int solve1(Grid grid) {
    log(grid);
    for (int step = 1; step <= 100; step++) {
      grid.step(step, false);
    }
    return grid.getFlashCount();
  }

  private int solve2(Grid grid) {
    log(grid);
    for (int step = 1; step <= 20000; step++) {
      if (grid.step(step, true)) {
        // All flashed
        return step;
      }
    }
    return grid.getFlashCount();
  }

}
