package com.example.users.myexpensemanager1.Models;

/**
 * Created by USER on 5/15/2017.
 */
public class MoneyItem {
    private String userName;
    private long amount;
    private long timestamp;
    private String description;


    public MoneyItem(String userName, long amount, long timestamp, String description) {
        this.userName = userName;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAmount() {

        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }
}
