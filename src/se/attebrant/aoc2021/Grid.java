package se.attebrant.aoc2021;

import java.util.List;

class Grid {

  int[][] theGrid;
  int rows;
  int cols;

  public Grid(List<String> input) {
    rows = input.size();
    cols = input.get(0).length();
    theGrid = new int[rows][cols];
    for (int r = 0; r < rows; r++) {
      String row = input.get(r).trim();
      if (row.isBlank()) {
        continue;
      }
      for (int c = 0; c < cols; c++) {
        theGrid[r][c] = Integer.parseInt(row.charAt(c) + "");
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder text = new StringBuilder();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        text.append(theGrid[r][c]);
      }
      text.append("\n");
    }
    text.append("\n");
    return text.toString();
  }

}