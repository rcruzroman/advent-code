package adventcalendarcode;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day1 {


  @Test
  public void getNumberMeasurementsLargerThanPrevious() throws IOException {
    AtomicInteger previousValue = new AtomicInteger(0);
    AtomicInteger total = new AtomicInteger(0);

    Path path = Paths.get("src/test/resources/day1data.txt");

    System.out.println("Total Lineas" + Files.readAllLines(path).stream().count());

    Files.readAllLines(path).stream().forEach(value -> {
      if (previousValue.get() != 0) {
        if (Integer.valueOf(value) > previousValue.get()) {
          total.getAndIncrement();
        }
      }
      previousValue.set(Integer.valueOf(value));
    });
    System.out.println("total: " + total.get());
  }

  @Test
  public void getNumberMeasurementsLargerThanPreviousSecondPart() throws IOException {
    Integer value1 = 0;
    Integer value2 = 0;
    Integer value3 = 0;
    Integer previousSum = 0;
    Integer currentSum = 0;
    Integer total = 0;
    int i = 0;

    Path path = Paths.get("src/test/resources/day1data.txt");
    List<Integer> values = Files.readAllLines(path).stream().map(Integer::valueOf).collect(Collectors.toList());
    while (i < values.size()) {
      if ((i + 1) > values.size() - 1 || (i + 2) > values.size() - 1) {
        break;
      }
      value1 = values.get(i);
      value2 = values.get(i + 1);
      value3 = values.get(i + 2);
      i++;

      currentSum = value1 + value2 + value3;

      if (previousSum != 0) {
        if (currentSum > previousSum) {
          total++;
        }
      }
      previousSum = currentSum;

    }

    System.out.println("total: " + total);
  }
}
