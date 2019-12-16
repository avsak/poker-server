package by.avsak.test;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class HandCompareToTest {
    private Hand hand1;
    private Hand hand2;

    @Before
    public void init() {
        hand1 = new Hand(Arrays.asList(new Card(CardRank.Ace, CardSuit.Clubs), new Card(CardRank.King, CardSuit.Clubs)));
        hand1.setCombinationType(CombinationType.Pair);

        hand2 = new Hand(Arrays.asList(new Card(CardRank.King, CardSuit.Hearts), new Card(CardRank.Queen, CardSuit.Hearts)));
        hand2.setCombinationType(CombinationType.Pair);
    }

    @Test
    public void compareTo() {
        assertEquals(hand1.compareTo(hand1), 0);
        assertEquals(hand1.compareTo(hand2), 1);
        assertEquals(hand2.compareTo(hand1), -1);
    }

}
