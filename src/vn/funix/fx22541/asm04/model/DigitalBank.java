package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.service.Validator;

import java.util.ArrayList;
import java.util.List;

public class DigitalBank extends Bank {
    private List<Customer> customers;
    private List<Account> accounts;
//    private static final String CUSTOMER_FILE = "src/vn/funix/fx22541/asm04/store/customers.txt";

    public DigitalBank(String name, String id) {
        super(name, id);
        customers = new ArrayList<>();
        accounts = new ArrayList<>();
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
            customers.add(new Customer(name, customerId));
        } else {
            System.out.println("Account existed  .... " + name);
        }

    }
    public void addCustomer(String name, String customerId) {
        if (getCustomerById(customerId) == null) {
            customers.add(new Customer(name, customerId));
        } else {
            System.out.println("Account existed  .... " + name);
        }

    }

    public void addCustomer(Customer customer) {
        if (customer != null) {
            addCustomer(customer.getName(), customer.getId());
        }
    }

    public Customer getCustomerById(String customerId) {

        if (customers.size() > 0) {
            for (Customer customer : customers) {
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
        Customer customer = getCustomerById(customerId);
        if (customer != null && validated && !accounts.contains(accountNumber)) {
            SavingsAccount.createAccount(accountNumber, initialAmount, customer);
        } else {
            System.out.println("Account does not exist!! ");
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


    public void save() {
        CustomerDAO.save(customers);
    }

    public void importCustomers() {
        List<Customer> list = CustomerDAO.readFile();
        var customerInTextFile = CustomerDAO.importCustomers();
//        customerInTextFile.stream().filter(e -> !list.contains(e)).forEach(list::add);


    }

    public void list() {
        customers = CustomerDAO.readFile();
        customers.forEach(System.out::println);
    }

    public int getCustomerNumbers() {
        return customers.size();
    }
}
