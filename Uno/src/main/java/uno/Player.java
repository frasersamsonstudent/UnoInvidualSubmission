package uno;

import uno.Cards.Card;
import uno.input_output.Input;

import java.util.List;

public interface Player {
    List<Card> getHand();
    String getName();
    void setHand(List<Card> hand);

    Card chooseCardToPlay(List<Card> cardsWhichCanBePlayedInHand, Input input);
    void removeCardFromHand(Card cardToRemove);
    void addCardToHand(Card card);
    void addCardsToHand(List<Card> cards);
    int chooseColour(Input input);
}
