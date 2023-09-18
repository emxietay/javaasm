package vn.funix.fx22541.asm03.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.fx22541.asm02.models.Customer;

import static org.junit.jupiter.api.Assertions.*;

class DigitalBankTest {
	private static final DigitalBank activeBank = new DigitalBank();
	private static final String CUSTOMER_ID = "001215000001";
	private static final String CUSTOMER_NAME = "FUNIX";

	@BeforeEach
	void setUp() {
		activeBank.addCustomer(CUSTOMER_ID, CUSTOMER_NAME);
		activeBank.addSavingsAccount(CUSTOMER_ID, "123456", 2000000);
		activeBank.addLoanAccount(CUSTOMER_ID, "123457", 2000000);
	}

	@Test
	void getAccountByAccountNumber() {
		Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
		assertNotNull(customer.getAccountByAccountNumber("123456"));
	}

	@Test
	void getCustomerById() {
		assertNotNull(activeBank.getCustomerById(CUSTOMER_ID));
		assertNull(activeBank.getCustomerById("1234567"));
	}

	@Test
	void addCustomer() {
		assertEquals("FUNIX", activeBank.getCustomerById(CUSTOMER_ID).getName());
	}

	@Test
	void withdraw() {
		Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
		activeBank.withdraw((DigitalCustomer) customer, "123456", 1000000);
		assertEquals(3000000, customer.getTotalBalanceAccount());
	}

	@Test
	void isAccountExisted() {
		assertFalse(activeBank.isAccountExisted(CUSTOMER_ID, "123458"));
		assertTrue(activeBank.isAccountExisted(CUSTOMER_ID, "123457"));
	}

	@Test
	void isCustomerExisted() {
		assertTrue(activeBank.isCustomerExisted(CUSTOMER_ID));
		assertFalse(activeBank.isCustomerExisted("001092009430"));
	}

	@Test
	void isCustomerPremium() {
		Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
		assertFalse(customer.isCustomerPremium());
		customer.addAccount("123459", 100000000);
		assertTrue(customer.isCustomerPremium());
	}

}