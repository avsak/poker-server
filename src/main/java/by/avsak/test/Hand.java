package by.avsak.test;

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

    public CombinationType detectCombinmation(Board board) {

        return null;
    }

    @Override
    public int compareTo(Hand hand2) {
        return Integer.compare(combinationType.getPower(), hand2.combinationType.getPower());
    }
}
