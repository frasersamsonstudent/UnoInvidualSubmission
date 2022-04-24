package blackjack.input_output;

import blackjack.Cards.Card;
import blackjack.Player;

import java.util.Arrays;
import java.util.List;

/** Class which handles the text that should be output.
 *
 */
public class Display {
    private Output output;
    private List<TextColour> playerColours = Arrays.asList(TextColour.boldRed, TextColour.boldYellow,
            TextColour.boldGreen, TextColour.boldBlue);

    public Display(Output output) {
        this.output = output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void noCardsCanBePlayed() {
        System.out.println("No cards can be played");
    }

    public void drewCard(Player player, Card drawnCard, boolean shouldOutputWhichCardDrawn) {
        String cardName = shouldOutputWhichCardDrawn ? drawnCard.toString() : "card";
        output.displayString(player.getName() + " drew a " + cardName);
    }

    public void showCardsWhichCanBePlayed(List<Card> cards) {
        int cardCounter = 1;

        for(Card card : cards) {
            output.displayString(cardCounter + ") " + card);
            cardCounter++;
        }

    }

    public void showHand(List<Card> hand) {
        StringBuilder outputStringBuilder = new StringBuilder();
        outputStringBuilder.append("Hand: ");

        for(int i=0; i < hand.size(); i++) {
            outputStringBuilder.append(hand.get(i));

            if(i != hand.size()-1) {
                outputStringBuilder.append(", ");
            }
        }

        output.displayString(outputStringBuilder.toString());
    }

    public void faceUpCard(Card faceUpCard) {
        String outputString = "Face up card: " + faceUpCard;
        output.displayString(outputString);
    }

    public void playedCard(Card playedCard) {
        output.displayString("Played: " + playedCard);
    }

    public void plusCard(Player playerWhoHasToDrawCards, int numCards) {
        String outputString = playerWhoHasToDrawCards.getName() + " has to draw " + numCards + " cards";
        output.displayString(outputString);
    }

    public void chooseCard() {
        output.displayString("Which card do you want to play?");
    }

    public void winningPlayer(Player winningPlayer) {
        output.displayString(winningPlayer.getName() + " has won.");
    }

    public void noWinner() {
        output.displayString("No players have won");
    }

    public void enterPlayerName() {
        output.displayString("Enter player name: ");
    }

    public void playersTurn(Player turnPlayer, int playerIndex) {
        if(playerIndex >= 0 && playerIndex < playerColours.size()) {
            output.setColourForNextLine(playerColours.get(playerIndex));
        }
        output.displayString(turnPlayer.getName() + "'s turn");
    }

    public void displayListOfOptions(List<String> options) {
        int choiceCounter = 1;
        for(String option : options) {
            output.displayString(choiceCounter + ") " + option);
            choiceCounter++;
        }
    }

    public void selectingColour() {
        output.displayString("Select a colour to change the top card to:");
    }

}
