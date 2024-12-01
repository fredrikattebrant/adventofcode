package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import se.attebrant.common.AbstractAdvent;

public class Advent13 extends AbstractAdvent {

  public Advent13(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent13 advent = new Advent13(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    return isPart2 ? solvePart2(input) : solvePart1(input);
  }

  private int solvePart1(List<String> input) {
    int total = 0;

    List<String> pattern = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      // Read lines until empty line, then process the pattern and repeat for next pattern
      String line = input.get(i);
      if (!line.isBlank()) {
        pattern.add(line);
      } else {
        total += getMirroringRowScore(pattern);
        total += getMirroringColumnScore(pattern);
        pattern = new ArrayList<>();
        log("Total: " + total);
        log("");
      }
    }
    if (!pattern.isEmpty()) {
      total += getMirroringRowScore(pattern);
      total += getMirroringColumnScore(pattern);
    }
    return total;
  }

  private int getMirroringRowScore(List<String> pattern) {
    int mirroredRow = findMirroringLine(pattern);
    if (mirroredRow > 0) {
      System.out.println("Mirrored at row: " + mirroredRow);
      return 100 * mirroredRow;
    }
    return 0;
  }

  private int getMirroringColumnScore(List<String> pattern) {
    List<String> columnLines = new ArrayList<>();
    for (int c = 0; c < pattern.get(0).length(); c++) {
      final int cIx = c;
      String column = pattern.stream()
          .map(s -> s.charAt(cIx))
          .map(Object::toString)
          .collect(Collectors.joining());
      columnLines.add(column);
    }

    int mirroredColumn = findMirroringLine(columnLines);
    if (mirroredColumn > 0) {
      System.out.println("Mirrored at column: " + mirroredColumn);
      return mirroredColumn;
    }
    return 0;
  }

  private int findMirroringLine(List<String> lines) {
    Set<String> patterns = new HashSet<>();
    int count = 0;
    int firstStop = 0;
    for (String line : lines) {
      patterns.add(line);
      count++;
      if (count > patterns.size() && firstStop == 0) {
        firstStop = patterns.size();
      }
      logf("%s %d %d", line, count, patterns.size());
    }
    if (patterns.size() > firstStop) {
      System.out.println("NOT mirrored");
      return 0;
    }
    System.out.println();
    return firstStop;
  }

  private int solvePart2(List<String> input) {
    return -1;
  }

}
