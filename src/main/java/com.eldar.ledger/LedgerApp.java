package com.eldar.ledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) {
        LedgerApp app = new LedgerApp();
        app.run();
    }

    private final Scanner scanner = new Scanner(System.in);
    private final TransactionRepository repo = new CSVTransactionRepo("transactions.csv");

    public void run() {
        boolean running = true;

        while (running) {
            printHomeScreen();
            String input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "D":
                    System.out.println("*ADD DEPOSIT*");
                    Transaction deposit = createTransaction(true);
                    repo.add(deposit);
                    System.out.println("Deposit added.");
                    break;
                case "P":
                    System.out.println("*MAKE PAYMENT*");
                    Transaction payment = createTransaction(false);
                    repo.add(payment);
                    System.out.println("Payment recorded.");
                    break;
                case "L":
                    System.out.println("*LEDGER* Showing all transactions:");
                    List<Transaction> all = repo.getAll();
                    for (Transaction tx : all) {
                        System.out.println(tx);
                    }
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

    private Transaction createTransaction(boolean isDeposit) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (!isDeposit) {
            amount *= -1;
        }

        return new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
    }
}