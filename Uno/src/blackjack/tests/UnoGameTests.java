package blackjack.tests;

import blackjack.*;
import blackjack.Cards.*;
import blackjack.input_output.ConsoleInput;
import blackjack.input_output.ConsoleOutput;
import blackjack.input_output.DisplayStub;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

public class UnoGameTests {
    private UnoGame getGenericGameInstance() {
        return new UnoGame(new ArrayList<>(), new ConsoleOutput(), new ConsoleInput(), new DisplayStub(),
                new ReadDelimitedFile());
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
        List<Card> functionOutput = game.getCardsFromStringArray(cardsAsStringArray);

        assertEquals(expectedCards.size(), functionOutput.size());
        for(int i=0; i<expectedCards.size(); i++) {
            assertEquals(expectedCards.get(i), functionOutput.get(i));
        }
    }
}
