package se.attebrant.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractAdvent {

  /**
   * Define the baseDir for input files, must end with a slash '/'!
   */
  protected String baseDir =
      "/Users/fredrik/git/adventofcode/src/se/attebrant/";

  protected boolean debug = false;
  protected static boolean IS_TEST = true;
  protected static boolean IS_LIVE = false;

  protected AbstractAdvent() {
    baseDir += "aoc2017/";
  }

  /**
   * Constructor with parameters:
   *
   * @param year set the year to read data from correct folder.
   * @param debug flip debug logging on/off
   */
  protected AbstractAdvent(String year, boolean... debug) {
    baseDir += "aoc" + year + "/";
    this.debug = debug.length > 0 && debug[0];
  }

  /**
   * Print text but keep sonar happy in regular code ;-)
   */
  protected static void print(String text) {
    System.out.println(text);
  }

  protected static String getDayPart(Object object) {
    return object.getClass().getSimpleName().replace("Advent", "");
  }

  public void test(Function<String, Integer> fun, String digits, Integer expected) {
    Integer theSum = fun.apply(digits);
    if (theSum.equals(expected) || expected == -1) {
      log(digits + " -> " + theSum);
    } else {
      System.err.println(digits + " -> " + theSum + ", expected: " + expected);
    }
  }

  public List<List<String>> readData(String filename) {
    List<List<String>> dataMatrix = new ArrayList<>();

    try (Stream<String> stream = Files.lines(Paths.get(filename))) {
      List<String> row = new ArrayList<>();
      stream.forEach(row::add);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return dataMatrix;
  }

  public List<List<String>> readData2(String filename) {
    List<List<String>> dataMatrix = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.lines().forEach(line -> dataMatrix.add(convertLineTolist(line)));
      List<String> list = br.lines().toList();
      if (!list.isEmpty()) {
        dataMatrix.add(list);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return dataMatrix;
  }

  public List<String> readData3(String filename) {
    List<String> data = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(Paths.get(baseDir + filename))) {
      br.lines().forEach(data::add);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return data;
  }

  protected List<String> readData4(boolean isTest) {
    String name = this.getClass().getSimpleName() + (isTest ? "test" : "") + ".txt";
    return readData3(name);
  }

  protected String getInputFileName(boolean isTest) {
    return baseDir + this.getClass().getSimpleName() + (isTest ? "test" : "") + ".txt";
  }

  private List<String> convertLineTolist(String line) {
    return Arrays.asList(line.split("\t"));
  }

  /**
   * Print the message to the console.
   *
   * @param message
   */
  public void log(Object message) {
    if (debug) {
      System.out.println(message);
    }
  }

  public void logf(String format, String... message) {
    if (debug) {
      System.out.println(String.format(format, message));
    }
  }

}
