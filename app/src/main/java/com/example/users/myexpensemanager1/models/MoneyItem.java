package com.example.users.myexpensemanager1.models;

/**
 * Created by USER on 5/15/2017.
 */
public class MoneyItem {
    private int Id;
    private String userName;
    private long amount;
    private long timestamp;
    private String description;

    public MoneyItem( String userName, long amount, long timestamp, String description) {
        this.userName = userName;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
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
}
