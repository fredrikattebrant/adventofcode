package se.attebrant.aoc2018;

import java.util.List;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0101 extends AbstractAdvent {

  public static void main(String[] args) {
    Advent0101 advent = new Advent0101();
    System.out.println("Resulting frequency: " + advent.solve());
  }

  private int solve() {
    int frequency = 0;

    List<Integer> changes = getChanges();
    for (Integer change : changes) {
      frequency += change;
    }

    return frequency;
  }

  private List<Integer> getChanges() {
    return readFrequencyData();
    // return Arrays.asList(1, 1, -2);
    // return Arrays.asList(-1, -2, -3);
  }

  private List<Integer> readFrequencyData() {
    baseDir =
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2018/";
    return readData3("Advent0101.txt").stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

}
