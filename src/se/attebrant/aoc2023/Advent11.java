package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.attebrant.common.AbstractAdvent;

public class Advent11 extends AbstractAdvent {

  public Advent11(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent11 advent = new Advent11(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  record Pos(int r, int c) {
    int distance(Pos pos) {
      return Math.abs(r - pos.r) + Math.abs(c - pos.c);
    }
  }

  private Map<Integer, Pos> galaxyMap = new HashMap<>();
  private int nGalaxys = 0;

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    List<String> image = expand(input);
    for (int r = 0; r < image.size(); r++) {
      String row = image.get(r);
      for (int c = 0; c < row.length(); c++) {
        if (row.charAt(c) == '#') {
          nGalaxys++;
          galaxyMap.put(nGalaxys, new Pos(r, c));
        }
      }
    }
    
    return isPart2 ? solvePart2(image) : solvePart1(image);
  }

  private List<String> expand(List<String> input) {
    List<String> image = new ArrayList<>();
    String emptyLine = input.get(0).replace("#", ".");
    for (String line : input) {
      image.add(line);
      if (!line.contains("#")) {
        image.add(emptyLine);
      }
    }
    List<Integer> columnsToAdd = new ArrayList<>();
    for (int c = 0; c < emptyLine.length(); c++) {
      // traverse down break if a # is found else insert a vertical column
      boolean columnHasGalaxy = false;
      for (int r = 0; r < input.size(); r++) {
        if (input.get(r).charAt(c) == '#') {
          columnHasGalaxy = true;
          break;
        }
      }
      if (columnHasGalaxy) {
        continue;
      }
      columnsToAdd.add(c);
    }
    List<String> image2;
    for (int c : columnsToAdd.reversed()) {
      image2 = new ArrayList<>();
      for (String row : image) {
        String newRow = row.substring(0, c) + "." + row.substring(c);
        image2.add(newRow);
      }
      image = new ArrayList<>(image2);
    }
    return image;
  }

  private int solvePart1(List<String> image) {
    int total = 0;
    for (int i = 1; i <= nGalaxys; i++) {
      for (int j = i + 1; j <= nGalaxys; j++) {
        total += galaxyMap.get(i).distance(galaxyMap.get(j));
      }
    }

    return total;
  }

  private int solvePart2(List<String> image) {
    return -1;
  }

}
