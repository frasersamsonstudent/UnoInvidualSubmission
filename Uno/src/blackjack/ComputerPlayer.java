package blackjack;

import blackjack.Cards.Card;
import blackjack.input_output.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer implements Player {
    private List<Card> hand;
    private String name;
    private Random rand = new Random();

    public ComputerPlayer(String cpuName) {
        name = cpuName;
        hand = new ArrayList<>();
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
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
        // Choose random card here
        return cardsWhichCanBePlayedInHand.remove(0);
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
        return getRandomNumberInRange(1, 4);
    }

    private int getRandomNumberInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
