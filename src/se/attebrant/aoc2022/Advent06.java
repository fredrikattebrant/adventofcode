package se.attebrant.aoc2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent06 extends AbstractAdvent {

  public Advent06() {
    super("2022");
  }

  public static void main(String[] args) {
    var advent = new Advent06();
    var dayPart = getDayPart(advent);
    var mode = IS_LIVE;
    print("Result " + dayPart + " part 1: " + advent.solvePart(mode, 4));
    print("Result " + dayPart + " part 2: " + advent.solvePart(mode, 14));
  }

  private int solvePart(boolean isTest, int distinctCount) {
    if (isTest) {
      for (int j = 1; j < 6; j++) {
        String input = getTestData(j);
        print("Test result " + solve(input, distinctCount));
      }
      return -2;
    } else {
      String input = readData();
      return solve(input, distinctCount);
    }
  }

  private int solve(String input, int distinctCount) {
    List<Character> queue = new ArrayList<>();

    for (int i = 0; i < input.length(); i++) {
      int marker = i + 1;
      queue.add(input.charAt(i));

      if (queue.size() == distinctCount) {
        Set<Character> set = new HashSet<>();
        set.addAll(queue);
        if (set.size() == distinctCount) {
          return marker;
        }
        queue.remove(0);
      }
    }
    return -1;
  }

  private String getTestData(int i) {
    switch (i) {
      case 1:
        return "mjqjpqmgbljsphdztnvjfqwrcgsmlb";
      case 2:
        return "bvwbjplbgvbhsrlpgdmjqwftvncz";
      case 3:
        return "nppdvjthqldpwncqszvftbrmjlhg";
      case 4:
        return "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg";
      case 5:
        return "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw";
      default:
        throw new IllegalArgumentException("Unexpected value: " + i);
    }
  }

  private String readData() {
    return readData3("Advent06.txt").stream().findFirst().get();
  }

}
