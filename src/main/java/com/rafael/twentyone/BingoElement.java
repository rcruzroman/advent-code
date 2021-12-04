package com.rafael.twentyone;

public class BingoElement {

  private int value;
  private boolean marked;

  public BingoElement(int value) {
    this.value = value;
    this.marked = false;
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
