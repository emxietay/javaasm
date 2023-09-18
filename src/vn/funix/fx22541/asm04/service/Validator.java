package vn.funix.fx22541.asm04.service;

public class Validator {
    private static final double LOAN_ACCOUNT_MAX_BALANCE = 100_000_000;
    public static final String ACC_NO_REGEX = "\\d{6}";
    public static final String CUSTOMER_ID_REGEX = "\\d{12}";
    public static final double PREMIUM_BALANCE_AMOUNT = 10_000_000.00;
    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000_000;
    public static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50_000;
    public static final int MINIMUM_BALANCE_AFTER_WITHDRAW = 50000;

    public static boolean validateAccountNumber(String accountNumber) {
        return accountNumber.matches(ACC_NO_REGEX);
    }

    public static boolean validateCustomerId(String id) {
        return id.matches(CUSTOMER_ID_REGEX);
    }

    public static boolean validateLoanAccount(String accountNumber, double initialAmount) {
        if (initialAmount > LOAN_ACCOUNT_MAX_BALANCE || initialAmount < 0) {
            throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
        }
        if (!validateAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Vui lòng nhập tài khoản gồm 6 kí tự số.");
        }
        return true;
    }

    public static boolean validateSavingsAccount(String accountNumber, double initialAmount) {
        if (initialAmount < 50000) {
            throw new IllegalArgumentException("Số dư khởi tạo không hợp lệ.");
        }
        if (!validateAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Vui lòng nhập tài khoản gồm 6 kí tự số.");
        }
        return true;
    }

    public static boolean isAccountPremium(double balance) {
        return balance > PREMIUM_BALANCE_AMOUNT;
    }

    public static boolean validateSavingsWithdrawAmount(double balance, double amount) {
        if (isAccountPremium(balance)) {
            return (amount % 10_000 == 0) && (balance - amount < 50_000);
        } else {
            return !(amount < SAVINGS_ACCOUNT_MIN_WITHDRAW)
                    && (amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW)
                    && (amount % 10000 == 0)
                    && !((balance - amount) < MINIMUM_BALANCE_AFTER_WITHDRAW);
        }
    }
}
