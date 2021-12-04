package adventcalendarcode;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

  @Test
  public void getHorizontalPositionAndDepth() throws Exception {
    String expected_value = "Hello, world!";
    AtomicInteger horizontal = new AtomicInteger(0);
    AtomicInteger depth = new AtomicInteger(0);

    Path path = Paths.get("src/test/resources/day2data.txt");
    Files.readAllLines(path).stream().forEach(value -> {

        Pattern p = Pattern.compile("([a-zA-Z]+) (\\d+)");
        Matcher m = p.matcher(value);
        String direction = new String();
        Integer units = 0;
        if (m.find()) {
          direction = m.group(1);
          units = Integer.valueOf(m.group(2));

          switch (direction) {
            case "forward":
              horizontal.getAndAdd(units);
              break;
            case "down":
              depth.getAndAdd(units);
              break;
            case "up":
              depth.getAndAdd(-units);
              break;
            default:
              System.out.println("invalid data");
          }
        }
      });

        System.out.println("horizontal: " + horizontal.get() + " depth: " + depth.get());
        System.out.println("total: " + horizontal.get() * depth.get());
  }

  @Test
  public void getHorizontalPositionAndDepthPart2() throws Exception {
    String expected_value = "Hello, world!";
    AtomicInteger horizontal = new AtomicInteger(0);
    AtomicInteger depth = new AtomicInteger(0);
    AtomicInteger aim = new AtomicInteger(0);

    Path path = Paths.get("src/test/resources/day2data.txt");

    Files.readAllLines(path).stream().forEach(value -> {

      Pattern p = Pattern.compile("([a-zA-Z]+) (\\d+)");
      Matcher m = p.matcher(value);
      String direction = new String();
      Integer units = 0;
      if (m.find()) {
        direction = m.group(1);
        units = Integer.valueOf(m.group(2));

        switch (direction) {
          case "forward":
            horizontal.getAndAdd(units);
            depth.getAndAdd(aim.get() * units);
            break;
          case "down":
            aim.getAndAdd(units);
            break;
          case "up":
            aim.getAndAdd(-units);
            break;
          default:
            System.out.println("invalid data");
        }

      }
    });

    System.out.println("horizontal: " + horizontal.get() + " depth: " + depth.get());
    System.out.println("total: " + horizontal.get() * depth.get());
  }

}
