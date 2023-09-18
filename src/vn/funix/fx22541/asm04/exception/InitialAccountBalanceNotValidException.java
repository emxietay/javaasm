package vn.funix.fx22541.asm04.exception;

public class InitialAccountBalanceNotValidException extends IllegalArgumentException {
    public InitialAccountBalanceNotValidException() {
        System.out.println("Invalid initial account balance");
    }
}
