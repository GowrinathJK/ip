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
                + "  \\ \\/ /  __/ |  | | | | | | | |_| | | | (_) | |\n"
                + "   \\__/ \\___|_|  |_| |_| |_|_|\\__|_| |_|\\___/|_|\n";

        System.out.println(banner);
        System.out.println("Hello! I'm Vermithor.\nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                Task task = tasks[taskNumber - 1];
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                Task task = tasks[taskNumber - 1];
                task.markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
            } else if (input.startsWith("todo ")) {
                Task task = new ToDo(input.substring(5));
                tasks[taskCount] = task;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String[] deadlineParts = input.substring(9).split(" /by ", 2);
                Task task = new Deadline(deadlineParts[0], deadlineParts[1]);
                tasks[taskCount] = task;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String[] eventParts = input.substring(6).split(" /from | /to ", 3);
                Task task = new Event(eventParts[0], eventParts[1], eventParts[2]);
                tasks[taskCount] = task;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
        }

        scanner.close();
    }
}
