package adventcalendarcode;

import com.rafael.twentyone.CustomLifo;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day10 {

  private static final Map<String, String> charactersMap = new HashMap<>();

  static {
    charactersMap.put("}", "{");
    charactersMap.put(")", "(");
    charactersMap.put("]", "[");
    charactersMap.put(">", "<");
  }

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day10dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    List<List<String>> data = new ArrayList<>();

    for (String line : inputData) {
      data.add(getData(line));
    }

    Assert.assertEquals(26397, getScore(data, true));
  }

  @Test
  public void part2Example() throws IOException {
    Path path = Paths.get("src/test/resources/day10dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    List<List<String>> data = new ArrayList<>();

    for (String line : inputData) {
      data.add(getData(line));
    }

    Assert.assertEquals(288957, getScore(data, false));
  }

  @Test
  public void part1() throws IOException {
    Path path = Paths.get("src/test/resources/day10data.txt");
    List<String> inputData = Files.readAllLines(path);
    List<List<String>> data = new ArrayList<>();

    for (String line : inputData) {
      data.add(getData(line));
    }

    System.out.println("Total: " + getScore(data, true));
  }

  @Test
  public void part2() throws IOException {
    Path path = Paths.get("src/test/resources/day10data.txt");
    List<String> inputData = Files.readAllLines(path);
    List<List<String>> data = new ArrayList<>();

    for (String line : inputData) {
      data.add(getData(line));
    }

    System.out.println("Total: " + getScore(data, false));
  }

  private long getScore(List<List<String>> data, boolean part1) {
    long resultPart1 = 0;
    List<Long> resultPart2 = new ArrayList<>();
    CustomLifo<String> openedChars;
    boolean isCorrupted;

    for (List<String> line : data) {
      isCorrupted = false;
      openedChars = new CustomLifo<>();
      for (String character : line) {
        if (!isOpenChar(character)) {
          if (!charactersMap.get(character).equals(openedChars.peek())) {
            resultPart1 = resultPart1 + getPointsPerCharacter(character);
            isCorrupted = true;
            break;
          }
          openedChars.pop();
        } else {
          openedChars.add(character);
        }
      }

      if (!isCorrupted) {
        resultPart2.add(getPointsCharactersToBeClosed(openedChars));
      }
    }

    if (part1) {
      return resultPart1;
    } else {
      Collections.sort(resultPart2);
      return resultPart2.get((resultPart2.size() / 2));
    }
  }

  private long getPointsCharactersToBeClosed(CustomLifo<String> openedChars) {
    long total = 0;
    Collections.reverse(openedChars);
    for (String value : openedChars) {
      total = total * 5;
      total = total + getPointsPerCharacterPart2(value);
    }
    return total;
  }

  private boolean isOpenChar(String character) {
    return character.equals("{") || character.equals("(") || character.equals("[") || character.equals("<");
  }

  private int getPointsPerCharacter(String character) {
    switch (character) {
      case ")":
        return 3;
      case "]":
        return 57;
      case "}":
        return 1197;
      case ">":
        return 25137;
      default:
        return 0;
    }
  }

  private int getPointsPerCharacterPart2(String character) {
    switch (character) {
      case "(":
        return 1;
      case "[":
        return 2;
      case "{":
        return 3;
      case "<":
        return 4;
      default:
        return 0;
    }
  }

  private List<String> getData(String lineData) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < lineData.length(); i++) {
      result.add(lineData.substring(i, i + 1));
    }
    return result;
  }
}
