import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A command-line chatbot that records tasks and lets users update their status.
 */
public class Vermithor {
    /**
     * Starts Vermithor and processes commands until the user says goodbye.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = "__      __\n"
                + "\\ \\    / /__ _ __ _ __ ___ (_) |_| |__   ___  _ __\n"
                + " \\ \\  / / _ \\ '__| '_ ` _ \\| | __| '_ \\ / _ \\| '__|\n"
                + "  \\ \\/ /  __/ |  | | | | | | | |_| | | | | (_) | |\n"
                + "   \\__/ \\___|_|  |_| |_| |_|_|\\__|_| |_|\\___/|_|\n";
        System.out.println(banner);
        System.out.println("Hello! I'm Vermithor.\nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }
                processCommand(input, tasks);
            } catch (VermithorException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Processes one command.
     *
     * @param input the user's command
     * @param tasks the task storage list
     * @throws VermithorException if the command is invalid
     */
    private static void processCommand(String input, List<Task> tasks) throws VermithorException {
        String[] commandParts = input.split(" ", 2);
        String command = commandParts[0].toLowerCase();
        String details = commandParts.length == 2 ? commandParts[1].trim() : "";

        switch (command) {
        case "list":
            if (!details.isEmpty()) {
                throw new VermithorException("The list command does not take extra words.");
            }
            printTaskList(tasks);
            return;
        case "mark":
            updateTaskStatus(details, tasks, true);
            return;
        case "unmark":
            updateTaskStatus(details, tasks, false);
            return;
        case "todo":
            addTask(new ToDo(requireDescription(details, "todo")), tasks);
            return;
        case "deadline":
            addDeadline(details, tasks);
            return;
        case "event":
            addEvent(details, tasks);
            return;
        case "delete":
            deleteTask(details, tasks);
            return;
        default:
            throw new VermithorException("I don't know that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
    }

    /** Prints all stored tasks with their one-based list numbers. */
    private static void printTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /** Adds a task and prints confirmation. */
    private static void addTask(Task task, List<Task> tasks) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /** Adds a deadline command after validating its description and deadline. */
    private static void addDeadline(String details, List<Task> tasks) throws VermithorException {
        int byIndex = details.indexOf(" /by ");
        if (byIndex <= 0 || byIndex + 5 >= details.length()) {
            throw new VermithorException("Use deadline DESCRIPTION /by DATE.");
        }
        addTask(new Deadline(details.substring(0, byIndex), details.substring(byIndex + 5)), tasks);
    }

    /** Adds an event command after validating its description and time range. */
    private static void addEvent(String details, List<Task> tasks) throws VermithorException {
        int fromIndex = details.indexOf(" /from ");
        int toIndex = details.indexOf(" /to ");
        if (fromIndex <= 0 || toIndex <= fromIndex + 7 || toIndex + 5 >= details.length()) {
            throw new VermithorException("Use event DESCRIPTION /from START /to END.");
        }
        String description = details.substring(0, fromIndex);
        String from = details.substring(fromIndex + 7, toIndex);
        String to = details.substring(toIndex + 5);
        addTask(new Event(description, from, to), tasks);
    }

    /** Marks or unmarks a task after validating its one-based list number. */
    private static void updateTaskStatus(String details, List<Task> tasks, boolean isDone)
            throws VermithorException {
        int taskNumber = getTaskNumber(details, tasks.size());
        Task task = tasks.get(taskNumber - 1);
        if (isDone) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
    }

    /** Removes a task after validating its one-based list number. */
    private static void deleteTask(String details, List<Task> tasks) throws VermithorException {
        int taskNumber = getTaskNumber(details, tasks.size());
        Task removedTask = tasks.remove(taskNumber - 1);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    /** Validates and converts a user-entered one-based task number. */
    private static int getTaskNumber(String details, int taskCount) throws VermithorException {
        try {
            int taskNumber = Integer.parseInt(details);
            if (taskNumber < 1 || taskNumber > taskCount) {
                throw new VermithorException("Choose a task number from 1 to " + taskCount + ".");
            }
            return taskNumber;
        } catch (NumberFormatException e) {
            throw new VermithorException("Please provide a valid task number.");
        }
    }

    /** Returns a non-empty task description for commands that require one. */
    private static String requireDescription(String details, String command) throws VermithorException {
        if (details.isEmpty()) {
            throw new VermithorException("The description of a " + command + " cannot be empty.");
        }
        return details;
    }
}
