package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import se.attebrant.common.AbstractAdvent;

public class Advent10 extends AbstractAdvent {

  public Advent10(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent10 advent = new Advent10(false);
    boolean useTestInput = false;
    print("Day 10, part 1: " + advent.solvePart1(useTestInput));
    print("Day 10, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent10.txt");
  private List<String> testInput = readData3("Advent10test.txt");

  private long solvePart1(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  private long solvePart2(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  /**
   * Delimiters:
   * 
   * () [] {} <>
   */
  private boolean isClosingDelimiter(char delimiter) {
    return switch (delimiter) {
      case ')' -> true;
      case ']' -> true;
      case '}' -> true;
      case '>' -> true;
      default -> false;
    };
  }

  private boolean isOpeningDelimiter(char delimiter) {
    return switch (delimiter) {
      case '(' -> true;
      case '[' -> true;
      case '{' -> true;
      case '<' -> true;
      default -> false;
    };
  }

  private char getClosingDelimiter(char delimiter) {
    return switch (delimiter) {
      case '(' -> ')';
      case '[' -> ']';
      case '{' -> '}';
      case '<' -> '>';
      default -> '-';
    };
  }

  private char getOpeningDelimiter(char delimiter) {
    return switch (delimiter) {
      case ')' -> '(';
      case ']' -> '[';
      case '}' -> '{';
      case '>' -> '<';
      default -> '-';
    };
  }

  private int getScore(char delimiter) {
    return switch (delimiter) {
      case ')' -> 3;
      case ']' -> 57;
      case '}' -> 1197;
      case '>' -> 25137;
      default -> 0;
    };
  }

  private int getCompletionScore(char delimiter) {
    return switch (delimiter) {
      case ')' -> 1;
      case ']' -> 2;
      case '}' -> 3;
      case '>' -> 4;
      default -> 0;
    };
  }

  private long solve(boolean useTestInput, boolean isPart2) {
    var input = useTestInput ? testInput : puzzleInput;
    return isPart2 ? solve2(input) : solve1(input);
  }

  private long solve1(List<String> input) {
    var errorScore = 0;
    for (String line : input) {
      errorScore += scoreDelimiters(line);
    }

    return errorScore;
  }

  private long solve2(List<String> input) {
    Iterator<String> iter = input.iterator();
    while (iter.hasNext()) {
      String line = iter.next();
      var score = scoreDelimiters(line);
      if (score > 0) {
        iter.remove();
      }
    }

    // Repair remaining lines
    List<Long> autoCompletionScores = new ArrayList<>();
    for (String line : input) {
      autoCompletionScores.add(fixDelimiters(line));
    }
    var sortedScores = autoCompletionScores.stream()
        .sorted()
        .toList();

    var autoCompletionScore = sortedScores.get(sortedScores.size() / 2);
    return autoCompletionScore;
  }

  private int scoreDelimiters(String line) {
    Stack<Character> delimiters = new Stack<>();

    for (int i = 0; i < line.length(); i++) {

      char c = line.charAt(i);

      if (isOpeningDelimiter(c)) {
        delimiters.push(c);
      } else if (isClosingDelimiter(c)) {
        if (delimiters.isEmpty()) {
          log("No matching opening delimiter found at: " + c + " index: " + i);
        } else {
          char opening = delimiters.pop();
          if (opening != getOpeningDelimiter(c)) {
            char expected = getClosingDelimiter(opening);
            log(line + " - Expected " + expected + ", but found " + c + " instead.");
            return getScore(c);
          }
        }
      }
    }
    return 0;
  }

  private long fixDelimiters(String line) {
    Stack<Character> delimiters = new Stack<>();

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (isOpeningDelimiter(c)) {
        delimiters.push(c);
      } else if (isClosingDelimiter(c)) {
        if (delimiters.isEmpty()) {
          log("No matching opening delimiter found at: " + c + " index: " + i);
        } else {
          char opening = delimiters.pop();
          if (opening != getOpeningDelimiter(c)) {
            log("Should not happen - found opening " + opening);
          }
        }
      }
    }

    // Repairs
    long score = 0;
    String fixes = "";
    while (!delimiters.isEmpty()) {
      score = score * 5;
      char fix = getClosingDelimiter(delimiters.pop());
      score += getCompletionScore(fix);
      fixes += fix;
    }
    log("Line:  " + line);
    log("Fixes: " + fixes);
    log("Score: " + score);
    log("");

    return score;
  }


}
