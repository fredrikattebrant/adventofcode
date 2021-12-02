package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent0402 extends AbstractAdvent {

  public Advent0402(boolean... debug) {
    super("2017", debug);
  }

  public static void main(String[] args) {
    new Advent0402(false).solve();
  }

  private void solve() {
    List<List<String>> data = readData2(
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2017/Advent0401FA.txt");
    // "/Users/fredrik/dev/workspaces/adventofcode/adventofcode2017/src/adventofcode2017/Advent0401.txt");

    // List<List<String>> data = getTestData();
    int validCount = 0;
    for (List<String> wordsData : data) {
      String[] wordsArray = wordsData.get(0).split(" ");
      List<String> sortedWords = Arrays.asList(wordsArray).stream()
          .sorted()
          .collect(Collectors.toList());
      int startIx = 1;
      boolean foundMatch = false;
      for (String currentWord : sortedWords.subList(0, sortedWords.size() - 1)) {
        for (String word : sortedWords.subList(startIx, sortedWords.size())) {
          if (containsSameChars(word, currentWord)) {
            foundMatch = true;
            break;
          }
        }
        if (foundMatch) {
          break;
        }
        startIx++;
      }
      if (foundMatch) {
        log("*** " + wordsData);
      } else {
        log("+++ " + wordsData);
        validCount++;
      }
    }

    System.out.println("Number of valid passphrases are: " + validCount);


  }

  private boolean containsSameChars(String w1, String w2) {
    // log("Matching: " + w1 + " with: " + w2);
    if (w1 == null || w2 == null) {
      return false;
    }
    List<Character> w1Chars = stringToList(w1);
    List<Character> w2Chars = stringToList(w2);
    Iterator<Character> iter = w1Chars.iterator();
    while (iter.hasNext()) {
      Character next = iter.next();
      if (!w2Chars.contains(next)) {
        return false;
      }
      w2Chars.remove(next);
    }
    // return w2Chars.isEmpty();
    if (w2Chars.isEmpty()) {
      log(w1 + " matches " + w2);
      return true;
    } ;
    return false;
  }

  private List<Character> stringToList(String s) {
    return s.chars()
        .mapToObj(e -> (char) e)
        .collect(Collectors.toList());
  }

  /**
   * <pre>
  abcde fghij is a valid passphrase.
  abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
  a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
  iiii oiii ooii oooi oooo is valid.
  oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
   * </pre>
   */
  private List<List<String>> getTestData() {
    List<List<String>> data = new ArrayList<>();
    data.add(Arrays.asList("abcde fghij"));
    data.add(Arrays.asList("abcde xyz ecdab"));
    data.add(Arrays.asList("a ab abc abd abf abj"));
    data.add(Arrays.asList("iiii oiii ooii oooi oooo"));
    data.add(Arrays.asList("oiii ioii iioi iiio"));
    data.add(Arrays.asList("inc mpys mzqmcwx vryz ibqrzc pmsy fat rojpxwy rcbqzi gjef"));
    return data;
  }

}
