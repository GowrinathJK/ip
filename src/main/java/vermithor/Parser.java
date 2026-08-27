package vermithor;

/** Converts raw user input into a command and its remaining details. */
public class Parser {
    /** A parsed command word and its optional details. */
    public record ParsedCommand(CommandType command, String details) { }

    /** Parses one line of input. */
    public ParsedCommand parse(String input) {
        String[] parts = input.trim().split(" ", 2);
        String details = parts.length == 2 ? parts[1].trim() : "";
        return new ParsedCommand(CommandType.fromCommandWord(parts[0]), details);
    }
}
