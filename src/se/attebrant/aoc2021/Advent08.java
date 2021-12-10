package se.attebrant.aoc2021;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent08 extends AbstractAdvent {

  public Advent08(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent08 advent = new Advent08(true);
    boolean useTestInput = false;
    print("Day 08, part 1: " + advent.solvePart1(useTestInput));
    // print("Day 06, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent08.txt");
  private List<String> testInput = readData3("Advent08test.txt");

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solve(boolean useTestInput, boolean isPart2) {
    var input = useTestInput ? testInput : puzzleInput;
    int ones = 0;
    int fours = 0;
    int sevens = 0;
    int eights = 0;
    for (var entry : input) {
      String rightSide = entry.substring(entry.indexOf("|") + 1).trim();
      String[] tokens = rightSide.split("\\s");
      for (int i = 0; i < tokens.length; i++) {
        var tokenLen = tokens[i].length();
        if (tokenLen == 2) {
          ones++;
        } else if (tokenLen == 4) {
          fours++;
        } else if (tokenLen == 3) {
          sevens++;
        } else if (tokenLen == 7) {
          eights++;
        }
      }
    }
    var sum = ones + fours + sevens + eights;
    log("1,4,7,8 appears " + sum + " times");

    return sum;
  }

}
