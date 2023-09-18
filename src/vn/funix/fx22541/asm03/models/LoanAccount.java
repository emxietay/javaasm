package vn.funix.fx22541.asm03.models;

import vn.funix.fx22541.asm02.models.Account;

import java.util.ArrayList;
import java.util.List;

public class LoanAccount extends Account implements ReportService, Withdraw, ITransaction {
	private final static double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
	private final static double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
	private final static double LOAN_ACCOUNT_MAX_BALANCE = 100000000.00;

	private final List<ITransaction> transactions;

	public LoanAccount(String accountNumber, double initialAmount) {
		if (initialAmount > LOAN_ACCOUNT_MAX_BALANCE || initialAmount < 0) {
			throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
		}
		if (!validateAccount(accountNumber)) {
			throw new IllegalArgumentException("Vui lòng nhập tài khoản gồm 6 kí tự số.");
		}
		setAccountNumber(accountNumber);
		setBalance(initialAmount);
		System.out.printf("Tài khoản %s được tạo thành công.%n", accountNumber);
		transactions = new ArrayList<>();
		transactions.add(new Transaction(accountNumber, initialAmount, true));
	}

	@Override
	public void setBalance(double balance) {
		if (balance > LOAN_ACCOUNT_MAX_BALANCE || balance < 0) {
			throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
		} else {
			super.setBalance(balance);
		}
	}


	private String getTitle() {
		return "BIEN LAI GIAO DICH LOAN";
	}

	public double getTransactionFee(double amount) {
		if (isAccountPremium()) {
			return LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE * amount;
		}
		return LOAN_ACCOUNT_WITHDRAW_FEE * amount;
	}

	@Override
	public void log(double amount) {

		System.out.println(Utils.getDivider());
		System.out.printf("%30s%n", getTitle());
		System.out.printf("NGAY G/D: %28s%n", Utils.getDateTime());
		System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
		System.out.printf("SO TK: %31s%n", getAccountNumber());
		System.out.printf("SO TIEN: %29s%n", Utils.formatBalance(amount));
		System.out.printf("SO DU: %31s%n", Utils.formatBalance(getAccountBalance() - amount - getTransactionFee(amount)));
		System.out.printf("PHI + VAT: %27s%n", Utils.formatBalance(getTransactionFee(amount)));
		System.out.println(Utils.getDivider());
	}

	@Override
	public boolean withdraw(double amount) {
		transactions.add(new Transaction(getAccountNumber(), -amount, isAccepted(amount)));
		if (isAccepted(amount)) {
			log(amount);
			setBalance(getAccountBalance() - amount - getTransactionFee(amount));
			return true;
		}
		System.out.println("Số tiền không hợp lệ. ");
		return false;
	}

	@Override
	public boolean isAccepted(double amount) {
		return !(amount < 0) && (amount <= LOAN_ACCOUNT_MAX_BALANCE) & (amount % 10000 == 0) && !((getAccountBalance() - amount - getTransactionFee(amount)) < 50000);
	}

	public List<Transaction> getTransactions() {
		return null;
	}
}
