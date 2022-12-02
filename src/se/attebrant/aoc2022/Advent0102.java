package se.attebrant.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.attebrant.common.AbstractAdvent;

public class Advent0102 extends AbstractAdvent {

  public Advent0102() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0102 advent = new Advent0102();
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
    var sorted = caloriesPerElf.keySet().stream().sorted().toList();
    int sum = 0;
    for (int i = sorted.size() - 3; i < sorted.size(); i++) {
      final Integer s = sorted.get(i);
      sum += s;
      print(s + " => " + sum);
    }
    return sum;
  }

  private List<String> readData() {
    return readData3("Advent01.txt").stream()
        .toList();
  }

}
