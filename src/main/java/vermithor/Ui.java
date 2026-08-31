package vermithor;

import java.util.Scanner;

/** Handles console input and output for Vermithor. */
public class Ui implements AutoCloseable {
    private final Scanner scanner;

    /** Creates a console user interface. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Reads the next command line. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Prints a message. */
    public void show(String message) {
        System.out.println(message);
    }

    /** Closes the input stream wrapper. */
    @Override
    public void close() {
        scanner.close();
    }
}
