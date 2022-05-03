package uno.tests;

import org.junit.Test;
import uno.Cards.*;
import uno.Deck;
import uno.HumanPlayer;
import uno.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void dealCardsToPlays() {
        List<Player> players = new ArrayList<>(Arrays.asList(
                new HumanPlayer("player1"),
                new HumanPlayer("player2")
        ));

        Deck deck = new Deck();
        deck.dealCardsToPlayers(players, 7);

        // Check each player has been dealt 7 cards
        for(Player player : players) {
            assertEquals(7, player.getHand().size());
        }
    }

    @Test
    public void drawMultipleCardsFromDeck() {
        Deck deck = new Deck();
        assertEquals(5, deck.drawMultipleCardsFromDeck(5).size());
    }

    @Test
    public void drawCardFromDeck() {
        Deck deck = new Deck();

        int initialCardCount = deck.getNumberOfCards();
        assertTrue(initialCardCount > 0);

        // Check that there is one less card in the deck after drawing a card
        deck.drawCardFromDeck();
        assertEquals(initialCardCount-1, deck.getNumberOfCards());
    }

    @Test
    public void getAllSpecialCardsForColour() {
        Deck deck = new Deck();
        List<Card> redSpecialCards = deck.getAllSpecialCardsForColour(Colour.red, 1);

        for(SpecialCardType specialCardType : EnumSet.allOf(SpecialCardType.class)) {
            assertTrue(
                    redSpecialCards.contains(new SpecialCard(specialCardType, Colour.red))
            );
        }
    }

    @Test
    public void getAllNumberCardsForColour() {
        Deck deck = new Deck();
        List<Card> redNumberCards = deck.getAllNumberCardsForColour(Colour.red, 1);

        for(int i=1; i<=9; i++) {
            assertTrue(redNumberCards.contains(new NumberCard(Colour.red, i)));
        }
    }

}
