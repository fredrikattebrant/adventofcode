package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import se.attebrant.common.AbstractAdvent;

public class Advent04 extends AbstractAdvent {

  public Advent04(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = false;
    Advent04 advent = new Advent04(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    List<Card> cards = parseInput(input);
    return isPart2 ? solvePart2(cards) : solvePart1(cards);
  }

  record Card(int id, int count, List<String> winning, List<String> your) {
    @Override
    public String toString() {
      String winPart = winning.stream().collect(Collectors.joining(" "));
      String yourPart = your.stream().collect(Collectors.joining(" "));
      return String.format("Card %d (%d copies): %s | %s", id, count, winPart, yourPart);
    }
  }

  List<Card> parseInput(List<String> input) {
    List<Card> cards = new ArrayList<>();
    for (String line : input) {
      String[] lineSplit = line.split(": ");
      String cardStr = lineSplit[0];
      int id = Integer.parseInt(cardStr.substring(cardStr.lastIndexOf(" ")).trim());
      String[] numbersSplit = lineSplit[1].split(" \\| ");
      String[] winning = numbersSplit[0].trim().split("\\s+");
      String[] your = numbersSplit[1].trim().split("\\s+");
      List<String> w = List.of(winning);
      List<String> y = List.of(your);
      Card card = new Card(id, 1, w, y);
      cards.add(card);
    }
    return cards;
  }

  private int solvePart1(List<Card> cards) {
    int total = 0;
    for (Card card : cards) {
      total += getScore(card);
      log(String.format("%3d => %3d", card.id, total));
    }
    return total;
  }

  private int getScore(Card card) {
    int points = 0;
    for (String wn : card.winning) {
      if (card.your.contains(wn)) {
        points = points == 0 ? 1 : points * 2;
      }
    }
    return points;
  }

  private int getScore2(Card card) {
    int points = 0;
    for (String wn : card.winning) {
      if (card.your.contains(wn)) {
        points += 1;
      }
    }
    return points;
  }

  private int solvePart2(List<Card> originalCards) {
    List<Card> cards = new ArrayList<>(originalCards);
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);
      int count = card.count;
      int score = getScore2(card);
      for (int j = 1; j < score + 1; j++) {
        Card nextCard = cards.get(i + j);
        Card newCard =
            new Card(nextCard.id, nextCard.count + count, nextCard.winning, nextCard.your);
        cards.set(i + j, newCard);
      }
    }
    return cards.stream()
        .mapToInt(card -> card.count)
        .sum();
  }

}
