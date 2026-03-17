package seedu.duke.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TransactionTest {

    // ── Expense ───────────────────────────────────────────────────────────────

    @Test
    public void getAmount_expenseWithPositiveValue_returnsCorrectAmount() {
        Expense expense = new Expense("food", 10.50, "lunch", LocalDate.now());
        assertEquals(10.50, expense.getAmount(), 0.001);
    }

    @Test
    public void getType_expense_returnsExpenseString() {
        Expense expense = new Expense("food", 5.00, "snack", LocalDate.now());
        assertEquals("expense", expense.getType());
    }

    @Test
    public void getDescription_expenseWithDescription_returnsDescription() {
        Expense expense = new Expense("transport", 2.00, "bus fare", LocalDate.now());
        assertEquals("bus fare", expense.getDescription());
    }

    @Test
    public void getCategory_expenseWithCategory_returnsCorrectCategory() {
        Expense expense = new Expense("transport", 2.00, "bus fare", LocalDate.now());
        assertEquals("transport", expense.getCategory());
    }

    @Test
    public void getDate_expenseWithDate_returnsCorrectDate() {
        LocalDate date = LocalDate.of(2025, 3, 1);
        Expense expense = new Expense("food", 5.00, "lunch", date);
        assertEquals(date, expense.getDate());
    }

    @Test
    public void toString_expenseWithDescription_exactFormat() {
        Expense expense = new Expense("food", 8.00, "dinner", LocalDate.of(2025, 3, 1));
        assertEquals("[Expense] food \"dinner\" $8.00 (2025-03-01)", expense.toString());
    }

    @Test
    public void toString_expenseWithoutDescription_omitsQuotedDescription() {
        Expense expense = new Expense("misc", 3.00, "", LocalDate.of(2025, 3, 1));
        assertEquals("[Expense] misc $3.00 (2025-03-01)", expense.toString());
    }

    // ── Income ────────────────────────────────────────────────────────────────

    @Test
    public void getAmount_incomeWithPositiveValue_returnsCorrectAmount() {
        Income income = new Income("income", 500.00, "salary", LocalDate.now());
        assertEquals(500.00, income.getAmount(), 0.001);
    }

    @Test
    public void getType_income_returnsIncomeString() {
        Income income = new Income("income", 100.00, "freelance", LocalDate.now());
        assertEquals("income", income.getType());
    }

    @Test
    public void getDescription_incomeWithDescription_returnsDescription() {
        Income income = new Income("income", 100.00, "freelance", LocalDate.now());
        assertEquals("freelance", income.getDescription());
    }

    @Test
    public void getCategory_incomeWithCategory_returnsCorrectCategory() {
        Income income = new Income("salary", 5000.00, "monthly", LocalDate.now());
        assertEquals("salary", income.getCategory());
    }

    @Test
    public void toString_incomeWithDescription_exactFormat() {
        Income income = new Income("income", 1000.00, "monthly", LocalDate.of(2025, 3, 1));
        assertEquals("[Income] income \"monthly\" $1000.00 (2025-03-01)", income.toString());
    }

    @Test
    public void toString_incomeWithoutDescription_omitsQuotedDescription() {
        Income income = new Income("income", 500.00, "", LocalDate.of(2025, 3, 1));
        assertEquals("[Income] income $500.00 (2025-03-01)", income.toString());
    }
}
