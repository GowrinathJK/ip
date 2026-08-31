package vermithor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** A minimal, fit-for-purpose JavaFX interface for Vermithor. */
public class MainApp extends Application {
    private final List<Task> tasks = new ArrayList<>();
    private final Parser parser = new Parser();
    private final Storage storage = new Storage(Path.of("data", "vermithor.txt"));
    private final TextArea transcript = new TextArea();

    /** Builds and displays the chatbot window. */
    @Override
    public void start(Stage stage) {
        transcript.setEditable(false);
        transcript.setWrapText(true);
        TextField input = new TextField();
        input.setPromptText("Enter a command, e.g. list or todo read a book");
        Button send = new Button("Send");
        send.setOnAction(event -> submit(input));
        input.setOnAction(event -> submit(input));

        HBox controls = new HBox(8, input, send);
        controls.setPadding(new Insets(10));
        BorderPane root = new BorderPane(transcript, null, null, controls, null);
        root.setPadding(new Insets(10));
        stage.setTitle("Vermithor");
        stage.setScene(new Scene(root, 650, 450));
        stage.show();
        transcript.appendText("Hello! I'm Vermithor. What can I do for you?\n");
    }

    /** Processes one entered command and displays the chatbot response. */
    private void submit(TextField input) {
        String command = input.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        input.clear();
        transcript.appendText("> " + command + "\n");
        if (command.equalsIgnoreCase("bye")) {
            transcript.appendText("Bye. Hope to see you again soon!\n");
            return;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try {
            System.setOut(new PrintStream(output));
            Vermithor.processCommand(parser, command, tasks);
            storage.save(tasks);
        } catch (VermithorException exception) {
            System.out.println("OOPS!!! " + exception.getMessage());
        } finally {
            System.setOut(original);
        }
        transcript.appendText(output.toString());
    }
}
