package uno.file_reading;

import uno.*;
import uno.Cards.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Fakes reading in of existing game data.
 * Sets the attributes of the passed game, using values stored in the class.
 */
public class FakeExistingGameReader implements ExistingGameReader{
    private Deck deck = new Deck(
            new ArrayList<>(Arrays.asList(
                    new SpecialCard(SpecialCardType.plus2, Colour.red),
                    new NumberCard(Colour.blue, 6),
                    new SpecialCard(SpecialCardType.wildcard, Colour.green),
                    new NumberCard(Colour.yellow, 8)
            ))
    );
    private List<List<Card>> listOfHands = Arrays.asList(
            new ArrayList<>(List.of(
                    new SpecialCard(SpecialCardType.plus2, Colour.red)
            )),
            new ArrayList<>(List.of(
                    new NumberCard(Colour.yellow, 8)
            )),
            new ArrayList<>(List.of(
                    new NumberCard(Colour.blue, 7)
            )),
            new ArrayList<>(List.of(
                    new SpecialCard(SpecialCardType.plus2, Colour.blue)
            ))
    );

    @Override
    public void loadExistingGameData(UnoGame game) {
        game.setGameDeck(deck);

        List<Player> players = new ArrayList<>();

        for(int i=0; i<2; i++) {
            Player player = new HumanPlayer("player" + i);
            player.setHand(listOfHands.get(i));

            players.add(player);
        }

        game.setPlayers(players);
    }
}
