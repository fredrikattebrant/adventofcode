package se.attebrant.aoc2025;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent02 extends AbstractAdvent {

  public Advent02(boolean isTest) {
    super("2025", isTest);
  }

  public static void main(String[] args) throws IOException {
    var isTest = false;
    Advent02 advent = new Advent02(isTest);
    advent.debug = false;
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, false, isTest));
    print("Result day " + day + ", part2: " + advent.solve(day, true, isTest));
  }

  private long solve(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));
    log("");
    List<String> input = readData(day, false, isTest);
    // only one line, comma separated:
    String[] ranges = input.get(0).split(",");
    Set<String> invalidIds = new HashSet<>();
    for (String range : ranges) {
      String[] split = range.split("-");
      String firstId = split[0];
      String lastId = split[1];
      invalidIds.addAll(findInvalidIds(isPart2, firstId, lastId));
    }

    // Convert the invalid IDs to longs
    // Add all invalid IDs
    long sum = 0;
    for (String id : invalidIds) {
      // log("Invalid: " + id);
      sum += Long.parseLong(id);
    }

    return sum;
  }

  Collection<String> findInvalidIds(boolean isPart2, String firstId, String lastId) {
    Set<String> invalidIds = new HashSet<>();

    long first = Long.parseLong(firstId);
    long last = Long.parseLong(lastId);

    // Iterate from first to last id
    for (long id = first; id <= last; id++) {
      if (isPart2) {
        invalidIds.addAll(isInvalidIdPart2(id));
      } else if (isInvalidId(id, 2)) {
        invalidIds.add(id + "");
      }
    }

    return invalidIds;
  }

  private Set<String> isInvalidIdPart2(long idIn) {
    // Invalid if sequence is repeated at least twice, so could be repeated 2, 3, 4 .. times
    // Start by splitting in 2, if not invalid, split in 3 and so on
    Set<String> invalidIds = new HashSet<>();
    String id = idIn + "";
    int len = id.length();
    int splitBy = 2;
    boolean cont = true;
    do {
      if (isInvalidId(idIn, splitBy)) {
        invalidIds.add(id);
      }
      splitBy += 1;
      if (splitBy > len) {
        cont = false;
      }
    } while (cont);


    return invalidIds;
  }

  boolean isInvalidId(long idIn, int splitBy) {
    String id = idIn + "";
    int len = id.length();
    int partSize = len / splitBy;
    if (len / splitBy * splitBy == len) {
      // Can split into splitBy parts
      // Split into parts and check if identical
      // Split by 3, part size 6/3=2
      // 565656 => 56 56 56
      // 01 23 45
      String first = id.substring(0, len / splitBy);
      for (int ix = partSize; ix < len; ix += partSize) {
        String next = id.substring(ix, ix + partSize);
        if (!first.equals(next)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

}
