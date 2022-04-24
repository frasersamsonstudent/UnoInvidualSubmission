package blackjack.Cards;

public class SpecialCard implements Card {
    SpecialCardType cardType;
    Colour colour;

    public SpecialCard(SpecialCardType cardType, Colour colour) {
        this.cardType = cardType;
        this.colour = colour;
    }

    public SpecialCardType getCardType() {
        return cardType;
    }

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public boolean canPlay(Card card) {
        return card.getColour().equals(this.colour) ||
                (card instanceof SpecialCard && ((SpecialCard) card).getSpecialCardType().equals(this.cardType));
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public SpecialCardType getSpecialCardType() {
        return cardType;
    }


    @Override
    public String toString() {
        return colour + " " + cardType;
    }
}
