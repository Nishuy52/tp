package seedu.duke.transaction;

import org.junit.jupiter.api.Test;
import seedu.duke.MoneyBagProMaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FrequencyTest {

    @Test
    public void fromString_daily_returnsDaily() throws MoneyBagProMaxException {
        assertEquals(Frequency.DAILY, Frequency.fromString("daily"));
    }

    @Test
    public void fromString_weekly_returnsWeekly() throws MoneyBagProMaxException {
        assertEquals(Frequency.WEEKLY, Frequency.fromString("weekly"));
    }

    @Test
    public void fromString_monthly_returnsMonthly() throws MoneyBagProMaxException {
        assertEquals(Frequency.MONTHLY, Frequency.fromString("monthly"));
    }

    @Test
    public void fromString_caseInsensitive_returnCorrectFrequency() throws MoneyBagProMaxException {
        assertEquals(Frequency.DAILY, Frequency.fromString("DAILY"));
        assertEquals(Frequency.WEEKLY, Frequency.fromString("Weekly"));
        assertEquals(Frequency.MONTHLY, Frequency.fromString("MONTHLY"));
    }

    @Test
    public void fromString_invalid_throwsException() {
        assertThrows(MoneyBagProMaxException.class, () -> Frequency.fromString("biweekly"));
        assertThrows(MoneyBagProMaxException.class, () -> Frequency.fromString("yearly"));
        assertThrows(MoneyBagProMaxException.class, () -> Frequency.fromString(""));
    }
}
