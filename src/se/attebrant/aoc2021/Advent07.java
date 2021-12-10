package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent07 extends AbstractAdvent {

  public Advent07(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent07 advent = new Advent07(true);
    boolean useTestInput = false;
    print("Day 07, part 1: " + advent.solvePart1(useTestInput));
    print("Day 07, part 2: " + advent.solvePart2(useTestInput));
  }

  private String puzzleInput = readData3("Advent07.txt").get(0);
  private String testInput = "16,1,2,0,4,2,7,1,2,14";

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solve(boolean useTestInput, boolean isPart2) {
    String input = useTestInput ? testInput : puzzleInput;

    List<Integer> crabPositions = Arrays.asList(input.split(",")).stream()
        .map(Integer::parseInt)
        .sorted()
        .toList();

    Map<Integer, Integer> crabMap = new HashMap<>();
    int minPos = Integer.MAX_VALUE;
    int maxPos = 0;
    for (Integer crabPos : crabPositions) {
      Integer count = crabMap.get(crabPos);
      if (count == null) {
        count = 0;
      }
      crabMap.put(crabPos, count + 1);
      if (crabPos > maxPos) {
        maxPos = crabPos;
      }
      if (crabPos < minPos) {
        minPos = crabPos;
      }
    }

    if (!isPart2) {
      return getCostForPart1(crabMap, minPos, maxPos);
    }
    return getCostForPart2(crabMap, minPos, maxPos);
  }

  private int getCostForPart1(Map<Integer, Integer> crabMap, int minPos, int maxPos) {
    int cost = Integer.MAX_VALUE;
    Set<Integer> crabPositions = crabMap.keySet();
    for (int pos = minPos; pos <= maxPos; pos++) {
      int deltaSumAtPos = 0;
      for (int pos2 : crabPositions) {
        int delta = Math.abs(pos - pos2);
        int deltaProd = delta * crabMap.get(pos2);
        deltaSumAtPos += deltaProd;
      }
      if (cost > deltaSumAtPos) {
        cost = deltaSumAtPos;
      }
    }
    return cost;
  }

  private int getCostForPart2(Map<Integer, Integer> crabMap, int minPos, int maxPos) {
    // Calc cost for each delta distance
    List<Integer> part2costs = new ArrayList<>();
    int previousCost = 0;
    for (int i = 0; i <= (maxPos - minPos); i++) {
      int thisCost = i + previousCost;
      part2costs.add(i, thisCost);
      previousCost = thisCost;
    }

    int cost = Integer.MAX_VALUE;
    Set<Integer> crabPositions = crabMap.keySet();
    for (int pos = minPos; pos <= maxPos; pos++) {
      int deltaSumAtPos = 0;
      for (int pos2 : crabPositions) {
        int delta = Math.abs(pos - pos2);
        int costAtPos = part2costs.get(delta);
        int deltaProd = costAtPos * crabMap.get(pos2);
        deltaSumAtPos += deltaProd;
      }
      if (cost > deltaSumAtPos) {
        cost = deltaSumAtPos;
      }
    }
    return cost;
  }

}
