package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.exception.AccountNumberNotValidException;
import vn.funix.fx22541.asm04.exception.InitialAccountBalanceNotValidException;
import vn.funix.fx22541.asm04.service.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Account implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  protected String accountNumber;
  protected double balance;
  private String customerId;


  public Account(String customerId, String accountNumber, double balance) {
    this.customerId = customerId;
    this.accountNumber = accountNumber;
    this.balance = balance;
    Transaction transaction = new Transaction(accountNumber, balance, true, Transaction.TransactionType.DEPOSIT);
    TransactionDAO.update(transaction);
  }


  public Transaction createTransaction(double amount, boolean status, Transaction.TransactionType type) {
    return new Transaction(accountNumber, amount, true, type);
  }

  public <T extends Account> void input(Scanner scanner) {
    while (true) {
      try {
        System.out.print("Please enter Customer ID: ");
        String customerId = scanner.nextLine();
        if (CustomerDAO.isCustomerExisted(customerId)) {
          System.out.print("Please enter account number: ");
          String accountNumber = scanner.nextLine();
          if (Validator.validateAccountNumber(accountNumber)) {
            System.out.print("Please enter amount: ");
            double amount = Double.parseDouble(scanner.next());
            if (Validator.validateSavingAmount(amount)) {
              Account account = new Account(customerId, accountNumber, amount);
              Transaction transaction = createTransaction(amount, true, Transaction.TransactionType.DEPOSIT);
              AccountDAO.update(account);
              TransactionDAO.update(transaction);
              break;
            }
          }
        }
      } catch (AccountNumberNotValidException e) {
        System.out.println(e.getMessage());
      } catch (InitialAccountBalanceNotValidException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
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

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getCustomerId() {
    return customerId;
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

  public <T extends Customer> T getCustomer() {
    return (T) CustomerDAO.list().stream().filter(e -> e.getId().equals(customerId)).findFirst().orElse(null);
  }

  @Override
  public String toString() {
    return "Account{" + "customerId='" + customerId + '\'' + ", accountNumber='" + accountNumber + '\'' + ", balance=" + balance + '}';
  }

  protected void addToBalance(double amount) {
    if (amount > 0) {
      balance += amount;
    }
  }

  public void showtransactions() {
//    System.out.println("+------------+--------------------------------------------------+------------+");
    TransactionDAO.list().stream().filter(e -> e.getAccountNumber().equals(accountNumber)).forEach(System.out::println);
  }
}
