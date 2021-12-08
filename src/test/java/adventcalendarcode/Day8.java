package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

  //First value is string length and second value is coresponding number
  private Map<Integer, Integer> mapPart1 = Stream.of(new Object[][]{
    {2, 1},
    {4, 4},
    {3, 7},
    {7, 8}
  }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Integer) data[1]));

  @Test
  public void testDayPart1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day8dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    List<String> inputNumbers = inputData.stream().map(v -> {
      String data = v.substring(v.indexOf("|") + 2, v.length());
      return data;
    }).collect(Collectors.toList());

    AtomicInteger totalNumbers = new AtomicInteger(0);

    inputNumbers.stream().forEach(v -> {
      String[] values = v.split(" ");
      Arrays.stream(values).forEach(s -> {
        Integer value = mapPart1.get(s.length());
        if (value != null) {
          totalNumbers.getAndIncrement();
        }
      });
    });

    Assert.assertEquals(26, totalNumbers.get());
  }

  @Test
  public void testDayPart2Example() throws IOException {
    Path path = Paths.get("src/test/resources/day8dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    int contador = 0;
    Long result = 0L;

    List<String> outputValues = getOutputValues(inputData);
    List<String> inputListNumbers = getInputListNumbers(inputData);

    for (String input : inputListNumbers) {
      result = result + getNumOutputPerLine(input, outputValues.get(contador));
      contador++;
    }

    Assert.assertEquals(Optional.of(61229L).get(), result);
  }

  @Test
  public void testDayPart1() throws IOException {
    Path path = Paths.get("src/test/resources/day8data.txt");
    List<String> inputData = Files.readAllLines(path);
    List<String> inputNumbers = inputData.stream().map(v -> {
      String data = v.substring(v.indexOf("|") + 2, v.length());
      return data;
    }).collect(Collectors.toList());

    AtomicInteger totalNumbers = new AtomicInteger(0);

    inputNumbers.stream().forEach(v -> {
      String[] values = v.split(" ");
      Arrays.stream(values).forEach(s -> {
        Integer value = mapPart1.get(s.length());
        if (value != null) {
          totalNumbers.getAndIncrement();
        }
      });
    });

    System.out.println("Total : " + totalNumbers.get());
  }

  @Test
  public void testDayPart2() throws IOException {
    Path path = Paths.get("src/test/resources/day8data.txt");
    List<String> inputData = Files.readAllLines(path);
    int contador = 0;
    Long result = 0L;

    List<String> outputValues = getOutputValues(inputData);
    List<String> inputListNumbers = getInputListNumbers(inputData);

    for (String input : inputListNumbers) {
      result = result + getNumOutputPerLine(input, outputValues.get(contador));
      contador++;
    }

    System.out.println("Total: " + result);
  }

  private int getNumOutputPerLine (String input, String outputValue){
    Map<String, String> mappper = fillMapper(input);
    List<String> outputList = Arrays.stream(outputValue.split(" ")).collect(Collectors.toList());
    String result = new String();
    String keyOrdered = new String();

    for(String output: outputList){
      keyOrdered = mappper.keySet().stream().filter(v -> output.length() == v.length() && stringToCharacterSet(output).containsAll(stringToCharacterSet(v))).findFirst().orElseThrow();
      result = result.concat(mappper.get(keyOrdered));
    }

    return Integer.valueOf(result);
  }

  private Map<String, String> fillMapper(String input){
    Map<String, String> result = new HashMap<>();
    result.put(getZero(input), "0");
    result.put(getOne(input), "1");
    result.put(getTwo(input), "2");
    result.put(getThree(input), "3");
    result.put(getFour(input), "4");
    result.put(getFive(input), "5");
    result.put(getSix(input), "6");
    result.put(getSeven(input), "7");
    result.put(getEight(input), "8");
    result.put(getNine(input), "9");
    return result;
  }

  private List<String> getInputListNumbers(List<String> inputData) {
    return inputData.stream().map(v -> {
      String data = v.substring(0, v.indexOf("|") - 1);
      return data;
    }).collect(Collectors.toList());
  }

  private List<String> getOutputValues(List<String> inputData) {
    return inputData.stream().map(v -> {
      String data = v.substring(v.indexOf("|") + 2, v.length());
      return data;
    }).collect(Collectors.toList());
  }

  private String getZero(String inputString) {
    String eight = getSix(inputString);
    String nine = getNine(inputString);
    Set<String> eightAndNine = new HashSet<>();
    eightAndNine.add(eight);
    eightAndNine.add(nine);
    return Arrays.asList(inputString.split(" "))
      .stream()
      .filter(v -> v.length() == 6 && !eightAndNine.contains(v)).findFirst().orElseThrow();
  }

  private String getOne(String inputString) {
    return getNumberBySize(Arrays.asList(inputString.split(" ")), 2);
  }

  private String getTwo(String inputString) {
    String three = getThree(inputString);
    String five = getFive(inputString);
    List<String> remainingNumbers = new ArrayList<>();
    remainingNumbers.addAll(Arrays.stream(inputString.split(" ")).filter(v -> v.length() == 5).collect(Collectors.toList()));
    remainingNumbers.removeAll(List.of(three, five));
    return remainingNumbers.get(0);
  }

  private String getThree(String inputString) {
    String one = getOne(inputString);
    return Arrays.asList(inputString.split(" "))
      .stream()
      .filter(v -> v.length() == 5 && stringToCharacterSet(v).containsAll(stringToCharacterSet(one))).findFirst().orElseThrow();
  }

  private String getFour(String inputString) {
    return getNumberBySize(Arrays.asList(inputString.split(" ")), 4);
  }

  private String getFive(String inputString) {
    String three = getThree(inputString);
    String six = getSix(inputString);
    List<String> remainingNumbers = new ArrayList<>();
    remainingNumbers.addAll(Arrays.asList(inputString.split(" ")));
    remainingNumbers.removeAll(List.of(three));
    return remainingNumbers
      .stream()
      .filter(v -> v.length() == 5 && stringToCharacterSet(six).containsAll(stringToCharacterSet(v))).findFirst().orElseThrow();
  }

  private String getSix(String inputString) {
    String one = getOne(inputString);
    String three = getThree(inputString);
    List<String> remainingNumbers = new ArrayList<>();
    remainingNumbers.addAll(Arrays.asList(inputString.split(" ")));
    remainingNumbers.removeAll(List.of(three));
    return remainingNumbers
      .stream()
      .filter(v -> v.length() == 6 && !stringToCharacterSet(v).containsAll(stringToCharacterSet(one))).findFirst().orElseThrow();
  }

  private String getSeven(String inputString) {
    return getNumberBySize(Arrays.asList(inputString.split(" ")), 3);
  }

  private String getEight(String inputString) {
    return getNumberBySize(Arrays.asList(inputString.split(" ")), 7);
  }

  private String getNine(String inputString) {
    String three = getThree(inputString);
    String four = getFour(inputString);
    Set<Character> threePlusFour = new HashSet<>();
    threePlusFour.addAll(stringToCharacterSet(three));
    threePlusFour.addAll(stringToCharacterSet(four));
    return Arrays.asList(inputString.split(" "))
      .stream()
      .filter(v -> v.length() == 6 && stringToCharacterSet(v).containsAll(threePlusFour)).findFirst().orElseThrow();
  }

  private String getNumberBySize(List<String> inputListNumbers, int size) {
    for (String value : inputListNumbers) {
      if (value.length() == size) {
        return value;
      }
    }
    return "";
  }

  private Set<Character> stringToCharacterSet(String s) {
    Set<Character> set = new HashSet<>();
    for (char c : s.toCharArray()) {
      set.add(c);
    }
    return set;
  }
}

