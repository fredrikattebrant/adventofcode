package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import se.attebrant.common.AbstractAdvent;

public class Advent09 extends AbstractAdvent {

  public Advent09(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent09 advent = new Advent09(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  private long solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    List<List<Long>> valueLines = parseInput(input);
    return isPart2 ? solvePart2(valueLines) : solvePart1(valueLines);
  }

  private List<List<Long>> parseInput(List<String> input) {
    List<List<Long>> valueLines = new ArrayList<>();
    for (String inputLine : input) {
      String[] split = inputLine.split(" ");
      List<String> stringNums = Arrays.asList(split);
      List<Long> values = stringNums.stream()
          .map(Long::parseLong)
          .toList();
      valueLines.add(values);
    }
    return valueLines;
  }

  private void logValueLine(List<Long> valueLine) {
    log(valueLine.stream()
        .map(Object::toString)
        .collect(Collectors.joining(" ")));
  }

  private List<Long> calculateDeltas(List<Long> values) {
    List<Long> deltas = new ArrayList<>();
    // start at the 2nd value, compare w previous value
    for (int i = 1; i < values.size(); i++) {
      long delta = values.get(i) - values.get(i - 1);
      deltas.add(delta);
    }
    return deltas;
  }

  private List<Long> predictNext(List<Long> previousLine, List<Long> deltas) {
    List<Long> predictedLine = new ArrayList<>(previousLine);
    long next = previousLine.getLast() + deltas.getLast();
    predictedLine.add(next);
    return predictedLine;
  }

  private List<Long> predictPrevious(List<Long> previousLine, List<Long> deltas) {
    List<Long> predictedLine = new ArrayList<>(previousLine);
    long previous = previousLine.get(0) - deltas.get(0);
    predictedLine.add(0, previous);
    return predictedLine;
  }

  private boolean isZeroes(List<Long> values) {
    return values.stream().mapToLong(Long::longValue).allMatch(v -> v == 0L);
  }

  private long solvePart1(List<List<Long>> valueLines) {
    long predictionSum = 0L;
    for (int i = 0; i < valueLines.size(); i++) {
      List<Long> values = valueLines.get(i);
      List<List<Long>> deltas = new ArrayList<>();
      List<Long> current = values;
      do {
        current = calculateDeltas(current);
        deltas.add(current);
      } while (!isZeroes(current));
      for (int j = deltas.size() - 1; j > 0; j -= 1) {
        List<Long> previous = deltas.get(j - 1);
        List<Long> predicted = predictNext(previous, deltas.get(j));
        deltas.set(j - 1, predicted);
      }
      List<Long> theLine = new ArrayList<>(values);
      List<Long> predicted = predictNext(theLine, deltas.get(0));
      long lastPredicted = predicted.getLast();
      predictionSum += lastPredicted;
      // logf("(%3d) %12d, %12d", i, lastPredicted, predictionSum);
      // log(lastPredicted);
    }
    return predictionSum;
  }

  private long solvePart2(List<List<Long>> valueLines) {
    long predictionSum = 0L;
    for (int i = 0; i < valueLines.size(); i++) {
      List<Long> values = valueLines.get(i);
      List<List<Long>> deltas = new ArrayList<>();
      List<Long> current = values;
      do {
        current = calculateDeltas(current);
        deltas.add(current);
      } while (!isZeroes(current));
      for (int j = deltas.size() - 1; j > 0; j -= 1) {
        List<Long> previous = deltas.get(j - 1);
        List<Long> predicted = predictPrevious(previous, deltas.get(j));
        deltas.set(j - 1, predicted);
      }
      List<Long> theLine = new ArrayList<>(values);
      List<Long> predicted = predictPrevious(theLine, deltas.get(0));
      long firstPredicted = predicted.get(0);
      predictionSum += firstPredicted;
      // logf("(%3d) %12d, %12d", i, lastPredicted, predictionSum);
      // log(firstPredicted);
    }
    return predictionSum;
  }

}
