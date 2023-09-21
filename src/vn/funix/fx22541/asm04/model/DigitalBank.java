package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.exception.AccountNumberNotValidException;
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


  public void addCustomer(String fileName) {
    String path;
    while (true) {
      System.out.print("Enter path to customers file: ");
      path = fileName + scanner.nextLine();
      CustomerDAO.importCustomers(path);
      break;
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

  public <T extends Customer> T getCustomerById(String customerId) {
    List<T> customers = CustomerDAO.list();
    if (customers.size() > 0) {
      for (T customer : customers) {
        if (customer.getId().equals(customerId)) {
          return customer;
        }
      }
    }
    return null;
  }

  public void withdraw(Scanner scanner) {
    while (true) {

      String inputCustomer = getInputCustomer(scanner);
      if (inputCustomer.toLowerCase().equals("exit")) {
        break;
      }
      if (!AccountDAO.hasAnyAccount(inputCustomer)) {
        System.out.println("The customer has no account. ");
        break;
      }
      System.out.print("\nEnter Account Number: ");
      String inputNumberAccount = scanner.nextLine();
      if (!inputNumberAccount.matches("\\d{6}")) {
        System.out.println("Wrong Account Number format, please try again. ");
        continue;
      }
      Account foundAccount = AccountDAO.getAccountById(inputNumberAccount);
      if (foundAccount == null) {
        System.out.println("Cannot find account, please enter another number. ");
        continue;
      }
      if (foundAccount instanceof SavingsAccount s) {
        try {
          System.out.print("\nEnter Withdraw Amount: ");
          double inputAmount = Double.parseDouble(scanner.nextLine());
          s.withdraw(inputAmount);
          break;
        } catch (NumberFormatException e) {
          System.out.println("Please enter correct number. ");
        } catch (SavingsWithdrawAmountNotValidException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

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
      e.printTransactions();
    }
  }


  public void showCustomers() {


    List<Customer> customerList = CustomerDAO.list();
    if (customerList == null) {
      System.out.println("Can not find any customer / Customer list is empty");
    } else {
      customerList.forEach(Customer::displayInformation);
    }
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
            System.out.println("Wrong type of account, please select another option. ");
            continue;
          }
        }
      }
    }
  }

  public void showTransactions(Scanner scanner) {
    while (true) {
      String inputID = getInputCustomer(scanner);
      if (inputID == null) continue;
      showTransactions(inputID);
      break;
    }
  }

  private String getInputCustomer(Scanner scanner) {
    System.out.print("Enter Customer ID: ");
    String inputID = scanner.nextLine();
    if (inputID.toLowerCase().equals("exit")) {
      return "exit";
    }
    if (!inputID.matches("\\d{12}")) {
      System.out.println("Wrong ID format, please try again. ");
      return inputID;
    }

    DigitalCustomer customer = getCustomerById(inputID);
    if (customer == null) {
      System.out.print("\nCustomer not exist. ");
      return "exit";
    }
    customer.displayInformation();
    return inputID;
  }

  public void addSavingsAccount()  {
    while (true) {
      try {

        System.out.print("Enter Customer ID:");
        String inputID = scanner.nextLine();
        if (inputID.toLowerCase().equals("exit")) {
          break;
        }
        if (!inputID.matches("\\d{12}")) {
          System.out.println("Wrong ID format, please try again. ");
          continue;
        }
        if (!CustomerDAO.isCustomerExisted(inputID)) {
          System.out.print("\nCustomer not exist. \n");
          continue;
        } else {
          System.out.print("\nEnter Account Number: ");
          String inputNumberAccount = scanner.nextLine();
          if (!Validator.validateAccountNumber(inputNumberAccount)) {

          }
          if (AccountDAO.getAccountById(inputNumberAccount) != null) {
            System.out.print("\nAccount existed. \n");
            continue;
          } else {
            try {
              System.out.print("\nEnter Initial Amount: ");
              double inputAmount = Double.parseDouble(scanner.nextLine());
              if (Validator.validateSavingsAccountAmount(inputAmount)) {
                SavingsAccount savingsAccount = new SavingsAccount(inputID, inputNumberAccount, inputAmount);
                Transaction transaction = new Transaction(inputNumberAccount, inputAmount, true, Transaction.TransactionType.DEPOSIT);
                TransactionDAO.update(transaction);
                AccountDAO.update(savingsAccount);
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
      } catch (AccountNumberNotValidException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
