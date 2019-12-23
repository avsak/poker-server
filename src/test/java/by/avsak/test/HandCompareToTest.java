package by.avsak.test;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class HandCompareToTest {

    @Test
    public void pairComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Clubs))
        );
        hand1.setCombinationType(CombinationType.Pair);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.Ace, CardSuit.Clubs))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.Pair);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.Queen, CardSuit.Clubs),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

    @Test
    public void twoPairComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Clubs))
        );
        hand1.setCombinationType(CombinationType.TwoPairs);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Diamonds))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.TwoPairs);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.Queen, CardSuit.Hearts),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

    @Test
    public void threeOfKindComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.Queen, CardSuit.Clubs),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );
        hand1.setCombinationType(CombinationType.ThreeOfKind);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.Queen, CardSuit.Clubs),
                new Card(CardRank.Queen, CardSuit.Hearts),
                new Card(CardRank.Queen, CardSuit.Diamonds))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.ThreeOfKind);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.Jack, CardSuit.Clubs),
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Diamonds))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

    @Test
    public void straightComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.C5, CardSuit.Clubs))
        );
        hand1.setCombinationType(CombinationType.Straight);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.C2, CardSuit.Clubs),
                new Card(CardRank.C3, CardSuit.Hearts),
                new Card(CardRank.C4, CardSuit.Diamonds),
                new Card(CardRank.C5, CardSuit.Spades ))
        ); // wheel

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.C3, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.Straight);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.C6, CardSuit.Clubs),
                new Card(CardRank.C5, CardSuit.Clubs),
                new Card(CardRank.C4, CardSuit.Hearts),
                new Card(CardRank.C3, CardSuit.Diamonds),
                new Card(CardRank.C2, CardSuit.Spades ))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), -1);
        assertEquals(hand2.compareTo(hand1), 1);
    }

    @Test
    public void flushComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.C8, CardSuit.Diamonds),
                new Card(CardRank.C10, CardSuit.Hearts))
        );
        hand1.setCombinationType(CombinationType.Flush);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.C10, CardSuit.Hearts),
                new Card(CardRank.C9, CardSuit.Hearts))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.C2, CardSuit.Diamonds),
                new Card(CardRank.C7, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.Flush);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.C10, CardSuit.Hearts),
                new Card(CardRank.C7, CardSuit.Hearts))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

    @Test
    public void fullHouseComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.C10, CardSuit.Hearts),
                new Card(CardRank.C10, CardSuit.Diamonds))
        );
        hand1.setCombinationType(CombinationType.FullHouse);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.King, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.C10, CardSuit.Hearts),
                new Card(CardRank.C10, CardSuit.Diamonds))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.C9, CardSuit.Diamonds),
                new Card(CardRank.C9, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.FullHouse);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.King, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.C9, CardSuit.Hearts),
                new Card(CardRank.C9, CardSuit.Diamonds))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

    @Test
    public void fourOfKindComparison() {
        Hand hand1 = new Hand(Arrays.asList(
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.King, CardSuit.Clubs))
        );
        hand1.setCombinationType(CombinationType.FourOfKind);
        hand1.setCardCombinations(Arrays.asList(
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.King, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Hearts),
                new Card(CardRank.King, CardSuit.Diamonds))
        );

        Hand hand2 = new Hand(Arrays.asList(
                new Card(CardRank.Queen, CardSuit.Spades),
                new Card(CardRank.Queen, CardSuit.Hearts))
        );
        hand2.setCombinationType(CombinationType.FourOfKind);
        hand2.setCardCombinations(Arrays.asList(
                new Card(CardRank.Queen, CardSuit.Spades),
                new Card(CardRank.Queen, CardSuit.Hearts),
                new Card(CardRank.Queen, CardSuit.Diamonds),
                new Card(CardRank.Queen, CardSuit.Clubs))
        );

        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }
}
