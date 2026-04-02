package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.transaction.Expense;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.Income;
import seedu.duke.transaction.RecurringTransaction;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateRecurringCommandTest {

    private final Budget budget = new Budget();

    @Test
    public void execute_dailyThreeDaysAgo_generatesThreeTransactions() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("food", 10.0, "lunch", Frequency.DAILY, startDate));
        TransactionList list = new TransactionList();

        new GenerateRecurringCommand(recurringList).execute(list, budget, new Ui());

        assertEquals(3, list.size());
        assertInstanceOf(Expense.class, list.get(0));
    }

    @Test
    public void execute_weeklyOneWeekAgo_generatesTwoTransactions() {
        LocalDate startDate = LocalDate.now().minusWeeks(1);
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("food", 50.0, "", Frequency.WEEKLY, startDate));
        TransactionList list = new TransactionList();

        new GenerateRecurringCommand(recurringList).execute(list, budget, new Ui());

        assertEquals(2, list.size());
    }

    @Test
    public void execute_notYetDue_generatesNothing() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("food", 10.0, "", Frequency.DAILY, futureDate));
        TransactionList list = new TransactionList();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            new GenerateRecurringCommand(recurringList).execute(list, budget, new Ui());
        } finally {
            System.setOut(original);
        }

        assertEquals(0, list.size());
        assertTrue(out.toString().contains("No recurring transactions are due"));
    }

    @Test
    public void execute_runTwice_noDuplicates() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("food", 10.0, "", Frequency.DAILY, startDate));
        TransactionList list = new TransactionList();
        GenerateRecurringCommand cmd = new GenerateRecurringCommand(recurringList);

        cmd.execute(list, budget, new Ui());
        assertEquals(2, list.size());

        cmd.execute(list, budget, new Ui());
        assertEquals(2, list.size());
    }

    @Test
    public void execute_incomeCategory_generatesIncomeTransaction() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        RecurringTransactionList recurringList = new RecurringTransactionList();
        recurringList.add(new RecurringTransaction("salary", 500.0, "", Frequency.DAILY, startDate));
        TransactionList list = new TransactionList();

        new GenerateRecurringCommand(recurringList).execute(list, budget, new Ui());

        assertTrue(list.size() >= 1);
        assertInstanceOf(Income.class, list.get(0));
    }

    @Test
    public void execute_emptyRecurringList_generatesNothing() {
        RecurringTransactionList recurringList = new RecurringTransactionList();
        TransactionList list = new TransactionList();

        new GenerateRecurringCommand(recurringList).execute(list, budget, new Ui());

        assertEquals(0, list.size());
    }

    @Test
    public void isMutating_returnsTrue() {
        GenerateRecurringCommand cmd = new GenerateRecurringCommand(new RecurringTransactionList());
        assertTrue(cmd.isMutating());
        assertTrue(cmd.isMutatingRecurring());
    }
}
