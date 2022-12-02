package se.attebrant.aoc2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

public class Advent12 extends AbstractAdvent {

  public Advent12(boolean... debug) {
    super("2021", debug);
  }

  public static void main(String[] args) {
    Advent12 advent = new Advent12(true);
    boolean useTestInput = true;
    print("Day 12, part 1: " + advent.solvePart1(useTestInput));
    // print("Day 12, part 2: " + advent.solvePart2(useTestInput));
  }

  private List<String> puzzleInput = readData3("Advent12.txt");
  private List<String> testInput = readData3("Advent12test.txt");
  private List<String> testInput2 = readData3("Advent12test2.txt");

  private int solvePart1(boolean useTestInput) {
    return solve(useTestInput, true);
  }

  private int solvePart2(boolean useTestInput) {
    return solve(useTestInput, false);
  }


  private int solve(boolean useTestInput, boolean isPart1) {
    var input = useTestInput ? testInput : puzzleInput;
    return isPart1 ? solve1(input) : solve2(input);
  }

  private static final String START = "start";
  private static final String END = "end";

  class Node {
    String id;
    boolean isBig;
    Set<Node> connectedNodes = new HashSet<>();

    Node(String id) {
      this.id = id;
      isBig = id.equals(id.toUpperCase());
    }

    void add(Node node) {
      connectedNodes.add(node);
    }

    @Override
    public String toString() {
      StringBuilder text = new StringBuilder();
      text.append("[" + id + " -> [");
      text.append(connectedNodes.stream()
          .map(node -> node.id)
          .collect(Collectors.joining(",")));
      text.append("]");
      return text.toString();
    }
  }

  private int solve1(List<String> input) {
    Map<String, Node> nodeMap = new HashMap<>();

    for (String line : input) {
      String[] connection = line.trim().split("-");
      String from = connection[0];
      String to = connection[1];

      Node fromNode = nodeMap.get(from);
      if (fromNode == null) {
        fromNode = new Node(from);
        nodeMap.put(from, fromNode);
      }
      Node toNode = nodeMap.get(to);
      if (toNode == null) {
        toNode = new Node(to);
        toNode.add(fromNode);
        nodeMap.put(to, toNode);
      }
      fromNode.add(toNode);
      toNode.add(fromNode);
      log("");
    }

    // Path: start,A,end
    int paths = 0;
    return 0;
  }

  // private void next(Map<String, List<String>> map, String from, List<String> path) {
  // List<String> to = map.get(from);
  // if (to != null) {
  // for (String next : to) {
  // path.add(next);
  // if (next.equals(END)) {
  // return;
  // }
  // next(map, next, path);
  // log("");
  // }
  // }
  // }

  private int solve2(List<String> input) {
    return -1;
  }

}
