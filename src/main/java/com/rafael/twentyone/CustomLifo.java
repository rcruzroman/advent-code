package com.rafael.twentyone;

import java.util.Stack;

public class CustomLifo<E> extends Stack<E> {
  private int limit;

  public CustomLifo() {
    this.limit = 200;
  }
  public CustomLifo(int limit) {
    this.limit = limit;
  }

  @Override
  public boolean add(E o) {
    super.add(o);
    while (size() > limit) { super.pop(); }
    return true;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
