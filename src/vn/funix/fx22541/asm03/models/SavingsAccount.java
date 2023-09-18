package vn.funix.fx22541.asm03.models;

import vn.funix.fx22541.asm02.models.Account;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account implements Withdraw, ReportService, ITransaction {
  private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
  private static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50000;
  private final List<Transaction> transactions;

  public SavingsAccount(String accountNumber, double initialAmount) {
    if (initialAmount < 50000) {
      throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
    }
    if (!validateAccount(accountNumber)) {
      throw new IllegalArgumentException("Vui lòng nhập tài khoản gồm 6 kí tự số.");
    }
    setAccountNumber(accountNumber);
    setBalance(initialAmount);
    System.out.printf("Tài khoản %s được tạo thành công.%n", accountNumber);
    transactions = new ArrayList<>();
    getTransactions().add(new Transaction(accountNumber, initialAmount, true));
  }

  @Override
  public void setBalance(double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
    } else {
      super.setBalance(balance);
    }
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  @Override
  public boolean withdraw(double amount) {
    getTransactions().add(new Transaction(getAccountNumber(), -amount, isAccepted(amount)));
    if (isAccepted(amount)) {
      log(amount);
      setBalance(getAccountBalance() - amount);
      return true;
    }
    System.out.println("Số tiền không hợp lệ. ");
    return false;
  }

  @Override
  public boolean isAccepted(double amount) {
    if (isAccountPremium()) {
      return !(amount < SAVINGS_ACCOUNT_MIN_WITHDRAW) && (amount % 10000 == 0) && !((getAccountBalance() - amount) < 50000);
    } else {
      return !(amount < SAVINGS_ACCOUNT_MIN_WITHDRAW) && (amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW) & (amount % 10000 == 0) && !((getAccountBalance() - amount) < 50000);
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
    System.out.printf("SO DU: %31s%n", Utils.formatBalance(getAccountBalance() - amount));
    System.out.printf("PHI + VAT: %27s%n", 0);
    System.out.println(Utils.getDivider());
  }
}
