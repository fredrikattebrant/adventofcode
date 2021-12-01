package se.attebrant.aoc2018;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.attebrant.common.AbstractAdvent;

public class Advent0201 extends AbstractAdvent {

  public Advent0201(String year) {
    super(year);
  }

  public static void main(String[] args) {
    Advent0201 advent = new Advent0201("2018");
    System.out.println("Checksum: " + advent.solve());
  }

  private int solve() {
    // List<String> data = getTestData();
    List<String> data = readData3("Advent0201b.txt");
    int twiceCount = 0;
    int threeCount = 0;
    for (String id : data) {
      if (hasIdentical(id, 2)) {
        twiceCount++;
      }
      if (hasIdentical(id, 3)) {
        threeCount++;
      }
    }
    return twiceCount * threeCount;

  }

  private boolean hasIdentical(String id, int num) {
    Map<Character, Integer> charCountMap = new HashMap<>();
    for (int ix = 0; ix < id.length(); ix++) {
      char c = id.charAt(ix);
      Integer count = charCountMap.get(c);
      if (count == null) {
        charCountMap.put(c, 1);
      } else {
        count++;
        charCountMap.put(c, count);
      }
    }
    Collection<Integer> values = charCountMap.values();
    System.out.println(id + " => " + num);
    return values.contains(num);
  }

  private List<String> getTestData() {
    return Arrays.asList(
        "abcdef",
        "bababc",
        "abbcde",
        "abcccd",
        "aabcdd",
        "abcdee",
        "ababab");
  }
}
