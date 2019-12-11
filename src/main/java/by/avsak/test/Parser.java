package by.avsak.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final Logger log = LogManager.getRootLogger();

    public PokerRound parsePokerRound(String line) {

        return null;
    }

    public List<Card> getCardsFromString(String cardsString) {
        List<String> cardsList = Arrays.asList(cardsString.split("(?<=\\G.{2})")); //split line by 2 characters
        List<Card> cards = new ArrayList<>();
        cardsList.forEach(cardStr ->
        {
            Card card = new Card();
            switch (cardStr.substring(0, 1)) {
                case ("2"):
                    card.setRank(CardRank.C2);
                    break;
                case ("3"):
                    card.setRank(CardRank.C3);
                    break;
                case ("4"):
                    card.setRank(CardRank.C4);
                    break;
                case ("5"):
                    card.setRank(CardRank.C5);
                    break;
                case ("6"):
                    card.setRank(CardRank.C6);
                    break;
                case ("7"):
                    card.setRank(CardRank.C7);
                    break;
                case ("8"):
                    card.setRank(CardRank.C8);
                    break;
                case ("9"):
                    card.setRank(CardRank.C9);
                    break;
                case ("T"):
                    card.setRank(CardRank.C10);
                    break;
                case ("J"):
                    card.setRank(CardRank.Jack);
                    break;
                case ("Q"):
                    card.setRank(CardRank.Queen);
                    break;
                case ("K"):
                    card.setRank(CardRank.King);
                    break;
                case ("A"):
                    card.setRank(CardRank.Ace);
                    break;
                default:
                    card = null;
                    break;
            }
            switch (cardStr.substring(1, 2)) {
                case ("h"):
                    card.setSuit(CardSuit.Hearts);
                    break;
                case ("d"):
                    card.setSuit(CardSuit.Diamonds);
                    break;
                case ("c"):
                    card.setSuit(CardSuit.Clubs);
                    break;
                case ("s"):
                    card.setSuit(CardSuit.Spades);
                    break;
                default:
                    card = null;
                    break;
            }
            cards.add(card);
        });
        return cards;
    }
    }
}
