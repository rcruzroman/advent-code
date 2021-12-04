package adventcalendarcode;

import com.rafael.twentyone.BingoBoard;
import com.rafael.twentyone.BingoUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

  @Test
  public void bingo() throws IOException {
    List<BingoBoard> bingoBoards = new ArrayList<>();
    BingoUtil bingoUtil = new BingoUtil();
    Optional<BingoBoard> bingoBoardMarkedOptional = null;
    int winnerNumber = 0;
    Path path = Paths.get("src/test/resources/day4databoards.txt");
    List<String> lines = Files.readAllLines(path);
    path = Paths.get("src/test/resources/day4datalistnumber.txt");
    List<String> numbers = Arrays.asList(Files.readAllLines(path).get(0).split(","));

    //Populate bingo
    bingoUtil.populateBingoBoard(lines, bingoBoards);

    //Marked numbers
    for (String number : numbers) {
      bingoBoards.stream().forEach(bingoBoard -> bingoUtil.markedNumberInBingoBoard(bingoBoard, Integer.valueOf(number)));

      //Check if rows of column is marked completely
      bingoBoardMarkedOptional = bingoBoards.stream().filter(bingoBoard -> bingoUtil.isBingoBoardMarked(bingoBoard)).findFirst();

      if (bingoBoardMarkedOptional.isPresent()) {
        winnerNumber = Integer.valueOf(number);
        System.out.println(bingoBoardMarkedOptional.get());
        break;
      }
    }

    long sumValuesNotMarked = bingoUtil.getSumNotMarkedElements(bingoBoardMarkedOptional.get());
    System.out.println("winnerNumber: " + winnerNumber);
    System.out.println("sumValuesNotMarked: " + sumValuesNotMarked);
    System.out.println("Total: " + (sumValuesNotMarked * winnerNumber));
    Assert.assertEquals(5685, (sumValuesNotMarked * winnerNumber));
  }

  @Test
  public void bingoPart2() throws IOException {
    List<BingoBoard> bingoBoards = new ArrayList<>();
    List<BingoBoard> listBingoBoardWinners = new LinkedList<>();
    BingoUtil bingoUtil = new BingoUtil();
    int winnerNumber = 0;
    Path path = Paths.get("src/test/resources/day4databoards.txt");
    List<String> lines = Files.readAllLines(path);
    path = Paths.get("src/test/resources/day4datalistnumber.txt");
    List<String> numbers = Arrays.asList(Files.readAllLines(path).get(0).split(","));

    //Populate bingo
    bingoUtil.populateBingoBoard(lines, bingoBoards);

    //Marked numbers
    for (String number : numbers) {
      bingoBoards.stream().forEach(bingoBoard -> bingoUtil.markedNumberInBingoBoard(bingoBoard, Integer.valueOf(number)));

      //Check if rows or columns are marked completely
      List<BingoBoard> bingoBoardsWinners = bingoBoards.stream().filter(bingoBoard -> !bingoBoard.getFinished() && bingoUtil.isBingoBoardMarked(bingoBoard)).collect(Collectors.toList());

      listBingoBoardWinners.addAll(bingoBoardsWinners);
      winnerNumber = Integer.valueOf(number);

      if (listBingoBoardWinners.size() == bingoBoards.size()) {
        break;
      }
    }

    long sumValuesNotMarked = bingoUtil.getSumNotMarkedElements(listBingoBoardWinners.get(listBingoBoardWinners.size() - 1));
    System.out.println("winnerNumber: " + winnerNumber);
    System.out.println("Total: " + (sumValuesNotMarked * winnerNumber));
    Assert.assertEquals(21070, sumValuesNotMarked * winnerNumber);
  }
}
