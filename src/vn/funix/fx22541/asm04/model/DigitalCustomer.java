package vn.funix.fx22541.asm04.model;


import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.service.Validator;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DigitalCustomer extends Customer {

    @Serial
    private static final long serialVersionUID = 3L;

    public DigitalCustomer(String customerId, String name) {
        super(customerId, name);

    }

    public DigitalCustomer(List<String> strings) {
        super(strings);
    }

    public void withdraw(Scanner scanner) {
        List<Account> accounts = getAccounts();
        if (!accounts.isEmpty()) {
            Account account;
            double amount;
            do {
                System.out.print("Enter account number: ");
                account = getAccountByAccountNumber(accounts, scanner.nextLine());
            } while (account == null);

            do {
                System.out.print("Enter withdraw amount: ");
                amount = scanner.nextDouble();
            } while (amount < Validator.SAVINGS_ACCOUNT_MIN_WITHDRAW);

            if (account instanceof SavingsAccount e) {
                e.withdraw(amount);
            }
        } else {
            System.out.println("Can not find account, action not performed successfully.");
        }
    }

    @Override
    public String toString() {
        displayInformation();
        return "";
    }

    public void displayInformation() {
        List<Account> accounts = getAccounts();
        String isPremium = "Normal";
        int order = 1;
        if (isCustomerPremium()) {
            isPremium = "Premium";
        }
        System.out.println("+------------+--------------------------------------------------+------------+");
        System.out.printf("%12s |%22s| %8s | %,26.2fVND%n", getId(), getName().toUpperCase(), isPremium, getTotalBalance());
        for (Account account : accounts) {
            System.out.printf("%d%11s |  %20s|  %,36.2fVND%n", order++, account.getAccountNumber(), account instanceof LoanAccount ? "LOAN" : "SAVINGS", account.getAccountBalance());
        }
        System.out.println("+------------+--------------------------------------------------+------------+");
    }

    private double getTotalBalance() {
        List<Account> accounts = getAccounts();
        return accounts.stream().mapToDouble(e -> e.getAccountBalance()).sum();
    }


    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }


    public void printTransactions(List<Transaction> transactions) {
    }

    public void printTransactions() {
        List<Account> accounts = getAccounts();
        accounts.forEach(e -> e.showtransactions());
    }


    public List<Account> getAccounts() {
        List<Account> list = new ArrayList<>();
        try {
            list = AccountDAO.list()
                    .stream()
                    .filter(e -> getId().equals(e.getCustomer()
                            .getId()))
                    .toList();
        } catch (NullPointerException e) {
            System.out.println("No customer found!");
        }
        return list;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}