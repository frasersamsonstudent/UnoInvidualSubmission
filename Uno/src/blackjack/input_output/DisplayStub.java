package blackjack.input_output;

import blackjack.Cards.Card;
import blackjack.Player;

import java.util.List;

/** Stub which can be used in place of a display.
 * Does not actually output anything when called, will just prevent test failing because of the display.
 *
 */
public class DisplayStub implements Display {
    @Override
    public void setOutput(Output output) {

    }

    @Override
    public void noCardsCanBePlayed() {

    }

    @Override
    public void drewCard(Player player, Card drawnCard, boolean shouldOutputWhichCardDrawn) {

    }

    @Override
    public void showCardsWhichCanBePlayed(List<Card> cards) {

    }

    @Override
    public void showHand(List<Card> hand) {

    }

    @Override
    public void faceUpCard(Card faceUpCard) {

    }

    @Override
    public void playedCard(Card playedCard) {

    }

    @Override
    public void plusCard(Player playerWhoHasToDrawCards, int numCards) {

    }

    @Override
    public void chooseCard() {

    }

    @Override
    public void winningPlayer(Player winningPlayer) {

    }

    @Override
    public void noWinner() {

    }

    @Override
    public void enterPlayerName() {

    }

    @Override
    public void playersTurn(Player turnPlayer, int playerIndex) {

    }

    @Override
    public void displayListOfOptions(List<String> options) {

    }

    @Override
    public void selectingColour() {

    }

    @Override
    public void chooseLoadExistingGame() {

    }

    @Override
    public void couldNotReadFile() {

    }
}
