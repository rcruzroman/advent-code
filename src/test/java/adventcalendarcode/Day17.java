package adventcalendarcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

  public static final int X0 = 240;
  public static final int X1 = 292;
  public static final int Y0 = -57;
  public static final int Y1 = -90;

//  public static final int X0 = 20;
//  public static final int X1 = 30;
//  public static final int Y0 = -5;
//  public static final int Y1 = -10;

  @Test
  public void test() {

    List<Integer> yCoordinates = new ArrayList<>();
    int currentXValue = 0;
    int currentYValue = 0;
    int currentX;
    int currentY;
    int highestY = 0;

    for (int x = -300; x <= X1; x++) {
      for (int y = 100; y >= Y1; y--) {
        currentXValue = x;
        currentYValue = y;
        currentX = x;
        currentY = y;
        highestY = 0;
        while (true) {
          if(currentY > highestY){
            highestY = currentY;
          }
          if (isOutTargetArea(currentX, currentY)) {
            break;
          }

          if (isInTargetArea(currentX, currentY)) {
            yCoordinates.add(highestY);
            break;
          }

          currentY = getNewY(currentY, currentYValue);
          currentYValue--;

          if(currentXValue != 0) {
            currentX = getNewX(currentX, currentXValue);
            currentXValue--;
          }

        }
      }
    }

    System.out.println("Max Y: " + yCoordinates.stream().mapToInt(e -> e).max().getAsInt());
    System.out.println("Probes within the target: " + yCoordinates.size());

  }

  private boolean isOutTargetArea(int x, int y) {
    return x > X1 || y < Y1;
  }

  private boolean isInTargetArea(int x, int y) {
    return x >= X0 && x <= X1 && y <= Y0 && y >= Y1;
  }

  private int getNewX(int currentX, int currentXValue) {
    int newX = currentX + currentXValue - 1;
    return newX < 0 ? 0 : newX;
  }

  private int getNewY(int currentYPosition, int currentYValue) {
    return currentYPosition + currentYValue - 1;
  }
}
