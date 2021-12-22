package adventcalendarcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Day21 {

  @Test
  public void part1Example() {
    List<Integer> positions = new ArrayList<>();
    positions.add(4);
    positions.add(8);
    Assert.assertEquals(739785l, getScoreLooser(positions));
  }

  @Test
  public void part1() {
    List<Integer> positions = new ArrayList<>();
    positions.add(7);
    positions.add(10);
    System.out.println("total:" + getScoreLooser(positions));
  }

  private long getScoreLooser(List<Integer> positions) {
    List<Long> scores = initializeNumPlayers(positions);
    List<Integer> rolls = initializeNumRolls(positions);
    boolean isMaximumAchieve = false;
    int totalRolls = 1;

    while (true) {
      for (Integer player = 0; player < scores.size(); player++) {
        Long score = setScoreAndPosition(player, scores, positions, rolls, totalRolls);
        if (score >= 1000) {
          isMaximumAchieve = true;
          break;
        }
        totalRolls++;
      }
      if (isMaximumAchieve) {
        break;
      }
    }

//    int playerMinimumScore = IntStream.range(0, scores.size()).filter(i -> scores.get(i).equals(getMinScored(scores)))
//      .findFirst().getAsInt();

    return getMinScored(scores) * (totalRolls * 3);
  }
  

  private Long getMinScored(List<Long> scores) {
    return scores.stream().mapToLong(l -> l).min().getAsLong();
  }

  private Long getMaxWins(List<Long> wins) {
    return wins.stream().mapToLong(l -> l).max().getAsLong();
  }


  private Long setScoreAndPosition(Integer player, List<Long> scores, List<Integer> positions, List<Integer> rolls, int totalRolls) {
    Long currentScore = scores.get(player);
    int currentPosition = positions.get(player);
    rolls.set(player, rolls.get(player) + 1);
    int intermediateSumPosition = ((totalRolls * 3 - 1) + (totalRolls * 3 - 2) + (totalRolls * 3) + currentPosition);

    Integer position = (intermediateSumPosition % 10 == 0) ? 10 : intermediateSumPosition % 10;
    Long score = currentScore + position;
    scores.set(player, score);
    positions.set(player, position);
    return score;
  }

  private Long setScoreAndPositionWithUniverse(Integer player, List<Long> scores, List<Integer> positions, List<Integer> rolls, int totalRolls, int universeValue) {
    Long currentScore = scores.get(player);
    int currentPosition = positions.get(player);
    rolls.set(player, rolls.get(player) + 1);
    int intermediateSumPosition = ((totalRolls * 3 - 1) + (totalRolls * 3 - 2) + (totalRolls * 3) + currentPosition + universeValue);

    Integer position = (intermediateSumPosition % 10 == 0) ? 10 : intermediateSumPosition % 10;
    Long score = currentScore + position;
    scores.set(player, score);
    positions.set(player, position);
    return score;
  }

  private List<Long> initializeNumPlayers(List<Integer> startingPositions) {
    List<Long> scores = new ArrayList<>();
    for (Integer i = 0; i < startingPositions.size(); i++) {
      scores.add(0l);
    }
    return scores;
  }

  private List<Integer> initializeNumRolls(List<Integer> startingPositions) {
    List<Integer> rolls = new ArrayList<>();
    for (Integer i = 0; i < startingPositions.size(); i++) {
      rolls.add(0);
    }
    return rolls;
  }

}
