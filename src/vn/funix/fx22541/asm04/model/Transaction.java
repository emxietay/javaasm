package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm03.models.ITransaction;
import vn.funix.fx22541.asm04.service.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Transaction implements ITransaction, Serializable {
  public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER;
  }

  @Serial
  private static final long serialVersionUID = 4L;

  private final TransactionType transactionType;
  private final String accountNumber;
  private final double amount;
  private final String time;
  private final boolean status;

  public Transaction(String accountNumber, double amount, boolean status, TransactionType transactionType) {
    String id = String.valueOf(UUID.randomUUID());
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.time = Utils.getDateTime();
    this.status = status;
    this.transactionType = transactionType;
  }

  public boolean getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format(
            "[GD] %7s | %,17.2f đ | %15s", accountNumber, amount, time);
  }

}

