package blackjack;

import blackjack.Cards.Card;
import blackjack.input_output.Input;

import java.util.ArrayList;
import java.util.List;

public class HumanPlayer implements Player {
    private List<Card> hand;
    private String name;

    public HumanPlayer(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Card chooseCardToPlay(List<Card> cardsWhichCanBePlayedInHand, Input input) {
        int userChoice = input.getInputInRange(1, cardsWhichCanBePlayedInHand.size());
        return cardsWhichCanBePlayedInHand.get(userChoice - 1);
    }

    @Override
    public void removeCardFromHand(Card cardToRemove) {
        hand.remove(cardToRemove);
    }

    @Override
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    @Override
    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
    }

    @Override
    public int chooseColour(Input input) {
        int userChoice = input.getInputInRange(1, 4);
        return userChoice-1;
    }
}
