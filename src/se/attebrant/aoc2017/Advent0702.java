package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import se.attebrant.common.AbstractAdvent;
import java.util.Set;

public class Advent0702 extends AbstractAdvent {

  /**
   * <pre>
  pbga (66)
  xhth (57)
  ebii (61)
  havc (66)
  ktlj (57)
  fwft (72) -> ktlj, cntj, xhth
  qoyq (66)
  padx (45) -> pbga, havc, qoyq
  tknk (41) -> ugml, padx, fwft
  jptl (61)
  ugml (68) -> gyxo, ebii, jptl
  gyxo (61)
  cntj (57)
   * </pre>
   */

  public class Program {
    private String id;
    private int weight;
    private List<String> above;

    public Program(String id, int weight, List<String> above) {
      this.id = id;
      this.weight = weight;
      this.above = above;
    }

    /**
     * Create a new Program given a line from the input file.
     * 
     * @param inputLine format: name (weight) { --> name, name, ... }
     */
    public Program(String inputLine) {
      String[] tokens = inputLine.split("\\s");
      int inputLength = tokens.length;
      if (inputLength < 2) {
        throw new RuntimeException("Bad input: " + inputLine);
      }
      id = tokens[0];
      weight = Integer.parseInt(tokens[1].replaceAll("[()]", ""));
      if (inputLength > 2) {
        above = new ArrayList<>();
        for (int ix = 3; ix < inputLength; ix++) {
          above.add(tokens[ix].replaceAll(",", ""));
        }
      }
    }

    public String getId() {
      return id;
    }

    public int getWeight() {
      return weight;
    }

    public List<String> getAbove() {
      return above;
    }

    public boolean hasAbove() {
      return getAbove() != null;
    }

    @Override
    public String toString() {
      String text = id + " (" + weight + ")";
      if (above != null) {
        text += " -> ";
        String separator = "";
        for (String program : above) {
          text += separator + program;
          separator = ", ";
        }
      }
      return text;
    }
  }

  public class Node {
    private Program data;
    private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node() {}

    public Node(Program program) {
      this.data = program;
    }

    public void addChild(Node child) {
      children.add(child);
    }

    @Override
    public String toString() {
      String text = data.toString();
      String suffix = "";
      if (parent != null) {
        text = parent + "{";
        suffix = "}";
      }
      if (!children.isEmpty()) {
        text += " --- [";
        String sep = "";
        for (Node child : children) {
          text += sep + child;
          sep = ", ";
        }
        text += "]";
      }
      text += suffix;
      return text;
    }
  }


  public static void main(String[] args) {
    Advent0702 advent0701 = new Advent0702();
    advent0701.run();


  }

  Map<String, Program> programs;

  private void run() {
    // programs = getPrograms();
    programs = getTestPrograms();

    Map<String, Node> nodeMap = new HashMap<>();
    for (String key : programs.keySet()) {
      Program program = programs.get(key);
      Node node = createNode(nodeMap, program);
      // System.out.println("Node : " + node);
    }

    for (Entry<String, Node> entry : nodeMap.entrySet()) {
      String key = entry.getKey();
      Program program = programs.get(key);
      if (program.hasAbove()) {
        for (String above : program.getAbove()) {
          Node childNode = nodeMap.get(above);
          childNode.parent = entry.getValue();
        }
      }
    }

    System.out.println("parent/child established");

    Node bottomNode = null;
    for (String key : nodeMap.keySet()) {
      Node node = nodeMap.get(key);
      if (node.parent == null) {
        bottomNode = node;
        System.out.println("Bottom: " + node.data.id + " => " + node);
        break;
      }
    }

    if (bottomNode == null) {
      System.err.println("*** NO bottom node found");
      return;
    }

    List<Node> children = bottomNode.children;
    Map<Integer, Node> weightToNode = new HashMap<>();
    int commonWeight = 0;
    for (Node node : children) {
      int totalWeight = getTotalWeight(node);
      if (weightToNode.containsKey(totalWeight)) {
        commonWeight = totalWeight;
      }
      weightToNode.put(totalWeight, node);
    }
    System.out.println("Common weight: " + commonWeight);
    Set<Integer> weights = weightToNode.keySet();
    if (weights.size() == 0) {
      System.out.println("Tower is balanced");
    } else if (weights.size() != 2) {
      System.err.println("*** Multiple weights are off");
    } else {
      weights.remove(commonWeight);
      Integer diffWeight = weights.iterator().next();
      Node diffNode = weightToNode.get(diffWeight);
      System.out.println("Diff weight: " + diffWeight);
      System.out.println("Diff node:   " + diffNode);
      // TODO determine which node is off... "parent" or one of the children...
      // TODO refactor to recurse?
    }

  }
  
  private int getTotalWeight(Node node) {
    // System.out.println(node.data.id + "(" + node.data.weight + ")");
    Program program = node.data;
    int weight = program.getWeight();
    List<Node> children = node.children;
    for (Node childNode : children) {
      int childWeight = getTotalWeight(childNode);
      // System.out.println(childNode.data.id + ": " + childWeight);
      weight += childWeight;
    }
    return weight;
  }

  private Map<String, Program> getPrograms() {
    List<String> linesInFile = readData3("Advent0701FA.txt");
    Map<String, Program> programs = new HashMap<>();
    for (String line : linesInFile) {
      Program program = new Program(line);
      programs.put(program.getId(), program);
    }
    return programs;
  }

  private Map<String, Program> getTestPrograms() {
    Map<String, Program> programs = new HashMap<>();
    programs.put("pbga", new Program("pbga", 66, null));
    programs.put("xhth", new Program("xhth", 57, null));
    programs.put("ebii", new Program("ebii", 61, null));
    programs.put("havc", new Program("havc", 66, null));
    programs.put("ktlj", new Program("ktlj", 57, null));
    programs.put("fwft", new Program("fwft", 72, Arrays.asList("ktlj", "cntj", "xhth")));
    programs.put("qoyq", new Program("qoyq", 66, null));
    programs.put("padx", new Program("padx", 45, Arrays.asList("pbga", "havc", "qoyq")));
    programs.put("tknk", new Program("tknk", 41, Arrays.asList("ugml", "padx", "fwft")));
    programs.put("jptl", new Program("jptl", 61, null));
    programs.put("ugml", new Program("ugml", 68, Arrays.asList("gyxo", "ebii", "jptl")));
    programs.put("gyxo", new Program("gyxo", 61, null));
    programs.put("cntj", new Program("cntj", 57, null));
    return programs;
  }

  private Node createNode(Map<String, Node> nodeMap, Program program) {
    Node node = new Node(program);
    if (program.hasAbove()) {
      for (String above : program.getAbove()) {
        Node aboveNode = nodeMap.get(above);
        if (aboveNode == null) {
          Program programAbove = programs.get(above);
          aboveNode = createNode(nodeMap, programAbove);
          node.addChild(aboveNode);
        }
      }
    }
    nodeMap.put(program.getId(), node);
    return node;
  }

}
