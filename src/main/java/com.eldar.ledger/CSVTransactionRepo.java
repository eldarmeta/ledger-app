package com.eldar.ledger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CSVTransactionRepo implements TransactionRepository {
    private String filePath;

    public CSVTransactionRepo(String filePath) {
        this.filePath = filePath;

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("*ERROR* Could not create file: " + e.getMessage());
            }
        }
    }

    @Override
    public void add(Transaction transaction) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(transaction.toCSV());
            bw.newLine();

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("*ERROR* Could not write transaction: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    transactions.add(t);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("*ERROR* Could not read transactions: " + e.getMessage());
        }

        return transactions;
    }
}