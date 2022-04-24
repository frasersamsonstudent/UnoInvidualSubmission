package blackjack.Cards;


public class NumberCard implements Card {
    private Colour colour;
    private int value;

    public NumberCard(Colour colour, int value) {
        this.colour = colour;
        this.value = value;
    }

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean canPlay(Card playingCard) {
        if(playingCard instanceof NumberCard) {
            return this.value == playingCard.getValue() || this.colour == playingCard.getColour();
        }
        else {
            return this.colour == playingCard.getColour();
        }
    }

    @Override
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour + " " + value;
    }
}
