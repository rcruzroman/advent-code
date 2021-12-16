package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day15dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    int[][] matrix = new int[inputData.size()][inputData.get(0).length()];
    processInputData(inputData, matrix);
    ConcurrentLinkedDeque<Integer> risks = new ConcurrentLinkedDeque<>();
    findPaths(matrix, new Stack<>(), 0, 0, risks);
    Assert.assertEquals(40, risks.stream().mapToInt(i -> i).min().getAsInt());

  }

  @Test
  public void part1() throws IOException {
    Path path = Paths.get("src/test/resources/day15data.txt");
    List<String> inputData = Files.readAllLines(path);
    int[][] matrix = new int[inputData.size()][inputData.get(0).length()];
    processInputData(inputData, matrix);
    ConcurrentLinkedDeque<Integer> risks = new ConcurrentLinkedDeque<>();
    findPaths(matrix, new Stack<>(), 0, 0, risks);
    System.out.println("Total: " + risks.stream().mapToInt(i -> i).min().getAsInt());

  }

  private void processInputData(List<String> inputData, int[][] matrix) {
    AtomicInteger i = new AtomicInteger(0);
    inputData.stream().forEach(l -> {
      matrix[i.get()] = Arrays.stream(l.split(""))
        .mapToInt(Integer::parseInt)
        .toArray();
      i.getAndIncrement();
    });
  }

  public static void findPaths(int[][] mat, Stack<Integer> path, int i, int j, ConcurrentLinkedDeque<Integer> risks)
  {
    if (mat == null || mat.length == 0) {
      return;
    }

    int M = mat.length;
    int N = mat[0].length;

    if (i == M - 1 && j == N - 1)
    {
      path.add(mat[i][j]);
      int risk = IntStream.range(1, path.size())
        .filter(index -> index != 0)
        .mapToObj(v -> path.get(v))
        .collect(Collectors.toList())
        .stream()
        .mapToInt(e -> e)
        .sum();
      risks.add(risk);

      if(!risks.contains(risk)){
        risks.add(risk);
      }

      path.pop();

      return;
    }

    path.add(mat[i][j]);

    if ((i >= 0 && i < M && j + 1 >= 0 && j + 1 < N)) {
      findPaths(mat, path, i, j + 1, risks);
    }

    if ((i + 1 >= 0 && i + 1 < M && j >= 0 && j < N)) {
      findPaths(mat, path, i + 1, j, risks);
    }

    path.pop();
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
