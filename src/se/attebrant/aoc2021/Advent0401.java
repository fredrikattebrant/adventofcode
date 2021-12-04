package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0401 extends AbstractAdvent {

  public Advent0401(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent0401 advent = new Advent0401(true);
    print("Day 04, part 1: " + advent.solvePart1());
    print("Day 04, part 2: " + advent.solvePart2());
  }

  class Board {
    // 5 x 5
    List<List<String>> grid = new ArrayList<>();
    static final String EMPTY = "     ";
    static final String WINNER = "11111";
    List<String> markedRows = new ArrayList<>(Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
    boolean isWinning = false;
    /**
     * Mark in markedRows/markedCols by bits (00000 -> 11111) So 11111 (or 0x1F == 31) is a win!
     */
    void markCell(int row, int col) {
      String rowVal = markedRows.get(row);
      rowVal = rowVal.substring(0, col) + "1" + rowVal.substring(col + 1);
      markedRows.set(row, rowVal);
    }

    void mark(String marker) {
      for (int r = 0; r < grid.size(); r++) {
        List<String> row = grid.get(r);
        for (int c = 0; c < 5; c++) {
          String cell = row.get(c);
          if (cell.equals(marker)) {
            markCell(r, c);
          }
        }
      }
    }

    void printMarks() {
      log("Marked rows:");
      for (int r = 0; r < 5; r++) {
        log(markedRows.get(r));
      }
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      for (List<String> row : grid) {
        for (String col : row) {
          text.append(col);
          text.append(", ");
        }
        text.append("\n");
      }
      return text.toString();
    }

    boolean hasWon() {
      if (isWinning) {
        // Only report victory first time!
        return false;
      }
      if (markedRows.contains(WINNER)) {
        isWinning = true;
        return true;
      }
      for (int c = 0; c < 5; c++) {
        final int col = c;
        boolean columnWinner = markedRows.stream()
            .map(row -> row.charAt(col))
            .filter(m -> m == ' ')
            .findFirst()
            .isEmpty();
        if (columnWinner) {
          isWinning = true;
          return true;
        }
      }
      return false;
    }

    int getScore() {
      // Get the sum of all unmarked numbers
      // Check marked rows
      log("Get score:");
      int score = 0;
      for (int r = 0; r < 5; r++) {
        String rowVal = markedRows.get(r);
        for (int c = 0; c < 5; c++) {
          if (rowVal.charAt(c) == ' ') {
            score += Integer.parseInt(grid.get(r).get(c));
          }
        }

      }
      return score;
    }
  }

  class Data {
    List<String> numbers = new ArrayList<>();
    List<Board> boards = new ArrayList<>();
    int theNumber = -1;
  }

  private int solvePart1() {
    return solve(true);
  }

  private int solvePart2() {
    return solve(false);
  }

  private int solve(boolean firstWins) {
    Data data = getData();
    int score = draw(data, firstWins);
    log("Number: " + data.theNumber);
    log("Score : " + score);
    return score * data.theNumber;
  }

  private int draw(Data data, boolean firstWin) {
    // Draw numbers
    int drawCount = 1;
    Board lastWinning = null;
    int winningNumber = -1;
    for (String number : data.numbers) {
      log("Drawing number (turn " + drawCount + ") : " + number);
      // Check boards
      for (Board board : data.boards) {
        if (!board.isWinning) {
          board.mark(number);
        }
        if (!board.isWinning && board.hasWon()) {
          lastWinning = board;
          winningNumber = Integer.parseInt(number);
          data.theNumber = winningNumber;
          if (firstWin) {
            return reportWinner(board);
          }
        }
      }
      drawCount++;
    }
    if (lastWinning != null) {
      return reportWinner(lastWinning);
    }
    return -1;
  }

  private int reportWinner(Board board) {
    log("We have a winner: \n" + board);
    board.printMarks();
    return board.getScore();
  }

  private Data getData() {
    Data data = new Data();
    boolean first = true;
    Board board = new Board();
    for (String input : readData()) {
      if (first) {
        first = false;
        data.numbers = Arrays.asList(input.split(","));
      } else if (input.length() == 0) {
        if (!board.grid.isEmpty()) {
          data.boards.add(board);
        }
        board = new Board();
      } else {
        List<String> numbers = Arrays.asList(input.trim().split("\s+"));
        board.grid.add(numbers);
      }
    }
    data.boards.add(board);
    return data;

  }


  private List<String> readData() {
    return readData3("Advent04.txt").stream()
        // return readData3("Advent04test.txt").stream()
        .toList();
  }

}
