package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class NegativeListUser {

  public static void main(String[] args) {
    NegativeList<Integer> negList = new NegativeList<>(10);


    System.out.println("Size: " + negList.size());
    for (int ix = -5; ix < 10; ix++) {
      negList.add(ix, ix);
    }
    negList.add(-2, 42);
    System.out.println("Size: " + negList.size());
    System.out.println("Get@0: " + negList.get(0));
    System.out.println(negList);
    negList.add(-5, 17);
    System.out.println("Size: " + negList.size());
    System.out.println(negList);
    System.out.println("Get@-5:" + negList.get(-5));
    System.out.println("Get@-6:" + negList.get(-6));
    System.out.println("Get@0: " + negList.get(0));

    List<Integer> alist = new ArrayList<>();
    alist.add(1);
    System.out.println(alist);
    alist.add(0, 42);
    System.out.println(alist);
  }

}
