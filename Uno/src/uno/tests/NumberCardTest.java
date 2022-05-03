package uno.tests;

import org.junit.Test;
import uno.Cards.Card;
import uno.Cards.Colour;
import uno.Cards.NumberCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NumberCardTest {
    @Test
    public void canPlay() {
        Card topCard = new NumberCard(Colour.red, 7);
        List<Card> cardsWhichCanBePlayed = new ArrayList<>(
                Arrays.asList(
                        new NumberCard(Colour.red, 6),
                        new NumberCard(Colour.red, 7),
                        new NumberCard(Colour.green, 7)
                )
        );
        Card cardWhichCanNotBePlayed = new NumberCard(Colour.green, 5);

        // Check output matches expected
        for(int i=0; i<cardsWhichCanBePlayed.size(); i++) {
            assertTrue(topCard.canPlay(cardsWhichCanBePlayed.get(i)));
        }
        assertFalse(topCard.canPlay(cardWhichCanNotBePlayed));
    }
}
