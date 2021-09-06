package bank.account.kata.exceptions;

public class RejectedOperationException extends Exception {
    public RejectedOperationException(String errorMessage) {
        super(errorMessage);
    }
}
