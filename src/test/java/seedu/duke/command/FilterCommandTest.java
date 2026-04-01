package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.transaction.Expense;
import seedu.duke.transaction.Income;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilterCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_rangeCoversAllTransactions_displaysAll() {
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        list.add(new Expense("food", 10.50, "lunch", LocalDate.parse("2026-01-14")));
        list.add(new Income("salary", 50.00, "pocket money", LocalDate.parse("2026-03-15")));
        list.add(new Expense("transport", 5.00, "bus to lunch", LocalDate.parse("2026-10-16")));

        FilterCommand command = new FilterCommand(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );
        command.execute(list, budget, ui);

        assertTrue(outContent.toString().contains("3 transaction(s)"));
    }

    @Test
    void execute_exactDateBoundaryMatch_displaysSingleTransaction() {
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        list.add(new Expense("food", 10.50, "lunch", LocalDate.parse("2026-01-14")));
        list.add(new Income("salary", 50.00, "pocket money", LocalDate.parse("2026-03-15")));
        list.add(new Expense("transport", 5.00, "bus to lunch", LocalDate.parse("2026-10-16")));

        FilterCommand command = new FilterCommand(
                LocalDate.of(2026, 3, 15),
                LocalDate.of(2026, 3, 15)
        );
        command.execute(list, budget, ui);

        assertTrue(outContent.toString().contains("1 transaction(s)"));
        assertTrue(outContent.toString().contains("pocket money"));
    }

    @Test
    void execute_rangeWithNoTransactions_displaysNoMatchMessage() {
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        list.add(new Expense("food", 10.50, "lunch", LocalDate.parse("2026-01-14")));
        list.add(new Income("salary", 50.00, "pocket money", LocalDate.parse("2026-03-15")));
        list.add(new Expense("transport", 5.00, "bus to lunch", LocalDate.parse("2026-10-16")));

        FilterCommand command = new FilterCommand(
                LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 28)
        );
        command.execute(list, budget, ui);

        assertTrue(outContent.toString().contains("No matching transactions found"));
        assertFalse(outContent.toString().contains("transaction(s)"));
    }

    @Test
    void execute_futureRangeWithNoTransactions_displaysNoMatchMessage() {
        TransactionList list = new TransactionList();
        Ui ui = new Ui();
        Budget budget = new Budget();

        list.add(new Expense("food", 10.50, "lunch", LocalDate.parse("2026-01-14")));
        list.add(new Income("salary", 50.00, "pocket money", LocalDate.parse("2026-03-15")));
        list.add(new Expense("transport", 5.00, "bus to lunch", LocalDate.parse("2026-10-16")));

        FilterCommand command = new FilterCommand(
                LocalDate.of(2099, 1, 1),
                LocalDate.of(2099, 12, 31)
        );
        command.execute(list, budget, ui);

        assertTrue(outContent.toString().contains("No matching transactions found"));
        assertFalse(outContent.toString().contains("transaction(s)"));
    }
}
