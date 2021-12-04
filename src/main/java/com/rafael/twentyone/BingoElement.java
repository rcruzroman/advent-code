package com.rafael.twentyone;

public class BingoElement {

  private int row;
  private int column;
  private int value;
  private boolean marked;

  public BingoElement(int row, int column, int value) {
    this.row = row;
    this.column = column;
    this.value = value;
    this.marked = false;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public boolean isMarked() {
    return marked;
  }

  public void setMarked(boolean marked) {
    this.marked = marked;
  }

  @Override
  public String toString() {
    return String.valueOf(value) + ":" + marked;
  }
}
