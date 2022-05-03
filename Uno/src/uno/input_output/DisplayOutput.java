package uno.input_output;

import uno.Cards.Card;
import uno.Cards.Colour;
import uno.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Class which handles the text that should be output.
 *
 */
public class DisplayOutput implements  Display {
    private Output output;
    final private List<TextColour> playerColours = Arrays.asList(TextColour.boldCyan, TextColour.boldPurple,
            TextColour.boldWhite, TextColour.boldBlack);
    final private Map<Colour, String> cardColourToColourString = Map.ofEntries(
            Map.entry(Colour.red, "\033[0;31m"),
            Map.entry(Colour.green, "\033[0;32m"),
            Map.entry(Colour.yellow, "\033[0;33m"),
            Map.entry(Colour.blue, "\033[0;34m")
    );
    private final String colourResetString = "\u001B[0m";

    public DisplayOutput(Output output) {
        this.output = output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void noCardsCanBePlayed() {
        System.out.println("No cards can be played");
    }

    public void drewCard(Player player, Card drawnCard, boolean shouldOutputWhichCardDrawn) {
        String cardName = shouldOutputWhichCardDrawn ? getCardStringWithColour(drawnCard) : "card";
        output.displayString(player.getName() + " drew a " + cardName);
    }

    public void showCardsWhichCanBePlayed(List<Card> cards) {
        int cardCounter = 1;

        for(Card card : cards) {
            output.displayString(cardCounter + ") " + getCardStringWithColour(card));
            cardCounter++;
        }

    }

    public void showHand(List<Card> hand) {
        StringBuilder outputStringBuilder = new StringBuilder();
        outputStringBuilder.append("Hand: ");

        for(int i=0; i < hand.size(); i++) {
            outputStringBuilder.append(
                    getCardStringWithColour(hand.get(i))
            );

            if(i != hand.size()-1) {
                outputStringBuilder.append(", ");
            }
        }

        output.displayString(outputStringBuilder.toString());
    }

    public void faceUpCard(Card faceUpCard) {
        String outputString = "Face up card: " + getCardStringWithColour(faceUpCard);
        output.displayString(outputString);
    }

    public void playedCard(Card playedCard) {
        output.displayString("Played: " + getCardStringWithColour(playedCard));
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

    public void chooseLoadExistingGame() {
        output.displayString("Would you like to start a new game or load an existing game");
    }

    private String getCardStringWithColour(Card card) {
        String colourString = cardColourToColourString.get(card.getColour());
        return colourString + card + colourResetString;
    }

    public void couldNotReadFile() {
        output.displayString("Could not get data from file. Creating new game instead.");
    }


}
