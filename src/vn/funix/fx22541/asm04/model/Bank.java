package vn.funix.fx22541.asm04.model;


import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private String id;
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public Bank(String name, String id) {
        this();
        this.name = name;
        this.id = id;
    }


}
