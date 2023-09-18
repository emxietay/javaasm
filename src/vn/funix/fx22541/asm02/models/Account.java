package vn.funix.fx22541.asm02.models;

public class Account {
	private String accountNumber;
	private double balance;

	public Account(String accountNumber) {
		if (!validateAccount(accountNumber)) {
			throw new IllegalArgumentException("Vui lòng nhập số tài khoản gồm 6 chữ số.");
		}
		new Account(accountNumber, 0);
	}

	public static boolean validateAccount(String accountNumber) {
		return accountNumber.matches("\\d{6}");
	}

	public Account(String accountNumber, double initialAmount) {
		if (initialAmount < 50000) {
			throw new IllegalArgumentException("Số dư không được nhỏ hơn 50.000đ.");
		}
		this.accountNumber = accountNumber;
		setBalance(initialAmount);
	}

	public Account() {

	}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isAccountPremium() {
		return balance >= 10000000;
	}

	@Override
	public String toString() {
		return accountNumber + "\t"
						+ balance
						;
	}

//    public List<Transaction> getTransaction() {
//    }
}
