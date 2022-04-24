package blackjack;

public class DeckReader {
    ReadDelimitedFile readDelimitedFile;
    private String fileName = "deck.csv";

    public DeckReader() {
        readDelimitedFile = new ReadDelimitedFile();
    }

    public void setReadDelimitedFile(ReadDelimitedFile readDelimitedFile) {
        this.readDelimitedFile = readDelimitedFile;
    }

    public void getDeckFromFile() {
        readDelimitedFile.getFileData(fileName);
    }
}
