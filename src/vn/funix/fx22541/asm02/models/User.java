package vn.funix.fx22541.asm02.models;

import java.util.InputMismatchException;

public abstract class User {
    private String name;
    private String customerId;

    public User(String name, String customerId) {
        if (name == null || name.isEmpty()) {
            throw new InputMismatchException("Can not be empty");
        }
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
