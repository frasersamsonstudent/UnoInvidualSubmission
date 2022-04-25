package blackjack;

import blackjack.Cards.*;
import blackjack.input_output.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.zip.DataFormatException;

public class UnoGame {
    Deck gameDeck = new Deck();
    List<Player> players;
    Output output;
    Input userInput;
    Display display;
    ReadDelimitedFile readDelimitedFile = new ReadDelimitedFile();
    int currentPlayerIndex;


    public static void main(String[] args) {
        UnoGame game = new UnoGame();
        game.playGame(true);
    }

    public UnoGame() {
        output = new ConsoleOutput();
        userInput = new ConsoleInput();
        display = new DisplayOutput(output);

        int[] numPlayers = userChooseNumberPlayers();
        initialisePlayers(numPlayers[0], numPlayers[1]);
    }

    public UnoGame(List<Player> players, Output output, Input input, Display display, ReadDelimitedFile readDelimitedFile) {
        this.players = players;
        this.output = output;
        this.userInput = input;
        this.display = display;
        this.readDelimitedFile = readDelimitedFile;
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

        boolean isLoadExistingGame = chooseLoadExistingGame();
        if(isLoadExistingGame) {
            handleLoadingExistingGame();
        }
        else if(shouldShuffleAndDeal) {
            gameDeck.shuffleDeck();
            gameDeck.dealCardsToPlayers(players, 7);
        }

        gameLoop();
        handleGameOver();
    }

    public void gameLoop() {
        output.newLine();

        // Main game loop
        while(!isGameOver()) {
            Player turnPlayer = players.get(currentPlayerIndex);
            display.playersTurn(turnPlayer, currentPlayerIndex);

            playTurn(turnPlayer);
            output.newLine();
        }
    }

    public boolean chooseLoadExistingGame() {
        display.chooseLoadExistingGame();
        List<String> options = Arrays.asList("Start new game", "Load existing game");
        display.displayListOfOptions(options);

        return userInput.getInputInRange(1, 2) == 2;
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

        goToNextTurn();
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

    public void handleLoadingExistingGame() {
        try {
            loadExistingGameData();
        }
        catch(Exception e) {
            display.couldNotReadFile();
            gameDeck.shuffleDeck();
            gameDeck.dealCardsToPlayers(players, 7);
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

    /** Load in an existing game from csv file.
     *  File has the format:
     *      cards in deck
     *      currentPlayerIndex
     *      Player1 (name, card1, ..., cardN)
     *      ...
     *      PlayerM (name, card1, ..., cardN)
     *
     * @throws DataFormatException thrown when the format of the csv file doesn't match expected
     */
    public void loadExistingGameData() throws DataFormatException, IOException {
        final String fileName = "existingGameData.csv";
        List<String[]> fileData;
        fileData = readDelimitedFile.getFileData(fileName);


        // Handle file not matching expected format
        if(fileData == null || fileData.size() != players.size() + 2) {
            throw new DataFormatException("File does not contain expected data");
        }

        String[] deckData = fileData.get(0);

        // Parse data for each player from file
        List<Player> playersFromFile = new ArrayList<>();
        for(int i=2; i<fileData.size(); i++) {
            String[] line = fileData.get(i);
            playersFromFile.add(getPlayerFromFileLine(line, i-2));
        }

        players = playersFromFile;
    }

    public Player getPlayerFromFileLine(String[] data, int playerIndex) throws DataFormatException {
        String name = data[0];
        List<Card> hand = getCardsFromStringArray(Arrays.copyOfRange(data, 1, data.length));
        Player player = players.get(playerIndex) instanceof HumanPlayer ?
                new HumanPlayer(name) : new ComputerPlayer(name);

        player.setHand(hand);
        return player;
    }

    public Card getCardFromString(String cardString) throws DataFormatException {
        Colour cardColour = null;

        // Find the colour of the card
        for(Colour possibleColour : EnumSet.allOf(Colour.class)) {
            if(cardString.contains(possibleColour.name())) {
                cardColour = possibleColour;
            }
        }
        if(cardColour == null) {
            throw new DataFormatException("Invalid colour");
        }

        // Check if card is any of the special cards
        for(SpecialCardType specialCardType : EnumSet.allOf(SpecialCardType.class)) {
            if(cardString.contains(specialCardType.name())) {
                return new SpecialCard(specialCardType, cardColour);
            }
        }

        // Card is not special card, so must be a NumberCard
        int cardValue = Integer.parseInt(cardString.split(cardColour.name() + " ")[1]);
        return new NumberCard(cardColour, cardValue);
    }

    public List<Card> getCardsFromStringArray(String[] cardStringArray) throws DataFormatException {
        List<Card> cards = new ArrayList<>();

        for(String cardString : cardStringArray) {
            cards.add(getCardFromString(cardString));
        }

        return cards;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void goToNextTurn() {
        currentPlayerIndex = getNextPlayerIndex();
    }
}
