package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.attebrant.common.AbstractAdvent;

public class Advent0601 extends AbstractAdvent {

  public Advent0601() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0601 advent = new Advent0601();
    advent.solve();
  }

  private void solve() {
    List<String> rawData = getTestdata();
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
      id++;
      char first = fullId.charAt(0);
      if (id > 'z') {
        first++;
        id = 'a';
      }
      fullId = first + "" + id;
      coordinates.add(coord);
      coordinatesMap.put(coord.id, coord);
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
    System.out.println(coordinates);
    System.out.println("(" + minX + "," + minY + ")");
    System.out.println("(" + maxX + "," + maxY + ")");
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
    System.out.println("Start:");
    // printMatrix(matrix);

    // calc distance for each cell in the matrix to each of the coordinates
    // mark with the closest
    Map<Coordinate, Integer> areaCount = new HashMap<>();
    for (int y = 0; y <= maxY; y++) {
      for (int x = 0; x <= maxX + 1; x++) {
        if (matrix.get(y).get(x).equals(".")) {
          Coordinate current = new Coordinate(x + ", " + y, "-");
          int minDistance = Integer.MAX_VALUE;
          String closestId = "?";
          for (Coordinate coord : coordinates) {
            int distance = current.distanceTo(coord);
            if (distance < minDistance) {
              minDistance = distance;
              closestId = coord.id;
              Integer entry = areaCount.get(coord);
              if (entry == null) {
                entry = 0;
              } else {
                entry++;
              }
              areaCount.put(coord, entry);
            } else if (distance == minDistance) {
              // same, mark with a '.'
              closestId = ".";
            }
          }
          matrix.get(y).set(x, closestId);
        }
      }
    }

    // System.out.println("After distance calc:");
    // printMatrix(matrix);

    Map<String, Integer> coordCount = new HashMap<>();
    for (Coordinate coord : coordinates) {
      coordCount.put(coord.id, 0);
    }

    for (int y = 0; y <= maxY; y++) {
      List<String> row = matrix.get(y);
      for (int x = 0; x <= maxX + 1; x++) {
        String cell = row.get(x).toLowerCase();
        if (cell.equals(".")) {
          continue;
        }
        coordCount.put(cell, coordCount.get(cell) + 1);
      }
    }

    // Exclude coordinates with "infinite" area, i.e. on the edge
    List<Coordinate> exclude = new ArrayList<>();

    // Traverse around the edges - any cell on the edge is to be excluded
    for (int x = 0; x <= maxX; x++) {
      String currentId = matrix.get(0).get(x);
      if (!currentId.equals(".")) {
        exclude.add(coordinatesMap.get(currentId));
      }
    }
    for (int y = 0; y <= maxY; y++) {
      String currentId = matrix.get(y).get(maxX);
      if (!currentId.equals(".")) {
        exclude.add(coordinatesMap.get(currentId));
      }
    }
    for (int x = 0; x <= maxX; x++) {
      String currentId = matrix.get(maxY).get(x);
      if (!currentId.equals(".")) {
        exclude.add(coordinatesMap.get(currentId));
      }
    }
    for (int y = 0; y <= maxY; y++) {
      String currentId = matrix.get(y).get(0);
      if (!currentId.equals(".")) {
        exclude.add(coordinatesMap.get(currentId));
      }
    }
    coordinates.removeAll(exclude);

    Coordinate largest = null;
    int largestCount = 0;
    for (Coordinate coordinate : coordinates) {
      Integer count = coordCount.get(coordinate.id);
      if (largest == null || count > largestCount) {
        largest = coordinate;
        largestCount = count;
      }
    }
    System.out.println(largest.id + " => " + largestCount);
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

  private List<String> getTestdata() {
    boolean test = false;
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
    data.add("3, 2"); // LEAK out...
    return data;
  }
}
