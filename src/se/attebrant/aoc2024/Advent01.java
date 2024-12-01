package se.attebrant.aoc2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent01 extends AbstractAdvent {

  public Advent01(boolean isTest) {
    super("2024", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = true;
    Advent01 advent = new Advent01(isTest);
    advent.debug = true;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, false, false));
    print("Result day " + day + ", part2: " + advent.solve(day, true, false));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    log("=====");
    log("Part" + (isPart2 ? 2 : 1));
    log("=====");
    List<String> input = readData(day, false, isTest);
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    for (String line : input) {
      String[] tokens = line.split("\s+");
      int loc1 = Integer.parseInt(tokens[0]);
      int loc2 = Integer.parseInt(tokens[1]);
      list1.add(loc1);
      list2.add(loc2);
    }
    
    if (isPart2) { 
      return solvePart2(list1, list2);
    }
    return solvePart1(list1, list2);
  }
  
  private int solvePart1(List<Integer> list1, List<Integer> list2) {
    Collections.sort(list1);
    Collections.sort(list2);
    
    int totalDistance = 0;
    for (int ix = 0; ix < list1.size(); ix++) {
      int delta = Math.abs(list1.get(ix) - list2.get(ix));
      totalDistance += delta;
    }
    
    return totalDistance;
  }

  private int solvePart2(List<Integer> list1, List<Integer> list2) {
    int totalScore = 0;
    for (int loc : list1) {
      int count = Collections.frequency(list2, loc);
      int similarityScore = loc * count;
      totalScore += similarityScore;
    }
    
    return totalScore;
  }
}
