package vn.funix.fx22541.asm04.test.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx22541.asm04.dao.CustomerDAO;
import vn.funix.fx22541.asm04.model.DigitalBank;
import vn.funix.fx22541.asm04.model.DigitalCustomer;

import static org.junit.jupiter.api.Assertions.*;

class DigitalBankTest {

    private DigitalBank digitalBank;
    @BeforeEach
    void setUp() {
        digitalBank = new DigitalBank("VNN", "123");
        digitalBank.importCustomers();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void showCustomer() {
    }

    @Test
    void addCustomer() {
        digitalBank.addCustomer("Nam", "123123123123");
        assertNotNull(digitalBank.getCustomerById("123123123123"));
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void testWithdraw() {
    }

    @Test
    void addLoanAccount() {
    }

    @Test
    void addSavingsAccount() {
    }

    @Test
    void showTransactions() {
    }

    @Test
    void isAccountExisted() {
    }

    @Test
    void save() {

    }

    @Test
    void importCustomers() {

    }

    @Test
    void list() {
    }
}