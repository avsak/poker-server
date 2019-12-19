package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PokerServer {

    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {
        log.info("Server is running");
        Parser parser = new Parser();
        Validator validator = new Validator();
        String inputLine;

        System.out.println("Enter data: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while ((inputLine = br.readLine()) != null) {
            PokerRound pokerRound = parser.parsePokerRound(inputLine.trim());
            if (pokerRound.isValid()) {
                if (validator.checkCardsForDuplicates(pokerRound)) {
                    pokerRound.getHands().forEach(hand ->
                            hand.detectCombination(pokerRound.getBoard()));

                    List<Hand> sortedHands = pokerRound.getHands();
                    Collections.sort(sortedHands); // ascending
                    System.out.println(formattingPokerRoundResult(sortedHands));
                } else {
                    System.out.println(pokerRound.getErrorMessage());
                }
            } else {
                System.out.println(pokerRound.getErrorMessage());
            }
        }
        br.close();
    }

    private static String formattingPokerRoundResult(List<Hand> sortedHands) {
        String result = "";
        ListIterator<Hand> listIterator = sortedHands.listIterator();

        while (listIterator.hasNext()) {
            Hand currentHand = listIterator.next();
            Hand previousHand = listIterator.previousIndex() == 0 ? null : sortedHands.get(listIterator.previousIndex() - 1);

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
        return result;
    }
}
