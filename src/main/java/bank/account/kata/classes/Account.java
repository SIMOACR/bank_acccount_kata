package bank.account.kata.classes;

import bank.account.kata.enums.ErrorMessages;
import bank.account.kata.enums.OperationType;
import bank.account.kata.exceptions.RejectedOperationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
    private String iban;
    private LocalDateTime date;
    private float amount;
    private float balance;
    private float overdraft;
    private float depositLimit;
    private float withdrawalLimit;
    private List<String> operations;
    private Client client;

    public Account(
            String iban,
            LocalDateTime date,
            float amount,
            float balance,
            float overdraft,
            float depositLimit,
            float withdrawalLimit,
            List<String> operations,
            Client client
    ) {
        this.iban = iban;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.overdraft = overdraft;
        this.depositLimit = depositLimit;
        this.withdrawalLimit = withdrawalLimit;
        this.operations = operations;
        this.client = client;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public float getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(float depositLimit) {
        this.depositLimit = depositLimit;
    }

    public float getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(float withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void makeADeposit(Float depositAmount) throws NullPointerException, RejectedOperationException {
        if (depositAmount == null)
            throw new NullPointerException();
        if (depositAmount <= 0)
            throw new RejectedOperationException(ErrorMessages.DEPOSIT_LESS_THAN_ZERO.label);
        if (depositAmount >= depositLimit)
            throw new RejectedOperationException(ErrorMessages.DEPOSIT_MORE_THAN_DEPOSIT_LIMIT.label);
        this.balance += depositAmount;
        this.amount += depositAmount;
        this.date = LocalDateTime.now();
        this.operations.add(this.formatOperation(OperationType.DEPOSIT));
    }

    public void makeAWithdrawal(Float withdrawalAmount) throws RejectedOperationException, NullPointerException {
        if (withdrawalAmount == null)
            throw new NullPointerException();
        if (withdrawalAmount <= 0)
            throw new RejectedOperationException(ErrorMessages.WITHDRAWAL_LESS_THAN_ZERO.label);
        if (withdrawalAmount >= this.balance + this.overdraft)
            throw new RejectedOperationException(ErrorMessages.SURPASSING_OVERDRAFT_LIMIT.label + this.overdraft);
        if (withdrawalAmount >= withdrawalLimit)
            throw new RejectedOperationException(ErrorMessages.WITHDRAWAL_MORE_THAN_WITHDRAWAL_LIMIT.label);
        this.balance -= withdrawalAmount;
        this.amount -= withdrawalAmount;
        this.date = LocalDateTime.now();
        this.operations.add(this.formatOperation(OperationType.WITHDRAWAL));
    }

    public String seeTheHistory() {
        return this.operations.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }

    private String formatOperation(
            OperationType operationType
    ) {
        return "Operation{" +
                "operationType=" + operationType.label +
                ", date=" + this.date +
                ", amount=" + this.amount +
                ", balance=" + this.balance +
                ", overdraft=" + this.overdraft +
                ", deposit limit=" + this.depositLimit +
                ", withdrawal limit=" + this.withdrawalLimit +
                '}';
    }
}
