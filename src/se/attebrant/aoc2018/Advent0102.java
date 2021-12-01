package se.attebrant.aoc2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0102 extends AbstractAdvent {

  public static void main(String[] args) {
    Advent0102 advent = new Advent0102();
    int answer = advent.solve();
    if (answer == Integer.MAX_VALUE) {
      System.err.println("*** Did not find any repeated frequency");
    } else {
      System.out.println("First frequency reached twice: " + answer);
    }
  }

  private int solve() {
    int frequency = 0;
    Set<Integer> frequencies = new HashSet<>();

    List<Integer> changes = getChanges();
    int ix = 0;
    while (true) {
      int change = changes.get(ix);
      frequencies.add(frequency);
      frequency += change;
      // System.out.println(frequency);
      if (frequencies.contains(frequency)) {
        return frequency;
      }
      ix = ++ix % changes.size();
    }
  }

  private List<Integer> getChanges() {
    return readFrequencyData();
    // return Arrays.asList(1, -1);
    // return Arrays.asList(7, 7, -2, -7, -4);
    // return Arrays.asList(-6, +3, +8, +5, -6);
    // return Arrays.asList(1, -2, 3, 1, 1, -2);
  }

  private List<Integer> readFrequencyData() {
    baseDir =
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2018/";
    return readData3("Advent0101.txt").stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

}
