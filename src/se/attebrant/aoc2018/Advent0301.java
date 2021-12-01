package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0301 extends AbstractAdvent {

  public Advent0301() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0301 advent = new Advent0301();
    int result = advent.solve();
    System.out.println("Result: " + result);
  }

  /**
   * <pre>
   * Claims:
   * 
   *  #1 @ 1,3: 4x4
   *  #2 @ 3,1: 4x4
   *  #3 @ 5,5: 2x2
   *  
   *    01234567
   *  0 ........
   *  1 ...2222.
   *  2 ...2222.
   *  3 .11XX22.
   *  4 .11XX22.
   *  5 .111133.
   *  6 .111133.
   *  7 ........
   * </pre>
   */
  private class Claim {

    private String id;
    private int row;
    private int col;
    private int height;
    private int width;


    public Claim(String data) {
      String[] split = data.split("\\s");
      id = split[0].replaceAll("#", "");
      String[] rowcol = split[2].replaceAll(":", "").split(",");
      row = Integer.parseInt(rowcol[0]);
      col = Integer.parseInt(rowcol[1]);
      String[] wh = split[3].split("x");
      height = Integer.parseInt(wh[0]);
      width = Integer.parseInt(wh[1]);
    }

    public boolean coveredBy(int r, int c) {
      if ((r >= row && r < row + height)
          && (c >= col && c < col + width)) {
        return true;
      }
      return false;
    }

    @Override
    public String toString() {
      String text = "#" + id + " @ ";
      text += row + "," + col + ": ";
      text += height + "x" + width;
      return text;
    }
  }

  private int solve() {
    List<String> data = readData3("Advent0301.txt");// getTestData();
    List<Claim> claims = new ArrayList<>();
    for (String string : data) {
      claims.add(new Claim(string));
    }
    int size = 1000; // 8 for test data
    List<List<Integer>> matrix = new ArrayList<>();
    for (int r = 0; r < size; r++) {
      ArrayList<Integer> row = new ArrayList<>();
      matrix.add(row);
      for (int c = 0; c < size; c++) {
        row.add(0);
      }
    }
    // System.out.println("Created claims matrix size " + size);
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        for (Claim claim : claims) {
          if (claim.coveredBy(r, c)) {
            List<Integer> row = matrix.get(r);
            int count = row.get(c) + 1;
            row.set(c, count);
          }
        }
      }
      // System.out.println("Analyzed row " + r);
    }
    int multipleClaimsCount = 0;
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        List<Integer> row = matrix.get(r);
        if (row.get(c) > 1) {
          multipleClaimsCount++;
        }
      }
    }
    return multipleClaimsCount;
  }

  private List<String> getTestData() {
    List<String> data = new ArrayList<>();
    data.add("#1 @ 1,3: 4x4");
    data.add("#2 @ 3,1: 4x4");
    data.add("#3 @ 5,5: 2x2");
    return data;
  }

}
