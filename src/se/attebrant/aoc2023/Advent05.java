package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import se.attebrant.common.AbstractAdvent;

public class Advent05 extends AbstractAdvent {

  public Advent05(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = true;
    Advent05 advent = new Advent05(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    // print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_TEST));
  }

  private Long solve(String day, boolean isPart2, boolean isTest) throws IOException {
    List<String> input = readData(day, false, isTest); // same for both parts
    List<Mapping> mappings = parseInput(input);
    return isPart2 ? solvePart2(mappings) : solvePart1(mappings);
  }

  private Long solvePart1(List<Mapping> mappings) {
    Long lowest = Long.MAX_VALUE;

    List<Long> seeds = mappings.get(0).seeds;
    for (Long seed : seeds) {
      Long mappedSeed = seed;
      for (int mix = 1; mix < mappings.size(); mix++) {
        Mapping mapping = mappings.get(mix);
        Long seedIn = mappedSeed;
        for (Range range : mapping.ranges) {
          mappedSeed = range.convert(seedIn);
          if (mappedSeed != seedIn) {
            break; // mapping done for this range
          }
        }
      }
      if (mappedSeed < lowest) {
        lowest = mappedSeed;
      }
    }
    return lowest;
  }

  private Long solvePart2(List<Mapping> mapping) {
    return -1L;
  }

  record Range(
      Long destinationRangeStart,
      Long sourceRangeStart,
      Long rangeLength) {

    Long convert(Long from) {
      Long offset = from - sourceRangeStart;
      if (from >= sourceRangeStart && offset < rangeLength) {
        return destinationRangeStart + offset;
      }
      return from;
    }

    @Override
    public String toString() {
      return destinationRangeStart + " " + sourceRangeStart + " " + rangeLength;
    }
  }

  class Mapping {
    String name; // seed-to-soil map
    String from; // seed
    String to; // soil
    List<Range> ranges;
    List<Long> seeds; // special case for the seeds entry

    public Mapping() {
      this.ranges = new ArrayList<>();
    }

    boolean isSeeds() {
      return seeds != null;
    }

    @Override
    public String toString() {
      String text = "";
      if (isSeeds()) {
        text += "seeds;" + System.lineSeparator();
        text += seeds.stream()
            .map(Object::toString)
            .collect(Collectors.joining(" ")) + System.lineSeparator();
      } else {
        text = name + System.lineSeparator();
        for (Range range : ranges) {
          text += range + System.lineSeparator();
        }
      }
      return text;
    }
  }

  private List<Mapping> parseInput(List<String> input) {
    List<Mapping> mappedInput = new ArrayList<>();
    Mapping mapping = new Mapping();
    mappedInput.add(mapping);
    for (String line : input) {

      if (line.startsWith("seeds")) {
        String numbersPart = line.split(":")[1].trim();
        String[] numbers = numbersPart.split(" ");
        List<Long> seeds = Arrays.asList(numbers).stream()
            .map(Long::parseLong)
            .toList();
        mapping.seeds = seeds;

      } else if (line.isBlank()) {
        mapping = new Mapping();
        mappedInput.add(mapping);
      } else if (line.endsWith(":")) {
        mapping.name = line;
        String[] nameSplit = line.replace(" map:", "").split("-");
        mapping.from = nameSplit[0];
        mapping.to = nameSplit[2];

      } else {
        // the range entries
        String[] ranges = line.split(" ");

        Long destinationRangeStart = Long.parseLong(ranges[0]);
        Long sourceRangeStart = Long.parseLong(ranges[1]);
        Long rangeLength = Long.parseLong(ranges[2]);
        Range range = new Range(destinationRangeStart, sourceRangeStart, rangeLength);
        mapping.ranges.add(range);
      }
    }
    return mappedInput;
  }

}
