package blackjack;

import blackjack.Cards.*;

import java.util.*;

public class Deck {
    List<Card> cards;
    DeckReader deckReader;

    public Deck() {
        cards = new ArrayList<>();
        generateNewDeck();
    }

    public void generateNewDeck() {
        cards = new ArrayList<>();

        // Create cards for each colour
        EnumSet<Colour> colours = EnumSet.allOf(Colour.class);
        colours.forEach(colour -> {
            cards.addAll(getAllNumberCardsForColour(colour, 1));
            cards.addAll(getAllSpecialCardsForColour(colour, 1));
        });

        shuffleDeck();
    }

    public Card getTopCard() {
        return cards.get(0);
    }

    public void dealCardsToPlayers(List<Player> players, int numCards) {
        for(int i=0;i<numCards;i++) {
            for(Player player : players) {
                player.addCardToHand(cards.remove(0));
            }
        }
    }

    public void placeCardOnTop(Card card) {
        // Move card currently on top to end of deck
        Card currentTopCard = getTopCard();
        cards.remove(currentTopCard);
        cards.add(currentTopCard);

        // Place new card at top of deck
        cards.add(0, card);
    }

    public Card drawCardFromDeck() {
        return cards.remove(1);
    }

    public List<Card> drawMultipleCardsFromDeck(int numCards) {
        List<Card> drawnCards = new ArrayList<>();
        for(int i=0; i<numCards; i++) {
            drawnCards.add(cards.remove(1));
        }

        return drawnCards;
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    private List<Card> getAllNumberCardsForColour(Colour colour, int countOfEachCard) {
        List<Card> cards = new ArrayList<>();

        // Generate cards for each value from 1 to 9
        for(int cardValue=1; cardValue<=9; cardValue++) {
            for(int i=0; i<countOfEachCard; i++) {
                cards.add(new NumberCard(colour, cardValue));
            }
        }

        return cards;
    }

    private List<Card> getAllSpecialCardsForColour(Colour colour, int countOfEachCard) {
        List<Card> cards = new ArrayList<>();

        EnumSet<SpecialCardType> specialCardTypes = EnumSet.allOf(SpecialCardType.class);

        specialCardTypes.forEach(cardType -> {
            for(int i=0; i<countOfEachCard; i++) {
                cards.add(new SpecialCard(cardType, colour));
            }
        });

        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
