package se.attebrant.aoc2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.attebrant.common.AbstractAdvent;

public class Advent0202 extends AbstractAdvent {

  private static final String WIN = "win";
  private static final String DRAW = "draw";
  private static final String LOSE = "lose";
  private static final String ROCK = "Rock";
  private static final String PAPER = "Paper";
  private static final String SCISSORS = "Scissors";
  private static final String NONE = "";

  public Advent0202() {
    super("2022");
  }

  public static void main(String[] args) {
    Advent0202 advent = new Advent0202();
    String dayPart = getDayPart(advent);
    print("Result " + dayPart + ": " + advent.solve());
  }

  /**
   * <pre>
   * A Rock
   * B Paper
   * C Scissors
   *
   * X Lose
   * Y Draw
   * Z Win
   *
   * Paper > Rock
   * Scissors > Paper
   * Rock > Scissors
   *
   * Lose 0
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

  private static Map<String, String> outcomeMap = new HashMap<>();
  static {
    outcomeMap.put("X", LOSE);
    outcomeMap.put("Y", DRAW);
    outcomeMap.put("Z", WIN);
  }

  private static Map<String, Integer> itemScoreMap = new HashMap<>();
  static {
    itemScoreMap.put(ROCK, 1);
    itemScoreMap.put(PAPER, 2);
    itemScoreMap.put(SCISSORS, 3);
  }

  private int score(String item1, String outcome) {
    String item2 = NONE;
    if (outcome.equals(LOSE)) {
      switch (item1) {
        case ROCK:
          item2 = SCISSORS;
          break;
        case PAPER:
          item2 = ROCK;
          break;
        case SCISSORS:
          item2 = PAPER;
          break;
        default:
          break;
      }
    } else if (outcome.equals(DRAW)) {
      item2 = item1;
    } else { // WIN
      switch (item1) {
        case ROCK:
          item2 = PAPER;
          break;
        case PAPER:
          item2 = SCISSORS;
          break;
        case SCISSORS:
          item2 = ROCK;
          break;
        default:
          break;
      }
    }
    int itemScore = itemScoreMap.get(item2);
    if (item1.equals(item2)) {
      return itemScore + 3;
    }
    if (item1.equals(ROCK)) {
      if (item2.equals(PAPER)) {
        return itemScore + 6;
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

  private int playRound(String opponent, String encodedOutcome) {
    String opponentItem = opponentMap.get(opponent);
    String outcome = outcomeMap.get(encodedOutcome);
    return score(opponentItem, outcome);
  }

  private int solve() {
    List<String> input = readData();

    int totalScore = 0;
    for (String line : input) {
      String[] split = line.split(" ");
      int score = playRound(split[0], split[1]);
      totalScore += score;
    }

    return totalScore;
  }

  private List<String> readData() {
    return readData3("Advent02.txt").stream()
        .toList();
  }

}
