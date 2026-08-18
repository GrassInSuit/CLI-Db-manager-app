// Ergonomics.java
package org.example;

import java.util.Scanner;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Attributes;
import java.io.Reader;

public class Ergonomics {
    public static final String Prefix = Color.CYAN + "[IMS]:" + Color.RESET;
    public static final String Error = Color.RED + "[ERROR]: " + Color.RESET;
    public static final String Arrow = Color.CYAN + " -> " + Color.RESET;
    private static Terminal terminal;
    public static class Color{
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String CYAN = "\u001B[36m";
        public static final String MAGENTA = "\u001B[35m";

    }
    public static void clearLines(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.print("\033[1A"); // move cursor up one line
            System.out.print("\033[2K"); // clear that entire line
        }
        System.out.flush();
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static boolean comfirmAction(Scanner scanner){
        System.out.print(Arrow + Color.YELLOW + "Proceed? [y/N]: " + Color.RESET);
        String input = scanner.nextLine().trim();
        return input.equalsIgnoreCase("Y");
    }
    public static void waitAnyKey() {
        System.out.println(Prefix + "Press any key to proceed...");
        System.out.flush();

        if (terminal != null) {
            Attributes originalAttributes = terminal.enterRawMode();
            try {
                Reader reader = terminal.reader();
                reader.read();
            } catch (Exception e) {
                System.err.println("\nErreur lors de la lecture du terminal : " + e.getMessage());
            } finally {
                terminal.setAttributes(originalAttributes);
            }
        } else {
            try {
                System.in.read();
            } catch (Exception e) {
            }
        }
    }
}