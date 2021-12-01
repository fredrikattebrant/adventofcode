package se.attebrant.aoc2017;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * <pre>
17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...
 * 
 * ID   R   C Direction to next cell    Steps before turn    
 *  1:  0,  0 RIGHT                     1 1
 *  2:  0,  1 UP                        1 1
 *  3: -1,  1 LEFT                      2 2
 *  4: -1,  0 LEFT (DOWN is blocked)    1
 *  5, -1, -1 DOWN                      2 2
 *  6:  0, -1 DOWN (RIGHT is blocked)   1
 *  7:  1, -1 RIGHT                     3 3
 *  8:  1,  0 RIGHT                     2
 *  9:  1,  1 RIGHT                     1
 * 10:  1,  2 UP                        3 3
 * 11:  0,  2 UP                        2
 * 12: -1,  2 UP                        1
 * 13: -2,  2 LEFT                      4 4
 * 14: -2,  1 LEFT                      3
 * 15: -2,  0 LEFT                      2
 * 16: -2, -1 LEFT                      1
 * 17: -2, -2 DOWN                      4 4
 * 18: -2,  1 DOWN                      3
 * </pre>
 * 
 * @author fredrik
 *
 */
public class Advent0302 {

  private enum Direction {
    RIGHT, UP, LEFT, DOWN;

    private static Direction[] directions = values();

    /**
     * Returns the next direction after the current direction. When the last direction is reached,
     * restarts with the first.
     */
    public Direction next() {
      return directions[(this.ordinal() + 1) % directions.length];
    }
  }

  private class Cell {
    Integer value;
    Integer row; // row offset from cell ID==1
    Integer col; // col offset from cell ID==1

    public Cell(Integer value, Integer row, Integer col) {
      this.value = value;
      this.row = row;
      this.col = col;
    }

    public Integer getValue() {
      return value;
    }

    public Integer getCol() {
      return col;
    }

    public Integer getRow() {
      return row;
    }

    @Override
    public String toString() {
      if (value == null) {
        return "   (--,--)";
      }
      String asString = String.format("%3d(%2d,%2d)", value, row, col);
      // String asString = String.format("%4d", id);
      return asString;
    }
  }

  Map<Integer, Cell> cells = new HashMap<>();
  NegativeList<NegativeList<Cell>> cellsMatrix;

  public static void main(String[] args) {
    Advent0302 advent = new Advent0302();
    advent.run(289326); // fa -> 289326;
    // advent.run(277678); // anonymous -> 277678;

  }

  public void run(Integer numberOfCells) {
    initCells(numberOfCells);
    // printCells();
    // Cell cell = cells.get(numberOfCells);
    // System.out.println("Cell: " + cell + " => " + (cell.getCol() + cell.getRow()));

  }

  private void initCells(Integer numberOfCells) {
    // Size of matrix
    double side = Math.sqrt(numberOfCells) + 1;
    Integer size = (int) (side * 1);
    System.out.println("Size: [" + size + "*" + size + "]");
    cellsMatrix = new NegativeList<>(size);

    Integer stepsUntilTurn = 1;
    Integer stepsInTurn = stepsUntilTurn;
    Integer turnCount = 0;
    Integer row = 0;
    Integer col = 0;
    Direction direction = Direction.RIGHT;
    for (Integer id = 1; id < numberOfCells + 1;) {
      // Create a new cell and put it into the map for easy access:
      // Cell cell = new Cell(id, row, col);
      Integer sumOfAllAdjacent = sumOfAllAdjacent(row, col);
      Cell cell = new Cell(sumOfAllAdjacent, row, col);
      if (sumOfAllAdjacent > numberOfCells) {
        // Found the answer, the first value larger than the puzzle input:
        System.out.println();
        System.out.println("!!! " + sumOfAllAdjacent + " written to cell:" + cell);
        System.out.println();
        return;
      }
      cells.put(id, cell);

      // Maintain the matrix of cells, shadowing the map:
      NegativeList<Cell> rowEntry = cellsMatrix.get(row);
      if (rowEntry == null) {
        rowEntry = new NegativeList<>(size);
        cellsMatrix.set(row, rowEntry);
      }
      Cell colEntry = rowEntry.get(col);
      if (colEntry == null) {
        colEntry = cell;
        rowEntry.set(col, cell);
      }

      // System.out.println("Cell: " + cell);
      if (stepsUntilTurn == 0) {
        // Make a turn
        turnCount++;
        direction = direction.next();
        if (turnCount % 2 == 0) {
          // increment steps in turn for every other turn
          stepsInTurn++;
        }
        stepsUntilTurn = stepsInTurn;
      }
      switch (direction) {
        case RIGHT:
          col++;
          break;
        case UP:
          row--;
          break;
        case LEFT:
          col--;
          break;
        case DOWN:
          row++;
          break;
        default:
          break;
      }
      stepsUntilTurn--;
      id++;
    }
  }

  private Integer sumOfAllAdjacent(int row, int col) {
    if (row == 0 && col == 0) {
      // special case, initialize first cell to 1
      return 1;
    }
    Integer sum = valueOfOrZero(row, col);
    sum += valueOfOrZero(row - 1, col);
    sum += valueOfOrZero(row + 1, col);
    sum += valueOfOrZero(row, col - 1);
    sum += valueOfOrZero(row, col + 1);
    sum += valueOfOrZero(row - 1, col - 1);
    sum += valueOfOrZero(row + 1, col + 1);
    sum += valueOfOrZero(row + 1, col - 1);
    sum += valueOfOrZero(row - 1, col + 1);
    return sum;
  }

  private Integer valueOfOrZero(int row, int col) {
    NegativeList<Cell> rowEntry = cellsMatrix.get(row);
    if (rowEntry == null) {
      return 0;
    }
    Cell colsEntry = rowEntry.get(col);
    if (colsEntry == null) {
      return 0;
    }
    Integer value = colsEntry.getValue();
    return value == null ? 0 : value;
  }

  private void printCells() {
    Map<Integer, Map<Integer, Cell>> matrix = new HashMap<>();
    Integer minRow = Integer.MAX_VALUE;
    Integer minCol = Integer.MAX_VALUE;
    Integer maxRow = Integer.MIN_VALUE;
    Integer maxCol = Integer.MIN_VALUE;
    for (Entry<Integer, Cell> entry : cells.entrySet()) {
      Cell cell = entry.getValue();
      Integer row = cell.getRow();
      Integer col = cell.getCol();
      Map<Integer, Cell> rowEntry = matrix.get(row);
      if (rowEntry == null) {
        rowEntry = new HashMap<>();
      }
      rowEntry.put(col, cell);
      matrix.put(row, rowEntry);
      if (row < minRow) {
        minRow = row;
      }
      if (col < minCol) {
        minCol = col;
      }
      if (col > maxCol) {
        maxCol = col;
      }
      if (row > maxRow) {
        maxRow = row;
      }
    }

    for (Integer row = minRow; row < maxRow + 1; row++) {
      Map<Integer, Cell> colSet = matrix.get(row);
      if (colSet == null) {
        break;
      }
      String separator = "";
      for (Integer col = minCol; col < maxCol + 1; col++) {
        Cell cell = colSet.get(col);
        if (cell == null) {
          System.out.print(new Cell(null, null, null));
        } else {
          System.out.print(separator + cell);
        }
        separator = ",";
      }
      System.out.println();
    }
    System.out.println("Min row: " + minRow);
    System.out.println("Min col: " + minCol);
    System.out.println("Max row: " + maxRow);
    System.out.println("Max col: " + maxCol);

    Iterator<NegativeList<Cell>> iterator = cellsMatrix.iterator();
    while (iterator.hasNext()) {
      NegativeList<Cell> row = iterator.next();
      System.out.println(row);
    }
  }


}
