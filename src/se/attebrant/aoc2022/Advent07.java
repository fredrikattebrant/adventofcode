package se.attebrant.aoc2022;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.attebrant.common.AbstractAdvent;

public class Advent07 extends AbstractAdvent {

  public Advent07() {
    super("2022");
  }

  public static void main(String[] args) {
    var advent = new Advent07();
    var dayPart = getDayPart(advent);
    var mode = IS_LIVE;
    print("Result " + dayPart + " part 1: " + advent.solvePart1(mode));
    print("Result " + dayPart + " part 2: " + advent.solvePart2(mode));
  }

  private class Node {
    protected String name;

    public Node(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  private class File extends Node {
    private int size;

    public File(String name, int size) {
      super(name);
      this.size = size;
    }

    public int getSize() {
      return size;
    }

    @Override
    public String toString() {
      return size + " " + getName();
    }
  }

  private class Directory extends Node {

    private static String ROOT = "/";
    private Directory parent;
    private List<Node> content = new ArrayList<>();

    /**
     * Creates a root '/' directory.
     *
     * @param name
     */
    public Directory() {
      super(ROOT);
      parent = null;
    }

    public Directory(String name, Directory parent) {
      super(name);
      this.parent = parent;
    }

    public List<Node> getContent() {
      return content;
    }

    public Directory getParent() {
      return parent;
    }

    @Override
    public String getName() {
      if ("".equals(name)) {
        return "/";
      }
      return name;
    }

    /**
     * Returns the size of all files in this directory and its children.
     */
    int getSize() {
      int size = 0;
      for (Node node : getContent()) {
        if (node instanceof File) {
          File file = (File) node;
          size += file.getSize();
        } else {
          size += ((Directory) node).getSize();
        }
      }
      return size;
    }

    @Override
    public String toString() {
      return "dir " + name;
    }
  }

  private int solvePart1(boolean isTest) {
    List<String> input = readData4(isTest);

    Map<Directory, Integer> dirMap = new HashMap<>();
    Set<Directory> allDirectories = new HashSet<>();

    Directory root = new Directory();
    Directory cwd = root;
    allDirectories.add(root);

    parseInput(input, dirMap, allDirectories, root, cwd);
    Map<Directory, Integer> dirSum = new HashMap<>();
    for (Directory directory : dirMap.keySet()) {
      int size = getSizeOf(directory);
      dirSum.put(directory, size);
    }

    int sum = 0;
    for (Directory d : allDirectories) {
      int dSize = d.getSize();
      if (dSize <= 100000) {
        sum += dSize;
      }
    }
    return sum;
  }

  private void parseInput(List<String> input, Map<Directory, Integer> dirMap,
      Set<Directory> allDirectories, Directory root, Directory cwd) {
    boolean isLs = false;
    for (String line : input) {
      if (isCommand(line)) {
        if (isCd(line)) {
          isLs = false;
          String dir = getDir(line);
          if (dir.equals(Directory.ROOT)) {
            cwd = root;
          } else if (dir.equals("..")) {
            cwd = cwd.getParent();
          } else {
            Directory childDir = new Directory(dir, cwd);
            allDirectories.add(childDir);
            cwd.getContent().add(childDir);
            if (!dirMap.containsKey(childDir)) {
              dirMap.put(childDir, 0);
            }
            cwd = childDir;
          }
        } else if (isLs(line)) {
          isLs = true;
        } else if (isLs) {
        }
      } else {
        if (!isDirLine(line)) {
          int size = getSize(line);
          File file = new File(line.split(" ")[1], size);
          cwd.getContent().add(file);
          int currentSize = 0;
          if (dirMap.containsKey(cwd)) {
            currentSize = dirMap.get(cwd);
          }
          int newSize = currentSize + size;
          dirMap.put(cwd, newSize);
        }
      }
    }
  }

  private int solvePart2(boolean isTest) {
    List<String> input = readData4(isTest);

    Map<Directory, Integer> dirMap = new HashMap<>();
    Set<Directory> allDirectories = new HashSet<>();

    Directory root = new Directory();
    Directory cwd = root;
    allDirectories.add(root);

    parseInput(input, dirMap, allDirectories, root, cwd);
    Map<Integer, Directory> dirSum = new HashMap<>();
    for (Directory directory : dirMap.keySet()) {
      int size = getSizeOf(directory);
      dirSum.put(size, directory);
    }

    int total = 70000000;
    int used = root.getSize();
    int free = total - used;
    int needed = 30000000;

    Collection<Integer> values = dirSum.keySet();
    List<Integer> directorySizes = values.stream().sorted().toList();
    for (Integer size : directorySizes) {
      if (free + size > needed) {
        Directory delete = dirSum.get(size);
        return delete.getSize();
      }
    }


    return -1;
  }

  private int getSizeOf(Directory directory) {
    int size = 0;
    for (Node node : directory.getContent()) {
      if (node instanceof File) {
        File file = (File) node;
        size += file.getSize();
      } else {
        Directory subDir = (Directory) node;
        size += getSizeOf(subDir);
      }
    }
    return size;
  }

  private boolean isCommand(String line) {
    return line.startsWith("$");
  }

  private boolean isCd(String line) {
    return line.split(" ")[1].equals("cd");
  }

  private boolean isLs(String line) {
    return line.split(" ")[1].equals("ls");
  }

  private boolean isDirLine(String line) {
    return line.startsWith("dir");
  }

  private String getDir(String line) {
    return line.split(" ")[2];
  }

  private int getSize(String line) {
    return Integer.parseInt(line.split(" ")[0]);
  }

}
