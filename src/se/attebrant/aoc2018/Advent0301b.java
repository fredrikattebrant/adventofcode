package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0301b extends AbstractAdvent {

  public Advent0301b() {
    super("2018");
    if (System.getProperty("os.name").contains("Windows")) {
    baseDir =
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2018/";
    }
  }

  public static void main(String[] args) {
    Advent0301b advent = new Advent0301b();
    Set<String> result = advent.solve();
    System.out.println("Result: " + result.stream().collect(Collectors.joining(",")));
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

  // What is the ID of the only claim that doesn't overlap?
  private Set<String> solve() {
    // Edit to change between test and puzzleInput:
    int size = 1000; // 8 for test data
    List<String> puzzleInput = readData3("Advent0301.txt");

    // Let's solve this:
    for (String entry : puzzleInput) {
      Claim claim = new Claim(entry);
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

    // Create a size x size zero initialized matrix:
    List<List<Integer>> matrix = new ArrayList<>();
    for (int r = 0; r < size; r++) {
      ArrayList<Integer> row = new ArrayList<>();
      matrix.add(row);
      for (int c = 0; c < size; c++) {
        row.add(0);
      }
    }


    // TODO fix this broken algorithm...
    Set<String> disqualifiedClaims = new HashSet<>();
    Set<String> claimsWithNoOverlap = new HashSet<>();
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        String rc = getRc(r, c);
        Set<String> claimIdsAtRc = rcIdMap.get(rc);
        if (claimIdsAtRc == null) {
          // No claim - ignore
          continue;
        }
        if (claimIdsAtRc.size() == 1) {
          String claimId = claimIdsAtRc.iterator().next();
          if (disqualifiedClaims.contains(claimId)) {
            // already disqualified
            continue;
          }
          // Only one claim - add claim id for now
          claimsWithNoOverlap.add(claimId);
        } else if (claimIdsAtRc.size() > 1) {
          // More than one claim - remove this claim id
          String disqualifiedClaim = claimIdsAtRc.iterator().next();
          claimsWithNoOverlap.remove(disqualifiedClaim);
          disqualifiedClaims.add(disqualifiedClaim);
        }
      }
    }

    return claimsWithNoOverlap;

  }

  private List<String> readData3() {
    List<String> data = new ArrayList<>();
    data.add("#1 @ 1,3: 4x4");
    data.add("#2 @ 3,1: 4x4");
    data.add("#3 @ 5,5: 2x2");
    return data;
  }

}
