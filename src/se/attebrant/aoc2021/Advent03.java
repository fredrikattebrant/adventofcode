package se.attebrant.aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import se.attebrant.common.AbstractAdvent;

public class Advent03 extends AbstractAdvent {

  public Advent03(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent03 advent = new Advent03(true);
    print("Day 03, part 1: " + advent.solvePart1());
    print("Day 03, part 2: " + advent.solvePart2());
  }

  class Data {
    List<String> numbers;
    int[] zeroCount;
    int[] onesCount;
    int length;
  }

  private int solvePart1() {
    Data data = getData();
    int[] zeroCount = data.zeroCount;
    int[] onesCount = data.onesCount;
    int length = data.length;
    int gamma = 0;
    int epsilon = 0;
    for (int i = 0; i < length; i++) {
      int gammaAtIx = (onesCount[i] > zeroCount[i] ? 1 : 0);
      int epsilonAtIx = (onesCount[i] < zeroCount[i] ? 1 : 0);
      int bit = length - 1 - i;
      int gammaDelta = gammaAtIx << bit;
      int epsilonDelta = epsilonAtIx << bit;
      gamma += gammaDelta;
      epsilon += epsilonDelta;
    }
    return gamma * epsilon;
  }

  private int solvePart2() {
    Data bitCount = getBitCount(readData());
    int oxygenVal = getRating(bitCount, false);
    int co2val = getRating(bitCount, true);
    return oxygenVal * co2val;
  }

  private int getRating(Data bitCount, boolean useLessThan) {
    List<String> data = new ArrayList<>(bitCount.numbers);
    for (int i = 0; i < bitCount.length; i++) {
      bitCount = getBitCount(data);
      int zeroCount = bitCount.zeroCount[i];
      int onesCount = bitCount.onesCount[i];
      boolean keepZeros = useLessThan ? zeroCount <= onesCount : zeroCount > onesCount;
      ListIterator<String> iter = data.listIterator();
      while (iter.hasNext()) {
        String number = iter.next();
        int bitIndex = i;
        char bit = number.charAt(bitIndex);
        if (keepZeros && bit == '1' || !keepZeros && bit == '0') {
          iter.remove();
        }
      }
      if (data.size() == 1) {
        break;
      }
    }
    return Integer.parseInt(data.get(0), 2);
  }

  private Data getData() {
    return getBitCount(readData());
  }

  private Data getBitCount(List<String> input) {
    int length = input.get(0).length();
    int[] zeroCount = new int[length];
    int[] onesCount = new int[length];
    for (String entry : input) {
      for (int i = 0; i < length; i++) {
        char bit = entry.charAt(i);
        zeroCount[i] += bit == '0' ? 1 : 0;
        onesCount[i] += bit == '1' ? 1 : 0;
      }
    }
    Data bitCount = new Data();
    bitCount.numbers = input;
    bitCount.zeroCount = zeroCount;
    bitCount.onesCount = onesCount;
    bitCount.length = length;
    return bitCount;
  }

  private List<String> readData() {
    return readData3("Advent03.txt").stream()
        .toList();
  }

}
