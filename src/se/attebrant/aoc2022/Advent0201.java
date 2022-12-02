package se.attebrant.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.attebrant.common.AbstractAdvent;

public class Advent0201 extends AbstractAdvent {

  private static final String ROCK = "Rock";
  private static final String PAPER = "Paper";
  private static final String SCISSORS = "Scissors";

  public Advent0201() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0201 advent = new Advent0201();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  /**
   * <pre>
   * A Rock
   * B Paper
   * C Scissors
   *
   * X Rock
   * Y Paper
   * Z Scissors
   *
   * Paper > Rock
   * Scissors > Paper
   * Rock > Scissors
   *
   * Lost 0
   * Draw 3
   * Win  6
   * </pre>
   */
  private static Map<String, String> opponentMap = new HashMap<>();
  static {
    opponentMap.put("A", ROCK);
    opponentMap.put("B", PAPER);
    opponentMap.put("C", SCISSORS);
  }

  private static Map<String, String> myMap = new HashMap<>();
  static {
    myMap.put("X", ROCK);
    myMap.put("Y", PAPER);
    myMap.put("Z", SCISSORS);
  }

  private static Map<String, Integer> itemScoreMap = new HashMap<>();
  static {
    itemScoreMap.put(ROCK, 1);
    itemScoreMap.put(PAPER, 2);
    itemScoreMap.put(SCISSORS, 3);
  }

  private int score(String item1, String item2) {
    int itemScore = itemScoreMap.get(item2);
    if (item1.equals(item2)) {
      return itemScore + 3;
    }
    if (item1.equals(ROCK)) {
      if (item2.equals(PAPER)) {
        return itemScore +  6;
      }
      return itemScore + 0;
    } else if (item1.equals(PAPER)) {
      if (item2.equals(SCISSORS)) {
        return itemScore + 6;
      }
    } else if (item1.equals(SCISSORS)) {
      if (item2.equals(ROCK)) {
        return itemScore + 6;
      }
    }
    return itemScore + 0;
  }

  private int scoreRound(String opponent, String me) {
    String myItem = myMap.get(me);
    String opponentItem = opponentMap.get(opponent);
    return score(opponentItem, myItem);
  }

  private int solve() {
    List<String> input = readData();

    int totalScore = 0;
    for (String line : input) {
      String[] split = line.split(" ");
      int score = scoreRound(split[0], split[1]);
      totalScore += score;
    }

    return totalScore;
  }

  private List<String> readData() {
    return readData3("Advent02.txt").stream()
        .toList();
  }

}
