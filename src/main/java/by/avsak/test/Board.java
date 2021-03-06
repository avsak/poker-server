package by.avsak.test;

import java.util.List;

public class Board {
    private List<Card> cards;

    public Board(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Board[Cards=" + cards + "]";
    }
}
