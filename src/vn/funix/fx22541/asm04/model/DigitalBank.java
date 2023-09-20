package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.exception.CustomerIdNotValidException;
import vn.funix.fx22541.asm04.exception.SavingsWithdrawAmountNotValidException;
import vn.funix.fx22541.asm04.service.Validator;

import java.util.List;
import java.util.Scanner;


public class DigitalBank extends Bank {
  private final Scanner scanner = new Scanner(System.in);
  private List<DigitalCustomer> customers = CustomerDAO.list();
  ;
  private List<Account> accounts = AccountDAO.list();
  private List<Transaction> transactions = TransactionDAO.list();

  public DigitalBank(String name, String id) {
    super(name, id);
  }

  private static boolean isAccountExisted(String accountNumber) {
    return AccountDAO.list().stream().anyMatch(e -> e.getAccountNumber().equals(accountNumber));
  }

  public void addNewCustomer() {
    while (true) {
      try {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.printf("Enter Customer ID: ");
        String id = scanner.nextLine();
        if (Validator.validateCustomerId(id)) {
          addCustomer(id, name);
          break;
        }
      } catch (CustomerIdNotValidException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void addCustomer(String customerId, String name) {
    List<Customer> list = CustomerDAO.list();
    while (true) {
      try {
        if (Validator.validateCustomerId(customerId)) {
          if (getCustomerById(customerId) == null) {
            DigitalCustomer customer = new DigitalCustomer(customerId, name);
            list.add(customer);
            CustomerDAO.update(customer);
            break;
          }
        }
      } catch (CustomerIdNotValidException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public DigitalCustomer getCustomerById(String customerId) {
    if (customers.size() > 0) {
      for (DigitalCustomer customer : customers) {
        if (customer.getId().equals(customerId)) {
          return customer;
        }
      }
    }
    return null;
  }

  public void withdraw(Scanner scanner) {
    while (true) {
      System.out.print("Enter Customer ID:");
      String inputID = scanner.nextLine();
      DigitalCustomer customer = getCustomerById(inputID);
      if (customer == null) {
        System.out.print("\nCustomer not exist.");
        continue;
      }
      customer.displayInformation();
      System.out.print("\nEnter Account Number: ");
      String inputNumberAccount = scanner.nextLine();
      Account foundAccount = AccountDAO.getAccountById(inputNumberAccount);
      if (foundAccount == null) {
        System.out.println("Cannot find account, please enter another number.");
        continue;
      } if (foundAccount instanceof SavingsAccount s) {
        try {
          System.out.print("\nEnter Withdraw Amount: ");
          double inputAmount = Double.parseDouble(scanner.nextLine());
          s.withdraw(inputAmount);
            break;
        } catch (NumberFormatException e) {
          System.out.println("Please enter correct number");
        } catch (SavingsWithdrawAmountNotValidException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  public <T extends Customer> T getCustomerFromID(String id) {
    return (T) CustomerDAO.list().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
  }

  public void withdraw(Scanner scanner, String customerId) {
    Customer foundCustomer;
    if ((foundCustomer = getCustomerById(customerId)) instanceof DigitalCustomer e) {
      e.withdraw(scanner);
      return;
    }
    System.out.println("Customer not found: " + customerId);
  }


//  public void addAccount() {
//    boolean stop = false;
//    while (!stop) {
//      System.out.print("Enter Customer ID: ");
//      String inputId = scanner.nextLine();
//      DigitalCustomer customer = getCustomerById(inputId);
//      if (customer == null) {
//        System.out.printf("Cannot find customer with id: %s, please select other options %n", inputId);
//        stop = true;
//      } else {
//        while (true) {
//          System.out.print("Enter account number (6 digits) : ");
//          String inputAccountNumber = scanner.nextLine();
//          if (!inputAccountNumber.matches(Validator.ACC_NO_REGEX)) {
//            continue;
//          }
//          if (customer.isAccountExisted(inputAccountNumber)) {
//            System.out.println("Account existed");
//            continue;
//          } else {
//            while (true) {
//              double inputAmount = -1;
//              try {
//
//                System.out.print("Enter initial amount (at least 50.000VND): ");
//                inputAmount = Double.parseDouble(scanner.nextLine());
//              } catch (NumberFormatException e) {
//                System.out.println("\nWrong number!\n");
//              }
//              if (inputAmount < 50_000d) {
//                continue;
//              } else {
//                customer.addSavingsAccount(inputAccountNumber, inputAmount);
//                accounts.add(new SavingsAccount(customer.getId(),));
//                System.out.println("Savings account being created successfully");
//                stop = true;
//              }
//              break;
//            }
//          }
//          break;
//        }
//      }
//    }
//
//  }

  public void addLoanAccount(String customerId, String accountNumber, double initialAmount) {
    boolean validated = Validator.validateLoanAccount(accountNumber, initialAmount);
    Customer customer = getCustomerById(customerId);
    if (customer != null && validated && !accounts.contains(accountNumber)) {
      LoanAccount loanAccount = new LoanAccount(customerId, accountNumber, initialAmount);
    } else {
      System.out.println("Account does not exist!.");
    }
  }

  public void showTransactions(String customerId) {
    Customer customer = getCustomerById(customerId);
    if (customer instanceof DigitalCustomer e) {
      e.displayInformation();
      e.printTransactions();
    }
  }

  public void importCustomers() {
    List<DigitalCustomer> list = CustomerDAO.list();
    var customerInTextFile = CustomerDAO.importCustomers();
  }

  public void showCustomers() {
    List<Customer> customerList = CustomerDAO.list();
    if (customerList == null) {
      System.out.println("Can not find any customer / Customer list is empty");
    } else {
      customerList.forEach(Customer::displayInformation);
    }
  }

  public int getCustomerNumbers() {
    return customers.size();
  }

  public <T extends Account> void transfer() {
    while (true) {
      System.out.print("Enter account number: ");
      String inputAccount = scanner.nextLine();
      if (inputAccount.equals("exit")) {
        break;
      } else if (!isAccountExisted(inputAccount)) {
        continue;

      } else {
        System.out.print("\nEnter receiving account number: ");
        String receivingAcc = scanner.nextLine();
        if (!isAccountExisted(receivingAcc)) {
          continue;
        } else {
          System.out.print("\nEnter transfer amount: ");
          double amount = Double.parseDouble(scanner.nextLine());
          T sendingAccount = AccountDAO.getAccountById(inputAccount);
          if (sendingAccount instanceof SavingsAccount e) {
            e.transfer(receivingAcc, amount);
            break;
          } else {
            System.out.println("Wrong type of account, please select another option");
            continue;
          }
        }
      }
    }
  }

  public void showTransactions() {
    TransactionDAO.list().forEach(System.out::println);
  }

  public void addSavingsAccount() {
    while (true) {
      System.out.print("Enter Customer ID:");
      String inputID = scanner.nextLine();
      if (!CustomerDAO.isCustomerExisted(inputID)) {
        System.out.print("\nCustomer not exist. \n");
        continue;
      } else {
        System.out.print("\nEnter Account Number: ");
        String inputNumberAccount = scanner.nextLine();
        if (AccountDAO.getAccountById(inputNumberAccount) != null) {
          System.out.print("\nAccount existed. \n");
          continue;
        } else {
          try {
            System.out.print("\nEnter Initial Amount: ");
            double inputAmount = Double.parseDouble(scanner.nextLine());
            if (Validator.validateSavingsWithdrawAmount(inputAmount)) {
              SavingsAccount savingsAccount = new SavingsAccount(inputID, inputNumberAccount, inputAmount);
              TransactionDAO.list().stream().filter(e -> e.getAccountNumber().equals(inputNumberAccount)).forEach(System.out::println);
              break;
            }
          } catch (NumberFormatException e) {
            System.out.println("Please enter correct number");
          } catch (SavingsWithdrawAmountNotValidException e) {
            System.out.println(e.getMessage());
          }
        }
      }
    }
  }
}
