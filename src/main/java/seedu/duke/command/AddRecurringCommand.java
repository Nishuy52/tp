package seedu.duke.command;

import seedu.duke.budget.Budget;
import seedu.duke.transaction.Expense;
import seedu.duke.transaction.Frequency;
import seedu.duke.transaction.Income;
import seedu.duke.transaction.RecurringTransaction;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.time.LocalDate;

public class AddRecurringCommand extends Command {

    private final String category;
    private final double amount;
    private final String description;
    private final Frequency frequency;
    private final LocalDate startDate;
    private final RecurringTransactionList recurringList;

    public AddRecurringCommand(String category, double amount, String description,
                               Frequency frequency, LocalDate startDate,
                               RecurringTransactionList recurringList) {
        assert category != null && !category.isBlank() : "Category should not be null or blank";
        assert amount > 0 : "Amount should be positive";
        assert description != null : "Description should not be null";
        assert frequency != null : "Frequency should not be null";
        assert startDate != null : "Start date should not be null";
        assert recurringList != null : "RecurringTransactionList should not be null";
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.recurringList = recurringList;
    }

    @Override
    public void execute(TransactionList list, Budget budget, Ui ui) {
        boolean validIncome = Income.VALID_CATEGORIES.contains(category.toLowerCase());
        boolean validExpense = Expense.VALID_CATEGORIES.contains(category.toLowerCase());

        if (!validIncome && !validExpense) {
            ui.showMessage("Invalid category '" + category + "'."
                    + " Valid expense categories: " + Expense.VALID_CATEGORIES
                    + " Valid income categories: " + Income.VALID_CATEGORIES);
            return;
        }

        RecurringTransaction rt = new RecurringTransaction(category, amount, description, frequency, startDate);
        recurringList.add(rt);
        ui.showMessage("Added recurring: " + rt);
    }

    @Override
    public boolean isMutatingRecurring() {
        return true;
    }
}
