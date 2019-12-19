package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Validator {

    private static final Logger log = LogManager.getRootLogger();

    public boolean checkCardsForDuplicates(PokerRound pokerRound) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(pokerRound.getBoard().getCards());
        pokerRound.getHands().forEach(hand -> allCards.addAll(hand.getCards()));
        Stream stream = allCards.stream();
        if (!(stream.distinct().count() == allCards.size())) {
            pokerRound.setValid(false);
            pokerRound.setErrorMessage("Duplicate cards detected");
            log.warn(pokerRound.getErrorMessage());
            return false;
        }
        log.info("Duplicate cards not detected");
        return true;
    }
}
