package uno.Cards;

public interface Card {
    Colour getColour();
    int getValue();
    boolean canPlay(Card card);
    void setColour(Colour colour);

}
