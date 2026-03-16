package seedu.duke.transactionlist;

import seedu.duke.transaction.Transaction;

import java.util.ArrayList;

//todo: delete
public class TransactionList {
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    public void add(Transaction t) {
        assert t != null : "Transaction should not be null";
        transactions.add(t);
    }

    //returns num of transactions
    public int size() {
        return transactions.size();
    }

    //can be used for list command later to acquire a particular transaction
    public Transaction get(int i) {
        assert i >= 0 && i < transactions.size() : "Index is out of bounds";
        return transactions.get(i);
    }

    //removes a transaction from list
    public Transaction remove(int i) {
        assert i >= 0 && i < transactions.size() : "Index is out of bounds";
        return transactions.remove(i);
    }
}
