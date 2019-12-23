package by.avsak.test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Hand implements Comparable<Hand> {
    private List<Card> cards;
    private CombinationType combinationType;
    private List<Card> cardCombinations;

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

    public void setCombinationType(CombinationType combinationType) {
        this.combinationType = combinationType;
    }

    public List<Card> getCardCombinations() {
        return cardCombinations;
    }

    public void setCardCombinations(List<Card> cardCombinations) {
        this.cardCombinations = cardCombinations;
    }

    public void detectCombination(Board board) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(board.getCards());
        allCards.addAll(this.cards);

        if (detectStraightFlush(allCards)) return;
        if (detectFourOfKind(allCards)) return;
        if (detectFullHouse(allCards)) return;
        if (detectFlush(allCards)) return;
        if (detectStraight(allCards)) return;
        if (detectThreeOfKind(allCards)) return;
        if (detectTwoPairs(allCards)) return;
        if (detectPair(allCards)) return;

        this.combinationType = CombinationType.HighCard;
    }

    @Override
    public int compareTo(Hand hand2) {
        int comparisonCombinationType = Integer.compare(this.combinationType.getPower(), hand2.combinationType.getPower());

        if (comparisonCombinationType > 0) return 1;
        if (comparisonCombinationType < 0) return -1;
        if (this.combinationType == CombinationType.Pair) return comparePair(hand2);
        if (this.combinationType == CombinationType.TwoPairs) return compareTwoPair(hand2);
        if (this.combinationType == CombinationType.ThreeOfKind) return compareThreeOfKind(hand2);
        if (this.combinationType == CombinationType.Straight || this.combinationType == CombinationType.StraightFlush) return compareStraight(hand2);
        if (this.combinationType == CombinationType.Flush) return compareFlush(hand2);
        if (this.combinationType == CombinationType.FullHouse) return compareFullHouse(hand2);
        if (this.combinationType == CombinationType.FourOfKind) return compareFourOfKind(hand2);
        return 0;
    }

    private int comparePair(Hand hand2) {
        Card pairCard1 = this.cardCombinations.get(0);
        Card pairCard2 = hand2.getCardCombinations().get(0);

        if (pairCard1.compareTo(pairCard2) == 0) {
            return compareHighCard(hand2);
        } else return pairCard1.compareTo(pairCard2);
    }

    private int compareTwoPair(Hand hand2) {
        Card pairHighCard1 = this.cardCombinations.get(0);
        Card pairHighCard2 = hand2.getCardCombinations().get(0);

        if (pairHighCard1.compareTo(pairHighCard2) == 0) {
            Card pairSecondCard1 = this.cardCombinations.get(2);
            Card pairSecondCard2 = hand2.getCardCombinations().get(2);
            if (pairSecondCard1.compareTo(pairSecondCard2) == 0) {
                return compareHighCard(hand2);
            } else return pairSecondCard1.compareTo(pairSecondCard2);
        } else return pairHighCard1.compareTo(pairHighCard2);
    }

    private int compareThreeOfKind(Hand hand2) {
        Card threeCard1 = this.cardCombinations.get(0);
        Card threeCard2 = hand2.getCardCombinations().get(0);

        if (threeCard1.compareTo(threeCard2) == 0) {
            return compareHighCard(hand2);
        } else return threeCard1.compareTo(threeCard2);
    }

    private int compareStraight(Hand hand2) {
        List<Integer> wheel = new ArrayList<>(Arrays.asList(14, 2, 3, 4, 5));

        List<Integer> ranks1 = this.cardCombinations.stream()
                .map(card -> card.getRank().getPower())
                .collect(Collectors.toList());

        List<Integer> ranks2 = hand2.getCardCombinations().stream()
                .map(card -> card.getRank().getPower())
                .collect(Collectors.toList());

        if (ranks1.containsAll(wheel) && ranks2.containsAll(wheel)) { // hand1 and hand2 - wheels
            return compareHighCard(hand2);
        } else if (ranks1.containsAll(wheel) && !ranks2.containsAll(wheel)) { // hand1 < hand2
            return -1;
        } else if (!ranks1.containsAll(wheel) && ranks2.containsAll(wheel)) { // hand1 > hand2
            return 1;
        }

        int ranks1sum = ranks1.stream().mapToInt(Integer::intValue).sum();
        int ranks2sum = ranks2.stream().mapToInt(Integer::intValue).sum();

        if (ranks1sum == ranks2sum) {
            return compareHighCard(hand2);
        } else return Integer.compare(ranks1sum, ranks2sum);
    }

    private int compareFlush(Hand hand2) {
        if (this.cardCombinations.get(0).compareTo(hand2.getCardCombinations().get(0)) == 0) {
            if (this.cardCombinations.get(1).compareTo(hand2.getCardCombinations().get(1)) == 0) {
                if (this.cardCombinations.get(2).compareTo(hand2.getCardCombinations().get(2)) == 0) {
                    if (this.cardCombinations.get(3).compareTo(hand2.getCardCombinations().get(3)) == 0) {
                        if (this.cardCombinations.get(4).compareTo(hand2.getCardCombinations().get(4)) == 0) {
                            return compareHighCard(hand2);
                        } else return this.cardCombinations.get(4).compareTo(hand2.getCardCombinations().get(4));
                    } else return this.cardCombinations.get(3).compareTo(hand2.getCardCombinations().get(3));
                } else return this.cardCombinations.get(2).compareTo(hand2.getCardCombinations().get(2));
            } else return this.cardCombinations.get(1).compareTo(hand2.getCardCombinations().get(1));
        } else return this.cardCombinations.get(0).compareTo(hand2.getCardCombinations().get(0));
    }

    private int compareFullHouse(Hand hand2) {
        Card threeCard1 = this.cardCombinations.get(0);
        Card threeCard2 = hand2.getCardCombinations().get(0);

        if (threeCard1.compareTo(threeCard2) == 0) {
            Card pairCard1 = this.cardCombinations.get(3);
            Card pairCard2 = hand2.getCardCombinations().get(3);
            if (pairCard1.compareTo(pairCard2) == 0) {
                return compareHighCard(hand2);
            } else return pairCard1.compareTo(pairCard2);

        } else return threeCard1.compareTo(threeCard2);
    }

    private int compareFourOfKind(Hand hand2) {
        Card threeCard1 = this.cardCombinations.get(0);
        Card threeCard2 = hand2.getCardCombinations().get(0);

        return threeCard1.compareTo(threeCard2);
    }

    private int compareHighCard(Hand hand2) {
        List<Card> hand1Cards = this.cards;
        List<Card> hand2Cards = hand2.getCards();

        hand1Cards.sort(Comparator.reverseOrder()); // Descending
        hand2Cards.sort(Comparator.reverseOrder()); // Descending

        int comparisonHighCard = cards.get(0).compareTo(hand2Cards.get(0));
        int comparisonSecondCard = cards.get(1).compareTo(hand2Cards.get(1));

        if (comparisonHighCard > 0) {
            return 1;
        } else if (comparisonHighCard < 0) {
            return -1;
        } else return Integer.compare(comparisonSecondCard, 0);
    }

    private boolean detectStraightFlush(List<Card> cards) {
        List<Integer> straight = new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10));
        List<Integer> wheel = new ArrayList<>(Arrays.asList(14, 2, 3, 4, 5));
        List<Card> cardList = new ArrayList<>(cards);

        cardList = cardList.stream()
                .filter(distinctByKey(Card::getRank))
                .collect(Collectors.toList());

        if (cardList.size() < 5) return false;

        cardList.sort(Comparator.reverseOrder()); // Descending: A -> K -> Q -> ...
        List<Integer> cardsRank = cardList.stream()
                .map(card -> card.getRank().getPower())
                .collect(Collectors.toList());

        while (straight.get(straight.size() - 1) >= 2) {
            if (cardsRank.containsAll(straight)) {

                Map<CardSuit, Long> suitsCount = cardList.stream()
                        .collect(groupingBy(Card::getSuit, counting()));

                if (suitsCount.containsValue(5L) || suitsCount.containsValue(6L) || suitsCount.containsValue(7L)) {
                    this.combinationType = CombinationType.StraightFlush;
                    this.setCardCombinations(cardList.subList(0, 5));
                    return true;
                } else break;

            } else {
                straight = straight.stream()
                        .map(cardRank -> cardRank - 1)
                        .collect(Collectors.toList());
            }
        }

        if (cardsRank.containsAll(wheel)) {
            List<Card> cardCombinations = new ArrayList<>();

            cardList.stream()
                    .filter(c -> c.getRank().getPower() == 14
                            || c.getRank().getPower() == 2
                            || c.getRank().getPower() == 3
                            || c.getRank().getPower() == 4
                            || c.getRank().getPower() == 5)
                    .forEach(cardCombinations::add);

            Map<CardSuit, Long> suitsCount = cardCombinations.stream()
                    .collect(groupingBy(Card::getSuit, counting()));
            if (suitsCount.containsValue(5L)) {
                this.combinationType = CombinationType.StraightFlush;
                this.setCardCombinations(cardCombinations);
                return true;
            }
        }

        return false;
    }

    private boolean detectFourOfKind(List<Card> cards) {
        Map<CardRank, Long> ranksCount = cards.stream()
                .collect(groupingBy(Card::getRank, counting()));

        if (ranksCount.containsValue(4L)) {
            List<Card> cardCombinations = new ArrayList<>();

            CardRank fourCardRank = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 4L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            cards.stream()
                    .filter(card -> card.getRank() == fourCardRank)
                    .forEach(cardCombinations::add);

            this.combinationType = CombinationType.FourOfKind;
            this.setCardCombinations(cardCombinations);
            return true;
        }
        return ranksCount.containsValue(4L);
    }

    private boolean detectFullHouse(List<Card> cards) {
        Map<CardRank, Long> ranksCount = cards.stream()
                .collect(groupingBy(Card::getRank, counting()));

        if (ranksCount.containsValue(3L) && ranksCount.containsValue(2L)) {
            List<Card> cardCombinations = new ArrayList<>();

            CardRank threeCardRank = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 3L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            CardRank pairCardRank = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 2L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            cards.stream()
                    .filter(card -> card.getRank() == threeCardRank)
                    .forEach(cardCombinations::add);

            cards.stream()
                    .filter(card -> card.getRank() == pairCardRank)
                    .forEach(cardCombinations::add);

            this.combinationType = CombinationType.FullHouse;
            this.setCardCombinations(cardCombinations); // Ordered - [X,X,X,Y,Y]
            return true;
        }

        return false;
    }

    private boolean detectFlush(List<Card> cards) {
        Map<CardSuit, Long> suitsCount = cards.stream()
                .collect(groupingBy(Card::getSuit, counting()));

        if (suitsCount.containsValue(5L) || suitsCount.containsValue(6L) || suitsCount.containsValue(7L)) {
            List<Card> cardCombinations = new ArrayList<>();
            CardSuit flushCardSuit = suitsCount.entrySet().stream()
                    .filter(rc -> rc.getValue() >= 5L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            cards.stream()
                    .filter(card -> card.getSuit() == flushCardSuit)
                    .sorted(Comparator.reverseOrder()) // Descending
                    .limit(5)
                    .forEach(cardCombinations::add);

            this.combinationType = CombinationType.Flush;
            this.setCardCombinations(cardCombinations);
            return true;
        }
        return false;
    }

    private boolean detectStraight(List<Card> cards) {
        List<Integer> straight = new ArrayList<>(Arrays.asList(14, 13, 12, 11, 10));
        List<Integer> wheel = new ArrayList<>(Arrays.asList(14, 2, 3, 4, 5));
        List<Card> cardList = new ArrayList<>(cards);

        cardList = cardList.stream()
                .filter(distinctByKey(Card::getRank))
                .collect(Collectors.toList());

        if (cardList.size() < 5) return false;

        cardList.sort(Comparator.reverseOrder()); // Descending: A -> K -> Q -> ...
        List<Integer> cardsRank = cardList.stream()
                .map(card -> card.getRank().getPower())
                .collect(Collectors.toList());

        while (straight.get(straight.size() - 1) >= 2) {
            if (cardsRank.containsAll(straight)) {

                this.combinationType = CombinationType.Straight;
                this.setCardCombinations(cardList.subList(0, 5));
                return true;

            } else {
                straight = straight.stream()
                        .map(cardRank -> cardRank - 1)
                        .collect(Collectors.toList());
            }
        }

        if (cardsRank.containsAll(wheel)) {
            List<Card> cardCombinations = new ArrayList<>();

            cardList.stream()
                    .filter(c -> c.getRank().getPower() == 14
                            || c.getRank().getPower() == 2
                            || c.getRank().getPower() == 3
                            || c.getRank().getPower() == 4
                            || c.getRank().getPower() == 5)
                    .forEach(cardCombinations::add);

            this.combinationType = CombinationType.Straight;
            this.setCardCombinations(cardCombinations);
            return true;
        }

        return false;
    }

    private boolean detectThreeOfKind(List<Card> cards) {
        Map<CardRank, Long> ranksCount = cards.stream()
                .collect(groupingBy(Card::getRank, counting()));

        if (ranksCount.containsValue(3L)) {
            List<Card> cardCombinations = new ArrayList<>();

            CardRank threeCardRank = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 3L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            cards.stream()
                    .filter(card -> card.getRank() == threeCardRank)
                    .forEach(cardCombinations::add);

            this.combinationType = CombinationType.ThreeOfKind;
            this.setCardCombinations(cardCombinations);
            return true;
        }

        return false;
    }

    private boolean detectTwoPairs(List<Card> cards) {
        Map<CardRank, Long> ranksCount = cards.stream()
                .collect(groupingBy(Card::getRank, counting()));

        if (Collections.frequency(new ArrayList<>(ranksCount.values()), 2L) == 2) {
            List<Card> cardCombinations = new ArrayList<>();

            List<CardRank> pairedCardRanks = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 2L)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            pairedCardRanks.forEach(pairCardRank ->
                    cards.stream()
                            .filter(card -> card.getRank() == pairCardRank)
                            .forEach(cardCombinations::add)
            );

            this.combinationType = CombinationType.TwoPairs;
            this.setCardCombinations(cardCombinations);
            return true;
        }

        return false;
    }

    private boolean detectPair(List<Card> cards) {
        Map<CardRank, Long> ranksCount = cards.stream()
                .collect(groupingBy(Card::getRank, counting()));

        if (ranksCount.containsValue(2L)) {
            List<Card> cardCombinations = new ArrayList<>();

            CardRank pairCardRank = ranksCount.entrySet().stream()
                    .filter(rc -> rc.getValue() == 2L)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            cards.stream()
                    .filter(card -> card.getRank() == pairCardRank)
                    .forEach(cardCombinations::add);

            cardCombinations.sort(Comparator.reverseOrder()); // Descending
            this.combinationType = CombinationType.Pair;
            this.setCardCombinations(cardCombinations);
            return true;
        }

        return false;
    }


    /**
     * Function that returns a predicate that maintains state about what it's seen previously,
     * and that returns whether the given element was seen for the first time
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
