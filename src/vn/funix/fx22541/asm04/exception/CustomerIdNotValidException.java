package vn.funix.fx22541.asm04.exception;

public class CustomerIdNotValidException extends IllegalArgumentException {
    public CustomerIdNotValidException() {
        System.out.println("Exception: Customer ID is not valid.");
    }
}
