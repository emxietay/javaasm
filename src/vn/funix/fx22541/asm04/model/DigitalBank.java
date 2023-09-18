package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.AccountDAO;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.service.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DigitalBank extends Bank {
  private final Scanner scanner = new Scanner(System.in);
  private List<DigitalCustomer> customers = CustomerDAO.readFile();
  ;
  private List<Account> accounts = AccountDAO.list();
//    private static final String CUSTOMER_FILE = "src/vn/funix/fx22541/asm04/store/customers.txt";

  public DigitalBank(String name, String id) {
    super(name, id);
//    customers = new ArrayList<DigitalCustomer>();
  }

//    public DigitalBank() {
//        super();
//        customers = new ArrayList<>();
//    }


  public void showCustomer() {
    list();
  }

  public void addCustomer(String name, String customerId, double initialAmount) {
    if (getCustomerById(customerId) == null) {
      customers.add(new DigitalCustomer(name, customerId));
    } else {
      System.out.println("Account existed  .... " + name);
    }

  }

  public void addCustomer(String name, String customerId) {
    if (getCustomerById(customerId) == null) {
      customers.add(new DigitalCustomer(name, customerId));
    } else {
      System.out.println("Account existed  .... " + name);
    }

  }

  public void addCustomer(Customer customer) {
    if (customer != null) {
      addCustomer(customer.getName(), customer.getId());
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

  public void withdraw(Customer customer, String accountNumber, double amount) {
    if (customers.contains(customer)) {
      customer.withdraw(accountNumber, amount);
    }
  }

  public void withdraw(String customerId, String accountNumber, double amount) {
    Customer foundCustomer;
    if ((foundCustomer = getCustomerById(customerId)) != null) {
      foundCustomer.withdraw(accountNumber, amount);
      return;
    }
    System.out.println("Customer not found: " + customerId);
  }

  public void addLoanAccount(String customerId, String accountNumber, double initialAmount) {

//        isAccountExisted()
    boolean validated = Validator.validateLoanAccount(accountNumber, initialAmount);
    Customer customer = getCustomerById(customerId);
    if (customer != null && validated && !accounts.contains(accountNumber)) {
      LoanAccount.createAccount(accountNumber, initialAmount, customer);
    } else {
      System.out.println("Account does not exist!.");
    }
  }

  public void addSavingsAccount(String customerId, String accountNumber, double initialAmount) {
    boolean validated = Validator.validateSavingsAccount(accountNumber, initialAmount);
    DigitalCustomer customer = (DigitalCustomer) getCustomerById(customerId);
    if (customer != null && validated && !accounts.contains(accountNumber)) {
     accounts.add(SavingsAccount.createAccount(accountNumber, initialAmount, customer));
    } else {
      System.out.println("Customer does not exist!! ");
    }
  }


  public void addAccount() {
    boolean stop = false;
    while (!stop) {
      System.out.print("Enter Customer ID: ");
      String inputId = scanner.nextLine();
      DigitalCustomer customer = getCustomerById(inputId);
      if (customer == null) {
        System.out.printf("Cannot find customer with id: %s, please select other options %n", inputId);
        stop = true;
      } else {
        while (true) {
          System.out.print("Enter account number (6 digits) : ");
          String inputAccountNumber = scanner.nextLine();
          if (!inputAccountNumber.matches(Validator.ACC_NO_REGEX)) {
            continue;
          }
          if (customer.isAccountExisted(inputAccountNumber)) {
            System.out.println("Account existed");
            continue;
          } else {
            while (true) {
              double inputAmount = -1;
              try {

                System.out.print("Enter initial amount (at least 50.000VND): ");
                inputAmount = Double.parseDouble(scanner.nextLine());
              } catch (NumberFormatException e) {
                System.out.println("\nWrong number!\n");
              }
              if (inputAmount < 50_000d) {
                continue;
              } else {
                customer.addSavingsAccount(inputAccountNumber, inputAmount);
                accounts.add(new SavingsAccount(inputAccountNumber, inputAmount, customer));
                System.out.println("Savings account being created successfully");
                stop = true;
              }
              break;
            }
          }
          break;
        }
      }
    }

  }


  public void showTransactions(String customerId) {
    Customer customer = getCustomerById(customerId);
    if (customer instanceof DigitalCustomer e) {
      e.displayInformation();
      e.printTransactions();
    }
  }

  public boolean isAccountExisted(String customerId, String accountNumber) {
    Customer customer = getCustomerById(customerId);
    return (customers.contains(customer) && customer.isAccountExisted(accountNumber));
  }

  public boolean isAccountExisted(String accountNumber) {
    return accounts.stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
  }


  public void save() {
    CustomerDAO.save(customers);
  }

  public void importCustomers() {
    List<DigitalCustomer> list = CustomerDAO.readFile();
    var customerInTextFile = CustomerDAO.importCustomers();
//        customerInTextFile.stream().filter(e -> !list.contains(e)).forEach(list::add);


  }

  public void list() {
//    customers = CustomerDAO.readFile();
    customers.forEach(System.out::println);
  }

  public int getCustomerNumbers() {
    return customers.size();
  }

  public void transfer() {
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
          SavingsAccount sendingAccount = getSavingsAccountByID(inputAccount);
          SavingsAccount receivingAccount = getSavingsAccountByID(receivingAcc);
          sendingAccount.transfer(receivingAccount, amount);
          break;
        }
      }
    }
  }

  private SavingsAccount getSavingsAccountByID(String inputAccount) {
    return (SavingsAccount) accounts.stream().filter(e-> e.getClass().equals(SavingsAccount.class)).filter(e -> e.getAccountNumber().equals(inputAccount)).findFirst().orElse(null);
  }

  public void showTransactions() {
    TransactionDAO.list().forEach(System.out::println);
  }

  public void showAccounts() {
    accounts.forEach(System.out::println);
  }
}
