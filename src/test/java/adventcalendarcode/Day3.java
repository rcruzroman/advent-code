package adventcalendarcode;

import com.rafael.twentyone.DayThreeUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

  private static final String ZERO = "0";
  private static final String ONE = "1";

  @Test
  public void getGammaAndEpsilonRate() throws IOException {
    int contador = 0;
    long numZeros = 0;
    long numOnes = 0;
    String gammaRateBinary = new String();
    String epsilonRateBinary = new String();
    Path path = Paths.get("src/test/resources/day3data.txt");
    int numCharacters = Files.readAllLines(path).get(0).length();

    while (contador < numCharacters) {
      numZeros = DayThreeUtil.getNumElementsInListByPositionAndValue(Files.readAllLines(path).stream(), contador, '0');
      numOnes = DayThreeUtil.getNumElementsInListByPositionAndValue(Files.readAllLines(path).stream(), contador, '1');

      if (numZeros > numOnes) {
        gammaRateBinary = gammaRateBinary.concat(ZERO);
        epsilonRateBinary = epsilonRateBinary.concat(ONE);
      } else {
        gammaRateBinary = gammaRateBinary.concat(ONE);
        epsilonRateBinary = epsilonRateBinary.concat(ZERO);
      }
      contador++;
    }

    System.out.println("gammaBinary: " + gammaRateBinary);
    System.out.println("epsilonBinary: " + epsilonRateBinary);
    System.out.println("gammaDecimal:" + Integer.parseInt(gammaRateBinary, 2));
    System.out.println("epsilonDecimal:" + Integer.parseInt(epsilonRateBinary, 2));
    System.out.println("Total: " + Integer.parseInt(gammaRateBinary, 2) * Integer.parseInt(epsilonRateBinary, 2));
  }

  @Test
  public void getOxigenAndCO2ScrubberRating() throws IOException {
    String oxigenBinary = new String();
    String co2Binary = new String();
    Path path = Paths.get("src/test/resources/day3data.txt");
    int numCharacters = Files.readAllLines(path).get(0).length();

    System.out.println("Total Lineas" + Files.readAllLines(path).stream().count());

    List<String> binariesToAnalyze = Files.readAllLines(path).stream().collect(Collectors.toList());
    oxigenBinary = DayThreeUtil.getOxigenRating(numCharacters, 0, binariesToAnalyze);
    co2Binary = DayThreeUtil.getCO2Rating(numCharacters, 0, binariesToAnalyze);
    System.out.println("oxigenBinary: " + oxigenBinary);
    System.out.println("co2Binary: " + co2Binary);
    System.out.println("Total: " + Integer.parseInt(oxigenBinary, 2) * Integer.parseInt(co2Binary, 2));
  }
}
