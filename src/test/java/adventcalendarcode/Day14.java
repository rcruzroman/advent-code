package adventcalendarcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    @Test
    public void part1Example() throws IOException {
        Path path = Paths.get("src/test/resources/day14dataexample.txt");
        List<String> inputData = Files.readAllLines(path);

        Map<String, String> insertionRules = getInsertionRules(inputData);
        LinkedList<String> result = populateTemplate(inputData.get(0));
        LinkedList<String> resultFinal = processTemplate(result, insertionRules, 10);
        Map<String, Long> frequentChars = resultFinal.stream().collect(
                Collectors.groupingBy(c -> c, Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e1.getValue()
                        .compareTo(e2.getValue())
                );
        Optional<Map.Entry<String, Long>> minEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e2.getValue()
                        .compareTo(e1.getValue())
                );
        System.out.println(frequentChars);
        System.out.println(maxEntry.get());
        System.out.println(minEntry.get());
        Assert.assertEquals(1588, maxEntry.get().getValue() - minEntry.get().getValue());

    }

    @Test
    public void part1() throws IOException {
        Path path = Paths.get("src/test/resources/day14data.txt");
        List<String> inputData = Files.readAllLines(path);

        Map<String, String> insertionRules = getInsertionRules(inputData);
        LinkedList<String> result = populateTemplate(inputData.get(0));
        LinkedList<String> resultFinal = processTemplate(result, insertionRules, 10);
        Map<String, Long> frequentChars = resultFinal.stream().collect(
                Collectors.groupingBy(c -> c, Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e1.getValue()
                        .compareTo(e2.getValue())
                );
        Optional<Map.Entry<String, Long>> minEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e2.getValue()
                        .compareTo(e1.getValue())
                );
        System.out.println(frequentChars);
        System.out.println(maxEntry.get());
        System.out.println(minEntry.get());
        System.out.println("Total: " + (maxEntry.get().getValue() - minEntry.get().getValue()));

    }

    @Test
    public void part2() throws IOException {
        Path path = Paths.get("src/test/resources/day14data.txt");
        List<String> inputData = Files.readAllLines(path);

        Map<String, String> insertionRules = getInsertionRules(inputData);
        LinkedList<String> result = populateTemplate(inputData.get(0));
        LinkedList<String> resultFinal = processTemplateMultithreading(result, insertionRules, 40);
        Map<String, Long> frequentChars = resultFinal.stream().collect(
                Collectors.groupingBy(c -> c, Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e1.getValue()
                        .compareTo(e2.getValue())
                );
        Optional<Map.Entry<String, Long>> minEntry =frequentChars.entrySet()
                .stream()
                .max((Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) -> e2.getValue()
                        .compareTo(e1.getValue())
                );
        System.out.println(frequentChars);
        System.out.println(maxEntry.get());
        System.out.println(minEntry.get());
        System.out.println("Total: " + (maxEntry.get().getValue() - minEntry.get().getValue()));

    }

    private LinkedList<String> processTemplate(LinkedList<String> result, Map<String, String> insertionRules, int numSteps) {

        if(numSteps == 0){
            return result;
        }
        int position = 0;
        String insertion;
        LinkedList<String> tempResult = new LinkedList<>();
        while(position < result.size()) {
            String currentValue = result.get(position);
            tempResult.add(currentValue);
            if(position + 1 != result.size()){
                insertion = insertionRules.get(currentValue + result.get(position + 1));
                if(insertion != null){
                    tempResult.add(insertion);
                }
            }
            position++;
        }
        System.out.println("NumSteps:" + numSteps);
        return processTemplate(tempResult, insertionRules, numSteps-1);
    }

    private LinkedList<String> processTemplateMultithreading(LinkedList<String> result, Map<String, String> insertionRules, int numSteps) {
        ExecutorService executor = Executors.newFixedThreadPool(6);

        //Create all CFs
        List<CompletableFuture<LinkedList<String>>> futureList = result.stream()
                .map(strings -> CompletableFuture.supplyAsync(() -> processList(strings, insertionRules), executor))
                .collect(Collectors.toList());

        //Wait for them all to complete
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        System.out.println("TEST");

        //Do processing of the results
//        List<CompletableFuture<LinkedList<String>>> joinResult = futureList.stream()
//                .map(CompletableFuture::join);
        return null;

    }

    private LinkedList<String> processList(String currentResult, Map<String, String> insertionRules) {
        LinkedList<String> tempResult = new  LinkedList<>();
        String insertion;
        tempResult.add(currentResult);
//        if(position + 1 != result.size()){
            insertion = insertionRules.get(currentResult + "TEST");
            if(insertion != null){
                tempResult.add(insertion);
            }
//        }
        return tempResult;
    }

    private Map<String, String> getInsertionRules(List<String> inputData) {
        Map<String, String> insertionRules = new HashMap<>();
        for (String input : inputData) {
            if (input.contains("->")) {
                insertionRules.put(input.split("->")[0].trim(), input.split("->")[1].trim());
            }
        }
        return insertionRules;
    }

    private LinkedList<String> populateTemplate(String template) {
        LinkedList<String> result = new LinkedList<>();

        int i = 0;
        while (i < template.length()) {
            result.add(template.substring(i, i + 1));
            i++;
        }
        return result;
    }
}
