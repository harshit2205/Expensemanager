package com.example.users.myexpensemanager1.models;

/**
 * Created by USER on 2/20/2018.
 */

public class MessageEvent {
    String message;
    String participantsName;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getParticipantsName() {
        return participantsName;
    }

    public void setParticipantsName(String participantsName) {
        this.participantsName = participantsName;
    }
}
