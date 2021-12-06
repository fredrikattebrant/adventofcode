package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent05 extends AbstractAdvent {

  public Advent05(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent05 advent = new Advent05(false);
    print("Day 05, part 1: " + advent.solvePart1());
    print("Day 05, part 2: " + advent.solvePart2());
  }

  private int solvePart1() {
    return solve(false);
  }

  private int solvePart2() {
    return solve(true);
  }

  class Diagram {
    //
    // matrix[y][x]
    //
    // Outer array represents rows (y)
    // Each row, holds an array representing the columns (x)
    //
    private int[][] matrix;
    private int size;

    Diagram(int size) {
      this.size = size;
      matrix = new int[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          matrix[i][j] = 0;
        }
      }
    }

    int getStep(int from, int to) {
      return to > from ? 1 : -1;
    }

    void add(Line line) {
      if (line.hasSameX()) {
        int x = line.from.x;
        int from = line.from.y;
        int to = line.to.y;
        int step = getStep(from, to);
        for (int y = from; y != to + step; y += step) {
          int val = matrix[y][x];
          matrix[y][x] = val + 1;
        }
      } else if (line.hasSameY()) {
        int y = line.from.y;
        int from = line.from.x;
        int to = line.to.x;
        int step = getStep(from, to);
        for (int x = from; x != to + step; x += step) {
          int val = matrix[y][x];
          matrix[y][x] = val + 1;
        }
      } else {
        int fromX = line.from.x;
        int toX = line.to.x;
        int fromY = line.from.y;
        int toY = line.to.y;
        int stepX = getStep(fromX, toX);
        int stepY = getStep(fromY, toY);
        for (int x = fromX, y = fromY; //
            (x != (toX + stepX)) && (y != (toY + stepY)); //
            x += stepX, y += stepY) {
          int val = matrix[y][x];
          matrix[y][x] = val + 1;
        }
      }
    }

    int getOverlaps() {
      int overlaps = 0;
      for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
          int val = matrix[y][x];
          if (val > 1) {
            overlaps++;
          }
        }
      }
      return overlaps;
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
          int val = matrix[y][x];
          text.append(val == 0 ? '.' : val + "");
        }
        text.append("\n");
      }
      return text.toString();
    }

  }

  record Point(int x, int y) {
    public String toString() {
      return x + "," + y;
    }
  }

  record Line(Point from, Point to) {
    public boolean hasSameX() {
      return from.x == to.x;
    }

    public boolean hasSameY() {
      return from.y == to.y;
    }

    public String toString() {
      return from + " -> " + to;
    }
  }

  private int solve(boolean considerAll) {
    List<String> inputs = readData();

    // Parse input, create pairs of from/to
    // and determine the size of the map
    // also only save valid entries (same row or column)
    int maxX = 0;
    int maxY = 0;
    List<Line> lineData = new ArrayList<>();


    for (String input : inputs) {
      String[] split = input.split(" -> "); // E.g.: 0,9 -> 5,9
      String from = split[0];
      String to = split[1];
      String[] xy0 = from.split(",");
      String[] xy1 = to.split(",");
      int x0 = Integer.parseInt(xy0[0]);
      int y0 = Integer.parseInt(xy0[1]);
      int x1 = Integer.parseInt(xy1[0]);
      int y1 = Integer.parseInt(xy1[1]);
      if (x0 > maxX) {
        maxX = x0;
      }
      if (x1 > maxX) {
        maxX = x1;
      }
      if (y0 > maxY) {
        maxY = y0;
      }
      if (y1 > maxY) {
        maxY = y1;
      }
      if (considerAll || x0 == x1 || y0 == y1) {
        Line mapLine = new Line(new Point(x0, y0), new Point(x1, y1));
        lineData.add(mapLine);
      }
    }

    // Print the map
    log("Input size: " + inputs.size());
    log("Map size:   " + maxX + " x " + maxY);
    log("Line count: " + lineData.size());
    Diagram diagram = new Diagram(maxY + 1);
    for (Line line : lineData) {
      diagram.add(line);
    }
    log(diagram.toString());

    return diagram.getOverlaps();
  }


  private List<String> readData() {
    return readData3("Advent05.txt").stream()
        // return readData3("Advent05test.txt").stream()
        .toList();
  }

}
