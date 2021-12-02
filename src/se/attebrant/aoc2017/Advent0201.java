package se.attebrant.aoc2017;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

/**
 * <pre>
 * 5 1 9 5
 * 7 5 3
 * 2 4 6 8
 * The first row's largest and smallest values are 9 and 1, and their difference is 8.
 * The second row's largest and smallest values are 7 and 3, and their difference is 4.
 * The third row's difference is 6.
 * In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
 * </pre>
 * 
 * @author fredrik
 *
 */
public class Advent0201 extends AbstractAdvent {

  public Advent0201(boolean... debug) {
    super("2017", debug);
  }

  public static void main(String[] args) {
    Advent0201 advent0201 = new Advent0201(true);
    advent0201.solve();
  }

  private void solve() {
    List<List<String>> data = readData2(
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2017/Advent0201.txt");
    // data.forEach(row -> row.stream().forEach(System.out::println));

    List<Integer> input1 = Arrays.asList(5, 1, 9, 5);
    List<Integer> input2 = Arrays.asList(7, 5, 3);
    List<Integer> input3 = Arrays.asList(2, 4, 6, 8);
    test2(this::rowMaxMinDiff, input1, 8);
    test2(this::rowMaxMinDiff, input2, 4);
    test2(this::rowMaxMinDiff, input3, 6);

    log("My answer: ");
    Integer totalDelta = 0;
    for (List<String> row : data) {
      String rowData = row.get(0);
      List<String> rowSplit = Arrays.asList(rowData.split("\\s"));
      Integer delta =
          rowMaxMinDiff(rowSplit.stream()
              .filter(s -> !s.isEmpty())
              .map(Integer::parseInt)
              .collect(Collectors.toList()));
      totalDelta = totalDelta + delta;
      // log("Delta: " + delta + " : " + totalDelta);
    }
    log(totalDelta);
  }

  private void test2(Function<List<Integer>, Integer> fun, List<Integer> input1,
      int expected) {
    int sum = fun.apply(input1);
    if (sum == expected) {
      log("+++ " + input1 + " => " + expected);
    } else {
      log("--- " + input1 + " => " + expected);
    }

  }

  public Integer rowMaxMinDiff(List<Integer> numbers) {
    Integer min = Integer.MAX_VALUE;
    Integer max = 0;
    for (Integer num : numbers) {
      if (num < min) {
        min = num;
      }
      if (num > max) {
        max = num;
      }
    }
    // log("Min: " + min + ", max: " + max);
    return max - min;
  }

}
