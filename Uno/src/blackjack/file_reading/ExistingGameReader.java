package blackjack.file_reading;

import blackjack.Player;

import java.util.List;

public interface ExistingGameReader {

    void loadExistingGameData(List<Player> players);

}
