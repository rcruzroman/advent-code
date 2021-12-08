package com.rafael.twentyone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class LanternFishForJoinUsingRecursion extends RecursiveTask<LinkedList<Integer>> {

  private List<Integer> inputList;
  private int contador;

  public LanternFishForJoinUsingRecursion(List<Integer> inputList, Integer contador) {
    this.inputList = inputList;
    this.contador = contador;
  }

  @Override
  protected LinkedList<Integer> compute() {

    long numZeroBefore = inputList.parallelStream().filter(f -> f == 0).count();
    if(inputList.size()<=1){
      LinkedList<Integer> resultList = new LinkedList<>();
      inputList.forEach(fish-> {
        if (fish == 0) {
          resultList.add(6);
        } else {
          fish--;
          resultList.add(fish);
        }
      });

      //First day nothing to be done
      if(contador > 0) {
        addNewFishes(resultList, numZeroBefore);
      }

      return resultList;
    }
    int midpoint = inputList.size()/2;
    ForkJoinTask<LinkedList<Integer>> leftInputList=  new LanternFishForJoinUsingRecursion(inputList.subList(0,midpoint), contador).fork();
    inputList = inputList.subList(midpoint, inputList.size());
    LinkedList<Integer> rightResult = compute();
    LinkedList<Integer> leftResult = leftInputList.join();
    leftResult.addAll(rightResult);
    return leftResult;
  }

  private void addNewFishes(List<Integer> lanternFishs, long numZeros){
    if(numZeros > 0){
      while (numZeros > 0){
        lanternFishs.add(8);
        numZeros--;
      }
    }
  }
}
