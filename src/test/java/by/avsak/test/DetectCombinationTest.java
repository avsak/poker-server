package by.avsak.test;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class DetectCombinationTest {
    private Board board;

    @Before
    public void init() {
        List<Card> boardCards = new ArrayList<>();
        boardCards.add(new Card(CardRank.Ace, CardSuit.Spades));
        boardCards.add(new Card(CardRank.King, CardSuit.Spades));
        boardCards.add(new Card(CardRank.Queen, CardSuit.Spades));
        boardCards.add(new Card(CardRank.Jack, CardSuit.Spades));
        boardCards.add(new Card(CardRank.Jack, CardSuit.Clubs));
        board = new Board(boardCards); // AsKsQsJsJс
    }

    @Test
    public void detectStraightFlush() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.C10, CardSuit.Spades),
                new Card(CardRank.C2, CardSuit.Clubs)));

        hand.detectCombination(board); // Ts2c
        assertEquals(hand.getCombinationType(), CombinationType.StraightFlush);
    }

    @Test
    public void detectFourOfKind() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Diamonds)));

        hand.detectCombination(board); // JhJd
        assertEquals(hand.getCombinationType(), CombinationType.FourOfKind);
    }

    @Test
    public void detectFullHouse() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Hearts),
                new Card(CardRank.Jack, CardSuit.Diamonds)));

        hand.detectCombination(board); // AhJd
        assertEquals(hand.getCombinationType(), CombinationType.FullHouse);
    }

    @Test
    public void detectFlush() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.C2, CardSuit.Spades),
                new Card(CardRank.C3, CardSuit.Hearts)));

        hand.detectCombination(board); // 2s3h
        assertEquals(hand.getCombinationType(), CombinationType.Flush);
    }

    @Test
    public void detectStraight() {
        Hand hand = new Hand(Arrays.asList(new Card(CardRank.C10, CardSuit.Hearts), new Card(CardRank.C3, CardSuit.Hearts)));
        hand.detectCombination(board); // Th3h

        assertEquals(hand.getCombinationType(), CombinationType.Straight);
    }

    @Test
    public void detectThreeOfKind() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.Jack, CardSuit.Hearts),
                new Card(CardRank.C2, CardSuit.Diamonds)));

        hand.detectCombination(board); // Jh2d
        assertEquals(hand.getCombinationType(), CombinationType.ThreeOfKind);
    }

    @Test
    public void detectTwoPairs() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.C2, CardSuit.Hearts),
                new Card(CardRank.C2, CardSuit.Diamonds)));

        hand.detectCombination(board); // 2h2d
        assertEquals(hand.getCombinationType(), CombinationType.TwoPairs);
    }

    @Test
    public void detectPair() {
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.C2, CardSuit.Hearts),
                new Card(CardRank.C3, CardSuit.Diamonds)));

        hand.detectCombination(board); // 2h3d
        assertEquals(hand.getCombinationType(), CombinationType.Pair);
    }

    @Test
    public void detectHighCard() {
        Board board = new Board(Arrays.asList(
                new Card(CardRank.Ace, CardSuit.Clubs),
                new Card(CardRank.King, CardSuit.Spades),
                new Card(CardRank.C4, CardSuit.Diamonds),
                new Card(CardRank.Jack, CardSuit.Spades),
                new Card(CardRank.C9, CardSuit.Hearts))); //AcKsQdJsTh
        Hand hand = new Hand(Arrays.asList(
                new Card(CardRank.C2, CardSuit.Hearts),
                new Card(CardRank.C3, CardSuit.Diamonds)));
        hand.detectCombination(board); // 2h3d

        assertEquals(hand.getCombinationType(), CombinationType.HighCard);
    }
}