package vermithor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Verifies that tasks survive a save and load cycle. */
class StorageTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void saveThenLoadRestoresTasks() throws VermithorException {
        Storage storage = new Storage(temporaryDirectory.resolve("data/tasks.txt"));
        Task todo = new ToDo("read book");
        todo.markAsDone();
        List<Task> original = List.of(todo, new Deadline("submit report", LocalDate.of(2026, 9, 18)));

        storage.save(original);

        List<Task> restored = storage.load();
        assertEquals(original.toString(), restored.toString());
    }
}
