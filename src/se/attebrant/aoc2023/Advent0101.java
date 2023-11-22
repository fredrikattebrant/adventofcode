package se.attebrant.aoc2023;

import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent0101 extends AbstractAdvent {

  public Advent0101() {
    super("2023");
  }

  public static void main(String[] args) {
    Advent0101 advent = new Advent0101();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  private int solve() {
    List<String> input = readData();
    return -1;
  }

  private List<String> readData() {
    return readData3("Advent01.txt").stream()
        .toList();
  }

}
