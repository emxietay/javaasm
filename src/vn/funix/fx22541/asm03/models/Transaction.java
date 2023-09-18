package vn.funix.fx22541.asm03.models;

import java.util.UUID;

public class Transaction implements ITransaction {
  private final String accountNumber;
  private final double amount;
  private final String time;
  private final boolean status;

  public Transaction(String accountNumber, double amount, boolean status) {
    String id = String.valueOf(UUID.randomUUID());
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.time = Utils.getDateTime();
    this.status = status;
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

