package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.service.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private String name;
    private String id;
    protected final List<Account> accounts;


    public Customer(List<String> strings) {
        this(strings.get(0), strings.get(1));
    }
    public Customer(String id, String name) {
        this.name = name;
//        if (Validator.validateCustomerId(id)) {
            this.id = id;
            accounts = new ArrayList<>();
//        } else {
//            throw new IllegalArgumentException("Wrong ID");
//        }
    }



    @Override
    public String toString() {
        String textBlock = """
                Customer: %s (id: %s):
                accounts: %s
                """;
        return textBlock.formatted(name, id, accounts);


//                "Customer{" +
//               ", name='" + name + '\'' +
//                ", id='" + id + '\'' +
//                ", accounts=" + accounts +
//                '}';
    }

    public boolean isCustomerPremium() {
        for (var account : accounts) {
            if (Validator.isAccountPremium(account.getAccountBalance())) return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void withdraw(String accountNumber, double amount) {

    }

    public Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean isAccountExisted(Account account) {
        return accounts.contains(account);
    }
    public boolean isAccountExisted(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
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

}
