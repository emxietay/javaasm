package vn.funix.fx22541.asm03.models;

import vn.funix.fx22541.asm02.models.Account;
import vn.funix.fx22541.asm02.models.Customer;

import java.util.List;

public class DigitalCustomer extends Customer {

    public DigitalCustomer(String name, String customerId) {
        super(name, customerId);
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = getAccountByAccountNumber(accountNumber);
        if (getAccountByAccountNumber(accountNumber) == null) {
            System.out.println("Không tìm thấy tài khoản.");
            return;
        }
        if (account instanceof Withdraw acc) {
            acc.withdraw(amount);
        }

    }

    @Override
    public void displayInformation() {
        String premium = "Normal";
        int order = 1;
        if (isCustomerPremium()) {
            premium = "Premium";
        }
        System.out.println("+---------+-----------------------------------+--------+");
        System.out.printf("%12s |%10s| %8s | %,18.2fđ%n", getCustomerId(), getName().toUpperCase(), premium, getTotalBalanceAccount());
        for (Account account : accounts) {
            System.out.printf("%d%11s |  %8s|  %,28.2fđ%n", order++, account.getAccountNumber(), account instanceof LoanAccount ? "LOAN" : "SAVINGS", account.getAccountBalance());
        }
    }

    public void addLoanAccount(String accountNumber, double amount) {

        if (getAccountByAccountNumber(accountNumber) == null) {
            accounts.add(new LoanAccount(accountNumber, amount));
        } else {
            System.out.println("Tài khoản đã tồn tại trên hệ thống.");
        }
    }

    public void addSavingsAccount(String accountNumber, double amount) {
        if (getAccountByAccountNumber(accountNumber) == null) {
            accounts.add(new SavingsAccount(accountNumber, amount));
        } else {
            System.out.println("Tài khoản đã tồn tại trên hệ thống. ");

        }
    }

    public void printTransactions(List<Transaction> transactions) {
        for (Transaction tr :
                transactions) {
            if (tr.getStatus()) {
                System.out.println(tr);
            }
        }
    }

    public void printTransactions() {
        for (Account acc : accounts) {
            if (acc instanceof LoanAccount e) {
                printTransactions(e.getTransactions());
            }
            //      if (acc instanceof  LoanAccount a) {
            if (acc instanceof SavingsAccount e) {
                printTransactions(e.getTransactions());
            }
        }
    }
}
