package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.service.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class SavingsAccount extends Account implements Withdraw, Report, Serializable, ITransfer<SavingsAccount> {
  @Serial
  private static final long serialVersionUID = 6L;
  private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
  private static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50000;
  private final DigitalCustomer customer;

  SavingsAccount(String accountNumber, double initialAmount, DigitalCustomer customer) {
    this.accountNumber = accountNumber;
    this.balance = initialAmount;
    this.customer = customer;
    System.out.printf("Account: %s has been created successfully.%n", accountNumber);
    Transaction transaction = new Transaction(accountNumber, initialAmount, true, Transaction.TransactionType.DEPOSIT);
    log(initialAmount);
    transactions.add(transaction);
    TransactionDAO.save(transactions);
  }

  public static SavingsAccount createAccount(String accountNumber, double initialAmount, DigitalCustomer customer) {
    if (Validator.validateSavingsAccount(accountNumber, initialAmount)) {
      return new SavingsAccount(accountNumber, initialAmount, customer);
    }
    return null;
  }


  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void printTransactions() {
    transactions.forEach(System.out::println);
  }

  @Override
  public boolean withdraw(double amount) {
    Transaction transaction = new Transaction(getAccountNumber(), -amount, isAccepted(amount), Transaction.TransactionType.WITHDRAW);
    if (isAccepted(amount)) {
      balance = getAccountBalance() - amount;
      log(amount);
      transactions.add(transaction);
      TransactionDAO.save(transactions);
      return true;
    }

    System.out.println("Số tiền không hợp lệ. ");
    return false;
  }

  @Override
  public boolean isAccepted(double amount) {
    if (Validator.isAccountPremium(balance)) {
      return !(amount < SAVINGS_ACCOUNT_MIN_WITHDRAW)
              && (amount % 10000 == 0)
              && !((getAccountBalance() - amount) < 50000);
    } else {
      return !(amount < SAVINGS_ACCOUNT_MIN_WITHDRAW)
              && (amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW)
              && (amount % 10000 == 0)
              && !((getAccountBalance() - amount) < 50000);
    }
  }

  private String getTitle() {
    return "BIEN LAI GIAO DICH SAVINGS";
  }

  @Override
  public void log(double amount) {
    System.out.println(Utils.getDivider());
    System.out.printf("%30s%n", getTitle());
    System.out.printf("NGAY G/D: %28s%n", Utils.getDateTime());
    System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
    System.out.printf("SO TK: %31s%n", getAccountNumber());
    System.out.printf("SO TIEN: %29s%n", Utils.formatBalance(amount));
    System.out.printf("SO DU: %31s%n", Utils.formatBalance(getAccountBalance()));
    System.out.printf("PHI + VAT: %27s%n", 0);
    System.out.println(Utils.getDivider());
  }

  @Override
  public DigitalCustomer getCustomer() {
    return customer;
  }

  @Override
  public void transfer(SavingsAccount receivingAccount, double amount) {
    if (balance - amount >= 50_000 && amount > 10_000) {
      this.balance -= amount;
      transactions.add(new Transaction(accountNumber, -amount, true, Transaction.TransactionType.TRANSFER));
      TransactionDAO.save(transactions);
      log(amount);
      receivingAccount.balance += amount;
//      receivingAccount.receive(amount);
      receivingAccount.addTransaction(new Transaction(receivingAccount.getAccountNumber(), amount, true, Transaction.TransactionType.TRANSFER));
    }
  }



  private void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }


}
