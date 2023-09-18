package vn.funix.fx22541.asm02.models;

import java.util.ArrayList;
import java.util.List;

public class  Customer extends User {

    protected  final  List<Account> accounts;

    public Customer(String name, String customerId) {
        super(name, customerId);
        this.accounts = new ArrayList<>();
    }

    public boolean isCustomerPremium() {
        for (Account account : accounts
        ) {
            if (account.isAccountPremium()) {
                return true;
            }
        }
        return false;
    }

    public boolean isAccountExisted(Account newAccount) {
        return accounts.contains(newAccount);
    }

    public void addAccount(String accountNumber, double initialAmount) {
        if (!isAccountExisted(getAccountByAccountNumber(accountNumber))) {
        accounts.add(new Account(accountNumber, initialAmount));
        }
    }

    public double getTotalBalanceAccount() {
        double totalBalance = 0;
        for (Account acc :
                accounts) {
            totalBalance += acc.getAccountBalance();
        }
        return totalBalance;
    }

    public Account getAccountByAccountNumber(String accNo) {
        for (Account account :
                accounts) {
            if (account.getAccountNumber().equals(accNo)) {
                return account;
            }
        }
        return null;
    }

    public void displayInformation() {
        String premium = "Normal";
        int order = 1;

        if (isCustomerPremium()) {
            premium = "Premium";
        }
        System.out.println("+---------+-----------------------------------+--------+");
        System.out.printf("%12s |%10s| %8s | %,18.2fđ%n", getCustomerId(), getName().toUpperCase(), premium, getTotalBalanceAccount());
        for (Account account : accounts) {
            System.out.printf("%d%11s |  %8s   %,28.2fđ%n", order++, account.getAccountNumber(),"", account.getAccountBalance());
        }
    }

}
