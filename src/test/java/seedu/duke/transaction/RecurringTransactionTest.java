package seedu.duke.transaction;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecurringTransactionTest {

    private final LocalDate startDate = LocalDate.of(2026, 3, 1);

    @Test
    public void constructor_storesAllFields() {
        RecurringTransaction rt = new RecurringTransaction("food", 10.0, "lunch", Frequency.DAILY, startDate);
        assertEquals("food", rt.getCategory());
        assertEquals(10.0, rt.getAmount(), 0.001);
        assertEquals("lunch", rt.getDescription());
        assertEquals(Frequency.DAILY, rt.getFrequency());
        assertEquals(startDate, rt.getStartDate());
        assertNull(rt.getLastGeneratedDate());
    }

    @Test
    public void setLastGeneratedDate_updatesCorrectly() {
        RecurringTransaction rt = new RecurringTransaction("food", 10.0, "lunch", Frequency.DAILY, startDate);
        LocalDate generated = LocalDate.of(2026, 3, 5);
        rt.setLastGeneratedDate(generated);
        assertEquals(generated, rt.getLastGeneratedDate());
    }

    @Test
    public void isExpense_expenseCategory_returnsTrue() {
        RecurringTransaction rt = new RecurringTransaction("food", 10.0, "", Frequency.WEEKLY, startDate);
        assertTrue(rt.isExpense());
        assertFalse(rt.isIncome());
    }

    @Test
    public void isIncome_incomeCategory_returnsTrue() {
        RecurringTransaction rt = new RecurringTransaction("salary", 500.0, "", Frequency.MONTHLY, startDate);
        assertTrue(rt.isIncome());
        assertFalse(rt.isExpense());
    }

    @Test
    public void toString_formatsCorrectly() {
        RecurringTransaction rt = new RecurringTransaction("food", 10.0, "lunch", Frequency.WEEKLY, startDate);
        assertEquals("[Recurring] food \"lunch\" $10.00 (weekly, from 2026-03-01)", rt.toString());
    }

    @Test
    public void toString_noDescription_formatsCorrectly() {
        RecurringTransaction rt = new RecurringTransaction("food", 10.0, "", Frequency.MONTHLY, startDate);
        assertEquals("[Recurring] food $10.00 (monthly, from 2026-03-01)", rt.toString());
    }
}
