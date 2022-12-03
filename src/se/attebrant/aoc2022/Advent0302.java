package se.attebrant.aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent0302 extends AbstractAdvent {

  public Advent0302() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0302 advent = new Advent0302();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  private int solve() {
    List<String> input = readData();

    int totalScore = 0;
    int j = 0;
    Set<String> common = new HashSet<>();
    for (String line : input) {
      Set<String> lineSet = toSet(line);
      if (common.isEmpty()) {
        common.addAll(lineSet);
      } else {
        common.retainAll(lineSet);
      }
      j++;
      if (j % 3 == 0) {
        String item = common.iterator().next();
        int priority = getPriority(item);
        common = new HashSet<>();
        totalScore += priority;
      }
    }

    return totalScore;
  }

  private Set<String> toSet(String input) {
    Set<String> result = new HashSet<>();
    for (int i = 0; i < input.length(); i++) {
      result.add(String.valueOf(input.charAt(i)));
    }
    return result;
  }

  private int getPriority(String item) {
    char c = item.charAt(0);
    if (c < 'a') {
      return 27 + c - 'A';
    } else {
      return 1 + c - 'a';
    }
  }

  private List<String> readData() {
    return readData3("Advent03.txt").stream().toList();
  }

}
