package com.example.users.myexpensemanager1.models;

public class RemainderItem {
    private int Id;
    private String userName;
    private String ItemName;
    private long timestamp;
    private String description;
    private long uniqueKey;

    public RemainderItem(String userName, String itemName, long timestamp, String description, long uniqueKey) {
        this.userName = userName;
        ItemName = itemName;
        this.timestamp = timestamp;
        this.description = description;
        this.uniqueKey = uniqueKey;
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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
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

    public long getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(long uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}