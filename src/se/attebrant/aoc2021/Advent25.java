package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent25 extends AbstractAdvent {

  public Advent25(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent25 advent = new Advent25(true);
    boolean useTestInput = false;
    print("Day 25, part 1: " + advent.solvePart1(useTestInput));
    // print("Day 25, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent25.txt");
  private List<String> testInput = readData3("Advent25test.txt");

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, false);
  }

  class SeaFloor {

    char[][] floor;
    int rows;
    int cols;

    public SeaFloor(List<String> input) {
      rows = input.size();
      cols = input.get(0).length();
      floor = new char[rows][cols];
      for (int r = 0; r < rows; r++) {
        String row = input.get(r).trim();
        if (row.isBlank()) {
          continue;
        }
        for (int c = 0; c < cols; c++) {
          floor[r][c] = row.charAt(c);
        }
      }
    }

    public boolean step() {
      boolean changed = step(true);
      changed |= step(false);
      return changed;
    }

    class Move {
      int fromRow;
      int fromCol;
      int toRow;
      int toCol;

      Move(int fromRow, int fromCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
      }
    }

    public boolean step(boolean moveEast) {
      List<Move> moves = new ArrayList<>();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          char cucumber = floor[r][c];
          if (moveEast && cucumber == '>' || !moveEast && cucumber == 'v') {
            Move move = new Move(r, c);
            if (canMove(moveEast, r, c, move)) {
              moves.add(move);
            }
          }
        }
      }
      for (Move move : moves) {
        floor[move.toRow][move.toCol] = floor[move.fromRow][move.fromCol];
        floor[move.fromRow][move.fromCol] = '.';
      }
      return !moves.isEmpty();
    }


    public boolean canMove(boolean moveEast, int row, int col, Move move) {
      if (moveEast) {
        if (col < cols - 1) {
          if (floor[row][col + 1] == '.') {
            move.toRow = row;
            move.toCol = col + 1;
            return true;
          }
          return false;
        }
        if (floor[row][0] == '.') {
          move.toRow = row;
          move.toCol = 0;
          return true;
        }
        return false;
      } else /* mouthSouth */ {
        if (row < rows - 1) {
          if (floor[row + 1][col] == '.') {
            move.toRow = row + 1;
            move.toCol = col;
            return true;
          }
          return false;
        }
        if (floor[0][col] == '.') {
          move.toRow = 0;
          move.toCol = col;
          return true;
        }
        return false;
      }
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          text.append(floor[r][c]);
        }
        text.append("\n");
      }
      text.append("\n");
      return text.toString();
    }

  }

  private int solve(boolean useTestInput, boolean isPart1) {
    var input = useTestInput ? testInput : puzzleInput;
    SeaFloor seaFloor = new SeaFloor(input);
    return isPart1 ? solve1(seaFloor) : solve2(seaFloor);
  }

  private int solve1(SeaFloor seaFloor) {
    log(seaFloor);
    for (int step = 1; step <= 1_000_000; step++) {
      if (!seaFloor.step()) {
        log(seaFloor);
        return step;
      }
    }
    // Error
    return -1;
  }

  private int solve2(SeaFloor seaFloor) {
    return -1;
  }

}
