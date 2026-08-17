package org.example;

import java.util.Scanner;

public class Ergonomics {

    public static class Color{
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String CYAN = "\u001B[36m";
        public static final String MAGENTA = "\u001B[35m";
        public static final String Prefix = BLUE + "[IMS]: " + RESET;
        public static final String Arrow = BLUE + " -> " + RESET;
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
    public static void comfirmAction(Scanner scanner){
        System.out.println(Color.Arrow + Color.YELLOW + "Proceed? [y/N]: " + Color.RESET);
        String input = scanner.nextLine().toUpperCase();
        if (String.valueOf(input) != "Y"){
            return;
        }
    }
}
