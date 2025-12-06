package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent06 extends AbstractAdvent {

  public Advent06(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent06 advent = new Advent06(isTest);
    advent.debug = true;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve1(day, false, isTest));
    print("Result day " + day + ", part2: " + advent.solve2(day, true, isTest));
  }

  private long solve1(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> input = readData(day, false, isTest);

    List<List<Long>> numbersInProblem = new ArrayList<>();

    long grandTotal = 0;

    boolean firstRow = true;
    for (String line : input) {
      String[] split = line.trim().split("\\s+");
      for (int i = 0; i < split.length; i++) {
        if (firstRow) {
          // Initialize the storage
          List<Long> numList = new ArrayList<>();
          numbersInProblem.add(numList);
        }

        String value = split[i].trim();
        List<Long> numList = numbersInProblem.get(i);

        // Load the operators (if last row)
        if (value.equals("*") || value.equals("+")) {
          long result = calculate(numList, value);
          grandTotal += result;
        } else {
          Long num = Long.parseLong(value.trim());
          numList.add(num);
        }
      }
      firstRow = false;
    }


    log("");
    return grandTotal;
  }

  private long solve2(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> input = readData(day, false, isTest);

    List<List<Long>> numbersInProblem = new ArrayList<>();

    long grandTotal = 0;

    // Get the zero based column count from the input
    int columns = 0;
    for (String line : input) {
      int length = line.length();
      if (length > columns) {
        columns = length - 1;
      }
    }

    // Number of lines, including the operator line
    int lines = input.size();

    List<Long> numbers = new ArrayList<>();
    for (int column = columns; column >= 0; column--) {
      long numberInColumn = 0;
      int magnitude = 1;
      String operator = "";
      for (int l = lines - 1; l >= 0; l--) {
        String line = input.get(l);
        if (column < line.length()) {
          char c = line.charAt(column);
          if (c == '*' || c == '+') {
            // Operators
            operator = c + "";
          } else {
            int i = Character.getNumericValue(c);
            if (i >= 0) {
              numberInColumn += i * magnitude;
              magnitude *= 10;
            }
          }
        }
      }
      if (numberInColumn > 0) {
        numbers.add(numberInColumn);
      } else {
        numbers.clear();
      }
      if (operator.equals("*") || operator.equals("+")) {
        long result = calculate(numbers, operator);
        grandTotal += result;
        numbers.clear();
      }
    }

    log("");
    return grandTotal;
  }

  private long calculate(List<Long> numbers, String operator) {
    long result = operator.equals("+") ? 0 : 1;
    for (Long number : numbers) {
      if (operator.equals("+")) {
        result = result + number;
      } else {
        result = result * number;
      }
    }
    return result;
  }

}
