package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.RecurringTransaction;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListRecurringCommandTest {

    private final LocalDate date = LocalDate.of(2026, 3, 1);

    @Test
    public void execute_emptyList_showsNoTemplatesMessage() {
        RecurringTransactionList recurringList = new RecurringTransactionList();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            new ListRecurringCommand(recurringList).execute(new TransactionList(), new Budget(), new Ui());
        } finally {
            System.setOut(original);
        }

        assertTrue(out.toString().contains("No recurring transactions found"));
    }

    @Test
    public void execute_oneEntry_showsEntry() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("food", 10.0, "lunch", Frequency.WEEKLY, date));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            new ListRecurringCommand(recurringList).execute(new TransactionList(), new Budget(), new Ui());
        } finally {
            System.setOut(original);
        }

        assertTrue(out.toString().contains("food"));
        assertTrue(out.toString().contains("1."));
    }
}
