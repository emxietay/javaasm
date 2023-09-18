package vn.funix.fx22541.asm04.model;

import vn.funix.fx22541.asm04.dao.TransactionDAO;
import vn.funix.fx22541.asm04.exception.InitialAccountBalanceNotValidException;
import vn.funix.fx22541.asm04.service.Report;
import vn.funix.fx22541.asm04.service.Utils;
import vn.funix.fx22541.asm04.service.Validator;
import vn.funix.fx22541.asm04.service.Withdraw;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static vn.funix.fx22541.asm04.service.Validator.validateLoanAccount;

public class LoanAccount extends Account implements Report, Withdraw, Serializable {

	@Serial
	private static final long serialVersionUID = 3L;
	private Customer customer;
	private final static double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
	private final static double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
	private final static double LOAN_ACCOUNT_MAX_BALANCE = 100000000.00;

	private LoanAccount(String accountNumber, double initialAmount, Customer customer) {
		if (validateLoanAccount(accountNumber, initialAmount)) {
			this.accountNumber = accountNumber;
			this.balance = initialAmount;
			this.customer = customer;
			transactions.add(new Transaction(accountNumber, initialAmount, true, Transaction.TransactionType.DEPOSIT));
			TransactionDAO.save(transactions);
			System.out.printf("Account: %s just be created successfully.\n", accountNumber);
		}
	}

	public static LoanAccount createAccount(String accountNumber, double initialAmount, Customer customer) {
		if (validateLoanAccount(accountNumber, initialAmount)) {
			return new LoanAccount(accountNumber, initialAmount, customer);
		}
		return null;
	}

	private LoanAccount() {
	}

	public Customer getCustomer() {
		return this.customer;
	}

	@Override
	public String getAccountNumber() {
		return super.getAccountNumber();
	}

	public void setBalance(double balance) {
		if (balance > LOAN_ACCOUNT_MAX_BALANCE || balance < 0) {
			throw new InitialAccountBalanceNotValidException();
		} else {
			this.balance = balance;
		}
	}


	private String getTitle() {
		return "BIEN LAI GIAO DICH LOAN";
	}

	public double getTransactionFee(double amount) {
		if (Validator.isAccountPremium(balance)) {
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
		System.out.printf("SO DU: %31s%n", Utils.formatBalance(
				getAccountBalance() - amount - getTransactionFee(amount)));
		System.out.printf("PHI + VAT: %27s%n",
				Utils.formatBalance(getTransactionFee(amount)));
		System.out.println(Utils.getDivider());
	}

	@Override
	public boolean withdraw(double amount) {
		Transaction transaction = new Transaction(getAccountNumber(),
				(-amount), isAccepted(amount), Transaction.TransactionType.WITHDRAW);
		transactions.add(transaction);
		if (isAccepted(amount)) {
			log(amount);
			setBalance(getAccountBalance() - amount - getTransactionFee(amount));
			return true;
		}
		System.out.println("Not valid withdrawing amount. ");
		return false;
	}

	@Override
	public boolean isAccepted(double amount) {
		return !(amount < 0)
				&& (amount <= LOAN_ACCOUNT_MAX_BALANCE)
				&& (amount % 10000 == 0)
				&& !((getAccountBalance() - amount - getTransactionFee(amount)) < 50000);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}
