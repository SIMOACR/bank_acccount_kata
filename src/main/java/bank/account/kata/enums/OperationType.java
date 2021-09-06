package bank.account.kata.enums;

public enum OperationType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    public final String label;

    OperationType(String label) {
        this.label = label;
    }
}
