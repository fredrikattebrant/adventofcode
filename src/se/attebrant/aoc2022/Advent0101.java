package se.attebrant.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.attebrant.common.AbstractAdvent;

public class Advent0101 extends AbstractAdvent {

  public Advent0101() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0101 advent = new Advent0101();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  private int solve() {
    List<String> input = readData();
    Map<Integer, Integer> caloriesPerElf = new HashMap<>();
    int calories = 0;
    int elf = 0;
    for (String line : input) {
      try {
        var cals = Integer.parseInt(line);
        calories += cals;
      } catch (NumberFormatException ex) {
        caloriesPerElf.put(calories, elf);
        // New elf
        elf++;
        calories = 0;
      }
    }
    int max = 0;
    for (Entry<Integer, Integer> entry : caloriesPerElf.entrySet()) {
      final Integer value = entry.getKey();
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  private List<String> readData() {
    return readData3("Advent01.txt").stream()
        .toList();
  }

}
