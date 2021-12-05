package com.rafael.twentyone;

import java.util.List;
import java.util.stream.Collectors;

public class DayFiveUtil {

  public static int[][] intializeArray(int size) {
    int[][] result = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        result[i][j] = 0;
      }
    }
    return result;
  }

  public static List<VentPosition> getPositionList(List<String> lines) {
    List<VentPosition> listValues = lines.stream()
      .map(line -> line.replaceAll("->", ","))
      .map(line -> new VentPosition(line.split(",")[0], line.split(",")[1], line.split(",")[2], line.split(",")[3])).collect(Collectors.toList());

    return listValues;
  }

  public static void populateMatrix(List<VentPosition> positions, int[][] matrix, boolean withDiagonals) {
    for (VentPosition ventPosition : positions) {
      if (ventPosition.getX0() == ventPosition.getX1() || ventPosition.getY0() == ventPosition.getY1()) {
        for (int i = Math.min(ventPosition.getY0(), ventPosition.getY1()); i <= Math.max(ventPosition.getY0(), ventPosition.getY1()); i++) {
          for (int j = Math.min(ventPosition.getX0(), ventPosition.getX1()); j <= Math.max(ventPosition.getX0(), ventPosition.getX1()); j++) {
            matrix[i][j] = matrix[i][j] + 1;
          }
        }
      } else if (withDiagonals && ventPosition.getX0() == ventPosition.getY0() && ventPosition.getX1() == ventPosition.getY1()) {
        for (int i = Math.min(ventPosition.getY0(), ventPosition.getY1()); i <= Math.max(ventPosition.getY0(), ventPosition.getY1()); i++) {
          matrix[i][i] = matrix[i][i] + 1;
        }
      } else if (withDiagonals) {
        for (int y = Math.min(ventPosition.getY0(), ventPosition.getY1()); y <= Math.max(ventPosition.getY0(), ventPosition.getY1()); y++) {
          for (int x = Math.max(ventPosition.getX0(), ventPosition.getX1()); x >= Math.min(ventPosition.getX0(), ventPosition.getX1()); x--) {
            if ((x + y) == Math.max(ventPosition.getX0(), ventPosition.getX1())) {
              matrix[y][x] = matrix[y][x] + 1;
            }
          }
        }
      }
    }
  }


  public static int findOverlap(int[][] matrix) {
    int total = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] > 1) {
          total++;
        }
      }
    }
    return total;
  }

  public static String toStringMatrix(int[][] matrix) {
    String str = "";

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        str += matrix[i][j] + "\t";
      }
      str += "\n";
    }
    return str;
  }
}
