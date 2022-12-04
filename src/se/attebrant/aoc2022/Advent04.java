package se.attebrant.aoc2022;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent04 extends AbstractAdvent {

  public Advent04() {
    super("2022");
  }

  public static void main(String[] args) {
    var advent = new Advent04();
    var dayPart = getDayPart(advent);
    print("Result " + dayPart + " part 1: " + advent.solvePart1(IS_LIVE));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(IS_LIVE));
  }

  private int solvePart1(boolean isTest) {
    var input = readData(isTest);

    var fullyContained = 0;
    for (var line : input) {
      var split = line.split(",");
      var sections1 = toDigits(split[0]);
      var sections2 = toDigits(split[1]);

      var isFullyContained = setIsFullyContainedBy(sections1, sections2)
          || setIsFullyContainedBy(sections2, sections1);
      if (isFullyContained) {
        fullyContained++;
      }
    }

    return fullyContained;
  }

  private int solvePart2(boolean isTest) {
    var input = readData(isTest);


    var partlyContained = 0;
    for (var line : input) {
      var split = line.split(",");
      var sections1 = toDigits(split[0]);
      var sections2 = toDigits(split[1]);

      var overlaps = setOverlaps(sections1, sections2);
      if (overlaps) {
        partlyContained++;
      }
    }

    return partlyContained;
  }

  private boolean setOverlaps(Set<Integer> set1, Set<Integer> set2) {
    set1.retainAll(set2);
    return !set1.isEmpty();
  }

  private boolean setIsFullyContainedBy(Set<Integer> set, Set<Integer> container) {
    var setCopy = new HashSet<>(set);
    setCopy.retainAll(container);
    return setCopy.containsAll(set);
  }

  private Set<Integer> toDigits(String range) {
    var rangeSplit = range.split("-");
    var start = Integer.valueOf(rangeSplit[0]);
    var end = Integer.valueOf(rangeSplit[1]);
    Set<Integer> digits = new HashSet<>();
    for (int i = start; i <= end; i++) {
      digits.add(i);
    }
    return digits;
  }

  private List<String> readData(boolean isTest) {
    if (isTest) {
      var input = """
              2-4,6-8
              2-3,4-5
              5-7,7-9
              2-8,3-7
              6-6,4-6
              2-6,4-8""";
      var noSpace = input.replace(" ", "");
      return Arrays.asList(noSpace.split("\n"));
    }
    return readData3("Advent04.txt").stream().toList();
  }

}
