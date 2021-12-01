package se.attebrant.aoc2021;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0101 extends AbstractAdvent {

  public Advent0101() {
    super("2021");
  }

  public static void main(String[] args) {
    Advent0101 advent = new Advent0101();
    System.out.println("Result 0101: " + advent.solve());
  }

  private int solve() {
    List<Integer> input = readData();
    int increments = 0;
    int previous = input.get(0);
    for (int i = 1; i < input.size(); i++) {
      int current = input.get(i);
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
