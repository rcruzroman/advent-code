package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

  @Test
  public void getLowestCostHorizontalPositionExample() throws IOException {
    Path path = Paths.get("src/test/resources/day7dataexample.txt");
    List<Integer> crabPositionsList = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(v -> Integer.valueOf(v)).collect(Collectors.toList());

    int maxPosition = crabPositionsList.stream().mapToInt(v -> Integer.valueOf(v)).max().orElseThrow(NoSuchElementException::new);

    List<Integer> fuelPerPosition = new ArrayList<Integer>(Collections.nCopies(maxPosition + 1, 0));
    populateFuelPerPosition(crabPositionsList, fuelPerPosition);

    System.out.println(fuelPerPosition);
    Assert.assertEquals(37, fuelPerPosition.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new));
  }

  @Test
  public void getLowestCostHorizontalPositionExamplePart2() throws IOException {
    Path path = Paths.get("src/test/resources/day7dataexample.txt");
    List<Integer> crabPositionsList = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(v -> Integer.valueOf(v)).collect(Collectors.toList());

    int maxPosition = crabPositionsList.stream().mapToInt(v -> Integer.valueOf(v)).max().orElseThrow(NoSuchElementException::new);

    List<Integer> fuelPerPosition = new ArrayList<Integer>(Collections.nCopies(maxPosition + 1, 0));
    populateFuelPerPositionSecondPart(crabPositionsList, fuelPerPosition);

    System.out.println(fuelPerPosition);
    Assert.assertEquals(168, fuelPerPosition.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new));
  }

  @Test
  public void getLowestCostHorizontalPosition() throws IOException {
    Path path = Paths.get("src/test/resources/day7data.txt");
    List<Integer> crabPositionsList = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(v -> Integer.valueOf(v)).collect(Collectors.toList());

    int maxPosition = crabPositionsList.stream().mapToInt(v -> Integer.valueOf(v)).max().orElseThrow(NoSuchElementException::new);

    List<Integer> fuelPerPosition = new ArrayList<Integer>(Collections.nCopies(maxPosition + 1, 0));
    populateFuelPerPosition(crabPositionsList, fuelPerPosition);

    System.out.println("Min Fuel: " + fuelPerPosition.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new));
  }

  @Test
  public void getLowestCostHorizontalPositionPart2() throws IOException {
    Path path = Paths.get("src/test/resources/day7data.txt");
    List<Integer> crabPositionsList = Arrays.stream(Files.readAllLines(path).get(0).split(",")).map(v -> Integer.valueOf(v)).collect(Collectors.toList());

    int maxPosition = crabPositionsList.stream().mapToInt(v -> Integer.valueOf(v)).max().orElseThrow(NoSuchElementException::new);

    List<Integer> fuelPerPosition = new ArrayList<Integer>(Collections.nCopies(maxPosition + 1, 0));
    populateFuelPerPositionSecondPart(crabPositionsList, fuelPerPosition);

    System.out.println("Min Fuel: " + fuelPerPosition.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new));
  }

  private void populateFuelPerPosition(List<Integer> crabPositionsList, List<Integer> fuelPerPosition) {
    Integer horizontalPosition = 0;
    while (horizontalPosition < fuelPerPosition.size()) {
      for (Integer crabPosition : crabPositionsList) {
        fuelPerPosition.set(horizontalPosition, fuelPerPosition.get(horizontalPosition) + (Math.max(horizontalPosition, crabPosition) - Math.min(horizontalPosition, crabPosition)));
      }
      horizontalPosition++;
    }
  }

  private void populateFuelPerPositionSecondPart(List<Integer> crabPositionsList, List<Integer> fuelPerPosition) {
    Integer horizontalPosition = 0;
    while (horizontalPosition < fuelPerPosition.size()) {
      for (Integer crabPosition : crabPositionsList) {
        fuelPerPosition.set(horizontalPosition, fuelPerPosition.get(horizontalPosition) + sumSerie((Math.max(horizontalPosition, crabPosition) - Math.min(horizontalPosition, crabPosition))));
      }
      horizontalPosition++;
    }
  }

  private int sumSerie(int max) {
    int sum = 0;
    int counter = 1;
    while (counter <= max) {
      sum = sum + counter;
      counter++;
    }
    return sum;
  }
}
