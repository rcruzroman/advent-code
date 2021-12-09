package adventcalendarcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    }

    private Integer getBasinsSize(int[][] data, int i, int j) {
        return 0;
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
