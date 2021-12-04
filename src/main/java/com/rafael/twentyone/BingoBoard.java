package com.rafael.twentyone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BingoBoard {

  public BingoElement[][] numbers = new BingoElement[5][5];
  public Boolean finished = false;

  public BingoElement[][] getNumbers() {
    return numbers;
  }

  public void setNumbers(BingoElement[][] numbers) {
    this.numbers = numbers;
  }

  public Boolean getFinished() {
    return finished;
  }

  public void setFinished(Boolean finished) {
    this.finished = finished;
  }

  @Override
  public String toString()
  {

    String str = "";

    for (int i = 0 ; i<this.numbers.length ; i ++ ){
      for (int j = 0 ; j < this.numbers[i].length ; j++){
        str += numbers[i][j]+"\t";
      }
      str += "\n";
    }
    return str;
  }
}
