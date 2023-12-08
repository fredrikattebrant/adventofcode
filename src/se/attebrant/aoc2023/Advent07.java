package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent07 extends AbstractAdvent {

  public Advent07(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent07 advent = new Advent07(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, true, isTest); // same for both parts
    return isPart2 ? solvePart2(input) : solvePart1(input);
  }

  String theCards = "23456789TJQKA"; // Higher index -> stronger card

  enum Type {
    HighCard, OnePair, TwoPair, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind
  }

  record Hand(String cards, int bid, Type type) {
    @Override
    public String toString() {
      // return String.format("%s %4d", cards, bid);
      return String.format("%s %4d %12s", cards, bid, type.name());
    }
  }

  private int solvePart1(List<String> input) {
    List<Hand> hands = new ArrayList<>();
    for (String line : input) {
      String[] split = line.split(" ");
      String cards = split[0];
      Type type = getType(cards);
      Hand hand = new Hand(cards, Integer.parseInt(split[1].trim()), type);
      hands.add(hand);
      log(hand);
    }
    log("");
    int total = 0;

    List<Hand> sortedHands = hands.stream()
        .sorted((Comparator<Hand>) this::compare)
        .toList();

    for (int i = 0; i < sortedHands.size(); i++) {
      int rank = i + 1;
      Hand hand = sortedHands.get(i);
      int bid = hand.bid;
      int plus = bid * rank;
      total += plus;
      logf("%s %4d %8d => %10d", hand, rank, plus, total);
      // logf("%4d %8d => %10d", bid, rank, total);
    }
    return total;
  }

  private int compare(Hand a, Hand b) {
    // 1: Compare type
    int typeComparison = a.type.compareTo(b.type);
    if (typeComparison == 0) {
      // 2. Compare cards
      for (int i = 0; i < 5; i++) {
        int a2 = theCards.indexOf(a.cards.charAt(i));
        int b2 = theCards.indexOf(b.cards.charAt(i));
        if (a2 > b2) {
          return 1;
        } else if (a2 < b2) {
          return -1;
        }
      }
    }
    return typeComparison;
  }

  private Type getType(String cards) {
    char[] sorted = cards.toCharArray();
    Arrays.sort(sorted);

    if (sorted[0] == sorted[1]
        && sorted[0] == sorted[2]
        && sorted[0] == sorted[3]
        && sorted[0] == sorted[4]) {
      return Type.FiveOfAKind;
    }

    if (sorted[0] == sorted[1] && sorted[0] == sorted[2] && sorted[0] == sorted[3]
        || sorted[1] == sorted[2] && sorted[1] == sorted[3] && sorted[1] == sorted[4]) {
      return Type.FourOfAKind;
    }

    if (sorted[0] == sorted[1] && sorted[0] == sorted[2] && sorted[3] == sorted[4]
        || sorted[0] == sorted[1] && sorted[2] == sorted[3] && sorted[2] == sorted[4]) {
      return Type.FullHouse;
    }

    if (sorted[0] == sorted[1] && sorted[0] == sorted[2]
        || sorted[1] == sorted[2] && sorted[1] == sorted[3]
        || sorted[2] == sorted[3] && sorted[2] == sorted[4]) {
      return Type.ThreeOfAKind;
    }

    // Two pair - e.g. sorted one of 22334, 23344, 22344
    if (isPair01(sorted) && isPair23(sorted)
        || isPair12(sorted) && isPair34(sorted)
        || isPair01(sorted) && isPair34(sorted)) {
      return Type.TwoPair;
    }

    if (hasAtLeastOnePair(sorted)) {
      return Type.OnePair;
    }

    return Type.HighCard;
  }

  private boolean hasAtLeastOnePair(char[] sorted) {
    return isPair01(sorted)
        || isPair12(sorted)
        || isPair23(sorted)
        || isPair34(sorted);
  }

  private boolean isPair01(char[] sorted) {
    return sorted[0] == sorted[1];
  }

  private boolean isPair12(char[] sorted) {
    return sorted[1] == sorted[2];
  }

  private boolean isPair23(char[] sorted) {
    return sorted[2] == sorted[3];
  }

  private boolean isPair34(char[] sorted) {
    return sorted[3] == sorted[4];
  }

  private int solvePart2(List<String> input) {
    return -1;
  }

}
