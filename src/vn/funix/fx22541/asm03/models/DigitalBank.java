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
  public DigitalCustomer getCustomerById(String customerId) {
    for (Customer customer : this.customers) {
      if (customer.getCustomerId().equals(customerId) && customer instanceof DigitalCustomer) {
        return (DigitalCustomer) customer;
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
    DigitalCustomer foundCustomer = getCustomerById(customerId);
    if (foundCustomer != null) {
      foundCustomer.withdraw(accountNumber, amount);
      return;
    }
    System.out.println("Customer not found.");
  }

  public void addLoanAccount(String customerId, String accountNumber, double initialAmount) {
    DigitalCustomer customer = getCustomerById(customerId);
    if (customer != null) {
      customer.addLoanAccount(accountNumber, initialAmount);
    }
  }

  public void addSavingsAccount(String customerId, String accountNumber, double initialAmount) {


    DigitalCustomer customer = getCustomerById(customerId);
    if (customer != null) {
      customer.addSavingsAccount(accountNumber, initialAmount);

    }
  }

  public void showTransactions(String customerId) {
    DigitalCustomer customer = getCustomerById(customerId);
    if (customer != null) {
      customer.displayInformation();
     customer.printTransactions();
    }
  }

  public boolean isAccountExisted(String customerId ,String accountNumber) {
    Customer customer = getCustomerById(customerId);
    Account account = customer.getAccountByAccountNumber(accountNumber);
    return customer.isAccountExisted(account);
  }


}
