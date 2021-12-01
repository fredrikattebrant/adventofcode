package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0102 extends AbstractAdvent {

  public Advent0102() {
    super("2021");
  }

  public static void main(String[] args) {
    Advent0102 advent = new Advent0102();
    System.out.println("Result 0102: " + advent.solve());
  }

  private int solve() {
    List<Integer> depthData = readData();
    List<Integer> accumulatedDepths = new ArrayList<>(depthData.size());

    int offset = 0;
    int windowSize = 3;
    for (int i = 0; i < depthData.size(); i++) {
      Integer depth = depthData.get(i);
      accumulatedDepths.add(i, depth);
      int i1 = i - 1;
      int i2 = i - 2;
      if (i1 >= 0) {
        accumulatedDepths.set(i1, accumulatedDepths.get(i1) + depth);
      }
      if (i2 >= 0) {
        accumulatedDepths.set(i2, accumulatedDepths.get(i2) + depth);
      }
      offset = (offset + 1) % windowSize;
    }

    int increments = 0;
    int previous = accumulatedDepths.get(0);
    for (int i = 1; i < depthData.size(); i++) {
      int current = accumulatedDepths.get(i);
      if (current > previous) {
        increments++;
      }
      previous = current;
    }
    return increments;
  }

  private List<Integer> readData() {
    return readData3("Advent01.txt").stream()
        .map(Integer::parseInt)
        .toList();
  }

}
