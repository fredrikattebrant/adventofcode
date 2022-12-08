package se.attebrant.aoc2022;

import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent08 extends AbstractAdvent {

  public Advent08(boolean... debug) {
    super("2022", debug);
  }

  public static void main(String[] args) {
    var advent = new Advent08();
    var dayPart = getDayPart(advent);
    var mode = IS_LIVE;
    print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(mode));
  }

  private int solvePart1(boolean isTest) {
    List<String> trees = readData4(isTest);
    int count = 0;

    int rows = trees.size();
    int cols = trees.get(0).length();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (isVisible(trees, r, c)) {
          count++;
        }
      }
    }

    return count;
  }

  private int solvePart2(boolean isTest) {
    List<String> trees = readData4(isTest);
    int highestScore = 0;

    int rows = trees.size();
    int cols = trees.get(0).length();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int score = getScore(trees, r, c);
        if (score > highestScore) {
          highestScore = score;
        }
      }
    }

    return highestScore;
  }

  private enum Direction {
    UP, LEFT, RIGHT, DOWN,
  }

  private int getScore(List<String> trees, int row, int col) {
    // Iterate over the trees and calculate the "visibility score"
    String treeRow = trees.get(row);
    char tree = treeRow.charAt(col);
    String treeCol = getColumn(trees, col);

    int scoreUp = getViewingDistance(tree, treeCol, row, Direction.UP);
    int scoreLeft = getViewingDistance(tree, treeRow, col, Direction.LEFT);
    int scoreRight = getViewingDistance(tree, treeRow, col, Direction.RIGHT);
    int scoreDown = getViewingDistance(tree, treeCol, row, Direction.DOWN);
    int score = scoreUp * scoreLeft * scoreRight * scoreDown;
    return score;
  }

  private int getViewingDistance(char tree, String trees, int pos, Direction direction) {
    int distance = 0;
    switch (direction) {
      case UP:
      case LEFT:
        for (int i = pos - 1; i >= 0; i--) {
          distance++;
          if (tree <= trees.charAt(i)) {
            break;
          }
        }
        return distance;
      default:
        for (int i = pos + 1; i < trees.length(); i++) {
          distance++;
          if (tree <= trees.charAt(i)) {
            break;
          }
        }
        return distance;
    }
  }

  private boolean isVisible(List<String> trees, int row, int col) {
    String treeRow = trees.get(row);
    char tree = treeRow.charAt(col);
    if (row == 0 || col == 0 || row == treeRow.length() - 1 || col == trees.size() - 1) {
      // Tree is at the edge => visible
      log(tree + ": " + row + ", " + col + " => .... true");
      return true;
    }
    String column = getColumn(trees, col);
    boolean visibleFromLeft = isVisibleFromLeft(treeRow, tree, row, col);
    boolean visibleFromRight = isVisibleFromRight(treeRow, tree, row, col);
    boolean visibleFromTop = isVisibleFromTop(column, tree, row, col);
    boolean visibleFromBottom = isVisibleFromBottom(column, tree, row, col);
    boolean isVisible = visibleFromLeft || visibleFromRight || visibleFromBottom || visibleFromTop;
    String visibleText = (visibleFromLeft ? "L" : ".") + (visibleFromRight ? "R" : ".")
        + (visibleFromTop ? "T" : ".") + (visibleFromBottom ? "B" : ".");
    log(tree + ": " + row + ", " + col + " => " + visibleText + " " + isVisible);
    return isVisible;
  }

  private boolean isVisibleFromLeft(String treeRow, char tree, int row, int col) {
    // Go left from the tree, return if a tree of same or larger height is found
    for (int c = col - 1; c >= 0; c--) {
      char tree2 = treeRow.charAt(c);
      if (tree2 >= tree) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleFromRight(String treeRow, char tree, int row, int col) {
    for (int c = col + 1; c < treeRow.length(); c++) {
      char tree2 = treeRow.charAt(c);
      if (tree2 >= tree) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleFromTop(String treeCol, char tree, int row, int col) {
    // Go left from the tree, return if a tree of same or larger height is found
    for (int r = row - 1; r >= 0; r--) {
      char tree2 = treeCol.charAt(r);
      if (tree2 >= tree) {
        return false;
      }
    }
    return true;
  }

  private boolean isVisibleFromBottom(String treeCol, char tree, int row, int col) {
    for (int r = row + 1; r < treeCol.length(); r++) {
      char tree2 = treeCol.charAt(r);
      if (tree2 >= tree) {
        return false;
      }
    }
    return true;
  }

  private String getColumn(List<String> trees, int col) {
    String column = "";
    for (int r = 0; r < trees.size(); r++) {
      column += trees.get(r).charAt(col);
    }
    return column;
  }
}
