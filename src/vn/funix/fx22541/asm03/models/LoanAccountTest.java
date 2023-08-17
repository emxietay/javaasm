package vn.funix.fx22541.asm03.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanAccountTest {

	LoanAccount loanAccount = new LoanAccount("123123", 1000000);

	@Test
	void validateAccount() {
		assertTrue(LoanAccount.validateAccount("111222"));
		assertFalse(LoanAccount.validateAccount("111"));
	}


	@Test
	void isAccepted() {
		boolean accepted = loanAccount.isAccepted(200000000);
		assertFalse(accepted);
	}

	@Test
	void getFee() {
		double transactionFee = loanAccount.getTransactionFee(1000000);
		assertEquals(50000, transactionFee);
		assertEquals(5000, loanAccount.getTransactionFee(100000));
	}

	@Test
	void isAccountPremium() {
		assertFalse(loanAccount.isAccountPremium());
		LoanAccount premium = new LoanAccount("221133", 10000000);
		assertTrue(premium.isAccountPremium());

	}
}