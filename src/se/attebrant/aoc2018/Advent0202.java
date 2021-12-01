package se.attebrant.aoc2018;

import java.util.Arrays;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0202 extends AbstractAdvent {

  public Advent0202(String year) {
    super(year);
  }

  public static void main(String[] args) {
    Advent0202 advent = new Advent0202("2018");
    String result = advent.solve();
    System.out.println("Common letters: ");
    System.out.println(result);
  }

  private String solve() {
    String result = "";
    // List<String> data = getTestData();
    List<String> data = readData3("Advent0201b.txt");
    for (int ix1 = 0; ix1 < data.size() - 1; ix1++) {
      String word1 = data.get(ix1);
      for (int ix2 = ix1 + 1; ix2 < data.size(); ix2++) {
        String word2 = data.get(ix2);
        int indexOfDifference = doCompare(word1, word2);
        if (indexOfDifference != -1) {
          String common =
              word1.substring(0, indexOfDifference) + word1.substring(indexOfDifference + 1);
          result += "[" + word1 + ", " + word2 + " => " + common + "]";
        }
      }
    }
    return result;
  }

  private int doCompare(String word1, String word2) {
    int diffCount = 0;
    int diffIndex = -1;
    for (int i = 0; i < word1.length(); i++) {
      char c1 = word1.charAt(i);
      char c2 = word2.charAt(i);
      if (c1 != c2) {
        diffCount++;
        diffIndex = i;
      }
    }
    return diffCount == 1 ? diffIndex : -1;
  }

  private List<String> getTestData() {
    return Arrays.asList(
        "xbcce",
        "xbbde",
        "abcde",
        "fghij",
        "klmno",
        "pqrst",
        "fguij",
        "axcye",
        "wvxyz");
  }
}
