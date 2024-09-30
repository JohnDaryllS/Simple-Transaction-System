import java.util.Date;

public class Transaction {
    private Date timestamp;
    private String userID;
    private String transactionType;
    private double amount;
    private String recipientUserID;

    public Transaction(String userID, String transactionType, double amount) {
        this.timestamp = new Date();
        this.userID = userID;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public Transaction(String userID, String recipientUserID, String transactionType, double amount) {
        this(userID, transactionType, amount);
        this.recipientUserID = recipientUserID;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Timestamp: ").append(timestamp);
        builder.append(" | UserID: ").append(userID);
        builder.append(" | Transaction Type: ").append(transactionType);
        builder.append(" | Amount: ₱ ").append(amount);
        if (recipientUserID != null) {
            builder.append(" | Recipient UserID: ").append(recipientUserID);
        }
        return builder.toString();
    }
}
