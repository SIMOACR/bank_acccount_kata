package bank.account.kata.classes;

import bank.account.kata.enums.ErrorMessages;
import bank.account.kata.enums.OperationType;
import bank.account.kata.exceptions.RejectedOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private float amount;
    private float balance;
    private float overdraft;
    private float depositLimit;
    private float withdrawalLimit;
    private LocalDateTime date;
    private float sum;
    private Account account;

    @BeforeEach
    void initTest() {
        amount = 500;
        balance = 370;
        overdraft = 50;
        depositLimit = 300;
        withdrawalLimit = 300;
        date = LocalDateTime.now();
        sum = 100;
        Client client = new Client(
                "Moahmed",
                "ACHKOUR",
                "medachkour@gmail.com"
        );
        account = new Account(
                "FRXXXX",
                LocalDateTime.now(),
                amount,
                balance,
                overdraft,
                depositLimit,
                withdrawalLimit,
                new ArrayList<>(),
                client
        );
    }

    @Test
    void makeADeposit_amount() throws RejectedOperationException {
        account.makeADeposit(sum);
        assertEquals(amount + sum, account.getAmount());
    }

    @Test
    void makeADeposit_balance() throws RejectedOperationException {
        account.makeADeposit(sum);
        assertEquals(balance + sum, account.getBalance());
    }

    @Test
    void makeADeposit_operations() throws RejectedOperationException {
        account.makeADeposit(sum);
        assertEquals(1, account.getOperations().size());
    }

    @Test
    void makeADeposit_date() throws RejectedOperationException {
        account.makeADeposit(sum);
        LocalDateTime accountDate = account.getDate();
        assertTrue(accountDate.isEqual(date) || accountDate.isAfter(date));
    }

    @Test
    void makeADeposit_null_exception() {
        assertThrows(
                NullPointerException.class,
                () -> account.makeADeposit(null)
        );
    }

    @Test
    void makeADeposit_zero_exception() {
        Exception exception = assertThrows(
                RejectedOperationException.class,
                () -> account.makeADeposit((float) 0)
        );
        assertEquals(
                ErrorMessages.DEPOSIT_LESS_THAN_ZERO.label,
                exception.getMessage()
        );
    }

    @Test
    void makeADeposit_surpassingTheLimit_exception() {
        Exception exception = assertThrows(
                RejectedOperationException.class,
                () -> account.makeADeposit(depositLimit + 1)
        );
        assertEquals(
                ErrorMessages.DEPOSIT_MORE_THAN_DEPOSIT_LIMIT.label,
                exception.getMessage()
        );
    }

    @Test
    void makeAWithdrawal_amount() throws RejectedOperationException {
        account.makeAWithdrawal(sum);
        assertEquals(amount - sum, account.getAmount());
    }

    @Test
    void makeAWithdrawal_balance() throws RejectedOperationException {
        account.makeAWithdrawal(sum);
        assertEquals(balance - sum, account.getBalance());
    }

    @Test
    void makeAWithdrawal_operations() throws RejectedOperationException {
        account.makeAWithdrawal(sum);
        assertEquals(1, account.getOperations().size());
    }

    @Test
    void makeAWithdrawal_date() throws RejectedOperationException {
        account.makeAWithdrawal(sum);
        LocalDateTime accountDate = account.getDate();
        assertTrue(accountDate.isEqual(date) || accountDate.isAfter(date));
    }

    @Test
    void makeAWithdrawal_null_exception() {
        assertThrows(
                NullPointerException.class,
                () -> account.makeAWithdrawal(null)
        );
    }

    @Test
    void makeAWithdrawal_zero_exception() {
        Exception exception = assertThrows(
                RejectedOperationException.class,
                () -> account.makeAWithdrawal((float) 0)
        );
        assertEquals(
                ErrorMessages.WITHDRAWAL_LESS_THAN_ZERO.label,
                exception.getMessage()
        );
    }

    @Test
    void makeAWithdrawal_surpassingTheLimit_exception() {
        Exception exception = assertThrows(
                RejectedOperationException.class,
                () -> account.makeAWithdrawal(withdrawalLimit + 1)
        );
        assertEquals(
                ErrorMessages.WITHDRAWAL_MORE_THAN_WITHDRAWAL_LIMIT.label,
                exception.getMessage()
        );
    }

    @Test
    void makeAWithdrawal_overdraftLimit_exception() {
        Exception exception = assertThrows(
                RejectedOperationException.class,
                () -> account.makeAWithdrawal(balance + overdraft + 1)
        );
        assertEquals(
                ErrorMessages.SURPASSING_OVERDRAFT_LIMIT.label + overdraft,
                exception.getMessage()
        );
    }

    @Test
    void seeTheHistory_empty() {
        assertEquals("", account.seeTheHistory());
    }

    @Test
    void seeTheHistory_deposit() throws RejectedOperationException {
        account.makeADeposit(sum);
        assertEquals(
                "Operation{operationType=" + OperationType.DEPOSIT.label +
                        ", date=" + account.getDate() + ", amount=" + account.getAmount() +
                        ", balance=" + account.getBalance() + ", overdraft=" + account.getOverdraft() +
                        ", deposit limit=" + account.getDepositLimit() + ", withdrawal limit=" + account.getWithdrawalLimit() + "}",
                account.seeTheHistory()
        );
    }

    @Test
    void seeTheHistory_withdrawal() throws RejectedOperationException {
        account.makeAWithdrawal(sum);
        assertEquals(
                "Operation{operationType=" + OperationType.WITHDRAWAL.label +
                        ", date=" + account.getDate() + ", amount=" + account.getAmount() +
                        ", balance=" + account.getBalance() + ", overdraft=" + account.getOverdraft() +
                        ", deposit limit=" + account.getDepositLimit() +
                        ", withdrawal limit=" + account.getWithdrawalLimit() + "}",
                account.seeTheHistory()
        );
    }
}
