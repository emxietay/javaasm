package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.service.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable {
  @Serial
  private static final long serialVersionUID = 3L;
  private String id;
  private String name;


  public Customer(List<String> strings) {
    this(strings.get(0), strings.get(1));
    CustomerDAO.update(this);
  }

  public Customer(String id, String name) {
    this.id = id;
    this.name = name;
  }

  private static List<Account> getAccountList(String id) {
    return AccountDAO.list().stream().filter(e -> e.getCustomerId().equals(id)).toList();
  }



  @Override
  public String toString() {
    String textBlock = """
            Customer: %s (id: %s):
            accounts: %s
            """;
    return textBlock.formatted(name, id, getAccountList());

  }

  public boolean isCustomerPremium() {
    List<Account> accountList = getAccountList();
    for (var account : accountList) {
      if (Validator.isAccountPremium(account.getAccountBalance())) return true;
    }
    return false;
  }

  public String getId() {
    return id;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(name, customer.name) && Objects.equals(id, customer.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }

  public String getName() {
    return name;
  }

  public void displayInformation() {
    String premium = "Normal";
    int order = 1;
    if (isCustomerPremium()) {
      premium = "Premium";
    }
    System.out.println("+---------+-----------------------------------+--------+");
    System.out.printf("%12s |%10s| %8s | %,18.2fđ%n", getId(), getName().toUpperCase(), premium, getTotalBalanceAccount());
    List<Account> list = getAccountList();
    for (Account account : list) {
      System.out.printf("%d%11s |  %8s|  %,28.2fđ%n", order++, account.getAccountNumber(), account instanceof SavingsAccount ? "SAVINGS" : "LOAN", account.getAccountBalance());
    }
  }

  private List<Account> getAccountList() {
    return getAccountList(id);
  }

  private double getTotalBalanceAccount() {
    double result = 0;
    try {
      result = getAccountList().stream().mapToDouble(Account::getAccountBalance).sum();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

}
