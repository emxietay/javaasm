package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.service.*;

import java.io.Serial;
import java.io.Serializable;

public class SavingsAccount extends Account implements Withdraw, Report, Serializable, ITransfer {
  @Serial
  private static final long serialVersionUID = 6L;
  private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
  private static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50000;

  public SavingsAccount(String customerId, String accountNumber, double balance) {
    super(customerId, accountNumber, balance);
  }


  @Override
  public boolean withdraw(double amount) {
    if (isAccepted(amount)) {
      balance -= amount;
      Transaction transaction = new Transaction(getAccountNumber(), -amount, isAccepted(amount), Transaction.TransactionType.WITHDRAW);
      TransactionDAO.update(transaction);
      log(amount);
    }
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

  public void transfer(String receivingAccountNumber, double amount) {
    AccountDAO.getAccountById(receivingAccountNumber);
    if (balance - amount >= 50_000 && amount > 10_000) {
      this.balance -= amount;
      Transaction transaction = new Transaction(accountNumber, -amount, true, Transaction.TransactionType.TRANSFER);
      TransactionDAO.update(transaction);
      logTransfer(amount, receivingAccountNumber);
      Account receivingAcc = AccountDAO.getAccountById(receivingAccountNumber);
      receivingAcc.addToBalance(amount);
      Transaction receivingAccountTransaction = new Transaction(receivingAccountNumber, amount, true, Transaction.TransactionType.TRANSFER);
      TransactionDAO.update(receivingAccountTransaction);
    }
  }

  public void logTransfer(double amount, String receivingAccountNumber) {
    System.out.println(Utils.getDivider());
    System.out.printf("%30s%n", getTitle());
    System.out.printf("NGAY G/D: %28s%n", Utils.getDateTime());
    System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
    System.out.printf("SO TK: %31s%n", getAccountNumber());
    System.out.printf("SO TK: %31s%n", receivingAccountNumber);
    System.out.printf("SO TIEN CHUYEN: %22s%n", Utils.formatBalance(amount));
    System.out.printf("SO DU TAI KHOAN: %21s%n", Utils.formatBalance(getAccountBalance()));
    System.out.printf("PHI + VAT: %27s%n", 0);
    System.out.println(Utils.getDivider());
  }
}
