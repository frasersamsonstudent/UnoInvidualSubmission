package uno.input_output;

import java.util.ArrayList;
import java.util.List;

public class TestInput implements Input {
    List<Integer> intValues = new ArrayList<>();
    List<String> stringValues = new ArrayList<>();

    @Override
    public int getInputInRange(int minValue, int maxValue) {
        return intValues.remove(0);
    }

    @Override
    public String getString() {
        return stringValues.remove(0);
    }

    public void addIntValue(int newValue) {
        intValues.add(newValue);
    }

    public void addStringValue(String newValue) {
        stringValues.add(newValue);
    }
}
