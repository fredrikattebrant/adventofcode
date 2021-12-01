package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.attebrant.common.AbstractAdvent;

public class Advent0602 extends AbstractAdvent {

  public Advent0602() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0602 advent = new Advent0602();
    advent.solve();
  }

  private void solve() {
    int limit = 10000; // test: 32;
    List<String> rawData = getTestdata(false); // test: true
    List<Coordinate> coordinates = new ArrayList<>();
    Map<String, Coordinate> coordinatesMap = new HashMap<>();
    List<List<String>> matrix = new ArrayList<>();

    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;

    char id = 'a';
    String fullId = "a" + id;
    for (String data : rawData) {
      Coordinate coord = new Coordinate(data, fullId);
      // System.out.println("ID: " + fullId);
      id++;
      char first = fullId.charAt(0);
      if (id > 'z') {
        first++;
        id = 'a';
      }
      fullId = first + "" + id;
      coordinates.add(coord);
      coordinatesMap.put(coord.id, coord);
      // TODO need a check for being closest to the edge... Or later/below?
      if (coord.x < minX) {
        minX = coord.x;
      }
      if (coord.y < minY) {
        minY = coord.y;
      }
      if (coord.x > maxX) {
        maxX = coord.x;
      }
      if (coord.y > maxY) {
        maxY = coord.y;
      }
    }
    // System.out.println(coordinates);
    // System.out.println("(" + minX + "," + minY + ")");
    // System.out.println("(" + maxX + "," + maxY + ")");
    for (int y = 0; y <= maxY + 1; y++) {
      List<String> row = new ArrayList<>();
      for (int x = 0; x <= maxX + 1; x++) {
        row.add(".");
      }
      matrix.add(row);
    }
    for (Coordinate coord : coordinates) {
      matrix.get(coord.y).set(coord.x, coord.id.toUpperCase());
    }
    // System.out.println("Start:");
    // printMatrix(matrix);

    // calc distance for each cell in the matrix to each of the coordinates
    // mark with the closest
    Map<Coordinate, Integer> areaCount = new HashMap<>();
    int sizeOfSafeRegion = 0;
    for (int y = 0; y <= maxY; y++) {
      for (int x = 0; x <= maxX + 1; x++) {
        Coordinate current = new Coordinate(x + ", " + y, "-");
        int sumDistance = 0;
        for (Coordinate coord : coordinates) {
          int distance = current.distanceTo(coord);
          sumDistance += distance;
        }
        if (sumDistance < limit) {
          // System.out.println(current + " => " + sumDistance);
          // if (matrix.get(y).get(x).equals(".")) {
            matrix.get(y).set(x, "#");
          sizeOfSafeRegion++;
          // }
        }
      }
    }

    System.out.println("After distance calc:");
    printMatrix(matrix);

    System.out.println("Size of safe region: " + sizeOfSafeRegion);
  }

  private void printMatrix(List<List<String>> matrix) {
    int rowIx = 0;
    for (List<String> row : matrix) {
      System.out.printf("%03d ", rowIx);
      for (String mark : row) {
        System.out.printf("%2s", mark);
      }
      System.out.println();
      rowIx++;
    }
  }

  /**
   * x marks column,<br>
   * y marks row
   * 
   * <pre>
   * \x -->
   * y\
   * |
   * v
   * </pre>
   * 
   */
  private class Coordinate {
    private int x = 0;
    private int y = 0;
    private String id;

    public Coordinate(String data, String id) {
      String[] split = data.split(", ");
      x = Integer.parseInt(split[0]);
      y = Integer.parseInt(split[1]);
      this.id = id;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public int distanceTo(Coordinate coord) {
      return Math.abs(x - coord.x) + Math.abs(y - coord.y);
    }

    @Override
    public String toString() {
      return id + " => (" + x + "," + y + ")";
    }
  }

  private List<String> getTestdata(boolean test) {
    if (!test) {
      return readData3("Advent0601.txt");
    }
    List<String> data = new ArrayList<>();
    data.add("1, 1");
    data.add("1, 6");
    data.add("8, 3");
    data.add("3, 4");
    data.add("5, 5");
    data.add("8, 9");
    // data.add("3, 2"); // LEAK out...
    return data;
  }
}
