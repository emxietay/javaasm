package vn.funix.fx22541.asm02.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Bank {
    private final Scanner scanner = new Scanner(System.in);
    private final String id;
    protected List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
    }


    public List<Customer> getCustomers() {
        return customers;
    }

    public void listCustomers() {
        for (Customer customer :
                customers) {
            customer.displayInformation();
        }
    }

    public boolean isCustomerExisted(String customerId) {
        for (Customer checkedCustomer :
                customers) {
            if (checkedCustomer.getCustomerId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    public void addCustomer(String customerName, String customerId) {
        if (getCustomerById(customerId) == null) {
            customers.add(new Customer(customerName, customerId));
        }
    }

    public Customer getCustomerById(String customerId) {
        for (Customer foundCustomer :
                customers) {
            if (customerId.equals(foundCustomer.getCustomerId())) {
                return foundCustomer;
            }
        }
        return null;
    }

    public void searchCustomerByName() {
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String customerName = scanner.nextLine();
        for (Customer customer :
                customers) {
            if (customer.getName().toLowerCase().contains(customerName.toLowerCase())) {
                customer.displayInformation();
            }
        }

    }

    public  void searchCustomerById() {
        System.out.print("Nhập số CCCD cần tìm: ");
        String searchId = scanner.nextLine();
        if (getCustomerById(searchId) != null) {
            System.out.println("Đã tìm thấy: ");
            getCustomerById(searchId).displayInformation();
        }
    }

    public boolean validateCustomerId(String canCuocCongDan) {
        return canCuocCongDan.matches("\\d{12}");
    }


}
