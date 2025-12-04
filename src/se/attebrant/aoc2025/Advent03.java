package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent03 extends AbstractAdvent {

  public Advent03(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent03 advent = new Advent03(isTest);
    advent.debug = true;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, false, isTest));
    // print("Result day " + day + ", part2: " + advent.solve(day, true, isTest));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> banks = readData(day, false, isTest);

    int totalJoltage = 0;
    for (String bank : banks) {
      totalJoltage += getMaxJoltage(bank);
    }

    return totalJoltage;
  }

  private int getMaxJoltage(String bank) {
    // Find highest digit in the length - 1 first values
    // Save the position of the highest digit (pos1)
    // Get highest value of the digits starting from (pos1 + 1)
    int len = bank.length();
    int pos1 = 0;
    int high1 = -1;
    for (int i = 0; i < len - 1; i++) {
      int value = Character.getNumericValue(bank.charAt(i));
      if (value > high1) {
        high1 = value;
        pos1 = i;
      }
    }
    int high2 = -1;
    for (int i = pos1 + 1; i < len; i++) {
      int value = Character.getNumericValue(bank.charAt(i));
      if (value > high2) {
        high2 = value;
      }
    }
    int maxJoltage = 10 * high1 + high2;
    log("Bank " + bank + ": max joltage: " + maxJoltage);
    return maxJoltage;
  }

}
