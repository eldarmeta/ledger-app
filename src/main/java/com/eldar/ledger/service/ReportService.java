package com.eldar.ledger.service;

import com.eldar.ledger.repo.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportService {

    public List<Transaction> filterMonthToDate(List<Transaction> all) {
        List<Transaction> result = new ArrayList<>();
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now();

        for (Transaction t : all) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                result.add(t);
            }
        }

        return result;
    }

    public List<Transaction> filterPreviousMonth(List<Transaction> all) {
        List<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(1).withDayOfMonth(1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        for (Transaction t : all) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                result.add(t);
            }
        }

        return result;
    }

    public List<Transaction> filterYearToDate(List<Transaction> all) {
        List<Transaction> result = new ArrayList<>();
        LocalDate start = LocalDate.now().withDayOfYear(1);
        LocalDate end = LocalDate.now();

        for (Transaction t : all) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                result.add(t);
            }
        }

        return result;
    }

    public List<Transaction> filterPreviousYear(List<Transaction> all) {
        List<Transaction> result = new ArrayList<>();
        LocalDate start = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate end = start.withDayOfYear(start.lengthOfYear());

        for (Transaction t : all) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                result.add(t);
            }
        }

        return result;
    }

    public List<Transaction> filterByVendor(List<Transaction> all, String vendorName) {
        List<Transaction> result = new ArrayList<>();

        for (Transaction t : all) {
            if (t.getVendor().equalsIgnoreCase(vendorName)) {
                result.add(t);
            }
        }

        return result;
    }

    public void showVendorSummary(List<Transaction> transactions) {
        HashMap<String, Double> vendorTotals = new HashMap<>();

        for (Transaction t : transactions) {
            String vendor = t.getVendor();
            double amount = t.getAmount();

            if (vendorTotals.containsKey(vendor)) {
                vendorTotals.compute(vendor, (k, currentTotal) -> currentTotal + amount);
            } else {
                vendorTotals.put(vendor, amount);
            }
        }

        System.out.println("\n***  Vendor Summary Report  ***");
        for (String vendor : vendorTotals.keySet()) {
            System.out.printf("%s: %.2f\n", vendor, vendorTotals.get(vendor));
        }
    }
}
