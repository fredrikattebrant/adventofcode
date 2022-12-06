package se.attebrant.aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import se.attebrant.common.AbstractAdvent;

public class Advent05 extends AbstractAdvent {

  public Advent05() {
    super("2022");
  }

  public static void main(String[] args) {
    var advent = new Advent05();
    var dayPart = getDayPart(advent);
    print("Result " + dayPart + " part 1: " + advent.solvePart1(IS_LIVE));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(IS_LIVE));
  }

  private String solvePart1(boolean isTest) {
    return parseInput1(readData(isTest));
  }

  private String solvePart2(boolean isTest) {
    return parseInput2(readData(isTest));
  }

  private class Instruction {
    int numberOfCrates;
    int fromStack;
    int toStack;

    public Instruction(String instruction) {
      String split[] = instruction.split(" ");
      numberOfCrates = Integer.parseInt(split[1]);
      fromStack = Integer.parseInt(split[3]);
      toStack = Integer.parseInt(split[5]);
    }

    @Override
    public String toString() {
      return "move " + numberOfCrates + " from " + fromStack + " to " + toStack;
    }
  }

  private String parseInput1(List<String> input) {
    List<Stack<String>> stacks = new ArrayList<>();

    List<String> stackLines = new ArrayList<>();
    List<Instruction> instructions = new ArrayList<>();

    boolean parsingStackLines = true;
    int numberOfStacks = 0;
    for (String line : input) {
      if (line.isBlank()) {
        continue;
      } else if (parsingStackLines && line.contains("[")) {
        stackLines.add(line);
        continue;
      } else if (parsingStackLines) {
        // The stack index line
        parsingStackLines = false;
        String split[] = line.split(" ");
        numberOfStacks = Integer.parseInt(split[split.length - 1].trim());
        continue;
      }
      // Parsing instructions
      Instruction instruction = new Instruction(line);
      instructions.add(instruction);
    }
    for (int i = 0; i < numberOfStacks; i++) {
      stacks.add(new Stack<>());
    }

    /**
     * Create 'numberOfStacks' stacks from the input There are stackLines.size() lines to parse and
     * to place into the stacks
     */
    for (int i = stackLines.size() - 1; i >= 0; i--) {
      String stackLine = stackLines.get(i);
      int length = stackLine.length();
      int stackIx = 0;
      for (int j = 1; j < length; j += 4) {
        Stack<String> stack = stacks.get(stackIx++);
        String crate = stackLine.charAt(j) + "";
        System.out.println(j + " -> Create = [" + crate + "]");
        System.out.println();
        if (!crate.isBlank()) {
          stack.add(crate);
        }
      }
    }
    System.out.println("Start processing instructions");
    for (var instruction : instructions) {
      System.out.println("+++ " + instruction);
      int from = instruction.fromStack;
      int to = instruction.toStack;
      int numberOfCrates = instruction.numberOfCrates;
      var fromStack = stacks.get(from - 1);
      var toStack = stacks.get(to - 1);
      for (int j = 0; j < numberOfCrates; j++) {
        String crate = fromStack.pop();
        toStack.push(crate);
      }
    }
    System.out.println("On top of each stack:");
    String result = "";
    for (int i = 0; i < stacks.size(); i++) {
      var stack = stacks.get(i);
      result += stack.peek();
    }
    return result;
  }

  private String parseInput2(List<String> input) {
    List<Stack<String>> stacks = new ArrayList<>();

    List<String> stackLines = new ArrayList<>();
    List<Instruction> instructions = new ArrayList<>();

    boolean parsingStackLines = true;
    int numberOfStacks = 0;
    for (String line : input) {
      if (line.isBlank()) {
        continue;
      } else if (parsingStackLines && line.contains("[")) {
        stackLines.add(line);
        continue;
      } else if (parsingStackLines) {
        // The stack index line
        parsingStackLines = false;
        String split[] = line.split(" ");
        numberOfStacks = Integer.parseInt(split[split.length - 1].trim());
        continue;
      }
      // Parsing instructions
      Instruction instruction = new Instruction(line);
      instructions.add(instruction);
    }
    for (int i = 0; i < numberOfStacks; i++) {
      stacks.add(new Stack<>());
    }

    /**
     * Create 'numberOfStacks' stacks from the input There are stackLines.size() lines to parse and
     * to place into the stacks
     */
    for (int i = stackLines.size() - 1; i >= 0; i--) {
      String stackLine = stackLines.get(i);
      int length = stackLine.length();
      int stackIx = 0;
      for (int j = 1; j < length; j += 4) {
        Stack<String> stack = stacks.get(stackIx++);
        String crate = stackLine.charAt(j) + "";
        System.out.println(j + " -> Create = [" + crate + "]");
        System.out.println();
        if (!crate.isBlank()) {
          stack.add(crate);
        }
      }
    }
    System.out.println("Start processing instructions");
    for (var instruction : instructions) {
      System.out.println("+++ " + instruction);
      int from = instruction.fromStack;
      int to = instruction.toStack;
      int numberOfCrates = instruction.numberOfCrates;
      var fromStack = stacks.get(from - 1);
      var toStack = stacks.get(to - 1);
      // Pop of multiple crates but reverse to retain their order
      Stack<String> poppedCrates = new Stack<>();
      for (int j = 0; j < numberOfCrates; j++) {
        String crate = fromStack.pop();
        poppedCrates.push(crate);
      }
      for (int j = 0; j < numberOfCrates; j++) {
        String crate = poppedCrates.pop();
        toStack.push(crate);
      }
      System.out.println();
    }
    System.out.println("On top of each stack:");
    String result = "";
    for (int i = 0; i < stacks.size(); i++) {
      var stack = stacks.get(i);
      result += stack.peek();
    }
    return result;
  }

  private List<String> readData(boolean isTest) {
    var fileName = "Advent05" + (isTest ? "test" : "") + ".txt";
    return readData3(fileName).stream().toList();
  }

}
