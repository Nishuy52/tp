package seedu.duke.storage;

import seedu.duke.MoneyBagProMaxException;
import seedu.duke.transaction.Expense;
import seedu.duke.transaction.Income;
import seedu.duke.transaction.Transaction;
import seedu.duke.transactionlist.TransactionList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


public class Storage {

    private static final String DATA_DIR  = "data";
    private static final String DATA_FILE = "data/transactions.txt";

    private static final String TXN_PREFIX = "[TXN]";
    private static final String KV_SEP     = "=";


    /**
     * Reads persisted transactions from disk into the provided TransactionList.
     * Call once in MoneyBagProMax.main() before the input loop.
     */
    public void load(TransactionList list) throws MoneyBagProMaxException {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Path p = Paths.get(DATA_FILE);
            if (!Files.exists(p)) {
                Files.createFile(p); 
                return; 
            }

            // TransactionList has no clear() — drain it manually
            while (!list.isEmpty()) list.remove(0);
            for (String line : Files.readAllLines(p)) {
                if (!line.startsWith(TXN_PREFIX)) continue;
                Map<String, String> f = parseLine(line);
                if (f == null) continue;
                String type        = f.get("type");
                String category    = f.get("category");
                double amount      = Double.parseDouble(f.get("amount"));
                String description = f.getOrDefault("description", "");
                LocalDate date     = LocalDate.parse(f.get("date"));
                Transaction t = switch (type) {
                    case "income"  -> new Income(category, amount, description, date);
                    case "expense" -> new Expense(category, amount, description, date);
                    default        -> null; // unknown type — skip
                };
                if (t != null) list.add(t);
            }
        } catch (IOException e) {
            throw new MoneyBagProMaxException("Failed to load data: " + e.getMessage());
        }
    }

    private Map<String, String> parseLine(String line) {
        try {
            Map<String, String> fields = new LinkedHashMap<>();
            String[] parts = line.split("\\s*\\|\\s*");
            for (int i = 1; i < parts.length; i++) {
                int eq = parts[i].indexOf(KV_SEP);
                if (eq < 0) continue;
                fields.put(parts[i].substring(0, eq).trim(),
                           parts[i].substring(eq + 1).trim());
            }
            return fields;
        } catch (Exception e) {
            return null;
        }
}
