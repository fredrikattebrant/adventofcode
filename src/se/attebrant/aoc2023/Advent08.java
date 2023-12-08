package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.attebrant.common.AbstractAdvent;

public class Advent08 extends AbstractAdvent {

  public Advent08(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent08 advent = new Advent08(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  private long solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, isPart2, isTest); // same for both parts
    parseRules(input);
    return isPart2 ? solvePart2() : solvePart1();
  }

  private Map<String, List<String>> map;
  private String rules;

  private void parseRules(List<String> input) {
    map = new HashMap<>();
    boolean isFirst = true;
    for (String line : input) {
      if (isFirst) {
        isFirst = false;
        rules = line;
        continue;
      } else if (line.isBlank()) {
        continue;
      }
      String[] split = line.split(" = ");
      map.put(split[0], getValues(split[1]));
    }
  }

  private List<String> getValues(String rightSide) {
    String[] split = rightSide.replace("(", "").replace(")", "").split(", ");
    return List.of(split[0], split[1]);
  }

  private String getNextElement(String element, int turnToIx) {
    return rules.charAt(turnToIx) == 'L' ? map.get(element).get(0) : map.get(element).get(1);
  }

  private int solvePart1() {
    int steps = 0;

    int turnToIx = 0;
    String element = "AAA";

    while (true) {
      steps++;
      element = getNextElement(element, turnToIx);
      turnToIx = (turnToIx += 1) % rules.length();
      if (element.equals("ZZZ")) {
        break;
      }
    }

    return steps;
  }

  private long solvePart2() {
    int threads = map.keySet().stream()
        .filter(k -> k.endsWith("A"))
        .toList()
        .size();

    int turnToIx = 0;
    List<String> elements = new ArrayList<>(map.keySet().stream()
        .filter(k -> k.endsWith("A"))
        .toList());

    long[] allSteps = new long[threads];
    for (int i = 0; i < threads; i++) {
      // get the steps for each 'thread'

      int steps = 0;
      while (true) {
        steps++;
        String element = getNextElement(elements.get(i), turnToIx);
        elements.set(i, element);
        turnToIx = (turnToIx += 1) % rules.length();
        if (element.endsWith("Z")) {
          allSteps[i] = steps;
          break;
        }
      }
    }
    
    return lcm(allSteps);

  }

  private static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  public static long gcd(long[] numbers) {
    return Arrays.stream(numbers).reduce(0, (x, y) -> gcd(x, y));
  }

  public static long lcm(long[] numbers) {
    return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
  }

}
