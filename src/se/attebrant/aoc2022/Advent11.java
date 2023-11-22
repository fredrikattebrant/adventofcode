package se.attebrant.aoc2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent11 extends AbstractAdvent {

  public Advent11(boolean... debug) {
    super("2022", debug);
  }

  public static void main(String[] args) {
    var advent = new Advent11(true);
    var dayPart = getDayPart(advent);
    var mode = IS_LIVE;
    // print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(IS_TEST));
  }

  private class Monkey {
    private String id;
    private List<BigInteger> items = new ArrayList<>();
    private String op;
    private String right;
    private BigInteger test;
    private int trueMonkey;
    private int falseMonkey;
    private long monkeyBusiness = 0;
    private BigInteger relief;

    public Monkey(String id, int relief) {
      this.id = id.trim().replace(":", "");
      this.relief = BigInteger.valueOf(relief);
    }

    public void addItem(BigInteger item) {
      items.add(item);
    }

    public void addStartingItems(String startingItems) {
      for (String item : startingItems.split(",")) {
        items.add(new BigInteger(item.trim()));
      }
    }

    public void setOperation(String operation) {
      String ops = operation.split("=")[1].trim();
      String[] split = ops.split(" ");
      op = split[1];
      right = split[2];
    }

    public void addTest(String test) {
      this.test = new BigInteger(test);
    }

    public void setThrowIfTrue(int trueMonkey) {
      this.trueMonkey = trueMonkey;
    }

    public void setThrowIfFalse(int falseMonkey) {
      this.falseMonkey = falseMonkey;
    }

    public BigInteger calculate(BigInteger old) {
      BigInteger v = old;
      BigInteger r = switch (right) {
        case "old" -> old;
        default -> new BigInteger(right);
      };
      BigInteger result = switch (op) {
        case "*" -> v.multiply(r);
        case "+" -> v.add(r);
        default -> throw new IllegalArgumentException("Unexpected value: " + op);
      };
      return result;
    }

    public long getMonkeyBusiness() {
      return monkeyBusiness;
    }

    public void play(List<Monkey> monkeys) {
      for (BigInteger item : items) {
        monkeyBusiness++;
        BigInteger worryLevel = calculate(item);
        worryLevel = worryLevel.divide(relief);
        if (worryLevel.mod(test).equals(BigInteger.ZERO)) {
          monkeys.get(trueMonkey).addItem(worryLevel);
        } else {
          monkeys.get(falseMonkey).addItem(worryLevel);
        }
      }
      items.clear();
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder("Monkey " + id + ":");
      text.append(System.lineSeparator());
      text.append(
          "  Starting items: " + items.stream().map(i -> i + "").collect(Collectors.joining(", ")));
      text.append(System.lineSeparator());
      text.append("  Operation: new = old ").append(op).append(" ").append(right);
      text.append(System.lineSeparator());
      text.append("  Test: divisible by ").append(test);
      text.append(System.lineSeparator());
      text.append("    If true: throw to monkey ").append(trueMonkey);
      text.append(System.lineSeparator());
      text.append("    If false: throw to monkey ").append(falseMonkey);
      text.append(System.lineSeparator());
      text.append("  Inspection count: ").append(monkeyBusiness);
      text.append(System.lineSeparator());
      text.append(System.lineSeparator());
      return text.toString();
    }

  }

  private long solvePart1(boolean isTest) {
    List<String> lines = readData4(isTest);
    // Parse input
    List<Monkey> monkeys = new ArrayList<>();
    Monkey monkey = null;
    for (String line : lines) {
      // log(line);
      if (line.startsWith("Monkey")) {
        monkey = new Monkey(line.split(" ")[1], 3);
        monkeys.add(monkey);
      } else if (line.trim().startsWith("Starting items:")) {
        monkey.addStartingItems(line.split(":")[1]);
      } else if (line.trim().startsWith("Operation:")) {
        monkey.setOperation(line);
      } else if (line.trim().startsWith("Test:")) {
        monkey.addTest(line.split("by")[1].trim());
      } else if (line.trim().startsWith("If true:")) {
        monkey.setThrowIfTrue(Integer.parseInt(line.split("monkey")[1].trim()));
      } else if (line.trim().startsWith("If false:")) {
        monkey.setThrowIfFalse(Integer.parseInt(line.split("monkey")[1].trim()));
      }
    }

    log("Start to play");
    // Play 20 rounds
    for (long round = 0; round < 20; round++) {
      for (Monkey current : monkeys) {
        current.play(monkeys);
      }
      log("Round " + (round + 1));
    }

    List<Long> mb = monkeys.stream().map(m -> m.getMonkeyBusiness())
        .sorted(Collections.reverseOrder()).toList();
    long monkeyBusiness = mb.get(0) * mb.get(1);
    return monkeyBusiness;
  }

  private long solvePart2(boolean isTest) {
    List<String> lines = readData4(isTest);
    // Parse input
    List<Monkey> monkeys = new ArrayList<>();
    Monkey monkey = null;
    for (String line : lines) {
      // log(line);
      if (line.startsWith("Monkey")) {
        monkey = new Monkey(line.split(" ")[1], 1);
        monkeys.add(monkey);
      } else if (line.trim().startsWith("Starting items:")) {
        monkey.addStartingItems(line.split(":")[1]);
      } else if (line.trim().startsWith("Operation:")) {
        monkey.setOperation(line);
      } else if (line.trim().startsWith("Test:")) {
        monkey.addTest(line.split("by")[1].trim());
      } else if (line.trim().startsWith("If true:")) {
        monkey.setThrowIfTrue(Integer.parseInt(line.split("monkey")[1].trim()));
      } else if (line.trim().startsWith("If false:")) {
        monkey.setThrowIfFalse(Integer.parseInt(line.split("monkey")[1].trim()));
      }
    }

    log("Start to play");
    // Play 10000 rounds
    for (long round = 0; round < 10000; round++) {
      for (Monkey current : monkeys) {
        current.play(monkeys);
        // log(monkey);
      }
      log("Round " + (round + 1));
    }

    List<Long> mb = monkeys.stream().map(m -> m.getMonkeyBusiness())
        .sorted(Collections.reverseOrder()).toList();
    long monkeyBusiness = mb.get(0) * mb.get(1);
    log(mb.get(0) + " * " + mb.get(1) + " = " + monkeyBusiness);
    return monkeyBusiness;
  }


}
