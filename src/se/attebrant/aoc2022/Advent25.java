package se.attebrant.aoc2022;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent25 extends AbstractAdvent {

  public Advent25() {
    super("2022");
    debug = true;
  }

  public static void main(String[] args) {
    var advent = new Advent25();
    var dayPart = getDayPart(advent);
    print("Result " + dayPart + " part 1: " + advent.solvePart1(IS_TEST));
    // print("Result " + dayPart + " part 2: " + advent.solvePart2(IS_LIVE));
  }

  private int snafuDigitToInt(char c) {
    return switch (c) {
      case '0' -> 0;
      case '1' -> 1;
      case '2' -> 2;
      case '-' -> -1;
      case '=' -> -2;
      default -> throw new IllegalArgumentException("Unexpected value: " + c);
    };
  }

  private String getSnafu(long exp, int remains) {
    double delta = Math.pow(5, exp);
    double ceil = Math.ceil(remains / delta);
    double smaller = exp > 0 ? Math.pow(5, exp - 1) : 0;


    double doubleDelta = delta * 2;
    double doubleSmaller = smaller * 2;
    double d1 = remains - delta;
    double d2 = d1 + smaller;
    double d3 = 0;

    String snafu = "";
    return snafu;
  }

  private String intToSnafu(int number) {
    String snafu = "";
    long exp = Math.round(Math.pow(number, 1.0 / 5.0));
    int remains = number;
    while (exp >= 0) {
      // How many of 5^exp can we fit before having to use a smaller exp?
      // Max in snafu is +/-2
      // When use +/- ?
      String snafuDigit = getSnafu(exp, remains);
      snafu += snafuDigit;
      double delta = snafu2Int(snafuDigit) * Math.pow(5, exp);
      remains -= delta;
      System.out.println();
    }

    return snafu;
  }

  private int snafu2Int(String snafu) {
    Double number = 0.0;
    int exp = 0;
    for (int i = snafu.length() - 1; i >= 0; i--) {
      char c = snafu.charAt(i);
      int n = snafuDigitToInt(c);
      double value = Math.pow(5, exp++) * n;
      number += value;
    }
    return number.intValue();
  }

  private String solvePart1(boolean isTest) {
    List<String> input = readData4(isTest);
    int sum = 0;
    for (String line : input) {
      int decimal = snafu2Int(line);
      sum += decimal;
      System.out.println(line + " -> " + decimal);
      System.out.print("");
    }
    String snafu = intToSnafu(sum);
    return snafu;
  }

  private int solvePart2(boolean isTest) {
    return -1;
  }

}
