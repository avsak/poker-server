package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class PokerServer {

    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {
        log.info("Server is running");
        Parser parser = new Parser();
        Validator validator = new Validator();
        List<PokerRound> pokerRounds = new ArrayList<>();
        String inputLine;

        System.out.println("Input:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while ((inputLine = br.readLine()) != null) {
            PokerRound pokerRound = parser.parsePokerRound(inputLine.trim());
            if (pokerRound.isValid()) {
                if (validator.checkCardsForDuplicates(pokerRound)) {
                    pokerRound.getHands().forEach(hand ->
                            hand.detectCombination(pokerRound.getBoard()));

                    List<Hand> hands = pokerRound.getHands();
                    List<Hand> sortedHands;

                    sortedHands = hands
                            .stream()
                            .sorted(Comparator.comparing(hand -> hand.getCards().toString())) // alphabetically
                            .collect(Collectors.toList());

                    Collections.sort(sortedHands); // ascending
                    pokerRound.setSortedHands(sortedHands);
                    pokerRounds.add(pokerRound);
                } else {
                    pokerRounds.add(pokerRound);
                }
            } else {
                pokerRounds.add(pokerRound);
            }
        }

        System.out.println("Output:");
        pokerRounds.forEach(PokerServer::printPokerRoundResult);
        br.close();
    }

    private static void printPokerRoundResult(PokerRound pokerRound) {

        if (!pokerRound.isValid()) {
            System.out.println(pokerRound.getErrorMessage());
            return;
        }

        String result = "";
        ListIterator<Hand> listIterator = pokerRound.getSortedHands().listIterator();

        while (listIterator.hasNext()) {
            Hand currentHand = listIterator.next();
            Hand previousHand = listIterator.previousIndex() == 0 ? null : pokerRound.getSortedHands().get(listIterator.previousIndex() - 1);

            if (previousHand == null) {
                result = result + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                        + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit();
            } else if (currentHand.compareTo(previousHand) == 0) {
                result = result + "=" + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                        + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit();
            } else {
                result = result + " " + currentHand.getCards().get(0).getRank() + currentHand.getCards().get(0).getSuit()
                        + currentHand.getCards().get(1).getRank() + currentHand.getCards().get(1).getSuit();
            }
        }
        System.out.println(result);
    }
}
