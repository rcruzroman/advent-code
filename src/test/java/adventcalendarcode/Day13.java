package adventcalendarcode;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

  @Test
  public void testPart1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day13dataexample.txt");
    List<String> inputData = Files.readAllLines(path);

    String[][] matrix = new String[numRowsMatrix(inputData)][numColumnMatrix(inputData)];
    initializeMatrix(matrix);

    processInputData(inputData, matrix);

    String[][] finalMatrix = foldMatrix(inputData, matrix, 1);
    drawMatrix(finalMatrix);

    long numTags = Arrays.stream(finalMatrix).flatMap(x -> Arrays.stream(x)).filter(v -> v.equals("#")).count();

    Assert.assertEquals(17l, numTags);

  }

  @Test
  public void testPart1() throws IOException {
    Path path = Paths.get("src/test/resources/day13data.txt");
    List<String> inputData = Files.readAllLines(path);

    String[][] matrix = new String[numRowsMatrix(inputData)][numColumnMatrix(inputData)];
    initializeMatrix(matrix);

    processInputData(inputData, matrix);

    String[][] finalMatrix = foldMatrix(inputData, matrix, 1);

    long numTags = Arrays.stream(finalMatrix).flatMap(x -> Arrays.stream(x)).filter(v -> v.equals("#")).count();

    System.out.println("Num Tags: " + numTags);

  }

  @Test
  public void testPart2() throws IOException {
    Path path = Paths.get("src/test/resources/day13data.txt");
    List<String> inputData = Files.readAllLines(path);

    String[][] matrix = new String[numRowsMatrix(inputData)][numColumnMatrix(inputData)];
    initializeMatrix(matrix);

    processInputData(inputData, matrix);

    String[][] finalMatrix = foldMatrix(inputData, matrix, -1);
    drawMatrix(finalMatrix);

  }

  private String[][] initializeMatrix(String[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] = ".";
      }
    }
    return matrix;
  }

  private void processInputData(List<String> inputData, String[][] data) {
    inputData.stream().forEach(l -> {
      int x = 0;
      int y = 0;
      if (!StringUtils.isEmpty(l) && !l.contains("fold")) {
        x = Integer.valueOf(l.split(",")[1]);
        y = Integer.valueOf(l.split(",")[0]);
        data[x][y] = "#";
      }
    });
  }

  private void drawMatrix(String[][] matrix) {
    System.out.println(" ");
    for (int i = 0; i < matrix.length; i++) {
      Arrays.stream(matrix[i]).forEach(v -> System.out.print(v));
      System.out.println(" ");
    }
    System.out.println(" ");
  }

  private int numRowsMatrix(List<String> inputData) {
    int maxNumber = inputData.stream().filter(l -> l.contains(",")).mapToInt(s -> Integer.valueOf(s.substring(s.indexOf(",") + 1, s.length()))).max().orElseThrow();
    maxNumber++;
    return maxNumber;
  }

  private int numColumnMatrix(List<String> inputData) {
    int maxNumber = inputData.stream().filter(l -> l.contains(",")).mapToInt(s -> Integer.valueOf(s.substring(0, s.indexOf(",")))).max().orElseThrow();
    maxNumber++;
    return maxNumber;
  }

  private String[][] foldMatrix(List<String> inputData, String[][] matrix, int numTimes) {
    List<String> folds = inputData.stream().filter(l -> l.contains("fold")).map(s ->
      s.replaceAll("fold along ", "")
    ).collect(Collectors.toList());

    String[][] newMatrix = null;
    int contador = 0;
    int internalCounter = 1;

    for (String fold : folds) {
      if(contador == numTimes){
        break;
      }
      int value = Integer.valueOf(fold.split("=")[1]) + 1;
      if(contador == 0) {
        newMatrix = getNewAuxMatrix(fold, matrix);
      }else{
        newMatrix = getNewAuxMatrix(fold, newMatrix);
      }
      if (fold.contains("x")) {
        for (int i = 0; i < matrix.length; i++) {
          internalCounter = 1;
          for (int j = value; j < matrix[i].length; j++) {
            matrix[i][j - (internalCounter*2)] = matrix[i][j - (internalCounter*2)] == "#" ? "#" : matrix[i][j];
            internalCounter++;
          }
        }
      } else {
        internalCounter = 1;
        for (int i = value; i < matrix.length; i++) {
          for (int j = 0; j < matrix[i].length; j++) {
            matrix[i - (internalCounter*2)][j] = matrix[i - (internalCounter*2)][j] == "#" ? "#" : matrix[i][j];
          }
          internalCounter++;
        }
      }
      newMatrix = copyMatrix(matrix, newMatrix);
      matrix = newMatrix.clone();
      contador++;
    }
    return newMatrix;
  }

  private String[][] getNewAuxMatrix(String fold, String[][] matrix) {
    String line = fold.substring(0, 1);
    Integer value = Integer.valueOf(fold.split("=")[1]);
    if (line.equals("x")) {
      return initializeMatrix(new String[matrix.length][value]);
    } else {
      return initializeMatrix(new String[value][matrix[0].length]);
    }
  }

  private String[][] copyMatrix(String[][] matrixOrigin, String[][] matrixDestination) {
    for (int i = 0; i < matrixDestination.length; i++) {
      matrixDestination[i] = Arrays.copyOfRange(matrixOrigin[i], 0, matrixDestination[i].length);
    }
    return matrixDestination;
  }
}
