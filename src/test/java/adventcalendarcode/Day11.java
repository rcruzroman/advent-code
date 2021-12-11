package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day11 {

  @Test
  public void test1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day11dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    int sizeLine = 10;
    int steps = 100;
    int numFlashes = 0;

    int[][] matrix = populateMatrix(inputData, sizeLine);

    for (int counter = 0; counter < steps; counter++) {
      matrixPlusOne(matrix);
      numFlashes = numFlashes + evaluateMaxEnergy(matrix);
    }

    Assert.assertEquals(1656, numFlashes);
  }

  @Test
  public void test2Example() throws IOException {
    Path path = Paths.get("src/test/resources/day11dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    int sizeLine = 10;
    int step = 1;

    int[][] matrix = populateMatrix(inputData, sizeLine);

    while (true) {
      matrixPlusOne(matrix);
      evaluateMaxEnergy(matrix);
      if (allIsIluimnated(matrix)) {
        break;
      }
      step++;
    }

    Assert.assertEquals(195, step);
  }

  @Test
  public void test1() throws IOException {
    Path path = Paths.get("src/test/resources/day11data.txt");
    List<String> inputData = Files.readAllLines(path);
    int sizeLine = 10;
    int steps = 100;
    int numFlashes = 0;

    int[][] matrix = populateMatrix(inputData, sizeLine);

    for (int counter = 0; counter < steps; counter++) {
      matrixPlusOne(matrix);
      numFlashes = numFlashes + evaluateMaxEnergy(matrix);
    }

    System.out.println("NumFlashes: " + numFlashes);
  }

  @Test
  public void test2() throws IOException {
    Path path = Paths.get("src/test/resources/day11data.txt");
    List<String> inputData = Files.readAllLines(path);
    int sizeLine = 10;
    int step = 1;

    int[][] matrix = populateMatrix(inputData, sizeLine);

    while (true) {
      matrixPlusOne(matrix);
      evaluateMaxEnergy(matrix);
      if (allIsIluimnated(matrix)) {
        break;
      }
      step++;
    }

    System.out.println("All Illumunated in : " + step);
  }

  private int evaluateMaxEnergy(int[][] matrix) {
    int numFlashes = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == -1) {
          numFlashes++;
          processMaximumEnergyAround(matrix, i, j);
        }
      }
    }
    if (arePendingElementsToBeEvaluated(matrix)) {
      return evaluateMaxEnergy(matrix) + numFlashes;
    } else {
      return numFlashes;
    }
  }

  private boolean arePendingElementsToBeEvaluated(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == -1) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean allIsIluimnated(int[][] matrix) {
    boolean allIluminated = true;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] != 0) {
          allIluminated = false;
          break;
        }
      }
    }
    return allIluminated;
  }

  private void matrixPlusOne(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == 9) {
          matrix[i][j] = -1;
        } else {
          matrix[i][j]++;
        }
      }
    }
  }

  private int[][] populateMatrix(List<String> inputData, int sizeLine) {
    int[][] data = new int[inputData.size()][10];
    int contador = 0;

    for (String line : inputData) {
      data[contador] = getData(sizeLine, line);
      contador++;
    }
    return data;
  }

  private int[] getData(int sizeLine, String lineData) {
    int[] data = new int[sizeLine];
    for (int i = 0; i < sizeLine; i++) {
      data[i] = Integer.valueOf(lineData.substring(i, i + 1));
    }
    return data;
  }

  private void processMaximumEnergyAround(int[][] data, int row, int col) {
    data[row][col] = 0;
    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = col - 1; j <= col + 1; j++) {
        if (i >= 0 && j >= 0 && (row != i || col != j) && i < data.length && j < data[row].length) {
          if (data[i][j] != 0 && data[i][j] != -1) {
            data[i][j] = data[i][j] + 1;
            if (data[i][j] > 9) {
              data[i][j] = -1;
            }
          }
        }
      }
    }
  }

  private void drawMatrix(int[][] matrix) {
    System.out.println(" ");
    for (int i = 0; i < matrix.length; i++) {
      Arrays.stream(matrix[i]).forEach(v -> System.out.print(v));
      System.out.println(" ");
    }
    System.out.println(" ");
  }
}
