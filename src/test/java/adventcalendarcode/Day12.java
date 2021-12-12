package adventcalendarcode;

import com.rafael.twentyone.Graph;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day12 {

  private static final String START = "start";
  private static final String END = "end";
  private static int pathsNum = 0;

  @Before
  public void before() {
    pathsNum = 0;
  }

  @Test
  public void part1Example() throws IOException {
    Path path = Paths.get("src/test/resources/day12dataexample.txt");
    List<String> inputData = Files.readAllLines(path);

    Graph graph = new Graph();
    for (String line : inputData) {
      String[] pathNodes = line.split("-");
      graph.addTwoWayVertex(pathNodes[0], pathNodes[1]);
    }
    LinkedList<String> visited = new LinkedList();
    visited.add(START);
    depthFirst(graph, visited);
    Assert.assertEquals(10, pathsNum);
  }

  @Test
  public void part2Example() throws IOException {
    Path path = Paths.get("src/test/resources/day12dataexample.txt");
    List<String> inputData = Files.readAllLines(path);

    Graph graph = new Graph();
    for (String line : inputData) {
      String[] pathNodes = line.split("-");
      graph.addTwoWayVertex(pathNodes[0], pathNodes[1]);
    }
    LinkedList<String> visited = new LinkedList();
    visited.add(START);
    depthFirstPart2(graph, visited);
    Assert.assertEquals(36, pathsNum);
  }

  @Test
  public void part1() throws IOException {
    Path path = Paths.get("src/test/resources/day12data.txt");
    List<String> inputData = Files.readAllLines(path);

    Graph graph = new Graph();
    for (String line : inputData) {
      String[] pathNodes = line.split("-");
      graph.addTwoWayVertex(pathNodes[0], pathNodes[1]);
    }
    LinkedList<String> visited = new LinkedList();
    visited.add(START);
    depthFirst(graph, visited);
    System.out.println("Total Path: " + pathsNum);
  }

  @Test
  public void part2() throws IOException {
    Path path = Paths.get("src/test/resources/day12data.txt");
    List<String> inputData = Files.readAllLines(path);

    Graph graph = new Graph();
    for (String line : inputData) {
      String[] pathNodes = line.split("-");
      graph.addTwoWayVertex(pathNodes[0], pathNodes[1]);
    }
    LinkedList<String> visited = new LinkedList();
    visited.add(START);
    depthFirstPart2(graph, visited);
    System.out.println("Total Path: " + pathsNum);
  }

  private void depthFirst(Graph graph, LinkedList<String> visited) {
    LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
    for (String node : nodes) {
      if (isVisitedWithRules(node, visited)) {
        continue;
      }
      if (node.equals(END)) {
        visited.add(node);
        pathsNum++;
        visited.removeLast();
        break;
      }
    }
    for (String node : nodes) {
      if (isVisitedWithRules(node, visited) || node.equals(END)) {
        continue;
      }
      visited.addLast(node);
      depthFirst(graph, visited);
      visited.removeLast();
    }
  }

  private void depthFirstPart2(Graph graph, LinkedList<String> visited) {
    LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
    for (String node : nodes) {
      if (isVisitedWithRulesPart2(node, visited)) {
        continue;
      }
      if (node.equals(END)) {
        visited.add(node);
        pathsNum++;
        visited.removeLast();
        break;
      }
    }
    for (String node : nodes) {
      if (isVisitedWithRulesPart2(node, visited) || node.equals(END)) {
        continue;
      }
      visited.addLast(node);
      depthFirstPart2(graph, visited);
      visited.removeLast();
    }
  }

  private boolean isVisitedWithRules(String node, LinkedList<String> visited) {
    return !StringUtils.isAllUpperCase(node) && visited.contains(node);
  }

  private boolean isVisitedWithRulesPart2(String node, LinkedList<String> visited) {
    return isVisitedWithRules(node, visited) && (node.equals(START) || node.equals(END) || !moreThanOneSmallCave(visited));
  }

  private boolean moreThanOneSmallCave(LinkedList<String> visited) {
    Map<String, Integer> numOcurrencePerNodeLowerCase = new HashMap<>();
    for (String nodeVisited : visited) {
      if (!nodeVisited.equals(START) && !nodeVisited.equals(END) && !StringUtils.isAllUpperCase(nodeVisited)) {
        numOcurrencePerNodeLowerCase.put(nodeVisited, numOcurrencePerNodeLowerCase.get(nodeVisited) == null ? 1 : numOcurrencePerNodeLowerCase.get(nodeVisited) + 1);
      }
    }
    return !numOcurrencePerNodeLowerCase.entrySet().stream()
      .filter(s -> s.getValue() > 1)
      .findFirst().isPresent();
  }

  private void printPath(LinkedList<String> visited) {
    for (String node : visited) {
      System.out.print(node);
      System.out.print(" ");
    }
    System.out.println();
  }
}
