package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm03.models.ITransaction;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.service.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaction implements ITransaction, Serializable {

  private final String id;

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
    id = String.valueOf(UUID.randomUUID());
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.time = Utils.getDateTime();
    this.status = status;
    this.transactionType = transactionType;
  }

  public boolean getStatus() {
    return status;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  @Override
  public String toString() {
    return String.format(
            "[GD-%s] %7s | %8s | %,17.2f VND | %15s",id.substring(0, 8), accountNumber, transactionType, amount, time);
  }

  public static void main(String[] args) {
    Transaction transaction = new Transaction("123", 123, true, TransactionType.TRANSFER);
    Transaction transaction2 = new Transaction("1234", 1234, true, TransactionType.DEPOSIT);
    System.out.println(transaction);
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    transactions.addAll(List.of(transaction, transaction2));
    TransactionDAO.save(transactions);
TransactionDAO.list().forEach(System.out::println);
  }

}

