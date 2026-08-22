import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Saves Vermithor tasks to, and loads them from, a local text file. */
public class Storage {
    private final Path filePath;

    /** Creates storage at the given relative or absolute path. */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /** Loads saved tasks, returning an empty list when the file does not exist yet. */
    public List<Task> load() throws VermithorException {
        List<Task> tasks = new ArrayList<>();
        if (Files.notExists(filePath)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(filePath)) {
                tasks.add(deserialize(line));
            }
            return tasks;
        } catch (IOException | IllegalArgumentException e) {
            throw new VermithorException("I could not load your saved tasks.");
        }
    }

    /** Saves all tasks, creating the parent directory when it does not exist. */
    public void save(List<Task> tasks) throws VermithorException {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(serialize(task));
        }
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new VermithorException("I could not save your tasks.");
        }
    }

    /** Converts one task into its saved text representation. */
    private String serialize(Task task) {
        String done = task.isDone() ? "1" : "0";
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D|" + done + "|" + task.getDescription() + "|" + deadline.getBy();
        }
        if (task instanceof Event) {
            Event event = (Event) task;
            return "E|" + done + "|" + task.getDescription() + "|" + event.getFrom() + "|" + event.getTo();
        }
        return "T|" + done + "|" + task.getDescription();
    }

    /** Recreates one task from its saved text representation. */
    private Task deserialize(String line) {
        String[] fields = line.split("\\|", -1);
        Task task;
        switch (fields[0]) {
        case "T":
            task = new ToDo(fields[2]);
            break;
        case "D":
            task = new Deadline(fields[2], LocalDate.parse(fields[3]));
            break;
        case "E":
            task = new Event(fields[2], fields[3], fields[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type");
        }
        task.setDone("1".equals(fields[1]));
        return task;
    }
}
