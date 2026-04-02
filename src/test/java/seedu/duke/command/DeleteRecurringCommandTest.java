package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.MoneyBagProMaxException;
import seedu.duke.budget.Budget;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.RecurringTransaction;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteRecurringCommandTest {

    private RecurringTransaction makeRt(String category) {
        return new RecurringTransaction(category, 10.0, "", Frequency.MONTHLY, LocalDate.now());
    }

    @Test
    public void execute_validIndex_removesEntry() throws MoneyBagProMaxException {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(makeRt("food"));
        recurringList.add(makeRt("salary"));

        DeleteRecurringCommand cmd = new DeleteRecurringCommand(1, recurringList);
        cmd.execute(new TransactionList(), new Budget(), new Ui());

        assertEquals(1, recurringList.size());
        assertEquals("salary", recurringList.get(0).getCategory());
    }

    @Test
    public void execute_indexTooLarge_throwsException() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(makeRt("food"));

        DeleteRecurringCommand cmd = new DeleteRecurringCommand(5, recurringList);
        assertThrows(MoneyBagProMaxException.class,
                () -> cmd.execute(new TransactionList(), new Budget(), new Ui()));
    }

    @Test
    public void execute_indexZero_throwsException() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(makeRt("food"));

        DeleteRecurringCommand cmd = new DeleteRecurringCommand(0, recurringList);
        assertThrows(MoneyBagProMaxException.class,
                () -> cmd.execute(new TransactionList(), new Budget(), new Ui()));
    }

    @Test
    public void isMutatingRecurring_returnsTrue() {
        DeleteRecurringCommand cmd = new DeleteRecurringCommand(1, new RecurringTransactionList());
        assertTrue(cmd.isMutatingRecurring());
    }
}
