package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.exception.InitialAccountBalanceNotValidException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static vn.funix.fx22541.asm04.service.Validator.validateAccountNumber;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String customerId;

    protected String accountNumber;
    double balance;

    protected List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber) {
        if (!validateAccountNumber(accountNumber)) {
            throw new InitialAccountBalanceNotValidException();
        }
        new Account(accountNumber, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(customerId, account.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    public Account(String accountNumber, double initialAmount) {
        if (initialAmount < 50000) {
            throw new InitialAccountBalanceNotValidException();
        }
        this.transactions = new ArrayList<>();
        this.accountNumber = accountNumber;
        setBalance(initialAmount);
    }

    protected Account() {

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isAccountPremium() {
        return balance >= 10000000;
    }

    public Customer getCustomer() {
        Optional<Customer> foundCustomer = CustomerDAO.readFile().stream().filter(e -> e.getId().equals(customerId)).findFirst();
        return foundCustomer.orElse(null);
    }

    @Override
    public String toString() {
        return "Account{" +
                "customerId='" + customerId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }

}
