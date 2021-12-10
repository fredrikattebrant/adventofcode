package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent09 extends AbstractAdvent {

  public Advent09(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent09 advent = new Advent09(false);
    boolean useTestInput = false;
    print("Day 09, part 1: " + advent.solvePart1(useTestInput));
    // print("Day 06, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent09.txt");
  private List<String> testInput = readData3("Advent09test.txt");

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solve(boolean useTestInput, boolean isPart2) {
    var input = useTestInput ? testInput : puzzleInput;
    var rows = input.size();
    var cols = input.get(0).length();

    int[][] heightMap = new int[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        heightMap[r][c] = Integer.parseInt(input.get(r).charAt(c) + "");
      }
    }

    var sum = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (isLowPoint(heightMap, r, c)) {
          sum += heightMap[r][c] + 1;
        }
      }
    }

    return sum;
  }

  private boolean isLowPoint(int[][] heightMap, int r, int c) {
    var neighbours = getNeighbours(heightMap, r, c);
    var height = heightMap[r][c];
    for (var h : neighbours) {
      if (h <= height) {
        return false;
      }
    }
    log("Lowpoint at [" + r + "][" + c + "] => " + heightMap[r][c]);
    return true;
  }

  private List<Integer> getNeighbours(int[][] heightMap, int r, int c) {
    List<Integer> neighbours = new ArrayList<>();
    getNeighbour(heightMap, r - 1, c, neighbours);
    getNeighbour(heightMap, r, c - 1, neighbours);
    getNeighbour(heightMap, r, c + 1, neighbours);
    getNeighbour(heightMap, r + 1, c, neighbours);
    return neighbours;
  }

  private void getNeighbour(int[][] heightMap, int r, int c,
      List<Integer> neighbours) {
    if (r >= 0 && r < heightMap.length && c >= 0 && c < heightMap[0].length) {
      neighbours.add(heightMap[r][c]);
    }
  }

}
