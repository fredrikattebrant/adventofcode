package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent01 extends AbstractAdvent {

  public Advent01(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent01 advent = new Advent01(isTest);
    advent.debug = true;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, false, isTest));
    // print("Result day " + day + ", part2: " + advent.solve(day, true, false));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    log("=====");
    log("Part" + (isPart2 ? 2 : 1));
    log("=====");
    List<String> input = readData(day, false, isTest);

    int zeros = 0;
    int pos = 50;
    for (String line : input) {
      boolean isLeft = line.charAt(0) == 'L';
      int num = Integer.parseInt(line.substring(1));
      int steps = (isLeft ? -1 : 1) * num;

      pos = turn(pos, steps);
      if (pos == 0) {
        zeros += 1;
      }
    }
    
    return zeros;
  }
  
  // TODO: Fix multiple * 100
  private int turn(int from, int steps) {
    int position = (from + steps) % 100;
    if (position < 0) {
      return position + 100;
    }
    if (position > 99) {
      return position - 100;
    }
    return position;
  }

}
