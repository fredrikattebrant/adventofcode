package se.attebrant.aoc2022;

import java.util.Arrays;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent10 extends AbstractAdvent {

  public Advent10(boolean... debug) {
    super("2022", debug);
  }

  private static int TEST1 = 1;
  private static int TEST2 = 2;
  private static int LIVE3 = 3;

  public static void main(String[] args) {
    var advent = new Advent10(true);
    var dayPart = getDayPart(advent);
    var mode = LIVE3;
    print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(mode));
  }

  private int solvePart1(int inputSelection) {
    List<String> instructions = getInput(inputSelection);

    int x = 1;
    int cycle = 1;
    int signalStrength = 0;
    int sum = 0;
    for (String instruction : instructions) {
      String split[] = instruction.split(" ");
      String opName = split[0];
      int cycles = 0;
      int delta = 0;
      switch (opName) {
        case "noop":
          cycles = 1;
          delta = 0;
          break;
        case "addx":
          cycles = 2;
          delta = Integer.parseInt(split[1]);
          break;
        default:
          throw new RuntimeException("Illegal instruction: " + opName);
      }
      for (int i = 0; i < cycles; i++) {
        cycle++;
        if (i == 1) {
          x += delta;
        }
        signalStrength = cycle * x;

        if ((cycle - 20) % 40 == 0) {
          sum += signalStrength;
          System.out.println("Cycle: " + cycle + ", x = " + x + " Sig str: " + signalStrength + " => " +sum);
          if (cycle == 220) {
            return sum;
          }
        }
      }
    }
    return -1;
  }

  private int solvePart2(int inputSelection) {
    List<String> instructions = getInput(inputSelection);

    int x = 1;
    int cycle = 1;
    int sum = 0;
    for (String instruction : instructions) {
      String split[] = instruction.split(" ");
      String opName = split[0];
      int cycles = 0;
      int delta = 0;
      switch (opName) {
        case "noop":
          cycles = 1;
          delta = 0;
          break;
        case "addx":
          cycles = 2;
          delta = Integer.parseInt(split[1]);
          break;
        default:
          throw new RuntimeException("Illegal instruction: " + opName);
      }
      for (int i = 0; i < cycles; i++) {
        if ((cycle - 1) % 40 == 0) {
          System.out.print(String.format("Cycle %3d -> ", cycle));
        }
        int pixel = (cycle - 1) % 40;
        if (pixel + 1 == x || pixel == x || pixel - 1 == x) {
          System.out.print("#");
        } else {
          System.out.print(".");
        }
        if (cycle % 40 == 0) {
          System.out.println(String.format(" <- Cycle %3d", cycle));
        }
        cycle++;
        if (i == 1) {
          // addx
          x += delta;
        }
      }
    }
    System.out.println();
    return sum;
  }

  private List<String> getInput(int ix) {
    if (ix == 1) {
      String instructions = """
              noop
              addx 3
              addx -5
              """;
      String[] split = instructions.split("\n");
      return Arrays.asList(split);
    } else if (ix == 2) {
      return readData4(true);
    } else {
      return readData4(false);
    }
  }

}
