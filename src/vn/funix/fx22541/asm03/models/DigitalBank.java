package vn.funix.fx22541.asm03.models;

import vn.funix.fx22541.asm02.models.Account;
import vn.funix.fx22541.asm02.models.Bank;
import vn.funix.fx22541.asm02.models.Customer;

public class DigitalBank extends Bank {

  public DigitalBank() {
    super();
  }

  @Override
  public void addCustomer(String customerId, String name) {
    if (getCustomerById(customerId) == null) {
      customers.add(new DigitalCustomer(name, customerId));
    }
  }

  @Override
  public Customer getCustomerById(String customerId) {
    for (Customer customer : this.customers) {
      if (customer.getCustomerId().equals(customerId)) {
        return customer;
      }
    }
    return null;
  }

  public void withdraw(DigitalCustomer customer, String accountNumber, double amount) {
    if (customers.contains(customer)) {
      customer.withdraw(accountNumber, amount);
    }
  }

  public void withdraw(String customerId, String accountNumber, double amount) {
    Customer foundCustomer = getCustomerById(customerId);
    if (foundCustomer instanceof Withdraw e) {
      e.withdraw(amount);
      return;
    }
    System.out.println("Customer not found.");
  }

  public void addLoanAccount(String customerId, String accountNumber, double initialAmount) {
    Customer customer = getCustomerById(customerId);
    if (customer instanceof DigitalCustomer e) {
      e.addLoanAccount(accountNumber, initialAmount);
    }
  }

  public void addSavingsAccount(String customerId, String accountNumber, double initialAmount) {


    Customer customer = getCustomerById(customerId);
    if (customer instanceof DigitalCustomer e) {
      e.addSavingsAccount(accountNumber, initialAmount);

    }
  }

  public void showTransactions(String customerId) {
    Customer customer = getCustomerById(customerId);
    if (customer instanceof DigitalCustomer e) {
      e.displayInformation();
     e.printTransactions();
    }
  }

  public boolean isAccountExisted(String customerId ,String accountNumber) {
    Customer customer = getCustomerById(customerId);
    Account account = customer.getAccountByAccountNumber(accountNumber);
    return customer.isAccountExisted(account);
  }


}
