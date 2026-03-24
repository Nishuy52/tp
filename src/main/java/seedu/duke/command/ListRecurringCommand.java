package seedu.duke.command;

import seedu.duke.budget.Budget;
import seedu.duke.transactionlist.RecurringTransactionList;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

public class ListRecurringCommand extends Command {

    private final RecurringTransactionList recurringList;

    public ListRecurringCommand(RecurringTransactionList recurringList) {
        assert recurringList != null : "RecurringTransactionList should not be null";
        this.recurringList = recurringList;
    }

    @Override
    public void execute(TransactionList list, Budget budget, Ui ui) {
        if (recurringList.isEmpty()) {
            ui.showMessage("No recurring transactions found.");
            return;
        }
        System.out.println(Ui.DIVIDER);
        for (int i = 0; i < recurringList.size(); i++) {
            ui.showMessage((i + 1) + ". " + recurringList.get(i));
        }
        System.out.println(Ui.DIVIDER);
    }
}
