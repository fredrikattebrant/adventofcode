package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent09 extends AbstractAdvent {

  Grid heightMap;

  public Advent09(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent09 advent = new Advent09(true);
    boolean useTestInput = true;
    // print("Day 09, part 1: " + advent.solve(useTestInput, false));
    print("Day 09, part 2: " + advent.solve(useTestInput, true));
  }

  private List<String> puzzleInput = readData3("Advent09.txt");
  private List<String> testInput = readData3("Advent09test.txt");

  record LowPoint(int row, int col, int value) {
    @Override
    public String toString() {
      return "LowPoint(" + row + ", " + col + ") => " + value;
    }
  }

  private long solve(boolean useTestInput, boolean isPart2) {
    var input = useTestInput ? testInput : puzzleInput;
    var rows = input.size();
    var cols = input.get(0).length();

    heightMap = new Grid(input);

    // int[][] heightMap = createHeightMap(input, rows, cols);
    Set<LowPoint> lowPoints = new HashSet<>();
    findLowPoints(heightMap, lowPoints);
    
    if (!isPart2) {
      var sum = 0;
      for (LowPoint lowPoint : lowPoints) {
        sum += heightMap.get(lowPoint.row, lowPoint.col) + 1;
      }
      return sum;
    }

    return findThreeLargestBasins(heightMap, lowPoints);
  }

  private void findLowPoints(Grid grid, Set<LowPoint> lowPoints) {
    for (int r = 0; r < grid.rows; r++) {
      for (int c = 0; c < grid.cols; c++) {
        if (isLowPoint(grid, r, c)) {
          lowPoints.add(new LowPoint(r, c, grid.get(r, c)));
        }
      }
    }
  }

  private long findThreeLargestBasins(Grid heightMap, Set<LowPoint> lowPoints) {
    var basinProduct = 0;
    for (LowPoint lowPoint : lowPoints) {
      findBasin(lowPoint, heightMap);
    }


    // for (int r = 0; r < rows; r++) {
    // for (int c = 0; c < cols; c++) {
    // // if (isLowPoint(heightMap, r, c)) {
    //
    // // }
    // }
    // }
    return 0;
  }

  private void findBasin(LowPoint lowPoint, Grid heightMap) {
    Integer lowPointValue = heightMap.get(lowPoint.row, lowPoint.col);
    log("Find basin " + lowPoint);
    List<Integer> neighbours = getNeighbours(heightMap, lowPoint.row, lowPoint.col);
    for (Integer neighbour : neighbours) {
      if (lowPointValue < neighbour) {
        // ***
        // TODO save if < 9 and scan around the neighbour ***
        // ***
        log(neighbour);
      }
    }
  }

  private int[][] createHeightMap(List<String> input, int rows, int cols) {
    int[][] heightMap = new int[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        heightMap[r][c] = Integer.parseInt(input.get(r).charAt(c) + "");
      }
    }
    return heightMap;
  }

  private boolean isLowPoint(Grid heightMap, int r, int c) {
    var neighbours = getNeighbours(heightMap, r, c);
    var height = heightMap.get(r, c);
    for (var h : neighbours) {
      if (h <= height) {
        return false;
      }
    }
    log("Lowpoint at [" + r + "][" + c + "] => " + heightMap.get(r, c));
    return true;
  }

  private List<Integer> getNeighbours(Grid heightMap, int r, int c) {
    List<Integer> neighbours = new ArrayList<>();
    getNeighbour(heightMap, r - 1, c, neighbours);
    getNeighbour(heightMap, r, c - 1, neighbours);
    getNeighbour(heightMap, r, c + 1, neighbours);
    getNeighbour(heightMap, r + 1, c, neighbours);
    return neighbours;
  }

  private void getNeighbour(Grid heightMap, int r, int c,
      List<Integer> neighbours) {
    if (r >= 0 && r < heightMap.rows && c >= 0 && c < heightMap.cols) {
      neighbours.add(heightMap.get(r, c));
    }
  }

}
