package se.attebrant.aoc2023;

import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent01 extends AbstractAdvent {

  public Advent01(boolean isTest) {
    super("2023", isTest);
  }

  List<String> digitsSpelledOut = List.of(
      "zero",
      "one",
      "two",
      "three",
      "four",
      "five",
      "six",
      "seven",
      "eight",
      "nine");

  public static void main(String[] args) {
    var isTest = false;
    Advent01 advent = new Advent01(isTest);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(false, false));
    print("Result day " + day + ", part2: " + advent.solve(true, false));
  }

  private int solve(boolean isPart2, boolean isTest) {
    log("=====");
    log("Part" + (isPart2 ? 2 : 1));
    log("=====");
    List<String> input = readData(isPart2, isTest);
    int sum = 0;
    for (String line : input) {
      logf("");
      logf("Line: %s", line);
      int first = getFirstDigit(line, isPart2);
      int last = getLastDigit(line, isPart2);
      if (last == -1) {
        last = first;
      }
      int combined = first * 10 + last;
      sum += combined;
      logf("%s: %d, %d => %d", line, first, last, sum);
    }
    return sum;
  }

  int getFirstDigit(String line, boolean isPart2) {
    int firstDigit = -1;
    int firstDigitIx = -1;
    for (int ix = 0; ix < line.length(); ix++) {
      char c = line.charAt(ix);
      try {
        firstDigit = Integer.parseUnsignedInt(c + "");
        firstDigitIx = ix;
        break;
      } catch (Exception e) {
        // Ignore an try the next
      }
    }
    if (!isPart2) {
      return firstDigit;
    }
    // fall back on spelled out digits - find all and take the lowest index
    int firstStringDigit = -1;
    int stringIx = Integer.MAX_VALUE;
    for (String digit : digitsSpelledOut) {
      int ix = line.indexOf(digit);
      if (ix > -1 && (ix < stringIx)) {
        firstStringDigit = digitsSpelledOut.indexOf(digit);
        stringIx = ix;
      }
    }
    int result = -1;
    if (firstDigitIx > -1 && firstDigitIx < stringIx) {
      result = firstDigit;
    } else {
      result = firstStringDigit;
    }
    log("+++ First digit: " + firstDigit + " or " + firstStringDigit + " => " + result);
    return result;
  }

  int getLastDigit(String line, boolean isPart2) {
    int lastDigit = -1;
    int lastDigitIx = -1;
    for (int ix = line.length() - 1; ix > 0; ix--) {
      char c = line.charAt(ix);
      try {
        lastDigit = Integer.parseUnsignedInt(c + "");
        lastDigitIx = ix;
        break;
      } catch (Exception e) {
        // Ignore an try the next
      }
    }
    if (!isPart2) {
      return lastDigit;
    }
    // find all spelled out digits, take the one with the highest index
    int lastStringDigit = -1;
    int stringIx = -1;
    for (String digit : digitsSpelledOut) {
      int ix = line.lastIndexOf(digit);
      if (ix > -1 && (ix > stringIx)) {
        lastStringDigit = digitsSpelledOut.indexOf(digit);
        stringIx = ix;
      }
    }
    int result = -1;
    if (lastDigitIx > -1 && lastDigitIx > stringIx) {
      result = lastDigit;
    } else {
      result = lastStringDigit;
    }
    logf("+++ Last digit: %d or %d => %d", lastDigit, lastStringDigit, result);
    return result;
  }

  private List<String> readData(boolean isPart2, boolean isTest) {
    String testSuffix = isTest ? "test" : "";
    String testPart = isPart2 ? "2" : "1";
    String part = isTest ? testPart : "";
    String inputFilename = "Advent01" + testSuffix + part + ".txt";
    return readData3(inputFilename).stream()
        .toList();
  }

}
