package se.attebrant.aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent0301 extends AbstractAdvent {

  public Advent0301() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0301 advent = new Advent0301();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  private int solve() {
    List<String> input = readData();

    int totalScore = 0;
    for (String line : input) {
      int mid = line.length() / 2;
      String left = line.substring(0, mid);
      String right = line.substring(mid);
      Set<String> sortedLeft = toSet(left);
      Set<String> sortedRight = toSet(right);
      sortedLeft.retainAll(sortedRight);
      for (int i = 0; i < sortedLeft.size(); i++) {
        String item = sortedLeft.iterator().next();
        int priority = getPriority(item);
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
