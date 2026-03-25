package seedu.duke.transactionlist;

import org.junit.jupiter.api.Test;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.RecurringTransaction;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecurringTransactionListTest {

    private RecurringTransaction makeRt(String category) {
        return new RecurringTransaction(category, 10.0, "", Frequency.MONTHLY, LocalDate.now());
    }

    @Test
    public void addAndSize_worksCorrectly() {
        RecurringTransactionList list = new RecurringTransactionList();
        assertTrue(list.isEmpty());
        list.add(makeRt("food"));
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    public void get_returnsCorrectElement() {
        RecurringTransactionList list = new RecurringTransactionList();
        RecurringTransaction rt = makeRt("food");
        list.add(rt);
        assertSame(rt, list.get(0));
    }

    @Test
    public void remove_removesCorrectElement() {
        RecurringTransactionList list = new RecurringTransactionList();
        RecurringTransaction rt1 = makeRt("food");
        RecurringTransaction rt2 = makeRt("salary");
        list.add(rt1);
        list.add(rt2);
        RecurringTransaction removed = list.remove(0);
        assertSame(rt1, removed);
        assertEquals(1, list.size());
        assertSame(rt2, list.get(0));
    }

    @Test
    public void get_outOfBounds_throwsAssertionError() {
        RecurringTransactionList list = new RecurringTransactionList();
        list.add(makeRt("food"));
        org.junit.jupiter.api.Assertions.assertThrows(AssertionError.class, () -> list.get(5));
    }

    @Test
    public void remove_outOfBounds_throwsAssertionError() {
        RecurringTransactionList list = new RecurringTransactionList();
        org.junit.jupiter.api.Assertions.assertThrows(AssertionError.class, () -> list.remove(0));
    }
}
