package bank.account.kata.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RejectedOperationExceptionTest {
    @Test
    public void when_getMessage_expect_message() {
        String message = "message";
        RejectedOperationException exception = new RejectedOperationException(message);
        assertEquals(message, exception.getMessage());
    }
}
