package seedu.duke.command;

import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(TransactionList list, Ui ui) {
        ui.showHelp();
    }
}
