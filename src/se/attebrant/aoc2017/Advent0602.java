package se.attebrant.aoc2017;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent0602 {

  public static void main(String[] args) {
    List<Integer> banks = Arrays.asList(
        // 0, 2, 7, 0);
        11, 11, 13, 7, 0, 15, 5, 5, 4, 4, 1, 1, 7, 1, 15, 11);
    // 14, 0, 15, 12, 11, 11, 3, 5, 1, 6, 8, 4, 9, 1, 8, 4);

    System.out.println("Initial count: " + getReallocationCount(banks));

    int loopCount = getReallocationCount(banks);
    System.out.println("Loop count: " + loopCount);
    int newCount = getReallocationCount(banks);
    while (newCount != loopCount) {
      System.out.println("Loop count: " + newCount);
      newCount = getReallocationCount(banks);
    }

  }

  private static int getReallocationCount(List<Integer> banks) {
    // System.out.println("Banks: " + banks);
    int largestBankIndex = findBankWithMostBlocks(banks);
    // System.out.println("Largest bank: " + largestBankIndex);

    Set<String> blocksInBankConfigurations = new HashSet<>();
    String initialConfiguration = banks.toString();
    blocksInBankConfigurations.add(initialConfiguration);
    // System.out.println("Initial configuration: " + initialConfiguration);
    int reallocationCount = 0;
    while (true) {
      reallocate(banks, largestBankIndex);
      largestBankIndex = findBankWithMostBlocks(banks);
      reallocationCount++;
      String currentConfiguration = banks.toString();
      // System.out.println(
      // "Configuration: " + currentConfiguration + ", at iteration: " + reallocationCount);
      if (blocksInBankConfigurations.contains(currentConfiguration)) {
        break;
      }
      blocksInBankConfigurations.add(currentConfiguration);
    }
    return reallocationCount;

  }

  private static int findBankWithMostBlocks(List<Integer> banks) {
    int largestSize = -1;
    int bankIndex = 0;
    for (int index = 0; index < banks.size(); index++) {
      Integer bankSize = banks.get(index);
      if (bankSize > largestSize) {
        largestSize = bankSize;
        bankIndex = index;
      }
      if (bankSize == largestSize && index != bankIndex) {
        // A tie - select the bank with the lowest index
        // System.out.println("Tie:" + index + ", " + bankIndex);
        if (index < bankIndex) {
          bankIndex = index;
        }
      }
    }
    return bankIndex;
  }

  private static void reallocate(List<Integer> banks, int largestBank) {
    int blocks = banks.get(largestBank);
    banks.set(largestBank, 0);
    int numberOfBanks = banks.size();
    int index = nextIndex(largestBank, numberOfBanks);
    while (blocks > 0) {
      banks.set(index, banks.get(index) + 1);
      blocks--;
      index = nextIndex(index, numberOfBanks);
    }
  }

  private static int nextIndex(int index, int numberOfBanks) {
    return (index + 1) % numberOfBanks;
  }
}
