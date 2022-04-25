package blackjack.Cards;


import java.util.Objects;

public class NumberCard implements Card {
    public Colour colour;
    public int value;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return value == that.value && colour == that.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, value);
    }
}
