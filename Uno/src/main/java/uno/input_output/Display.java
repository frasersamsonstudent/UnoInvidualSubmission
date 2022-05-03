package uno.input_output;

import uno.Cards.Card;
import uno.Player;

import java.util.List;

public interface Display {
    void setOutput(Output output);
    void noCardsCanBePlayed();
    void drewCard(Player player, Card drawnCard, boolean shouldOutputWhichCardDrawn);
    void showCardsWhichCanBePlayed(List<Card> cards);
    void showHand(List<Card> hand);
    void faceUpCard(Card faceUpCard);
    void playedCard(Card playedCard);
    void plusCard(Player playerWhoHasToDrawCards, int numCards);
    void chooseCard();
    void winningPlayer(Player winningPlayer);
    void noWinner();
    void enterPlayerName();
    void playersTurn(Player turnPlayer, int playerIndex);
    void displayListOfOptions(List<String> options);
    void selectingColour();
    void chooseLoadExistingGame();
    void couldNotReadFile();

}
