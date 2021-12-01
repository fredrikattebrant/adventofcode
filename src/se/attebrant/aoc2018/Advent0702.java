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
public class Advent0702 extends AbstractAdvent {

  public Advent0702() {
    super("2018");
  }

  public static void main(String[] args) {
    Advent0702 advent = new Advent0702();
    advent.solve();
  }

  Map<String, Node> nodes;

  // Use for: A,C means A is preceded by C
  Map<String, List<String>> preceding;

  Set<String> completedNodes;

  private void solve() {
    boolean isTest = true;
    List<String> rawData = getTestdata(isTest);
    nodes = new HashMap<>();
    preceding = new HashMap<>();
    completedNodes = new HashSet<>();

    int numberOfWorkers = isTest ? 2 : 5;

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
      // System.out.println(data + " -> " + node);
    }

    Set<String> potentialStartIds = new HashSet<>();
    for (String id : nodes.keySet()) {
      if (!preceding.keySet().contains(id)) {
        potentialStartIds.add(id);
      }
    }

    int totalTime = 0;
    String sequence = "";
    Set<Node> setOfNodes = new HashSet<>(nodes.values());
    Node firstNode = nodes.get(potentialStartIds.iterator().next());
    potentialStartIds.remove(firstNode.id);
    List<String> nextNodeIds = new ArrayList<>();
    for (String id : potentialStartIds) {
      nextNodeIds.add(id);
    }
    String nextId = firstNode.id;

    // 1. Construct the sequence
    List<Node> nodeSequence = new ArrayList<>();
    while (!setOfNodes.isEmpty()) {
      Node next = nodes.get(nextId);
      nodeSequence.add(next);
      if (next == null) {
        // reached last node - just add it
        break;
      }
      setOfNodes.remove(next);
      nextNodeIds.remove(nextId);
      // sequence += next.id;
      completedNodes.add(next.id);
      List<String> nextNodes = next.getNextNodes();
      for (String id : nextNodes) {
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
    nodeSequence.add(new Node(nextNodeIds.get(0)));

    // 2. Parallelize the work
    Map<Integer, Worker> workers = new HashMap<>();
    for (int workerId = 1; workerId <= numberOfWorkers; workerId++) {
      workers.put(workerId, new Worker(workerId, isTest));
    }

    boolean done = false;
    boolean startedOnLastNode = false;
    List<Node> nextNodes = new ArrayList<>();
    nextNodes.add(nodeSequence.get(0));

    // loop, incrementing time until done
    for (int second = 0; !done; second++) {
      // Loop over available workers
      for (int workerId : workers.keySet()) {
        Worker worker = workers.get(workerId);
        if (worker.hasWork() && nextNodes.isEmpty()) {
          // no new work, continue on current work
          if (worker.work()) {
            if (startedOnLastNode) {
              boolean more = false;
              // W1: true
              // W2: false => false
              for (Worker w : workers.values()) {
                more |= !w.isDone();
              }
              if (!more) {
                sequence += worker.getNode().id;
                done = true;
                break;
              }
            }
            Node workerNode = worker.getNode();
            // if (workerNode != null) {
            System.out.println("Done - " + workerNode.id);
            worker.setNode(null);
            sequence += workerNode.id;
            for (String id : workerNode.getNextNodes()) {
              Node next = nodes.get(id);
              if (!startedOnLastNode) {
                if (next == null) {
                  // special case - last node -- TODO verify this
                  Node lastNode = new Node(id);
                  nodes.put(id, lastNode);
                  nextNodes.add(lastNode);
                  startedOnLastNode = true;
                } else if (!nextNodes.contains(next)) {
                  System.out.println("Adding " + id + " to queue");
                  nextNodes.add(next);
                }
              }
            }
            Node nextStep = nextNodes.remove(0);
            worker.setStep(nextStep.id);
            // } // TODO else { done = true; } // or more checks needed???
          }
        } else {
          if (worker.hasWork()) {
            System.out.println("Should work on " + worker.step);
            System.out.println("TODO refactor...");
          } else if (!nextNodes.isEmpty()) {
            // process the nextNodes, distribute work over available workers

            // Get next node to work on
            Node node = nextNodes.remove(0);
            String step = node.id;
            worker.setStep(step);
          }
        }
        // } END NODE LOOP
      }
      System.out.println("Second " + second + " - Workers " + workers);
      totalTime = second;
    } // END time (second) loop

    System.out.println("Correct order is:   " + sequence);
    System.out.println("Completion time is: " + totalTime);
  }

  private int getTime(String letter, boolean isTest) {
    int time = isTest ? 0 : 60;
    time += letter.charAt(0) - 'A' + 1;
    return time;
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

  private class Worker {
    private int id;
    private String step;
    private int timeRemaining;
    private boolean isTest;

    public Worker(int id, boolean isTest) {
      this.id = id;
      this.isTest = isTest;
    }

    public void setNode(Node node) {
      step = node == null ? null : node.id;
    }

    public boolean hasWork() {
      return step != null && !isDone();
    }

    public boolean isDone() {
      return timeRemaining <= 0;
    }

    public Node getNode() {
      return nodes.get(step);
    }

    public int getTimeRemaining() {
      return timeRemaining;
    }

    public void setStep(String step) {
      this.step = step;
      timeRemaining = getTime(step, isTest);
    }

    /**
     * Performs one step of work. If no work is assigned - do nothing, this will equal to being
     * {@link #isDone()}. <br>
     * Returns <code>true</code> when work is {@link #isDone()}.
     */
    public boolean work() {
      if (isTest) {
        System.out.println(this + (isDone() ? " idling" : " working"));
      }
      if (isDone()) {
        return true;
      }
      timeRemaining = timeRemaining - 1;
      return isDone();
    }

    @Override
    public String toString() {
      String text = "Worker " + id;
      text += " (" + (step == null ? "-" : step) + "@" + timeRemaining + ")";
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
