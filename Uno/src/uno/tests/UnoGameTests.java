package uno.tests;

import uno.*;
import uno.Cards.*;
import uno.file_reading.ExistingGameDataCSVReader;
import uno.input_output.*;
import uno.file_reading.FakeExistingGameReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static uno.UnoGame.getCardsFromStringArray;

public class UnoGameTests {
    /** Returns an instance of the game which can be used for all the tests.
     *  DisplayStub is passed as argument, so none of the messages will actually be displayed.
     *
     * @return instance of game with generic arguments
     */
    public static UnoGame getGenericGameInstance() {
        return new UnoGame(new ArrayList<>(), new ConsoleOutput(), new ConsoleInput(), new DisplayStub(),
                new ExistingGameDataCSVReader());
    }

    @Test
    public void hasWon() {
        UnoGame game = getGenericGameInstance();
        Player playerWhoHasWon = new HumanPlayer("player");
        Player playerWhoHasNotWon = new HumanPlayer("player");

        playerWhoHasWon.setHand(new ArrayList<>());
        playerWhoHasNotWon.setHand(Arrays.asList(new Card[] {new NumberCard(Colour.red, 4)}));

        assertTrue(game.hasWon(playerWhoHasWon));
        assertFalse(game.hasWon(playerWhoHasNotWon));
    }

    @Test
    public void goToNextTurn() {
        UnoGame game = getGenericGameInstance();
        List<Player> players = Arrays.asList(
                new Player[] {new ComputerPlayer("p1"), new ComputerPlayer("p2")}
        );
        game.setPlayers(players);

        assertEquals(0, game.getCurrentPlayerIndex());
        game.goToNextTurn();
        assertEquals(1, game.getCurrentPlayerIndex());
        game.goToNextTurn();
        assertEquals(0, game.getCurrentPlayerIndex());
    }

    @Test
    public void getCardsFromString() throws DataFormatException {
        UnoGame game = getGenericGameInstance();
        List<Card> expectedCards = Arrays.asList(
                new NumberCard(Colour.red, 4),
                new SpecialCard(SpecialCardType.plus2, Colour.blue));

        String[] cardsAsStringArray = new String[] {"red 4", "blue plus2"};
        List<Card> functionOutput = getCardsFromStringArray(cardsAsStringArray);

        assertEquals(expectedCards.size(), functionOutput.size());
        for(int i=0; i<expectedCards.size(); i++) {
            assertEquals(expectedCards.get(i), functionOutput.get(i));
        }
    }

    @Test
    public void playFullGame() {
        UnoGame game = getGenericGameInstance();

        // Set input to testInput
        TestInput testInput = new TestInput();
        game.setUserInput(testInput);

        // Set existing to a fake existing game reader
        game.setExistingGameReader(new FakeExistingGameReader());

        // Set input for choosing to load existing game
        testInput.addIntValue(2);

        // Set input for choosing card to play
        testInput.addIntValue(1);

        game.playGame(false);

        // Check first player has won game
        assertEquals(game.getPlayers().get(0), game.whichPlayerHasWon());
    }

}
