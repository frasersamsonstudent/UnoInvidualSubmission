package blackjack;

import blackjack.Cards.Card;
import blackjack.Cards.SpecialCard;
import blackjack.Cards.Colour;
import blackjack.input_output.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class UnoGame {
    Deck gameDeck = new Deck();
    List<Player> players;
    Output output;
    Input userInput;
    Display display;
    int currentPlayerIndex;

    public static void main(String[] args) {
        UnoGame game = new UnoGame();
        game.playGame(true);
    }

    public UnoGame() {
        output = new ConsoleOutput();
        userInput = new ConsoleInput();
        display = new Display(output);

        int[] numPlayers = userChooseNumberPlayers();
        initialisePlayers(numPlayers[0], numPlayers[1]);
    }

    public UnoGame(List<Player> players, Output output, Input input) {
        this.players = players;
        this.output = output;
        this.userInput = input;
        this.display = new Display(output);
    }

    public void initialisePlayers(int humanPlayerCount, int cpuPlayerCount) {
        players = new ArrayList<>();

        for(int i=0; i<humanPlayerCount; i++) {
            players.add(new HumanPlayer(getUserToChooseName()));
        }

        for(int i=0; i<cpuPlayerCount; i++) {
            players.add(new ComputerPlayer("CPU" + (i+1)));
        }
    }

    public void playGame(boolean shouldShuffleAndDeal) {
        if(players == null) {
            int[] numPlayers = userChooseNumberPlayers();
            initialisePlayers(numPlayers[0], numPlayers[1]);
        }
        if(shouldShuffleAndDeal) {
            gameDeck.shuffleDeck();
            gameDeck.dealCardsToPlayers(players, 7);
        }

        // Main game loop
        while(!isGameOver()) {
            Player turnPlayer = players.get(currentPlayerIndex);
            display.playersTurn(turnPlayer, currentPlayerIndex);

            playTurn(turnPlayer);
            output.newLine();
        }

        handleGameOver();
    }

    public void playTurn(Player player) {
        if(hasWon(player)) {
            return;
        }

        display.faceUpCard(gameDeck.getTopCard());
        // Show hand if player is a human player
        if(player instanceof HumanPlayer) {
            display.showHand(player.getHand());
        }

        List<Card> cardsWhichCanBePlayed = getCardsWhichCanBePlayed(player, gameDeck.getTopCard());
        if(cardsWhichCanBePlayed.size() > 0) {
            handleCardsCanBePlayed(player, cardsWhichCanBePlayed);
        }
        else {
            handleNoCardsCanBePlayed(player);
        }

        currentPlayerIndex = getNextPlayerIndex();
    }

    public void handleCardsCanBePlayed(Player player, List<Card> cardsWhichCanBePlayed) {
        if(player instanceof HumanPlayer) {
            display.showCardsWhichCanBePlayed(cardsWhichCanBePlayed);
            display.chooseCard();
        }

        Card cardToPlay = player.chooseCardToPlay(cardsWhichCanBePlayed, userInput);
        player.removeCardFromHand(cardToPlay);
        handleCardPlayed(cardToPlay);
    }

    public void handleNoCardsCanBePlayed(Player player) {
        display.noCardsCanBePlayed();

        // Draw a new card from the deck and give to player's hand
        Card drawnCard = gameDeck.drawCardFromDeck();
        player.addCardToHand(drawnCard);

        display.drewCard(player, drawnCard, player instanceof HumanPlayer);
    }

    public List<Card> getCardsWhichCanBePlayed(Player player, Card topCard) {
        List<Card> cardsWhichCanBePlayed = new ArrayList<>();

        for(Card card : player.getHand()) {
            if(topCard.canPlay(card)) {
                cardsWhichCanBePlayed.add(card);
            }
        }

        return cardsWhichCanBePlayed;
    }

    public boolean hasWon(Player player) {
        return player.getHand().isEmpty();
    }

    public void handleCardPlayed(Card card) {
        display.playedCard(card);

        gameDeck.placeCardOnTop(card);

        if(card instanceof SpecialCard) {
            handleSpecialCardEffect((SpecialCard) card);
        }
    }

    public int getNextPlayerIndex() {
        int nextPlayerIndex = currentPlayerIndex + 1;

        if(nextPlayerIndex >= players.size()) {
            nextPlayerIndex = 0;
        }

        return nextPlayerIndex;
    }

    public void handleSpecialCardEffect(SpecialCard card) {
        switch (card.getCardType()) {
            case plus2 -> handlePlusCard(2);
            case plus4 -> handlePlusCard(4);
            case skip -> handleSkip();
            case wildcard -> handleWildcard();
        }
    }

    /** Next player must draw cards.
     *
     * @param valueOfPlusCard   number of cards to draw
     */
    public void handlePlusCard(int valueOfPlusCard) {
        Player nextPlayer = players.get(getNextPlayerIndex());
        display.plusCard(nextPlayer, valueOfPlusCard);

        // Draw 2 cards from deck
        List<Card> drawnCards = gameDeck.drawMultipleCardsFromDeck(valueOfPlusCard);
        nextPlayer.addCardsToHand(drawnCards);

        for(Card card : drawnCards) {
            display.drewCard(nextPlayer, card, nextPlayer instanceof HumanPlayer);
        }
    }

    /** Skip next player's turn.
     *
     */
    public void handleSkip() {
        currentPlayerIndex = getNextPlayerIndex();
    }

    /** Set colour of the top card to a user selected colour.
     *
     */
    public void handleWildcard() {
        EnumSet<Colour> colours = EnumSet.allOf(Colour.class);
        List<String> colourChoicesAsString = new ArrayList<>();
        for(Enum<Colour> colour : EnumSet.allOf(Colour.class)) {
            colourChoicesAsString.add(colour.name());
        }

        display.selectingColour();
        display.displayListOfOptions(colourChoicesAsString);

        int selectedColourIndex = players.get(currentPlayerIndex).chooseColour(userInput);
        gameDeck.getTopCard().setColour((Colour) colours.toArray()[selectedColourIndex]);
    }

    public boolean isGameOver() {
        return gameDeck.cards.isEmpty() || whichPlayerHasWon() != null;
    }

    public Player whichPlayerHasWon() {
        for(Player player : players) {
            if(player.getHand().isEmpty()) {
                return player;
            }
        }

        return null;
    }

    public String getUserToChooseName() {
        display.enterPlayerName();
        return userInput.getString();
    }

    public void handleGameOver() {
        Player playerWhoHasWon = whichPlayerHasWon();
        if(playerWhoHasWon != null) {
            display.winningPlayer(playerWhoHasWon);
        }
        else {
            display.noWinner();
        }
    }

    /** User chooses a number of human and cpu players.
     *
     * @return int[] [number of human players, number of computer players]
     */
    public int[] userChooseNumberPlayers() {
        String totalPlayersString = "How many players in total would you like in the game (including CPU)?";
        String humanPlayersString = "How many human players would you like in the game?";

        output.displayString(totalPlayersString);
        int totalPlayers = userInput.getInputInRange(1, 4);

        output.displayString(humanPlayersString);
        int humanPlayers = userInput.getInputInRange(0, 4);

        int cpuPlayers = totalPlayers - humanPlayers;

        return new int[] {humanPlayers, cpuPlayers};
    }


}
