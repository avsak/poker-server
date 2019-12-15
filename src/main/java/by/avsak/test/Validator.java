package by.avsak.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Validator {

    public boolean checkCardsForDuplicates(PokerRound pokerRound) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(pokerRound.getBoard().getCards());
        pokerRound.getHands().forEach(hand -> allCards.addAll(hand.getCards()));
        Stream stream = allCards.stream();
        return stream.distinct().count() == allCards.size();
    }
}
