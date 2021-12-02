package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0401 extends AbstractAdvent {

  public Advent0401(boolean... debug) {
    super("2017", debug);
  }

  public static void main(String[] args) {
    new Advent0401(true).solve();
  }

  private void solve() {
    List<List<String>> data = readData2(
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2017/Advent0401FA.txt");
    // "/Users/fredrik/dev/workspaces/adventofcode/adventofcode2017/src/adventofcode2017/Advent0401.txt");

    // data = getTestData();
    int validCount = 0;
    for (List<String> wordsData : data) {
      String[] wordsArray = wordsData.get(0).split(" ");
      List<String> sortedWords = Arrays.asList(wordsArray).stream()
          .sorted()
          .collect(Collectors.toList());
      String lastWord = null;
      boolean foundDuplicate = false;
      for (String word : sortedWords) {
        foundDuplicate = word.equals(lastWord);
        if (foundDuplicate) {
          break;
        }
        lastWord = word;
      }
      if (foundDuplicate) {
        System.out.println("*** " + wordsData);
      } else {
        // System.out.println("+++ " + wordsData);
        validCount++;
      }
    }

    System.out.println("Number of valid passphrases are: " + validCount);


  }

  private static List<List<String>> getTestData() {
    List<String> line1 = Arrays.asList("aa", "bb", "cc", "dd", "ee");
    List<String> line2 = Arrays.asList("aa", "bb", "cc", "dd", "aa");
    List<String> line3 = Arrays.asList("aa", "bb", "cc", "dd", "aaa");
    List<List<String>> data = new ArrayList<>();
    data.add(line1);
    data.add(line2);
    data.add(line3);
    return data;
  }

}
