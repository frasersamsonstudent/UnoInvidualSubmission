package uno.input_output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestOutput implements Output {
    List<String> outputValues = new ArrayList<>();
    List<String> colourStrings = Arrays.asList(
            "\033[0;30m", "\033[0;31m", "\033[0;32m", "\033[0;33m",
            "\033[0;34m", "\033[0;35m", "\033[0;36m", "\033[0;37m",
            "\033[1;30m", "\033[1;31m", "\033[1;32m", "\033[1;33m",
            "\033[1;34m", "\033[1;35m", "\033[1;36m", "\033[1;37m"
    );
    @Override
    public void displayString(String outputString) {
        outputString = getStringWithColourRemoved(outputString);
        outputValues.add(outputString);
    }

    @Override
    public void newLine() {

    }

    @Override
    public void setColourForNextLine(TextColour textColour) {

    }

    private String getStringWithColourRemoved(String string) {
        for(String colourString : colourStrings) {
            string = string.replace(colourString, "");
        }

        return string;
    }


    public String getMostRecentOutput() {
        return outputValues.get(outputValues.size()-1);
    }
}
