package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Day18 {

  @Test
  public void testHasToSplit(){
    Assert.assertTrue(hasToSplit("[[[[0,7],4],[15,[0,13]]],[1,1]]"));
  }

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day18dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    Assert.assertEquals(4140, processSnailFishNumbers(inputData));

  }

  private int processSnailFishNumbers(List<String> inputData) {
    int counter = 2;

    String addedNumbers = addNumbers(inputData.get(0), inputData.get(1));
    String reduced = reduce(addedNumbers);
    while (counter < inputData.size()) {
      addedNumbers = addedNumbers(reduced, inputData.get(counter));
      reduced = reduce(addedNumbers);
    }

    return getMagnitude(reduced);
  }

  private int getMagnitude(String reduced) {
    return 0;
  }

  private String addedNumbers(String reduced, String s) {
    return "";
  }

  private String reduce(String number) {
    boolean toBeExploded = hasToExplode(number);
    boolean toBeSplitted = hasToSplit(number);
    while (toBeExploded || toBeSplitted){
      if(toBeExploded){
        number = explode(number);
      }
      if(toBeSplitted){
        number = split(number);
      }
      toBeExploded = hasToExplode(number);
      toBeSplitted = hasToSplit(number);
    }
    return number;
  }

  private String split(String number) {
    return "";
  }

  private String explode(String number) {
    return "";
  }

  private boolean hasToSplit(String number) {
    return Pattern.compile("0*[1-9]\\d").matcher(number).find();
  }

  private boolean hasToExplode(String number) {
    return Pattern.compile("").matcher(number).find();
  }

  private String addNumbers(String s, String s1) {
    return s.substring(0, s.length() - 1).concat(s1).concat("]");
  }
}
