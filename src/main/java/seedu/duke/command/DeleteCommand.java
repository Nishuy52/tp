package seedu.duke.command;

import seedu.duke.MoneyBagProMaxException;
import seedu.duke.transaction.Transaction;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

public class DeleteCommand extends Command {

    private final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void execute(TransactionList list, Ui ui) throws MoneyBagProMaxException {
        assert list != null : "Transaction list should not be null";

        int listIndex = targetIndex - 1;

        if (listIndex < 0 || listIndex >= list.size()) {
            throw new MoneyBagProMaxException("Invalid transaction index. " +
                    "Please provide a number between 1 and " + list.size() + ".");
        }
        Transaction removed = list.remove(listIndex);
        ui.showMessage("Deleted: " + removed);
    }
}
