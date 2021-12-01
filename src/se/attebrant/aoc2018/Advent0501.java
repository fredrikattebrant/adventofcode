package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0501 extends AbstractAdvent {

  public Advent0501() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0501 advent = new Advent0501();
    advent.solve();
  }

  private void solve() {
    List<String> polymer = new ArrayList<>();
    String testData = getTestData();
    for (int i = 0; i < testData.length(); i++) {
      polymer.add(testData.charAt(i) + "");
    }
    int end = testData.length() - 1;
    for (int i = 0; i < end; i++) {
      String c1 = polymer.get(i);
      String c2 = polymer.get(i + 1);
      if (!c1.equals(c2) && c1.toLowerCase().equals(c2.toLowerCase())) {
        // c2 differs from c2 but are equal as lower case => we've got a match! Remove both
        // System.out.println("Removing: " + c1 + c2);
        polymer.remove(i);
        polymer.remove(i);
        end = end - 2;
        // reset index one step and continue
        i = -1;
      }
    }
    // System.out.println("Result: " + polymer.stream().collect(Collectors.joining("")));
    System.out.println("Result: " + polymer.size());
  }


  private String getTestData() {
    return readData3("Advent0501.txt").get(0);
    // return "dabAcCaCBAcCcaDA";
  }

}
