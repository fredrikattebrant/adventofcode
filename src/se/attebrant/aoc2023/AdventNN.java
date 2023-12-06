package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class AdventNN extends AbstractAdvent {

  public AdventNN(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    AdventNN advent = new AdventNN(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_TEST));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    return isPart2 ? solvePart2(input) : solvePart1(input);
  }

  private int solvePart1(List<String> input) {
    for (String line : input) {
      log(line);
    }
    int total = 0;
    return total;
  }

  private int solvePart2(List<String> input) {
    return -1;
  }

}
