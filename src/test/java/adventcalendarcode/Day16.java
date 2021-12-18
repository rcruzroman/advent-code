package adventcalendarcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 {

  private static final int START_PACKET_VERSION = 0;
  private static final int END_PACKET_VERSION = 3;
  private static final int START_TYPE_ID = 3;
  private static final int END_TYPE_ID = 6;
  private static final int START_LENGTH_TYPE_ID = 6;
  private static final int END_LENGTH_TYPE_ID = 7;
  private static final int START_NUM_SUBPACKETS = 7;
  private static final int END_NUM_SUBPACKETS = 18;

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day16dataexample.txt");
    List<String> inputData = Files.readAllLines(path);
    System.out.println(parseToBinary(inputData.get(0)));
    List<String> packets = new ArrayList<>();
    List<Integer> versions = new ArrayList<>();
    String binary = parseToBinary(inputData.get(0));
    while (binary.length() > 0){
      binary = addPacketAndGetRemainingBinary(packets, binary, versions);
      System.out.println(binary);
    }

    Assert.assertEquals(16, versions.stream().mapToInt(e -> e).sum());

  }

  private String parseToBinary(String hexadecimal) {
    return Arrays.stream(hexadecimal.split("")).map(s -> hexadecimalToBinary(s)).collect(Collectors.joining());
  }

  private String hexadecimalToBinary(String s) {
    String preBin = new BigInteger(s, 16).toString(2);
    Integer length = preBin.length();
    if (length < 8) {
      for (int i = 0; i < 4 - length; i++) {
        preBin = "0" + preBin;
      }
    }
    return preBin;
  }

  private String addPacketAndGetRemainingBinary(List<String> packets, String binary, List<Integer> versions) {
    int size = 0;
    String version = binary.substring(START_PACKET_VERSION, END_PACKET_VERSION);
    versions.add(Integer.parseInt(version, 2));
    size = updateSize(size, version);
    String type = binary.substring(START_TYPE_ID, END_TYPE_ID);
    size = updateSize(size, type);

    if (isLiteralValue(getType(binary))) {
      size = updateSize(size, String.join("", getLiterals(binary)));
    } else {
      int packetLengthType = getLengthType(binary);
      size = updateSize(size, binary.substring(START_LENGTH_TYPE_ID, END_LENGTH_TYPE_ID));
      if (packetLengthType == 0) {
        int subPacketsLenght = Integer.parseInt(binary.substring(7, 22), 2);
        size = updateSize(size, String.join("", getSubPackets(binary.substring(21, 21 + subPacketsLenght))));
      } else if (packetLengthType == 1) {
        int numSubPackets = Integer.parseInt(binary.substring(START_NUM_SUBPACKETS, END_NUM_SUBPACKETS), 2);
        size = updateSize(size, binary.substring(START_NUM_SUBPACKETS, END_NUM_SUBPACKETS));
        size = updateSize(size, getSubPacketsIdThree(binary.substring(END_NUM_SUBPACKETS, END_NUM_SUBPACKETS + (11 * numSubPackets))).stream().collect(Collectors.joining()));
      }

    }
    packets.add(binary.substring(0, size));
    return binary.substring(size, binary.length());
  }

  private int updateSize(int currentSize, String binary) {
    return currentSize + binary.length();
  }

  private List<String> getSubPacketsIdThree(String binary) {
    List<String> subPackets = new ArrayList<>();

    for (int i = 0; i < binary.length() / 11; i++) {
      subPackets.add(binary.substring(i, i + 11));
    }
    return subPackets;
  }

  private List<String> getSubPackets(String binary) {
    List<String> subPackets = new ArrayList<>();
    String subPacketsBinary = binary.substring(0, binary.length() - 1);

    int i = subPacketsBinary.length();
    while (i > 0) {
      int origin = i - 16;
      if (origin < 0) {
        origin = 16 - i;
      }

      subPackets.add(subPacketsBinary.substring(origin, i - 1));
      i = i - 16;
    }
    return subPackets;
  }

  private List<String> getLiterals(String binary) {
    String literal;
    List<String> literalsBinary = new ArrayList<>();

    int i = 1;
    while (true) {
      literal = binary.substring(5 * i, 10 * i);
      literalsBinary.add(literal);
      i++;
      if (literal.substring(0, 1).equals("0")) {
        break;
      }
    }
    return literalsBinary;
  }

  private int getVersion(String binary) {
    return Integer.parseInt(binary.substring(START_PACKET_VERSION, END_PACKET_VERSION), 2);
  }

  private int getType(String binary) {
    return Integer.parseInt(binary.substring(START_TYPE_ID, END_TYPE_ID), 2);
  }

  private int getLengthType(String binary) {
    return Integer.parseInt(binary.substring(START_LENGTH_TYPE_ID, END_LENGTH_TYPE_ID), 2);
  }

  private boolean isLiteralValue(int packetType) {
    return packetType == 4;
  }
}
