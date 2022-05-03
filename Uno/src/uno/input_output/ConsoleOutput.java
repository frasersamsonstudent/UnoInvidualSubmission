package uno.input_output;

import java.util.Map;

public class ConsoleOutput implements Output {
    private final Map<TextColour, String> colourStrings = getColourCodeMap();
    private TextColour colour;
    private final String colourResetString = "\u001B[0m";

    @Override
    public void displayString(String outputString) {
        if(colour == null) {
            System.out.println(outputString);
        }
        else {
            String outputStringWithColour = colourStrings.get(colour) + outputString + colourResetString;
            System.out.println(outputStringWithColour);
            colour = null;
        }
    }

    @Override
    public void newLine() {
        System.out.println("\n");
    }

    @Override
    public void setColourForNextLine(TextColour textColour) {
        colour = textColour;
    }

    public Map<TextColour, String> getColourCodeMap() {
        return Map.ofEntries(
                // Regular font weight
                Map.entry(TextColour.black, "\033[0;30m"),
                Map.entry(TextColour.red, "\033[0;31m"),
                Map.entry(TextColour.green, "\033[0;32m"),
                Map.entry(TextColour.yellow, "\033[0;33m"),
                Map.entry(TextColour.blue, "\033[0;34m"),
                Map.entry(TextColour.purple, "\033[0;35m"),
                Map.entry(TextColour.cyan, "\033[0;36m"),
                Map.entry(TextColour.white, "\033[0;37m"),

                // Bold
                Map.entry(TextColour.boldBlack, "\033[1;30m"),
                Map.entry(TextColour.boldRed, "\033[1;31m"),
                Map.entry(TextColour.boldGreen, "\033[1;32m"),
                Map.entry(TextColour.boldYellow, "\033[1;33m"),
                Map.entry(TextColour.boldBlue, "\033[1;34m"),
                Map.entry(TextColour.boldPurple, "\033[1;35m"),
                Map.entry(TextColour.boldCyan, "\033[1;36m"),
                Map.entry(TextColour.boldWhite, "\033[1;37m")
        );
    }

}
