package com.eldar.ledger;

import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) {
        LedgerApp app = new LedgerApp();
        app.run();
    }

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            printHomeScreen();
            String input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "D":
                    System.out.println("[ADD DEPOSIT] - Navigating to deposit screen...");
                    break;
                case "P":
                    System.out.println("[MAKE PAYMENT] - Navigating to payment screen...");
                    break;
                case "L":
                    System.out.println("[LEDGER] - Displaying transactions...");
                    break;
                case "X":
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printHomeScreen() {
        System.out.println("\n****** Ledger Home ******");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter your choice: ");
    }
}
