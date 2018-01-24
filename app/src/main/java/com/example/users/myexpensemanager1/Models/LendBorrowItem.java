package com.example.users.myexpensemanager1.Models;

/**
 * Created by USER on 1/24/2018.
 */

public class LendBorrowItem {
    private int Id;
    private String name;
    private long amount;
    private String description;
    private int isReminderSet;
    private long timeStamp;

    public LendBorrowItem(String name, long amount, String description, int isReminderSet, long timeStamp) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isReminderSet = isReminderSet;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReminderSet() {
        return isReminderSet;
    }

    public void setReminderSet(int remianderSet) {
        isReminderSet = remianderSet;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
