package adventcalendarcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day14 {

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day14dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    Assert.assertEquals(Long.valueOf(1588), processTemplate(inputData.get(0), getInsertionRules(inputData), 10));
  }

  @Test
  public void part1() throws IOException {
    Path path = Paths.get("src/test/resources/day14data.txt");
    List<String> inputData = Files.readAllLines(path);
    System.out.println("Total: " + processTemplate(inputData.get(0), getInsertionRules(inputData), 10));

  }

  @Test
  public void part2() throws IOException {
    Path path = Paths.get("src/test/resources/day14data.txt");
    List<String> inputData = Files.readAllLines(path);
    System.out.println("Total: " + processTemplate(inputData.get(0), getInsertionRules(inputData), 40));
  }

  private Long processTemplate(String template, Map<String, String> insertionRules, int steps) {
    Map<String, Long> pairValues = new HashMap<>();
    Map<Character, Long> characters = new HashMap<>();
    template.chars().forEach(c -> addToCharacters(characters, (char)c, null));

    for (int i = 0; i < template.length() - 1; i++) {
      addToMapPairs(pairValues, template.substring(i, i + 2), null);
    }

    for (int step = steps; step > 0; step--) {
      Map<String, Long> newPairValues = new HashMap<>();
      for (String singlePair : pairValues.keySet()) {
        long increment = pairValues.get(singlePair);
        if (insertionRules.containsKey(singlePair)) {
          String key = insertionRules.get(singlePair);
          addToCharacters(characters, key.charAt(0), increment);
          addToMapPairs(newPairValues, singlePair.substring(0, 1) + key, increment);
          addToMapPairs(newPairValues, key + singlePair.substring(1, 2), increment);
        } else {
          addToMapPairs(newPairValues, singlePair, increment);
        }
      }
      pairValues = newPairValues;
    }

    return characters.values().stream().mapToLong(l -> l).max().getAsLong() - characters.values().stream().mapToLong(l -> l).min().getAsLong();

  }

  private Map<String, String> getInsertionRules(List<String> inputData) {
    Map<String, String> insertionRules = new HashMap<>();
    for (String input : inputData) {
      if (input.contains("->")) {
        insertionRules.put(input.split("->")[0].trim(), input.split("->")[1].trim());
      }
    }
    return insertionRules;
  }

  private Long addToMapPairs(Map<String, Long> pairLetters, String key, Long count) {
    if (count != null) {
      return pairLetters.put(key, pairLetters.containsKey(key) ? pairLetters.get(key) + count : count);
    } else {
      return pairLetters.put(key, pairLetters.containsKey(key) ? pairLetters.get(key) + 1 : 1);
    }
  }

  private Long addToCharacters(Map<Character, Long> pairLetters, Character key, Long count) {
    if (count != null) {
      return pairLetters.put(key, pairLetters.containsKey(key) ? pairLetters.get(key) + count : count);
    } else {
      return pairLetters.put(key, pairLetters.containsKey(key) ? pairLetters.get(key) + 1 : 1);
    }
  }
}
