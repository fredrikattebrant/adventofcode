package se.attebrant.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import se.attebrant.common.AbstractAdvent;

/**
 * <pre>
 * O is the last step (sort/pcol checking of testdata)
 * 
 * $ sdiff -w 10 1st 2nd 
A   A
B   < * NOT in 2nd column => first step!
C   < * NOT in 2nd column, but after B...
D   D
E   E
F   F
G   G
H   H
I   I
J   J
K   K
L   L
M   M
N   N
>   O * Not in 1st column => last step!
P   P
Q   Q
R   R
S   S
T   T
U   U
V   < * NOT in 2nd column, but after C...
W   W
X   X
Y   Y
Z   < * NOT in 2nd column, but after V...
 * 
 * </pre>
 * 
 * @author fredrik
 *
 */
public class Advent0701 extends AbstractAdvent {

  public Advent0701() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0701 advent = new Advent0701();
    advent.solve();
  }

  Map<String, Node> nodes = new HashMap<>();

  // Use for: A,C means A is preceded by C
  Map<String, List<String>> preceding = new HashMap<>();

  Set<String> completedNodes = new HashSet<>();

  private void solve() {
    List<String> rawData = getTestdata(false); // TEST: true

    for (String data : rawData) {
      String thisId = extractId(data);
      Node node = nodes.get(thisId);
      if (node == null) {
        node = new Node(thisId);
      }
      String nextNode = extractNextNode(data);
      node.addNext(nextNode);
      nodes.put(thisId, node);
      addPreceding(nextNode, thisId);
      System.out.println(data + " -> " + node);
    }

    Set<String> potentialStartIds = new HashSet<>();
    for (String id : nodes.keySet()) {
      if (!preceding.keySet().contains(id)) {
        potentialStartIds.add(id);
      }
    }

    String sequence = "";
    Set<Node> setOfNodes = new HashSet<>(nodes.values());
    Node firstNode = nodes.get(potentialStartIds.iterator().next());
    potentialStartIds.remove(firstNode.id);
    sequence = firstNode.id;
    completedNodes.add(firstNode.id);
    setOfNodes.remove(firstNode);
    List<String> nextNodeIds = new ArrayList<>();
    for (String id : potentialStartIds) {
      nextNodeIds.add(id);
    }
    String nextId = nextNodeIds.get(0);

    while (!setOfNodes.isEmpty()) {
      Node next = nodes.get(nextId);
      if (next == null) {
        // reached last node - just add it
        sequence += nextId;
        break;
      }
      setOfNodes.remove(next);
      nextNodeIds.remove(nextId);
      sequence += next.id;
      completedNodes.add(next.id);
      List<String> nextNodes = next.getNextNodes();
      for (String id : nextNodes) {
        Node n = nodes.get(id);
        boolean accept = true;
        for (String nid : preceding.get(id)) {
          if (!completedNodes.contains(nid)) {
            accept = false;
            break;
          }
        }
        if (accept) {
          nextNodeIds.add(id);
        }
      }
      nextNodeIds.sort((s1, s2) -> s1.compareTo(s2));
      nextId = nextNodeIds.get(0);
    }
    sequence += nextNodeIds.get(0);
    System.out.println("Correct order is: " + sequence);
  }

  /**
   * Register that nodeId is preceded by precedingId.
   */
  private void addPreceding(String nodeId, String precedingId) {
    List<String> nodes = preceding.get(nodeId);
    if (nodes == null) {
      nodes = new ArrayList<>();
      preceding.put(nodeId, nodes);
    }
    nodes.add(precedingId);
  }

  private String extractNextNode(String data) {
    return data.split(" ")[7];
  }

  private String extractId(String data) {
    return data.split(" ")[1];
  }

  private class Node {
    private String id;
    private List<String> nextNodes;

    public Node(String id) {
      this.id = id;
      nextNodes = new ArrayList<>();
    }

    public void addNext(String node) {
      nextNodes.add(node);
    }

    /**
     * Return the next node IDs, in alphabetic order
     */
    public List<String> getNextNodes() {
      return nextNodes.stream()
          .sorted()
          .collect(Collectors.toList());
    }

    public List<String> getPrecedingNodeIds() {
      // Kludge
      List<String> pids = new ArrayList<>();
      if (preceding != null) {
        List<String> list = preceding.get(id);
        if (list != null) {
          pids.addAll(list);
          pids.sort((s1, s2) -> s1.compareTo(s2));
        }
      }
      return pids;
    }

    @Override
    public String toString() {
      String text = id;
      if (!nextNodes.isEmpty()) {
        text += "<";
        text += getNextNodes().stream()
            .collect(Collectors.joining(","));
      }
      return text;
    }

  }

  private List<String> getTestdata(boolean test) {
    if (!test) {
      return readData3("Advent0701.txt");
    }
    List<String> data = new ArrayList<>();
    data.add("Step C must be finished before step A can begin.");
    data.add("Step C must be finished before step F can begin.");
    data.add("Step A must be finished before step B can begin.");
    data.add("Step A must be finished before step D can begin.");
    data.add("Step B must be finished before step E can begin.");
    data.add("Step D must be finished before step E can begin.");
    data.add("Step F must be finished before step E can begin.");
    return data;
  }
}
