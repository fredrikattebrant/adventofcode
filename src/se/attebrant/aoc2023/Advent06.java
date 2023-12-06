package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent06 extends AbstractAdvent {

  public Advent06(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent06 advent = new Advent06(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest);
    return isPart2 ? solvePart2(input) : solvePart1(input);
  }

  private int solvePart1(List<String> input) {
    List<Integer> times = new ArrayList<>();
    List<Integer> distances = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      String line = input.get(i);
      if (line.startsWith("Time:")) {
        String[] timeSplit = line.split(":");
        String[] timeValues = timeSplit[1].trim().split("\\s+");
        for (int j = 0; j < timeValues.length; j++) {
          String timeValue = timeValues[j];
          int time = Integer.parseInt(timeValue);
          times.add(time);
        }
      } else {
        String[] distanceSplit = line.split(":");
        String[] distanceValues = distanceSplit[1].trim().split("\\s+");
        for (int j = 0; j < distanceValues.length; j++) {
          String distanceValue = distanceValues[j];
          int distance = Integer.parseInt(distanceValue);
          distances.add(distance);
        }
      }
    }
    int total = 1;
    for (int i = 0; i < times.size(); i++) {
      int time = times.get(i);
      int recordDistance = distances.get(i);
      int wins = countWins(recordDistance, time);
      total = total * wins;
    }
    return total;
  }

  private boolean isWin(long recordDistance, long time, long timeButton) {
    long distance = timeButton * (time - timeButton);
    return distance > recordDistance;
  }

  private int countWins(long recordDistance, long time) {
    int wins = 0;
    boolean hasStartedToWin = false;
    for (long timeButton = 0; timeButton <= time; timeButton++) {
      if (isWin(recordDistance, time, timeButton)) {
        wins++;
        hasStartedToWin = true;
      } else if (hasStartedToWin) {
        // quit when we loose again - slight optimization ;-)
        break;
      }
    }
    return wins;
  }

  private int solvePart2(List<String> input) {
    String timeLine = input.get(0).replace(" ", "");
    Long time = Long.parseLong(timeLine.split(":")[1]);
    String distanceLine = input.get(1).replace(" ", "");
    Long distance = Long.parseLong(distanceLine.split(":")[1]);
    return countWins(distance, time);
  }

}
