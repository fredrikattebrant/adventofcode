package se.attebrant.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractAdvent {

  /**
   * Define the baseDir for input files, must end with a slash '/'!
   */
  protected static String baseDir =
      "/Users/fredrik/git/adventofcode/src/se/attebrant/";

  protected AbstractAdvent() {
    baseDir = baseDir + "aoc2017/";
  };

  protected AbstractAdvent(String year) {
    baseDir = baseDir + "aoc" + year + "/";
  }

  public static void test(Function<String, Integer> fun, String digits, Integer expected) {
    Integer theSum = fun.apply(digits);
    if (theSum.equals(expected) || expected == -1) {
      System.out.println(digits + " -> " + theSum);
    } else {
      System.err.println(digits + " -> " + theSum + ", expected: " + expected);
    }
  }

  public static List<List<String>> readData(String filename) {
    List<List<String>> dataMatrix = new ArrayList<>();

    try (Stream<String> stream = Files.lines(Paths.get(filename))) {
      List<String> row = new ArrayList<>();
      stream.forEach(data -> row.add(data));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return dataMatrix;
  }

  public static List<List<String>> readData2(String filename) {
    List<List<String>> dataMatrix = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.lines().forEach(line -> dataMatrix.add(convertLineTolist(line)));
      List<String> list = br.lines().collect(Collectors.toList());
      if (!list.isEmpty()) {
        dataMatrix.add(list);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return dataMatrix;
  }

  public static List<String> readData3(String filename) {
    List<String> data = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(Paths.get(baseDir + filename))) {
      br.lines().forEach(line -> data.add(line));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return data;
  }

  private static List<String> convertLineTolist(String line) {
    List<String> list = new ArrayList<>();
    for (String word : line.split("\t")) {
      list.add(word);
    }
    return list;
  }

}
