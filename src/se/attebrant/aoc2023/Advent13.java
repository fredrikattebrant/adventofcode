package se.attebrant.aoc2023;

import java.io.IOException;
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
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_TEST));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    return isPart2 ? solvePart2(input) : solvePart1(input);
  }

  private int solvePart1(List<String> input) {
    Set<String> rowPatterns = new HashSet<>();
    int rowCount = 0;
    int rowFirstStop = 0;
    for (String line : input) {
      rowPatterns.add(line);
      rowCount++;
      if (rowCount > rowPatterns.size() && rowFirstStop == 0) {
        rowFirstStop = rowPatterns.size();
      }
      logf("%s %d %d", line, rowCount, rowPatterns.size());
    }
    if (rowPatterns.size() > rowFirstStop) {
      System.out.println("NOT mirrored");
    }
    System.out.println();

    Set<String> colPatterns = new HashSet<>();
    int colCount = 0;
    for (int c = 0; c < input.get(0).length(); c++) {
      final int cIx = c;
      String column = input.stream()
          .map(s -> s.charAt(cIx))
          .map(Object::toString)
          .collect(Collectors.joining());
      colPatterns.add(column);
      colCount++;
      logf("%s %d %d", column, colCount, colPatterns.size());
    }
    System.out.println();
    System.out.println("Rows - count, size: " + rowCount + ", " + rowPatterns.size());
    System.out.println("Colums - count, size:" + colCount + ", " + colPatterns.size());



    int total = rowCount + 100 * colCount;
    return total;
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
    }
    System.out.println();
    return firstStop;
  }

  private int solvePart2(List<String> input) {
    return -1;
  }

}
