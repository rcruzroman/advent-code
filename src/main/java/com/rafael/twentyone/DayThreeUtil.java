package com.rafael.twentyone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayThreeUtil {

  public static char getCharByPosition(String data, int position) {
    return data.charAt(position);
  }

  public static long getNumElementsInListByPositionAndValue(Stream<String> stream, int position, char value) {
    return stream.filter(v -> getCharByPosition(v, position) == value).count();
  }


  public static List<String> getElementsInListByPositionAndValue(Stream<String> stream, int position, char value) {
    return stream.filter(v -> getCharByPosition(v, position) == value).collect(Collectors.toList());
  }

  public static String getOxigenRating(int totalCharsPerline, int contador, List<String> values) {
    long numZeros = 0;
    long numOnes = 0;
    List<String> binariesToAnalyze = new ArrayList<>();
    while (true) {
      if (contador >= totalCharsPerline) {
        System.out.println("Value not found");
        return "0";
      }
      numZeros = getNumElementsInListByPositionAndValue(values.stream(), contador, '0');
      numOnes = getNumElementsInListByPositionAndValue(values.stream(), contador, '1');

      if (numZeros > numOnes) {
        binariesToAnalyze = getElementsInListByPositionAndValue(values.stream(), contador, '0');
      } else {
        binariesToAnalyze = getElementsInListByPositionAndValue(values.stream(), contador, '1');
      }

      if (binariesToAnalyze.size() == 1) {
        return binariesToAnalyze.get(0);
      } else {
        contador++;
        return getOxigenRating(totalCharsPerline, contador, binariesToAnalyze);
      }
    }
  }

  public static String getCO2Rating(int totalCharsPerline, int contador, List<String> values) {
    long numZeros = 0;
    long numOnes = 0;
    List<String> binariesToAnalyze = new ArrayList<>();
    while (true) {
      if (contador >= totalCharsPerline) {
        System.out.println("Value not found");
        return "0";
      }
      numZeros = getNumElementsInListByPositionAndValue(values.stream(), contador, '0');
      numOnes = getNumElementsInListByPositionAndValue(values.stream(), contador, '1');
      if (numZeros <= numOnes) {
        binariesToAnalyze = getElementsInListByPositionAndValue(values.stream(), contador, '0');
      } else {
        binariesToAnalyze = getElementsInListByPositionAndValue(values.stream(), contador, '1');
      }
      if (binariesToAnalyze.size() == 1) {
        return binariesToAnalyze.get(0);
      } else {
        contador++;
        return getCO2Rating(totalCharsPerline, contador, binariesToAnalyze);
      }
    }
  }
}
