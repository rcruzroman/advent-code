package adventcalendarcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day9 {

  @Test
  public void testPart1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day9dataexample.txt");
    List<String> inputData = Files.readAllLines(path);

    int[][] data = new int[inputData.size()][10];
    int contador = 0;
    int sum = 0;
    int sizeLine = 10;

    for (String line : inputData) {
      data[contador] = getData(sizeLine, line);
      contador++;
    }

    for (int i = 0; i < inputData.size(); i++) {
      for (int j = 0; j < sizeLine; j++) {
        if (isLowest(data, data[i][j], i, j)) {
          sum = sum + (data[i][j] + 1);
        }
      }
    }
    Assert.assertEquals(15, sum);
  }

  @Test
  public void testPart2Example() throws IOException {
    Path path = Paths.get("src/test/resources/day9dataexample.txt");
    List<String> inputData = Files.readAllLines(path);

    int[][] data = new int[inputData.size()][10];
    int contador = 0;
    int sizeLine = 10;
    List<Integer> basinsSize = new ArrayList<>();

    for (String line : inputData) {
      data[contador] = getData(sizeLine, line);
      contador++;
    }

    for (int i = 0; i < inputData.size(); i++) {
      for (int j = 0; j < sizeLine; j++) {
        if (isLowest(data, data[i][j], i, j)) {
          basinsSize.add(getBasinsSize(data, i, j));
        }
      }
    }

    Collections.sort(basinsSize, Collections.reverseOrder());

    Assert.assertEquals(1134, basinsSize.get(0) * basinsSize.get(1) * basinsSize.get(2));

  }

  private Integer getBasinsSize(int[][] data, int row, int col) {

    int[][] dataNextToLowest = new int[data.length][data[0].length];
    dataNextToLowest[row][col] = 1;
    //To bottom
    for (int i = row; i < data.length; i++) {
      for (int j = col; j < data[i].length; j++) {
        if (j >= 0 && j < data[i].length) {
          if (data[i][j] != 9 && (isOneAboveOrBelow(dataNextToLowest, i, j) || isOneLeftOrRight(dataNextToLowest, i, j))) {
            dataNextToLowest[i][j] = 1;
          }
        }
      }

      for (int j = col; j >= 0; j--) {
        if (j >= 0 && col != j && j < data[i].length) {
          if (data[i][j] != 9 && (isOneAboveOrBelow(dataNextToLowest, i, j) || isOneLeftOrRight(dataNextToLowest, i, j))) {
            dataNextToLowest[i][j] = 1;
          }
        }
      }
    }

    //To top
    for (int i = row; i >= 0; i--) {
      for (int j = col; j < data[i].length; j++) {
        if (j >= 0 && j < data[i].length) {
          if (data[i][j] != 9 && (isOneAboveOrBelow(dataNextToLowest, i, j) || isOneLeftOrRight(dataNextToLowest, i, j))) {
            dataNextToLowest[i][j] = 1;
          }
        }
      }

      for (int j = col; j >= 0; j--) {
        if (j >= 0 && j < data[row].length) {
          if (data[i][j] != 9 && (isOneAboveOrBelow(dataNextToLowest, i, j) || isOneLeftOrRight(dataNextToLowest, i, j))) {
            dataNextToLowest[i][j] = 1;
          }
        }
      }
    }
//    System.out.println(" ");
    AtomicInteger numElements = new AtomicInteger(0);
    for (int aux = 0; aux < data.length; aux++) {
//      Arrays.stream(dataNextToLowest[aux]).forEach(v -> System.out.print(v));
      Arrays.stream(dataNextToLowest[aux]).forEach(v -> numElements.getAndAdd(v));
//      System.out.println(" ");
    }
//    System.out.println(" ");

    return numElements.get();
  }

  private boolean isOneAboveOrBelow(int[][] data, int i, int j) {
    return (i - 1 >= 0 && i < data.length && data[i - 1][j] == 1) || (i >= 0 && i + 1 < data.length && data[i + 1][j] == 1);
  }

  private boolean isOneLeftOrRight(int[][] data, int i, int j) {
    return (j - 1 >= 0 && j < data[i].length && data[i][j - 1] == 1) || (j >= 0 && j + 1 < data[i].length && data[i][j + 1] == 1);
  }

  @Test
  public void testPart1() throws IOException {
    Path path = Paths.get("src/test/resources/day9data.txt");
    List<String> inputData = Files.readAllLines(path);

    int[][] data = new int[inputData.size()][10];
    int contador = 0;
    int sum = 0;
    int sizeLine = 100;

    for (String line : inputData) {
      data[contador] = getData(sizeLine, line);
      contador++;
    }

    for (int i = 0; i < inputData.size(); i++) {
      for (int j = 0; j < sizeLine; j++) {
        if (isLowest(data, data[i][j], i, j)) {
          sum = sum + (data[i][j] + 1);
        }
      }
    }
    System.out.println("Total: " + sum);
  }

  @Test
  public void testPart2() throws IOException {
    Path path = Paths.get("src/test/resources/day9data.txt");
    List<String> inputData = Files.readAllLines(path);

    int sizeLine = 100;
    int[][] data = new int[inputData.size()][sizeLine];
    int contador = 0;
    List<Integer> basinsSize = new ArrayList<>();

    for (String line : inputData) {
      data[contador] = getData(sizeLine, line);
      contador++;
    }

    for (int i = 0; i < inputData.size(); i++) {
      for (int j = 0; j < sizeLine; j++) {
        if (isLowest(data, data[i][j], i, j)) {
          basinsSize.add(getBasinsSize(data, i, j));
        }
      }
    }

    Collections.sort(basinsSize, Collections.reverseOrder());

    System.out.println("Total: " + basinsSize.get(0) * basinsSize.get(1) * basinsSize.get(2));

  }

  private int[] getData(int sizeLine, String lineData) {
    int[] data = new int[sizeLine];
    for (int i = 0; i < sizeLine; i++) {
      data[i] = Integer.valueOf(lineData.substring(i, i + 1));
    }
    return data;
  }

  public boolean isLowest(int[][] data, int value, int row, int col) {
    boolean isLowest = true;
    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = col - 1; j <= col + 1; j++) {
        if (i >= 0 && j >= 0 && (row != i || col != j) && i < data.length && j < data[row].length) {
          isLowest = isLowest && (value < data[i][j]);
        }
      }
    }
    return isLowest;
  }
}
