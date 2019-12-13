package by.avsak.test;

import java.util.List;

public class Combination {

    private List<Card> cards;
    private Card kicker;
    private CombinationType type;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getKicker() {
        return kicker;
    }

    public void setKicker(Card kicker) {
        this.kicker = kicker;
    }

    public CombinationType getType() {
        return type;
    }

    public void setType(CombinationType type) {
        this.type = type;
    }
}
