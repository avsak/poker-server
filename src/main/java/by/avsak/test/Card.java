package by.avsak.test;

import java.util.Objects;

public class Card implements Comparable<Card>{
    private CardRank rank;
    private CardSuit suit;

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof Card)) {
            return false;
        } else {
            Card card = (Card) obj;
            return rank.equals(card.getRank()) && suit.equals(card.getSuit());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        return "Card[Rank: " + rank.toString() + ", Suit: " + suit.toString() + "]";
    }

    @Override
    public int compareTo(Card card2) {
        return Integer.compare(rank.getPower(), card2.rank.getPower());
    }
}
