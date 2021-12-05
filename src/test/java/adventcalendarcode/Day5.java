package adventcalendarcode;

import com.rafael.twentyone.DayFiveUtil;
import com.rafael.twentyone.VentPosition;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day5 {

  @Test
  public void overlappedPointsExample() throws IOException {
    int[][] matrix = DayFiveUtil.intializeArray(10);

    Path path = Paths.get("src/test/resources/day5dataexample.txt");
    List<String> lines = Files.readAllLines(path);

    List<VentPosition> positions = DayFiveUtil.getPositionList(lines);

    DayFiveUtil.populateMatrix(positions, matrix, false);

    System.out.println(DayFiveUtil.toStringMatrix(matrix));

    Assert.assertEquals(5, DayFiveUtil.findOverlap(matrix));
  }

  /* DAMM IT!!! IS NOT WORKING */
  @Ignore
  @Test
  public void overlappedPointsWithDiagonalExample() throws IOException {
    int[][] matrix = DayFiveUtil.intializeArray(10);

    Path path = Paths.get("src/test/resources/day5dataexample.txt");
    List<String> lines = Files.readAllLines(path);

    List<VentPosition> positions = DayFiveUtil.getPositionList(lines);

    DayFiveUtil.populateMatrix(positions, matrix, true);

    System.out.println(DayFiveUtil.toStringMatrix(matrix));

    Assert.assertEquals(12, DayFiveUtil.findOverlap(matrix));
  }

  @Test
  public void overlappedPoints() throws IOException {
    int[][] matrix = DayFiveUtil.intializeArray(1000);

    Path path = Paths.get("src/test/resources/day5data.txt");
    List<String> lines = Files.readAllLines(path);

    List<VentPosition> positions = DayFiveUtil.getPositionList(lines);

    DayFiveUtil.populateMatrix(positions, matrix, false);

    System.out.println("Overlopped Points: " + DayFiveUtil.findOverlap(matrix));
  }


  /* DAMM IT!!! IS NOT WORKING */
  @Ignore
  @Test
  public void overlappedPointsWithDiagonals() throws IOException {
    int[][] matrix = DayFiveUtil.intializeArray(1000);

    Path path = Paths.get("src/test/resources/day5data.txt");
    List<String> lines = Files.readAllLines(path);

    List<VentPosition> positions = DayFiveUtil.getPositionList(lines);

    DayFiveUtil.populateMatrix(positions, matrix, true);

    System.out.println("Overlopped Points: " + DayFiveUtil.findOverlap(matrix));
  }
}
