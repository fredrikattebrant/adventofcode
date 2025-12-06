package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent05 extends AbstractAdvent {

  public Advent05(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent05 advent = new Advent05(isTest);
    advent.debug = false;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve1(day, false, isTest));
    // print("Result day " + day + ", part2: " + advent.solve2(day, true, isTest));
  }

  class Range {

    long start;
    long end;

    Range(String range) {
      String[] split = range.split("-");
      start = Long.parseLong(split[0]);
      end = Long.parseLong(split[1]);
    }

    boolean isInRange(long value) {
      return value >= start && value <= end;
    }
  }

  private long solve1(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> database = readData(day, false, isTest);

    List<Range> ranges = new ArrayList<>();

    long numberOfFreshIngredients = 0;
    boolean parseRanges = true;
    for (String entry : database) {
      if (parseRanges) {
        if (entry.length() > 0) {
          // Get the fresh ranges (read until blank line)
          Range range = new Range(entry);
          ranges.add(range);
        } else {
          parseRanges = false;
        }
      } else {
        // Check if ingredient is in any of the ranges -> fresh
        Long ingredient = Long.parseLong(entry);
        for (Range range : ranges) {
          if (range.isInRange(ingredient)) {
            numberOfFreshIngredients++;
            break;
          }
        }
      }
    }



    log("");
    return numberOfFreshIngredients;
  }

}
