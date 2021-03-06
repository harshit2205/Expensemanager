package com.example.users.myexpensemanager1.models;

/**
 * Created by USER on 5/15/2017.
 */
public class TransactionItem {
    private int Id;
    private String userName;
    private String item_name;
    private long amount;
    private long timestamp;
    private String description;
    private String filePath;
    private String transactionType;

    public TransactionItem(String userName,
                           String item_name,
                           long amount,
                           long timestamp,
                           String description,
                           String filePath,
                           String transactionType) {
        this.userName = userName;
        this.item_name = item_name;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.filePath = filePath;
        this.transactionType = transactionType;
     }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
