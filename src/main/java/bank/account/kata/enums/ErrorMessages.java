package bank.account.kata.enums;

public enum ErrorMessages {
    DEPOSIT_LESS_THAN_ZERO("The deposit should be greater than 0"),
    DEPOSIT_MORE_THAN_DEPOSIT_LIMIT("The deposit should be less than the deposit limit"),
    WITHDRAWAL_LESS_THAN_ZERO("The withdrawal should be greater than 0"),
    WITHDRAWAL_MORE_THAN_WITHDRAWAL_LIMIT("The withdrawal should be less than the withdrawal limit"),
    SURPASSING_OVERDRAFT_LIMIT("You can not surpass the overdraft limit: ");

    public final String label;

    ErrorMessages(String label) {
        this.label = label;
    }
}
