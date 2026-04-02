package seedu.duke.command;

import seedu.duke.budget.Budget;
import seedu.duke.transaction.Expense;
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
            int templateCount = 0;

            while (!nextDate.isAfter(today)) {
                Transaction t = createTransaction(rt, nextDate);
                if (t == null) {
                    ui.showMessage("[WARN] Could not generate transaction for category: " + rt.getCategory());
                    break;
                }
                list.add(t);
                templateCount++;
                rt.setLastGeneratedDate(nextDate);
                nextDate = rt.getFrequency().next(nextDate);
            }

            if (templateCount > 0) {
                generatedCount += templateCount;
                ui.showMessage("Generated " + templateCount + " transaction(s) from: " + rt);
            }
        }

        if (generatedCount == 0 && !recurringList.isEmpty()) {
            ui.showMessage("No recurring transactions are due.");
        }
    }

    private LocalDate getNextDate(RecurringTransaction rt) {
        if (rt.getLastGeneratedDate() == null) {
            return rt.getStartDate();
        }
        return rt.getFrequency().next(rt.getLastGeneratedDate());
    }

    private Transaction createTransaction(RecurringTransaction rt, LocalDate date) {
        String category = rt.getCategory().toLowerCase();
        if (rt.isIncome()) {
            return new Income(category, rt.getAmount(), rt.getDescription(), date);
        } else if (rt.isExpense()) {
            return new Expense(category, rt.getAmount(), rt.getDescription(), date);
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
