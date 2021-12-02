package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.List;
import se.attebrant.common.AbstractAdvent;

public class Advent0502 extends AbstractAdvent {

  public Advent0502(boolean... debug) {
    super("2017", debug);
  }

  public static void main(String[] args) {
    new Advent0502(false).solve();
  }

  private void solve() {
    List<String> instructions = readData3("Advent0501FA.txt");

    List<Integer> jumps = getJumps(instructions);
    // jumps.clear();
    // jumps.add(0);
    // jumps.add(3);
    // jumps.add(0);
    // jumps.add(1);
    // jumps.add(-3);
    processJumps(jumps);
  }

  private void processJumps(List<Integer> jumps) {
    int current = 0;
    int steps = 0;
    while (true) {
      // System.out.println("Before: " + jumps + ", Current: " + current);
      int jump = jumps.get(current);
      int next = current + jump;
      if (jump > 2) {
        jump = jump - 1;
      } else {
        jump = jump + 1;
      }
      jumps.set(current, jump);
      steps++;
      // System.out.println("After : " + jumps + ", Current: " + jumps.get(current));
      // System.out.println("Current: " + jumps.get(current));
      current = next;
      if (current >= jumps.size() || current < 0) {
        break;
      }
    }
    System.out.println("Done after " + steps + " step.");
  }

  private List<Integer> getJumps(List<String> instructions) {
    List<Integer> jumps = new ArrayList<>();
    for (String instruction : instructions) {
      jumps.add(Integer.parseInt(instruction));
    }
    return jumps;
  }

}
