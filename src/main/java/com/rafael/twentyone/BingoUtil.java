package com.rafael.twentyone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BingoUtil {

  public void populateBingoBoard(List<String> valuesFromFile, List<BingoBoard> bingoBoards ) {
    BingoBoard bingoBoard = new BingoBoard();
    int row = 0;
    int col = 0;
    BingoElement bingoElement1InLine;
    BingoElement bingoElement2InLine;
    BingoElement bingoElement3InLine;
    BingoElement bingoElement4InLine;
    BingoElement bingoElement5InLine;

    for (String line : valuesFromFile) {
      if (line.isEmpty()) {
        bingoBoards.add(bingoBoard);
        row = 0;
        bingoBoard = new BingoBoard();
        continue;
      }

      List<String> numbers = Arrays.asList(line.trim().split("( )+"));
      fillRowsBingoBoard(row, numbers, bingoBoard);
      row++;
    }
  }

  public void fillRowsBingoBoard(int row, List<String> numbers, BingoBoard bingoBoard) {
    for (int col = 0; col < 5; col++){
      bingoBoard.getNumbers()[row][col] = new BingoElement(row, col, Integer.valueOf(numbers.get(col)));
    }
  }

  public void markedNumberInBingoBoard(BingoBoard bingoBoard, int number) {
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (!bingoBoard.getNumbers()[row][col].isMarked()) {
          bingoBoard.getNumbers()[row][col].setMarked(bingoBoard.getNumbers()[row][col].getValue() == number);
        }
      }
    }
  }

  public boolean isBingoBoardMarked(BingoBoard bingoBoard) {
    if (getIfColumnIsMarked(bingoBoard) || getIfRowIsMarked(bingoBoard)) {
      bingoBoard.setFinished(true);
      return true;
    }
    return false;
  }

  public boolean getIfRowIsMarked(BingoBoard bingoBoard) {

    for (int i = 0; i < 5; i++) {
      if (Arrays.asList(bingoBoard.getNumbers()[i]).stream().allMatch(bingoElement -> bingoElement.isMarked())) {
        return true;
      }
    }
    return false;
  }

  public boolean getIfColumnIsMarked(BingoBoard bingoBoard) {

    for (int i = 0; i < 5; i++) {
      if (Arrays.asList(getColumn(bingoBoard.getNumbers(), i)).stream().allMatch(bingoElement -> bingoElement.isMarked())) {
        return true;
      }
    }
    return false;

  }

  public long getSumNotMarkedElements(BingoBoard bingoBoard) {
    int sumFalseValues = 0;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (!bingoBoard.getNumbers()[row][col].isMarked()) {
          sumFalseValues = sumFalseValues + bingoBoard.getNumbers()[row][col].getValue();
        }
      }
    }
    return sumFalseValues;
  }

  public BingoElement[] getColumn(BingoElement[][] array, int index) {
    BingoElement[] column = new BingoElement[array[0].length];
    for (int i = 0; i < column.length; i++) {
      column[i] = array[i][index];
    }
    return column;
  }
}
