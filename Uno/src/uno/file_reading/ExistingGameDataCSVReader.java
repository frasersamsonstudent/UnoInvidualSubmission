package uno.file_reading;

import uno.*;
import uno.Cards.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;


/** Reads in game data from existingGameData.csv, and updates values of the passed game instance.
 * Class uses ReadDelimitedFile for reading the csv, and then parses the strings which are returned.
 *
 */
public class ExistingGameDataCSVReader implements  ExistingGameReader{
    ReadDelimitedFile readDelimitedFile = new ReadDelimitedFile();

    /** Load in an existing game from csv file.
     *  File has the format:
     *      cards in deck
     *      currentPlayerIndex
     *      Player1 (name, card1, ..., cardN)
     *      ...
     *      PlayerM (name, card1, ..., cardN)
     *
     * @throws DataFormatException thrown when the format of the csv file doesn't match expected
     * @return players loaded from
     */
    public void loadExistingGameData(UnoGame game) throws DataFormatException, IOException {
        final String fileName = "existingGameData.csv";
        List<String[]> fileData;
        fileData = readDelimitedFile.getFileData(fileName);
        List<Player> players = game.getPlayers();


        // Handle file not matching expected format
        if(fileData == null || fileData.size() != players.size() + 2) {
            throw new DataFormatException("File does not contain expected data");
        }

        String[] deckData = fileData.get(0);

        // Parse data for each player from file
        List<Player> playersFromFile = new ArrayList<>();
        for(int i=2; i<fileData.size(); i++) {
            String[] line = fileData.get(i);
            playersFromFile.add(getPlayerFromFileLine(line, players,i-2));
        }

        game.setGameDeck(new Deck(UnoGame.getCardsFromStringArray(deckData)));
        game.setPlayers(playersFromFile);
    }

    public Player getPlayerFromFileLine(String[] data, List<Player> players, int playerIndex) throws DataFormatException {
        String name = data[0];
        List<Card> hand = UnoGame.getCardsFromStringArray(Arrays.copyOfRange(data, 1, data.length));
        Player player = players.get(playerIndex) instanceof HumanPlayer ?
                new HumanPlayer(name) : new ComputerPlayer(name);

        player.setHand(hand);
        return player;
    }

    public void setReadDelimitedFile(ReadDelimitedFile readDelimitedFile) {
        this.readDelimitedFile = readDelimitedFile;
    }

}
