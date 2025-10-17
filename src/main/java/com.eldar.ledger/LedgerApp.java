package com.eldar.ledger;

import repo.CSVTransactionRepo;
import repo.Transaction;
import repo.TransactionRepository;
import service.ReportService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) {
        LedgerApp app = new LedgerApp();
        app.run();
    }

    private final ReportService reportService = new ReportService();
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
                    showLedgerMenu();
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

    private void showLedgerMenu() {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n***** Ledger Menu *****");
            System.out.println("A) All Transactions");
            System.out.println("D) Only Deposits");
            System.out.println("P) Only Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose option: ");
            String input = scanner.nextLine();

            List<Transaction> all = repo.getAll();

            switch (input.toUpperCase()) {
                case "A":
                    System.out.println("\n*ALL TRANSACTIONS*");
                    for (Transaction t : all) System.out.println(t);
                    break;
                case "D":
                    System.out.println("\n*DEPOSITS ONLY*");
                    for (Transaction t : all) if (t.getAmount() > 0) System.out.println(t);
                    break;
                case "P":
                    System.out.println("\n*PAYMENTS ONLY*");
                    for (Transaction t : all) if (t.getAmount() < 0) System.out.println(t);
                    break;
                case "R":
                    showReportsMenu();
                    break;
                case "H":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void showReportsMenu() {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n***** Reports Menu *****");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Vendor Summary (Total by Vendor)");
            System.out.println("0) Back to Ledger");
            System.out.print("Choose option: ");

            String input = scanner.nextLine();
            List<Transaction> all = repo.getAll();
            List<Transaction> result = new ArrayList<>();

            switch (input) {
                case "1":
                    result = reportService.filterMonthToDate(all);
                    break;
                case "2":
                    result = reportService.filterPreviousMonth(all);
                    break;
                case "3":
                    result = reportService.filterYearToDate(all);
                    break;
                case "4":
                    result = reportService.filterPreviousYear(all);
                    break;
                case "5":
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine();
                    result = reportService.filterByVendor(all, vendor);
                    break;
                case "6":
                    reportService.showVendorSummary(all);
                    break;
                case "0":
                    viewing = false;
                    continue;
                default:
                    System.out.println("Invalid option.");
                    continue;
            }

            if (!result.isEmpty()) {
                System.out.println("\n=== Report Results ===");
                for (Transaction t : result) {
                    System.out.println(t);
                }
            }
        }
    }
}
