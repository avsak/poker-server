package by.avsak.test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand implements Comparable<Hand> {
    private List<Card> cards;
    private CombinationType combinationType;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Hand[Cards: " + cards;
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

    public void detectCombination(Board board) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(board.getCards());
        allCards.addAll(this.cards);

        if (detectStraightFlush(allCards)) {
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
        Collections.sort(cards);
        Collections.reverse(cards);
        List<Card> hand2Cards = hand2.getCards();
        Collections.sort(hand2Cards);
        Collections.reverse(hand2Cards);

        int comparisonCombinationType = Integer.compare(combinationType.getPower(), hand2.combinationType.getPower());
        int comparisonHighCard = cards.get(0).compareTo(hand2Cards.get(0));
        int comparisonSecondCard = cards.get(1).compareTo(hand2Cards.get(1));

        if (comparisonCombinationType > 0) {
            return 1;
        } else if (comparisonCombinationType < 0) {
            return -1;
        } else if (comparisonHighCard > 0) {
            return 1;
        } else if (comparisonHighCard < 0) {
            return -1;
        } else return Integer.compare(comparisonSecondCard, 0);
    }

    private boolean detectStraightFlush(List<Card> cards) {
        List<Card> cardList = new ArrayList<>(cards);
        List<Integer> straight = new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10));
        Map<CardSuit, Long> cardSuitsCount = cardList.stream().collect(groupingBy(Card::getSuit, counting()));
        cardSuitsCount.forEach((s, c) -> {
            if (c < 5L) {
                cardList.removeIf(card -> card.getSuit().equals(s));
            }
        });
        if (cardList.size() < 5) return false;

        Collections.sort(cardList);
        Collections.reverse(cardList);  // A -> K -> Q -> ...
        while (straight.get(straight.size() - 1) > 2) {
            if (cardList.stream().map(card -> card.getRank().getPower()).collect(Collectors.toList()).containsAll(straight)) {
                return true;
            } else straight = straight.stream().map(cardRank -> cardRank - 1).collect(Collectors.toList());
        }
        return false;
    }

    private boolean detectFourOfKind(List<Card> cards) {
        Map<CardRank, Long> cardRanksCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        return cardRanksCount.containsValue(4L);
    }

    private boolean detectFullHouse(List<Card> cards) {
        Map<CardRank, Long> cardRanksCount = cards.stream().collect(groupingBy(Card::getRank, counting()));
        return cardRanksCount.containsValue(3L) && cardRanksCount.containsValue(2L);
    }

    private boolean detectFlush(List<Card> cards) {
        Map<CardSuit, Long> cardSuitsCount = cards.stream().collect(groupingBy(Card::getSuit, counting()));
        return cardSuitsCount.containsValue(5L);
    }

    private boolean detectStraight(List<Card> cards) {
        List<Integer> straight = new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10));
        Collections.sort(cards);
        Collections.reverse(cards);  // A -> K -> Q -> ...
        List<Integer> cardsRank = cards.stream().map(card -> card.getRank().getPower()).collect(Collectors.toList());
        while (straight.get(straight.size() - 1) > 2) {
            if (cardsRank.containsAll(straight)) {
                return true;
            } else straight = straight.stream().map(cardRank -> cardRank - 1).collect(Collectors.toList());
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
