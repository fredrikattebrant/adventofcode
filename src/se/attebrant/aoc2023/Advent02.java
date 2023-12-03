package se.attebrant.aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.attebrant.common.AbstractAdvent;

public class Advent02 extends AbstractAdvent {

  public Advent02(boolean debug) {
    super("2023", debug);
  }

  public static void main(String[] args) throws IOException {
    var debug = false;
    Advent02 advent = new Advent02(debug);
    String day = getDayPart(advent);
    print("Result day " + day + ", part1: " + advent.solve(day, IS_PART1, IS_LIVE));
    print("Result day " + day + ", part2: " + advent.solve(day, IS_PART2, IS_LIVE));
  }

  class Cube {
    int red = 0;
    int green = 0;
    int blue = 0;

    Cube(String cubeStr) {
      String[] colorSplit = cubeStr.split(",");
      for (String colorStr : colorSplit) {
        String[] split = colorStr.trim().split(" ");
        int count = Integer.parseInt(split[0]);
        switch (split[1]) {
          case "red":
            red = count;
            break;
          case "green":
            green = count;
            break;
          case "blue":
            blue = count;
            break;
          default:
            throw new IllegalArgumentException("Unexpected value: " + split[1]);
        }
      }
    }

    @Override
    public String toString() {
      return String.format("<%d red, %d green, %d blue>", red, green, blue);
    }
  }

  record CubeSet(int red, int green, int blue) {

    @Override
    public String toString() {
      return String.format("%d red, %d green, %d blue", red, green, blue);
    }

  }

  class Game {

    private int gameId; // "Game n"
    private List<Cube> cubes;
    int red = 0;
    int green = 0;
    int blue = 0;

    Game(int gameId, List<Cube> cubes) {
      this.gameId = gameId;
      this.cubes = cubes;
      for (Cube cube : cubes) {
        if (cube.red > red) {
          red = cube.red;
        }
        if (cube.green > green) {
          green = cube.green;
        }
        if (cube.blue > blue) {
          blue = cube.blue;
        }
      }
    }

    public int getGameId() {
      return gameId;
    }

    @Override
    public String toString() {
      return String.format("Game %d: %d red, %d green, %d blue", gameId, red, green, blue);
    }
  }


  private int solve(String day, boolean isPart2, boolean isTest) throws IOException {
    log("Part" + (isPart2 ? 2 : 1));

    // same for both parts
    boolean part1SameAsPart2 = false;
    List<String> input = readData(day, part1SameAsPart2, isTest);
    List<Game> games = new ArrayList<>();
    for (String line : input) {
      log(line);

      games.add(parseLine(line));
    }

    if (!isPart2) {
      return solvePart1(games);
    }

    return solvePart2(games);

  }

  private int solvePart1(List<Game> games) {
    // Bag: 12 red cubes, 13 green cubes, and 14 blue cubes
    CubeSet theCubeSet = new CubeSet(12, 13, 14);

    int sum = 0;

    for (Game game : games) {
      if (game.red <= theCubeSet.red
          && game.green <= theCubeSet.green
          && game.blue <= theCubeSet.blue) {
        sum += game.gameId;
      }
    }

    log("=====");
    return sum;
  }

  private int solvePart2(List<Game> games) {
    int totalPower = 0;

    for (Game game : games) {
      int minRed = 0;
      int minGreen = 0;
      int minBlue = 0;
      if (game.red > minRed) {
        minRed = game.red;
      }
      if (game.green > minGreen) {
        minGreen = game.green;
      }
      if (game.blue > minBlue) {
        minBlue = game.blue;
      }
      int power = minRed * minGreen * minBlue;
      log("Power: " + power);
      totalPower += power;
    }
    return totalPower;
  }

  private Game parseLine(String line) {
    // Get the Game # - separated by ':':
    int indexOfColon = line.indexOf(':');
    String[] colonSplit = line.split(":");
    int gameId = Integer.parseInt(line.substring(5, indexOfColon));

    // The cubes - each 'grab' separated by ';'
    String cubesPart = colonSplit[1];
    String[] grabbedCubes = cubesPart.split(";");

    List<Cube> cubes = new ArrayList<>();

    for (String cubeSetStr : grabbedCubes) {
      Cube cube = new Cube(cubeSetStr);
      cubes.add(cube);
    }

    return new Game(gameId, cubes);
  }

}
