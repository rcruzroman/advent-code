package adventcalendarcode;

import com.rafael.twentyone.LanternFishForJoinUsingRecursion;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Day6 {

  private int NUMBER_OF_THREADS = 6;

  @Test
  public void calculateLanterfishExample() throws IOException {
    Path path = Paths.get("src/test/resources/day6dataexample.txt");
    List<String> lanternFishsString = Arrays.asList(Files.readAllLines(path).get(0).split(","));
    LinkedList<Integer> lanternFishs = lanternFishsString.stream().map(l -> Integer.valueOf(l)).collect(Collectors.toCollection(LinkedList::new));
    LinkedList<Integer> auxList = new LinkedList<>();

    int contador = 0;
    int fish = 0;

    while(contador < 80){
      System.out.println("contador" + contador);

      long numZeroBefore = lanternFishs.parallelStream().filter(f -> f == 0).count();
      for (int i = 0; i < lanternFishs.size(); i++) {
        fish = lanternFishs.get(i);
        if (fish == 0) {
          lanternFishs.set(i, 6);
        } else {
          fish--;
          lanternFishs.set(i, fish);
        }
      }

      //First day nothing to be done
      if(contador > 0) {
        addNewFishes(lanternFishs, numZeroBefore);
      }

      contador++;
    }
    Assert.assertEquals(Long.valueOf("5934"), Long.valueOf(lanternFishs.size()));

  }

  @Test
  public void calculateLanternFishParallelExample() throws IOException {
    Path path = Paths.get("src/test/resources/day6dataexample.txt");
    List<String> lanternFishsString = Arrays.asList(Files.readAllLines(path).get(0).split(","));
    LinkedList<Integer> lanternFishs = lanternFishsString.stream().map(l -> Integer.valueOf(l)).collect(Collectors.toCollection(LinkedList::new));
    LinkedList<Integer> auxList = new LinkedList<>();
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    int contador = 0;
    while(contador < 256) {
      System.out.println("contador" + contador);
      LanternFishForJoinUsingRecursion lanternFishForJoinUsingRecursion = new LanternFishForJoinUsingRecursion(lanternFishs, contador);
      auxList = forkJoinPool.invoke(lanternFishForJoinUsingRecursion);
      lanternFishs = auxList;
      contador++;
    }

    Assert.assertEquals(Long.valueOf("26984457539"), Long.valueOf(lanternFishs.size()));


  }

  @Test
  public void calculateLanterfish() throws IOException {
    Path path = Paths.get("src/test/resources/day6data.txt");
    List<String> lanternFishsString = Arrays.asList(Files.readAllLines(path).get(0).split(","));
    List<Integer> lanternFishs = lanternFishsString.stream().map(l -> Integer.valueOf(l)).collect(Collectors.toList());

    int contador = 0;
    int fish = 0;

    while(contador < 80){
      long numZerosBefore = lanternFishs.stream().filter(f -> f == 0).count();
      for (int i = 0; i < lanternFishs.size(); i++) {
        fish = lanternFishs.get(i);
        if (fish == 0) {
          lanternFishs.set(i, 6);
        } else {
          fish--;
          lanternFishs.set(i, fish);
        }
      }

      //First day nothing to be done
      if(contador > 0) {
        addNewFishes(lanternFishs, numZerosBefore);
      }
      contador++;
    }
    System.out.println("Total fishes: " + lanternFishs.size());

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
