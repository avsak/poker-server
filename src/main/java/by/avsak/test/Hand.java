package by.avsak.test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand implements Comparable<Hand> {
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

    public void detectCombination(Board board) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(board.getCards());
        allCards.addAll(this.cards);

        if (detectStraightFlush(allCards)) {
            System.out.println("ЗАШЛИИИ");
            this.combinationType = CombinationType.StraightFlush;
            return;
        }
        if (detectFourOfKind(allCards)) {
            this.combinationType = CombinationType.FourOfKind;
            return;
        }
        if (detectFullHouse(allCards)) {
            this.combinationType = CombinationType.FullHouse;
            return;
        }
        if (detectFlush(allCards)) {
            this.combinationType = CombinationType.Flush;
            return;
        }
        if (detectStraight(allCards)) {
            this.combinationType = CombinationType.Straight;
            return;
        }
        if (detectThreeOfKind(allCards)) {
            this.combinationType = CombinationType.ThreeOfKind;
            return;
        }
        if (detectTwoPairs(allCards)) {
            this.combinationType = CombinationType.TwoPairs;
            return;
        }
        if (detectPair(allCards)) {
            this.combinationType = CombinationType.Pair;
        } else {
            this.combinationType = CombinationType.HighCard;
        }
    }

    @Override
    public int compareTo(Hand hand2) {
        return Integer.compare(combinationType.getPower(), hand2.combinationType.getPower());
    }

    private boolean detectStraightFlush(List<Card> cards) {
        // TODO
        return false;
    }

    private boolean detectFourOfKind(List<Card> cards) {
        // TODO
        return false;
    }

    private boolean detectFullHouse(List<Card> cards) {
        // TODO
        return false;
    }

    private boolean detectFlush(List<Card> cards) {
        // TODO
        return false;
    }

    private boolean detectStraight(List<Card> cards) {
        List<Integer> straight = new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10));
        Collections.sort(cards);
        Collections.reverse(cards);  // A -> K -> Q -> ...
        List <Integer> cardsRank = cards.stream().map(card -> card.getRank().getPower()).collect(Collectors.toList());
        while (straight.get(straight.size()-1) > 2) {
            if (cardsRank.containsAll(straight)) {
                return true;
            } else {
                straight = straight.stream().map(cardRank -> cardRank - 1).collect(Collectors.toList());
            }
        }
        return false;
    }

    private boolean detectThreeOfKind(List<Card> cards) {
        Map<CardRank, Long> cardRanksCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        return cardRanksCount.containsValue(3L);
    }

    private boolean detectTwoPairs(List<Card> cards) {
        Map<CardRank, Long> cardRanksCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        return Collections.frequency(new ArrayList<>(cardRanksCount.values()), 2L) == 2;
    }

    private boolean detectPair(List<Card> cards) {
        Map<CardRank, Long> cardRanksCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        return cardRanksCount.containsValue(2L);
    }
}
