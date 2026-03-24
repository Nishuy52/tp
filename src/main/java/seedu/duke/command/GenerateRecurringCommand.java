package seedu.duke.command;

import seedu.duke.budget.Budget;
import seedu.duke.transaction.Expense;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.Income;
import seedu.duke.transaction.RecurringTransaction;
import seedu.duke.transaction.Transaction;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.time.LocalDate;

public class GenerateRecurringCommand extends Command {

    private final RecurringTransactionList recurringList;

    public GenerateRecurringCommand(RecurringTransactionList recurringList) {
        assert recurringList != null : "RecurringTransactionList should not be null";
        this.recurringList = recurringList;
    }

    @Override
    public void execute(TransactionList list, Budget budget, Ui ui) {
        LocalDate today = LocalDate.now();
        int generatedCount = 0;

        for (int i = 0; i < recurringList.size(); i++) {
            RecurringTransaction rt = recurringList.get(i);
            LocalDate nextDate = getNextDate(rt);

            while (!nextDate.isAfter(today)) {
                Transaction t = createTransaction(rt, nextDate);
                if (t != null) {
                    list.add(t);
                    ui.showMessage("Generated: " + t);
                    generatedCount++;
                }
                rt.setLastGeneratedDate(nextDate);
                nextDate = advance(nextDate, rt.getFrequency());
            }
        }

        if (generatedCount == 0) {
            ui.showMessage("No recurring transactions are due.");
        }
    }

    private LocalDate getNextDate(RecurringTransaction rt) {
        if (rt.getLastGeneratedDate() == null) {
            return rt.getStartDate();
        }
        return advance(rt.getLastGeneratedDate(), rt.getFrequency());
    }

    private LocalDate advance(LocalDate date, Frequency frequency) {
        switch (frequency) {
        case DAILY:
            return date.plusDays(1);
        case WEEKLY:
            return date.plusWeeks(1);
        case MONTHLY:
            return date.plusMonths(1);
        default:
            return date.plusMonths(1);
        }
    }

    private Transaction createTransaction(RecurringTransaction rt, LocalDate date) {
        if (rt.isIncome()) {
            return new Income(rt.getCategory(), rt.getAmount(), rt.getDescription(), date);
        } else if (rt.isExpense()) {
            return new Expense(rt.getCategory(), rt.getAmount(), rt.getDescription(), date);
        }
        return null;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public boolean isMutatingRecurring() {
        return true;
    }
}
