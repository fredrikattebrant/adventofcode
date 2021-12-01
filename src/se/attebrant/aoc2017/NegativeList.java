package se.attebrant.aoc2017;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Creates a zero centered ArrayList of a given size. E.g.:
 * 
 * <pre>
 * index:    -2 -1  0  1  2
 * </pre>
 * 
 * Can be used to create e.g. a matrix by nesting {@link NegativeList}s.
 * 
 * <pre>
 * Row\Col  -2 -1  0  1  2
 *      -2      c
 *      -1
 *       0         a
 *       1
 *       2               b
 * 
 * a is at ( 0, 0)
 * b is at ( 2, 2)
 * c is at (-1,-1)
 * </pre>
 * 
 * @author fredrik
 *
 */
public class NegativeList<E> extends ArrayList<E> {

  /**
   * 
   */
  private static final long serialVersionUID = -2078690284359900129L;

  private ArrayList<E> theList;

  private int zeroIndex;

  public NegativeList(int size) {
    theList = new ArrayList<E>(size);
    zeroIndex = size / 2;
    for (int i = 0; i < size; i++) {
      theList.add(i, null);
    }
  }

  @Override
  public E get(int index) {
    if (theList.isEmpty()) {
      return null;
    }
    int normalizedIndex = index + zeroIndex;
    if (normalizedIndex >= theList.size() || normalizedIndex < 0) {
      return null;
    }
    return theList.get(normalizedIndex);
  }

  @Override
  public boolean add(E element) {
    return theList.add(element);
  }

  @Override
  public void add(int index, E element) {
    if (index + zeroIndex < 0) {
      // "append on the minus side"
      zeroIndex++;
      theList.add(0, element);
    } else {
      theList.add(index + zeroIndex, element);
    }
  }

  @Override
  public E set(int index, E element) {
    if (theList.size() > index + zeroIndex) {
      E oldElement = theList.get(index + zeroIndex);
      theList.set(index + zeroIndex, element);
      return oldElement;
    }
    // Extend the list as this is a new element
    add(index, element);
    // There was no element at this index
    return null;
  }

  @Override
  public int size() {
    return theList.size();
  }

  @Override
  public Iterator<E> iterator() {
    return theList.iterator();
  }

  @Override
  public String toString() {
    return theList.toString();
  }
}
