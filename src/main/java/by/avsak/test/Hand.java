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
                    this.combinationType.setCardCombinations(cardList.subList(0, 5));
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
                this.combinationType.setCardCombinations(cardCombinations);
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
            this.combinationType.setCardCombinations(cardCombinations);
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
            this.combinationType.setCardCombinations(cardCombinations); // Ordered - [X,X,X,Y,Y]
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
            this.combinationType.setCardCombinations(cardCombinations);
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
                this.combinationType.setCardCombinations(cardList.subList(0, 5));
                System.out.println(cardList.subList(0, 5));
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
            this.combinationType.setCardCombinations(cardCombinations);
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
            this.combinationType.setCardCombinations(cardCombinations);
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
            this.combinationType.setCardCombinations(cardCombinations);
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

            this.combinationType = CombinationType.Pair;
            this.combinationType.setCardCombinations(cardCombinations);
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
