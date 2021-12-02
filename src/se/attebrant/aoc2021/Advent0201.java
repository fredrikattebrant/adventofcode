package se.attebrant.aoc2021;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0201 extends AbstractAdvent {

  public Advent0201(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent0201 advent = new Advent0201();
    System.out.println("Result 0201: " + advent.solve());
  }

  private int solve() {
    int horizontalPosition = 0;
    int depth = 0;
    for (String line : readData()) {
      String[] tokens = line.split(" ");
      log("Token => " + tokens[0]);
      switch (tokens[0]) {
        case "down":
          log(tokens[0]);
          depth += Integer.parseInt(tokens[1]);
          break;
        case "up":
          log(tokens[0]);
          depth -= Integer.parseInt(tokens[1]);
          break;
        case "forward":
          log(tokens[0]);
          horizontalPosition += Integer.parseInt(tokens[1]);
          break;
        default:
          throw new IllegalArgumentException("Unexpected value: " + tokens[0]);
      }
      log("HP: " + horizontalPosition + ", D: " + depth);
    }
    return horizontalPosition * depth;
  }

  private List<String> readData() {
    return readData3("Advent02.txt").stream()
        .toList();
  }

}
