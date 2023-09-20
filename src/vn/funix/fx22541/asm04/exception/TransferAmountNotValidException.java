package vn.funix.fx22541.asm04.exception;

public class TransferAmountNotValidException extends IllegalArgumentException {
  public TransferAmountNotValidException() {
    System.out.println("Amount not valid");
  }
}
