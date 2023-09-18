package vn.funix.fx22541.asm04.model;


import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.service.Validator;

import java.io.Serial;
import java.util.List;
import java.util.Scanner;

public class DigitalCustomer extends Customer {

    @Serial
    private static final long serialVersionUID = 3L;

    public DigitalCustomer(String name, String customerId) {
        super(name, customerId);

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
        String isPremium = "Normal";
        int order = 1;
        if (isCustomerPremium()) {
            isPremium = "Premium";
        }
        System.out.println("+---------+-----------------------------------+---------------------+");
        System.out.printf("%12s |%22s| %8s | %,18.2fđ%n", getId(), getName().toUpperCase(), isPremium, getTotalBalanceAccount());
        for (Account account : accounts) {
            System.out.printf("%d%11s |  %20s|  %,28.2fđ%n", order++, account.getAccountNumber(), account instanceof LoanAccount ? "LOAN" : "SAVINGS", account.getAccountBalance());
        }
        System.out.println("+---------+-----------------------------------+---------------------+");
    }


    private double getTotalBalanceAccount() {
        double sum = 0;
        for (Account account : accounts) {
            sum += account.getAccountBalance();
        }
        return sum;
    }

    public boolean addLoanAccount(String accountNumber, double amount) {
        if (!isAccountExisted(accountNumber)) {
            accounts.add(LoanAccount.createAccount(accountNumber, amount, this));
            return true;
        } else {
            System.out.println("LoanAccount existed: " + accountNumber);
            return false;
        }
    }

    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean addSavingsAccount(String accountNumber, double amount) {
        boolean isAccountExisted = accounts.stream().anyMatch(e -> e.getAccountNumber().equals(accountNumber));
        if (!isAccountExisted) {
            accounts.add(SavingsAccount.createAccount(accountNumber, amount, this));
            return true;
        } else {
            System.out.println("Savings Account existed: " + accountNumber);
            return false;
        }

    }

    public void printTransactions(List<Transaction> transactions) {
    }

    public void printTransactions() {
    }


    public List<Account> getAccounts() {
        return AccountDAO.list()
                        .stream()
                        .filter(e -> getId().equals(e.getCustomer()
                        .getId()))
                        .toList();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    //    @Override
    public void transfer(Account account, double amount) {

    }
}