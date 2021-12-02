package se.attebrant.aoc2021;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0202 extends AbstractAdvent {

  public Advent0202(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent0202 advent = new Advent0202(false);
    System.out.println("Result 0201: " + advent.solve());
  }

  private int solve() {
    int horizontalPosition = 0;
    int depth = 0;
    int aim = 0;
    for (String line : readData()) {
      String[] tokens = line.split(" ");
      log(tokens[0] + " " + tokens[1]);
      int delta = Integer.parseInt(tokens[1]);
      switch (tokens[0]) {
        case "down":
          aim += delta;
          break;
        case "up":
          aim -= delta;
          break;
        case "forward":
          horizontalPosition += delta;
          depth += aim * delta;
          break;
        default:
          throw new IllegalArgumentException("Unexpected value: " + tokens[0]);
      }
      log("HP: " + horizontalPosition + ", D: " + depth + ", A: " + aim);
    }
    return horizontalPosition * depth;
  }

  private List<String> readData() {
    return readData3("Advent02.txt").stream()
        .toList();
  }

}
