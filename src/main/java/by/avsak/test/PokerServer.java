package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PokerServer {

    private static final Logger log = LogManager.getRootLogger();
    private static final String INPUT_FILE_PATH = "./src/main/resources/input.txt";

    public static void main(String[] args) throws IOException {
        log.info("Server is running");
        Parser parser = new Parser();
        Validator validator = new Validator();

        List<String> fileLines = readFile();
        fileLines.forEach(line -> {
            PokerRound pokerRound = parser.parsePokerRound(line);
            if (pokerRound.isValid()) {
                if (validator.checkCardsForDuplicates(pokerRound)) {
                    log.info("Duplicate cards not detected");

                    pokerRound.getHands().forEach(hand ->
                            hand.detectCombination(pokerRound.getBoard()));

                    List<Hand> sortedHands = pokerRound.getHands();
                    Collections.sort(sortedHands);
                    Collections.reverse(sortedHands);

                    System.out.println(formattingPokerRoundResult(sortedHands));
                } else {
                    System.out.println("Duplicate cards detected\n");
                }
            } else {
                System.out.println(pokerRound.getErrorMessage() + "\n");
            }
        });
    }

    private static List<String> readFile() throws IOException {
        List<String> fileLines = new ArrayList<>();
        Files.lines(Paths.get(PokerServer.INPUT_FILE_PATH), StandardCharsets.UTF_8).forEach(line -> fileLines.add(line.trim()));
        log.info("File read successfully");
        return fileLines;
    }


    private static String formattingPokerRoundResult(List<Hand> sortedHands) {
        String result = "";
        ListIterator<Hand> listIterator = sortedHands.listIterator();

        while(listIterator.hasNext()) {
            Hand currentHand = listIterator.next();
            Hand previousHand = listIterator.previousIndex() == 0 ? null : sortedHands.get(listIterator.previousIndex() - 1);

            if (previousHand == null) {
                result = result + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                                + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit()
                                + "(" + currentHand.getCombinationType() + ")";
            } else if (currentHand.compareTo(previousHand) == 0) {
                result = result + " = " + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                                        + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit()
                                        + "(" + currentHand.getCombinationType() + ")";
            } else {
                result = result + " " + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                                      + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit()
                                      + "(" + currentHand.getCombinationType() + ")";
            }
        }
        return result + "\n";
    }
}
