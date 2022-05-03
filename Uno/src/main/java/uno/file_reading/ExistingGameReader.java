package uno.file_reading;

import uno.UnoGame;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface ExistingGameReader {

    void loadExistingGameData(UnoGame game) throws DataFormatException, IOException;

}
