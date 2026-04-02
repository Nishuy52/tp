package seedu.duke.transaction;

import java.time.LocalDate;

/**
 * Represents a recurring transaction template.
 * Not a subclass of Transaction — it is a template that generates transactions.
 */
public class RecurringTransaction {

    private final String category;
    private final double amount;
    private final String description;
    private final Frequency frequency;
    private final LocalDate startDate;
    private final String transactionType;
    private LocalDate lastGeneratedDate;

    public RecurringTransaction(String category, double amount, String description,
                                Frequency frequency, LocalDate startDate) {
        assert category != null && !category.isBlank() : "Category should not be null or blank";
        assert amount > 0 : "Amount should be positive";
        assert description != null : "Description should not be null";
        assert frequency != null : "Frequency should not be null";
        assert startDate != null : "Start date should not be null";
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.transactionType = Income.VALID_CATEGORIES.contains(category.toLowerCase()) ? "income" : "expense";
        this.lastGeneratedDate = null;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getLastGeneratedDate() {
        return lastGeneratedDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setLastGeneratedDate(LocalDate date) {
        assert date != null : "Last generated date should not be null";
        assert !date.isBefore(startDate) : "Last generated date should not be before start date";
        this.lastGeneratedDate = date;
    }

    public boolean isIncome() {
        return "income".equals(transactionType);
    }

    public boolean isExpense() {
        return "expense".equals(transactionType);
    }

    @Override
    public String toString() {
        String desc = description.isEmpty() ? "" : " \"" + description + "\"";
        return String.format("[Recurring] %s%s $%.2f (%s, from %s)",
                category, desc, amount,
                frequency.name().toLowerCase(), startDate);
    }
}
