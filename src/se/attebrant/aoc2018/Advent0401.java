package se.attebrant.aoc2018;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0401 extends AbstractAdvent {

  public Advent0401() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0401 advent = new Advent0401();
    advent.solve();
  }


  // Guard ID -> String 60 chars, where # = sleeping and . = awake
  private Map<String, List<Integer>> guardMap = new HashMap<>();

  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  Calendar calendar = new GregorianCalendar();

  private void solve() {
    List<String> records = getTestData();

    List<Integer> guardEntry = null;

    String id = "";
    int asleep = -1;
    int wakeup = -1;
    // FIXME track the state etc... then fill in the guard entry in the map...
    for (String record : records) {
      Date date;
      try {
        date = format.parse(record.substring(1, 17));
        calendar.setTime(date);
      } catch (ParseException e) {
        System.err.println("*** Bad data: " + record);
        return;
      }
      int minute = calendar.get(Calendar.MINUTE);
      if (record.contains("Guard")) {
        // [1518-05-01 00:00] Guard #2207 begins shift
        id = record.substring(record.indexOf("#")).split(" ")[0];
        guardEntry = guardMap.get(id);
        if (guardEntry == null) {
          guardEntry = initEntry();
          guardMap.put(id, guardEntry);
        }
        // System.out.println("Guard: " + id);
      } else if (record.contains("asleep")) {
        asleep = minute;
      } else if (record.contains("wakes up")) {
        wakeup = minute;
        // System.out.println("Guard" + id + ": asleep: " + asleep + ", wakeup: " + wakeup);
        for (int i = asleep; i < wakeup; i++) {
          int valueAtI = guardEntry.get(i);
          valueAtI++;
          guardEntry.set(i, valueAtI);
        }
        guardMap.put(id, guardEntry);
      }
    }
    // System.out.println();
    // System.out.print(".... ");
    // for (int i = 0; i < 60; i++) {
    // System.out.print(i / 10);
    // }
    // System.out.println();
    // System.out.print(".... ");
    // for (int i = 0; i < 60; i++) {
    // System.out.print(i % 10);
    // }
    // System.out.println();
    // System.out.println();

    int answerId = -1;
    int answerMinutesAsleep = -1;
    int answerMinute = -1;
    int answerMaxMinute = -1;
    int answerMaxMinuteId = -1;
    int answerMaxSleepCount = -1;
    for (String gid : guardMap.keySet()) {
      int guardId = Integer.parseInt(gid.replace("#", ""));
      List<Integer> entry = guardMap.get(gid);
      int minutesAsleep = entry.stream().mapToInt(Integer::intValue).sum();
      // String content = gid + ": " + entry.stream()
      // .map(v -> "" + v)
      // .collect(Collectors.joining(""));
      // System.out.println(content);
      int indexOfMax = indexOfMaxValue(entry);
      Integer maxSleepCount = entry.get(indexOfMax);
      if (maxSleepCount > answerMaxSleepCount) {
        answerMaxSleepCount = maxSleepCount;
        answerMaxMinute = indexOfMax;
        answerMaxMinuteId = guardId;
      }
      // System.out.println("Guard #" + gid + " @ " + indexOfMax + " => " + maxSleepCount
      // + ", minutes asleep = " + minutesAsleep);
      if (minutesAsleep > answerMinutesAsleep) {
        answerId = guardId;
        answerMinutesAsleep = minutesAsleep;
        answerMinute = indexOfMax;
      }
    }
    System.out.println("[Answer1: Guard " + answerId + " at minute " + answerMinute + " => "
        + (answerId * answerMinute) + "]");
    System.out.println("[Answer2: Guard " + answerMaxMinuteId + " at minute " + answerMaxMinute
        + " => " + (answerMaxMinuteId * answerMaxMinute) + "]");
  }

  private int indexOfMaxValue(List<Integer> entries) {
    int max = Integer.MIN_VALUE;
    int maxindex = -1;
    for (int i = 0; i < entries.size(); i++) {
      Integer current = entries.get(i);
      if (current > max) {
        max = current;
        maxindex = i;
      }
    }
    return maxindex;
  }

  private List<Integer> initEntry() {
    List<Integer> initialValue = new ArrayList<>(60);
    for (int i = 0; i < 60; i++) {
      initialValue.add(0);
    }
    return initialValue;
  }

  private List<String> getTestData() {
    List<String> readData3 = readData3("Advent0401.txt");
    List<String> sorted = readData3.stream()
        .sorted()
        .collect(Collectors.toList());
    return sorted;
  }

  private List<String> getTestDatatest() {
    List<String> lines = new ArrayList<>();
    lines.add("[1518-11-01 00:00] Guard #10 begins shift");
    lines.add("[1518-11-01 00:05] falls asleep");
    lines.add("[1518-11-01 00:25] wakes up");
    lines.add("[1518-11-01 00:30] falls asleep");
    lines.add("[1518-11-01 00:55] wakes up");
    lines.add("[1518-11-01 23:58] Guard #99 begins shift");
    lines.add("[1518-11-02 00:40] falls asleep");
    lines.add("[1518-11-02 00:50] wakes up");
    lines.add("[1518-11-03 00:05] Guard #10 begins shift");
    lines.add("[1518-11-03 00:24] falls asleep");
    lines.add("[1518-11-03 00:29] wakes up");
    lines.add("[1518-11-04 00:02] Guard #99 begins shift");
    lines.add("[1518-11-04 00:36] falls asleep");
    lines.add("[1518-11-04 00:46] wakes up");
    lines.add("[1518-11-05 00:03] Guard #99 begins shift");
    lines.add("[1518-11-05 00:45] falls asleep");
    lines.add("[1518-11-05 00:55] wakes up");
    return lines;
  }

}
