import java.util.Scanner;
public class Vermithor {
    public static void main(String[] args) {
        String banner = "__      __\n"
                + "\\ \\    / /__ _ __ _ __ ___ (_) |_| |__   ___  _ __\n"
                + " \\ \\  / / _ \\ '__| '_ ` _ \\| | __| '_ \\ / _ \\| '__|\n"
                + "  \\ \\/ /  __/ |  | | | | | | | |_| | | | (_) | |\n"
                + "   \\__/ \\___|_|  |_| |_| |_|_|\\__|_| |_|\\___/|_|\n";

        System.out.println(banner);
        System.out.println("Hello! I'm Vermithor.\nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("added: " + input);
            }
        }

        scanner.close();
    }
}
