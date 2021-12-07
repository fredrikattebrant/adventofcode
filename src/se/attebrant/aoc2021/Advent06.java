package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent06 extends AbstractAdvent {

  public Advent06(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent06 advent = new Advent06(false);
    boolean useTestInput = false;
    print("Day 06, part 1: " + advent.solvePart1(useTestInput));
    print("Day 06, part 2: " + advent.solvePart2(useTestInput));
  }

  private String puzzleInput =
      "3,5,2,5,4,3,2,2,3,5,2,3,2,2,2,2,3,5,3,5,5,2,2,3,4,2,3,5,5,3,3,5,2,4,5,4,3,5,3,2,5,4,1,1,1,5,1,4,1,4,3,5,2,3,2,2,2,5,2,1,2,2,2,2,3,4,5,2,5,4,1,3,1,5,5,5,3,5,3,1,5,4,2,5,3,3,5,5,5,3,2,2,1,1,3,2,1,2,2,4,3,4,1,3,4,1,2,2,4,1,3,1,4,3,3,1,2,3,1,3,4,1,1,2,5,1,2,1,2,4,1,3,2,1,1,2,4,3,5,1,3,2,1,3,2,3,4,5,5,4,1,3,4,1,2,3,5,2,3,5,2,1,1,5,5,4,4,4,5,3,3,2,5,4,4,1,5,1,5,5,5,2,2,1,2,4,5,1,2,1,4,5,4,2,4,3,2,5,2,2,1,4,3,5,4,2,1,1,5,1,4,5,1,2,5,5,1,4,1,1,4,5,2,5,3,1,4,5,2,1,3,1,3,3,5,5,1,4,1,3,2,2,3,5,4,3,2,5,1,1,1,2,2,5,3,4,2,1,3,2,5,3,2,2,3,5,2,1,4,5,4,4,5,5,3,3,5,4,5,5,4,3,5,3,5,3,1,3,2,2,1,4,4,5,2,2,4,2,1,4";
  private String testInput = "3,4,3,1,2";

  private long solvePart1(boolean useTestInput) {
    return solve(useTestInput, 80);
  }

  private long solvePart2(boolean useTestInput) {
    return solve(useTestInput, 256);
  }

  private long solve(boolean useTestInput, int days) {
    long[] ages = new long[9];
    // init all ages to 0
    var input = useTestInput ? testInput : puzzleInput;
    List<Integer> inputAges = Arrays.asList(input.split(",")).stream()
        .map(Integer::parseInt).toList();
    for (Integer age : new ArrayList<>(inputAges)) {
      ages[age]++;
    }
    for (int day = 0; day < days; day++) {
      long spawn = ages[0];
      for (int age = 1; age < 9; age++) {
        if (age == 7) {
          ages[6] = ages[7] + spawn;
        } else {
          ages[age - 1] = ages[age];
        }
      }
      ages[8] = spawn;
    }
    long fishes = 0;
    for (int age = 0; age < 9; age++) {
      fishes += ages[age];
    }
    return fishes;
  }

}
