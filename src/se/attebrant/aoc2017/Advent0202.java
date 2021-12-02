package se.attebrant.aoc2017;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

/**
 * <pre>
 * 5 9 2 8
 * 9 4 7 3
 * 3 8 6 5
 * In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
 * In the second row, the two numbers are 9 and 3; the result is 3.
 * In the third row, the result is 2.
 * In this example, the sum of the results would be 4 + 3 + 2 = 9.
 * </pre>
 * 
 * @author fredrik
 *
 */
public class Advent0202 extends AbstractAdvent {

  public Advent0202(boolean... debug) {
    super("2017", debug);
  }

  public static void main(String[] args) {
    Advent0202 advent0202 = new Advent0202(true);
    advent0202.solve();
  }

  private void solve() {
    List<List<String>> data = readData2(
        "/Users/fredrik/git/adventofcode/src/se/attebrant/aoc2017/Advent0201.txt");
    // "/Users/fredrik/dev/workspaces/adventofcode/adventofcode2017/src/adventofcode2017/Advent0201.txt");
    // data.forEach(row -> row.stream().forEach(System.out::println));

    List<Integer> input1 = Arrays.asList(5, 9, 2, 8);
    List<Integer> input2 = Arrays.asList(9,4,7,3);
    List<Integer> input3 = Arrays.asList(3,8,6,5);
    test2(this::rowDivideEven, input1, 4);
    test2(this::rowDivideEven, input2, 3);
    test2(this::rowDivideEven, input3, 2);

    System.out.print("My answer: ");
    Integer totalDelta = 0;
    for (List<String> row : data) {
      String rowData = row.get(0);
      List<String> rowSplit = Arrays.asList(rowData.split("\\s"));
      Integer delta =
          rowDivideEven(rowSplit.stream()
              .filter(s -> !s.isEmpty())
              .map(Integer::parseInt)
              .collect(Collectors.toList()));
      totalDelta = totalDelta + delta;
      // System.out.println("Delta: " + delta + " : " + totalDelta);
    }
    System.out.println(totalDelta);
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

  public Integer rowDivideEven(List<Integer> numbers) {
    List<Integer> sorted =
        numbers.stream()
            .sorted((i1, i2) -> Integer.compare(i2, i1))
            .toList();
    for (int yx = 0; yx < sorted.size() - 1; yx++) {
      Integer first = sorted.get(yx);
      for (int ix = yx+1; ix < sorted.size(); ix++) {
        Integer second = sorted.get(ix);
        if (Integer.remainderUnsigned(first, second) == 0) {
          return first / second;
        }
      }
    }    
    // none found - what to do???
    log("*** No match found in: " + numbers);
    return 0;
  
  }

}
