package seedu.duke.transactionlist;

import seedu.duke.transaction.RecurringTransaction;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the list of recurring transaction templates.
 */
public class RecurringTransactionList {

    private static final Logger logger = Logger.getLogger(RecurringTransactionList.class.getName());
    static {
        logger.setLevel(Level.WARNING);
    }
    private final ArrayList<RecurringTransaction> recurringTransactions = new ArrayList<>();

    public void add(RecurringTransaction rt) {
        assert rt != null : "RecurringTransaction should not be null";
        logger.info("Adding recurring transaction: " + rt);
        recurringTransactions.add(rt);
    }

    public int size() {
        return recurringTransactions.size();
    }

    public boolean isEmpty() {
        return recurringTransactions.isEmpty();
    }

    public RecurringTransaction get(int i) {
        assert i >= 0 && i < recurringTransactions.size() : "Index is out of bounds";
        return recurringTransactions.get(i);
    }

    public RecurringTransaction remove(int i) {
        assert i >= 0 && i < recurringTransactions.size() : "Index is out of bounds";
        logger.info("Removing recurring transaction at index: " + i);
        return recurringTransactions.remove(i);
    }

    public void clear() {
        logger.info("Clearing all recurring transactions");
        recurringTransactions.clear();
    }
}
