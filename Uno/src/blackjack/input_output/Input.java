package blackjack.input_output;

public interface Input {
    int getInputInRange(int minValue, int maxValue);
    String getString();
}
