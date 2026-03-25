package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.transaction.Frequency;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddRecurringCommandTest {

    private final LocalDate date = LocalDate.of(2026, 3, 1);

    @Test
    public void execute_validExpenseCategory_addsToRecurringList() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        AddRecurringCommand cmd = new AddRecurringCommand(
                "food", 10.0, "lunch", Frequency.WEEKLY, date, recurringList);
        cmd.execute(list, budget, ui);

        assertEquals(1, recurringList.size());
        assertEquals(0, list.size(), "Should not add to main transaction list");
        assertEquals("food", recurringList.get(0).getCategory());
    }

    @Test
    public void execute_validIncomeCategory_addsToRecurringList() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        AddRecurringCommand cmd = new AddRecurringCommand(
                "salary", 500.0, "monthly", Frequency.MONTHLY, date, recurringList);
        cmd.execute(list, budget, ui);

        assertEquals(1, recurringList.size());
        assertEquals(0, list.size());
    }

    @Test
    public void execute_invalidCategory_doesNotAdd() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        TransactionList list = new TransactionList();
        Budget budget = new Budget();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui ui = new Ui();

        AddRecurringCommand cmd = new AddRecurringCommand(
                "invalid", 10.0, "", Frequency.DAILY, date, recurringList);
        cmd.execute(list, budget, ui);

        assertEquals(0, recurringList.size());
        assertTrue(outContent.toString().contains("Invalid category"));

        System.setOut(System.out);
    }

    @Test
    public void isMutatingRecurring_returnsTrue() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        AddRecurringCommand cmd = new AddRecurringCommand(
                "food", 10.0, "", Frequency.DAILY, date, recurringList);
        assertTrue(cmd.isMutatingRecurring());
    }
}
