package seedu.duke;

public class MoneyBagProMaxException extends Exception {
    public MoneyBagProMaxException(String message) {
        super("[ERROR!] " + message);
    }
}
