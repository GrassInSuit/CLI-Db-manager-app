package org.example;

import org.example.ContactSystem;
import org.example.Ergonomics;
import org.example.interFunctions;

import javax.xml.crypto.Data;
import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String Prefix = Ergonomics.Color.Prefix;
        final String Arrow = Ergonomics.Color.Arrow;
        final Console console = System.console();
        //This boolean checkes if you are already loged in or not
        ContactSystem connectionManager = new ContactSystem();
        boolean isConnected = false;

        //Simple login system for the postgres database (URL, Database's name , user , password)
        while(!isConnected){
            System.out.println(Ergonomics.Color.YELLOW + "---Welcome to IMS---" + Ergonomics.Color.RESET);
            System.out.println(Prefix + "Connecting to your Database:");
                System.out.print(Arrow + "Enter Database URL " + Ergonomics.Color.YELLOW +"(default: localhost:5432):" + Ergonomics.Color.RESET);
                String URL = scanner.nextLine();
            if (URL.isBlank()) {
                URL = "localhost:5432";
            }
            System.out.print("\u001B[1A\u001B[2K");
                System.out.print(Arrow + "Enter Database's name:" + Ergonomics.Color.GREEN);
                String name = scanner.nextLine();
            System.out.print("\u001B[1A\u001B[2K");
                System.out.print(Arrow + "Enter Username:" + Ergonomics.Color.GREEN);
                String user = scanner.nextLine();
            System.out.print("\u001B[1A\u001B[2K");
            String password;
                if (console != null) {
                    char[] passwordChars = console.readPassword(Arrow + "Enter password: ");
                    password = new String(passwordChars);
                } else {
                    System.out.print(Arrow + "Enter password:" + Ergonomics.Color.GREEN);
                    password = scanner.nextLine();
                }
            
                connectionManager.setConnection(URL,name,user,password);
                isConnected = connectionManager.isConnected;

        }
        interFunctions.mainScreenMenu();

        scanner.close();
    }
}
