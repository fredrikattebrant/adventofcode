package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent0302 extends AbstractAdvent {

  public Advent0302() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0302 advent = new Advent0302();
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

  private Map<String, Set<String>> rcIdMap = new HashMap<>();
  private Map<String, Claim> idClaimMap = new HashMap<>();

  private String getRc(int r, int c) {
    return r + "," + c;
  }

  private int solve() {
    List<String> puzzleInput = readData3("Advent0301.txt");
    List<Claim> claims = new ArrayList<>();
    for (String entry : puzzleInput) {
      Claim claim = new Claim(entry);
      claims.add(claim);
      idClaimMap.put(claim.id, claim);
      int row = claim.row;
      int col = claim.col;
      for (int r = row; r < row + claim.height; r++) {
        for (int c = col; c < col + claim.width; c++) {
          String rc = getRc(r, c);
          Set<String> claimIds = rcIdMap.get(rc);
          if (claimIds == null) {
            claimIds = new HashSet<>();
          }
          claimIds.add(claim.id);
          rcIdMap.put(rc, claimIds);
        }
      }
    }

    Set<String> overlappingClaimIds = new HashSet<>();
    for (Set<String> claimIds : rcIdMap.values()) {
      if (claimIds.size() > 1) {
        overlappingClaimIds.addAll(claimIds);
      }
    }

    Set<String> claimIds = idClaimMap.keySet();
    Set<String> nonOverlappingClaimIds = new HashSet<>(claimIds);
    nonOverlappingClaimIds.removeAll(overlappingClaimIds);

    if (nonOverlappingClaimIds.size() == 1) {
      return Integer.parseInt(nonOverlappingClaimIds.iterator().next());
    }

    System.err.println("*** Multiple non overlapping claim IDs: " + nonOverlappingClaimIds);
    return -1;

  }

  private List<String> readData3() {
    List<String> data = new ArrayList<>();
    data.add("#1 @ 1,3: 4x4");
    data.add("#2 @ 3,1: 4x4");
    data.add("#3 @ 5,5: 2x2");
    return data;
  }

}
