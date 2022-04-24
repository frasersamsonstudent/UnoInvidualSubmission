package blackjack.input_output;

import java.util.Scanner;

public class ConsoleInput implements Input {
    Scanner scanner = new Scanner(System.in);

    @Override
    public int getInputInRange(int minValue, int maxValue) {
        int userInput = scanner.nextInt();

        while(userInput < minValue || userInput > maxValue) {
            System.out.println("Invalid option");
            userInput = scanner.nextInt();
        }

        return userInput;
    }

    @Override
    public String getString() {
        return scanner.next();
    }
}
