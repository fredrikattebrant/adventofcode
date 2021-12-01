package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import se.attebrant.common.AbstractAdvent;

public class Advent0502 extends AbstractAdvent {

  public Advent0502() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0502 advent = new Advent0502();
    advent.solve();
  }

  private void solve() {
    List<String> polymer = new ArrayList<>();
    String testData = getTestData();
    for (int i = 0; i < testData.length(); i++) {
      polymer.add(testData.charAt(i) + "");
    }

    String unitTypes = getUnitTypes(testData);
    int minLength = Integer.MAX_VALUE;
    char minType = '-';
    for (int i = 0; i < unitTypes.length(); i++) {
      char type = unitTypes.charAt(i);
      List<String> resultingPolymer = reducePolymer(polymer, type);
      int length = resultingPolymer.size();
      if (length < minLength) {
        minLength = length;
        minType = type;
      }
    }
    System.out.println("Result: " + minLength + " for type: " + minType);
  }


  private String getUnitTypes(String rawData) {
    String sorted =
        rawData.toUpperCase(Locale.ENGLISH)
            .chars()
            .sorted()
            .distinct()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    return sorted;
  }

  private List<String> reducePolymer(List<String> polymer, char type) {
    System.out.println("Testing type: " + type);
    List<String> pCopy = new ArrayList<>();
    for (String t : polymer) {
      if (!t.equalsIgnoreCase(type + "")) {
        pCopy.add(t);
      }
    }
    int end = pCopy.size() - 1;
    for (int i = 0; i < end; i++) {
      String c1 = pCopy.get(i);
      String c2 = pCopy.get(i + 1);
      if (!c1.equals(c2) && c1.toLowerCase().equals(c2.toLowerCase())) {
        // c1 differs from c2 but are equal as lower case => we've got a match! Remove both
        // System.out.println("Removing: " + c1 + c2);
        pCopy.remove(i);
        pCopy.remove(i);
        end = end - 2;
        // reset index one step and continue
        i = -1;
      }
    }
    return pCopy;
  }

  private String getTestData() {
    return readData3("Advent0501.txt").get(0);
    // return "dabAcCaCBAcCcaDA";
  }

}
