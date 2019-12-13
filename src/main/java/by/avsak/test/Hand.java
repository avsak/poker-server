package by.avsak.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand>{
    private List<Card> cards;
    private Card kicker;
    private CombinationType combinationType;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Hand[Cards: " + cards;
    }

    public Card getKicker() {
        return kicker;
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

    public CombinationType detectCombination(Board board) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(board.getCards());
        allCards.addAll(this.cards);

        Collections.sort(allCards);
        Collections.reverse(allCards);

        if (detectStraightFlush(allCards)) return CombinationType.StraightFlush;
        if (detectFourOfKind(allCards)) return CombinationType.FourOfKind;
        if (detectFullHouse(allCards)) return CombinationType.FullHouse;
        if (detectFlush(allCards)) return CombinationType.Flush;
        if (detectStraight(allCards)) return CombinationType.Straight;
        if (detectThreeOfKind(allCards)) return CombinationType.ThreeOfKind;
        if (detectTwoPairs(allCards)) return CombinationType.TwoPairs;
        if (detectPair(allCards)) return CombinationType.Pair;
        if (detectHighCard(allCards)) return CombinationType.HighCard;
        return CombinationType.UNKNOWN;
    }

    @Override
    public int compareTo(Hand hand2) {
        return Integer.compare(combinationType.getPower(), hand2.combinationType.getPower());
    }

    public boolean detectStraightFlush(List<Card> sortedCards) {
        return false;
    }

    public boolean detectFourOfKind(List<Card> sortedCards) {
        return false;
    }

    public boolean detectFullHouse(List<Card> sortedCards) {
        return false;
    }

    public boolean detectFlush(List<Card> sortedCards) {
        return false;
    }

    public boolean detectStraight(List<Card> sortedCards) {
        return false;
    }

    public boolean detectThreeOfKind(List<Card> sortedCards) {
        return false;
    }

    public boolean detectTwoPairs(List<Card> sortedCards) {
        return false;
    }

    public boolean detectPair(List<Card> sortedCards) {
        return false;
    }

    public boolean detectHighCard(List<Card> sortedCards) {
        return false;
    }
}
