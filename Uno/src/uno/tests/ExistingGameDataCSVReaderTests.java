package uno.tests;

import uno.Cards.*;
import uno.ComputerPlayer;
import uno.Player;
import uno.ReadDelimitedFile;
import uno.UnoGame;
import uno.file_reading.ExistingGameDataCSVReader;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExistingGameDataCSVReaderTests {

    /** Uses a mockFileReader in place of ReadDelimitedFile.
     *
     * @throws IOException
     */
    @Test
    public void loadExistingGameData() throws IOException {
        UnoGame game = UnoGameTests.getGenericGameInstance();
        game.setPlayers(Arrays.asList(new ComputerPlayer(""), new ComputerPlayer("")));
        ExistingGameDataCSVReader existingGameDataCSVReader = new ExistingGameDataCSVReader();

        // Mock ReadDelimitedFile to set the data which will be processed in game data reader
        List<String[]> mockFileData = Arrays.asList(
                new String[]{"red plus4, green 7"},
                new String[]{"0"},
                new String[]{"player1", "blue 9"},
                new String[]{"player2", "yellow skip"}
        );
        ReadDelimitedFile mockFileReader = mock(ReadDelimitedFile.class);
        when(mockFileReader.getFileData(anyString())).thenReturn(mockFileData);
        existingGameDataCSVReader.setReadDelimitedFile(mockFileReader);

        // Set the game instance's existingGameDataReader and call loadExistingGameData
        game.setExistingGameReader(existingGameDataCSVReader);
        game.handleLoadingExistingGame();

        // Check attributes of game match expected
        assertEquals(2, game.getPlayers().size());

        Card firstPlayerCard = new NumberCard(Colour.blue, 9);
        Card secondPlayerCard = new SpecialCard(SpecialCardType.skip, Colour.yellow);
        assertEquals(firstPlayerCard, game.getPlayers().get(0).getHand().get(0));
        assertEquals(secondPlayerCard, game.getPlayers().get(1).getHand().get(0));
    }

    @Test
    public void getPlayerFromFileLine() throws DataFormatException {
        ExistingGameDataCSVReader existingGameDataCSVReader = new ExistingGameDataCSVReader();

        List<Player> playersListForTest = Arrays.asList(new ComputerPlayer(""));
        Player returnedPlayer = existingGameDataCSVReader.getPlayerFromFileLine(
                new String[]{"playername", "red 5", "yellow wildcard"}, playersListForTest, 0
        );

        // Check player has expected name
        assertEquals("playername", returnedPlayer.getName());

        // Check player has expected hand
        List<Card> expectedHand = Arrays.asList(
                new NumberCard(Colour.red, 5), new SpecialCard(SpecialCardType.wildcard, Colour.yellow)
        );

        assertEquals(expectedHand.size(), returnedPlayer.getHand().size());
        for(int i=0; i< expectedHand.size(); i++) {
            assertEquals(expectedHand.get(i), returnedPlayer.getHand().get(i));
        }
    }

}
