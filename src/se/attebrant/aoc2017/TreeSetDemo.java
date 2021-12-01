package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetDemo {

  public static class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;
  }

  public static void main(String[] args) {

    Integer[] nums = {2, 4, 1, 6, 3, 7, 9, 5};
    SortedSet<Integer> tree = new TreeSet<>(Arrays.asList(nums));

    // Print first and last element
    System.out.println(tree.first());
    System.out.println(tree.last());
    System.out.println("--");
    printAll(tree);
    // False. Set does not allow duplicates,
    // so this will not be added.
    System.out.println(tree.add(1));

    // But, this will be added because 11 is not a duplicate
    System.out.println(tree.add(11));
    printAll(tree);

    printAll(tree.headSet(7));

    System.out.println("===");
    Node<String> root = new Node<>();
    root.data = "root";
    root.children = new ArrayList<>();
    Node<String> c1 = new Node<>();
    c1.data = "c1";

    Node<String> c2 = new Node<>();
    c2.data = "c2";

    root.children.add(c1);
    root.children.add(c2);

    System.out.println(root.data);
    if (root.children != null && !root.children.isEmpty()) {
      for (Node<String> child : root.children) {
        System.out.println(" + " + child.data);
      }
    }

  }

  public static void printAll(SortedSet<Integer> tree) {
    for (int s : tree) {
      System.out.println(s);
    }
    System.out.println();
  }
}
