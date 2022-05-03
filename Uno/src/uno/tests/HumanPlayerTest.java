package uno.tests;

import org.junit.Test;
import uno.Cards.Card;
import uno.Cards.Colour;
import uno.Cards.NumberCard;
import uno.ComputerPlayer;
import uno.HumanPlayer;
import uno.Player;
import uno.input_output.TestInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class HumanPlayerTest {
    private Player getHumanPlayer() {
            return new HumanPlayer("name");
    }

    @Test
    public void chooseCardToPlay() {
        Player player = getHumanPlayer();

        // Create a testInput for selecting a card
        TestInput testInput = new TestInput();
        testInput.addIntValue(1);

        List<Card> playableCards = new ArrayList<>(Arrays.asList(
                new NumberCard(Colour.red, 5),
                new NumberCard(Colour.red, 6)
        ));

        Card playedCard = player.chooseCardToPlay(playableCards, testInput);
        assertEquals(playableCards.get(0), playedCard);
    }
}
