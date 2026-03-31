package seedu.duke.command;

import seedu.duke.budget.Budget;
import seedu.duke.transactionlist.TransactionList;
import seedu.duke.ui.Ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Filters transactions within an inclusive date range [from, to].
 * Displays matching transactions with their original list indices.
 */
public class FilterCommand extends Command {
    private final LocalDate from;
    private final LocalDate to;

    public FilterCommand(LocalDate from, LocalDate to) {
        assert from != null : "from date should not be null";
        assert to != null : "to date should not be null";
        assert !from.isAfter(to) : "from date should not be after to date";
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TransactionList list, Budget budget, Ui ui) {
        assert list != null : "list should not be null";

        List<String> matched = IntStream.range(0, list.size())
                .filter(i -> {
                    LocalDate date = list.get(i).getDate();
                    return !date.isBefore(from) && !date.isAfter(to);
                })
                .mapToObj(i -> (i + 1) + ". " + list.get(i).toString())
                .toList();
        if (matched.isEmpty()) {
            ui.showMessage("No matching transactions found between " + from + " and " + to + ".");
        } else {
            ui.showMessage("Found " + matched.size() + " transaction(s) between " + from + " and " + to + ":");
            matched.forEach(ui::showMessage);
        }
    }
}