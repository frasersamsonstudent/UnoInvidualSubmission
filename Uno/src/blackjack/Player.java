package blackjack;

import blackjack.Cards.Card;
import blackjack.input_output.Input;

import java.util.List;

public interface Player {
    List<Card> getHand();
    String getName();

    Card chooseCardToPlay(List<Card> cardsWhichCanBePlayedInHand, Input input);
    void removeCardFromHand(Card cardToRemove);
    void addCardToHand(Card card);
    void addCardsToHand(List<Card> cards);
    int chooseColour(Input input);
}
