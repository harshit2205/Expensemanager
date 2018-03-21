package com.example.users.myexpensemanager1.models;

/**
 * Created by USER on 3/19/2018.
 */

public class ParticipantItem {
    public static int IS_IN_DEBT = 1;
    public static int NO_DEBT = 0;
    public static int HAS_LENDED = 2;
    private String participantname;
    private int inDebt;
    private long dues;
    private int Id;

    public ParticipantItem(String participantname, long dues, int inDebt) {
        this.participantname = participantname;
        this.dues = dues;
        this.inDebt = inDebt;
    }

    public String getParticipantname() {
        return participantname;
    }

    public void setParticipantname(String participantname) {
        this.participantname = participantname;
    }

    public long getDues() {
        return dues;
    }

    public void setDues(long dues) {
        this.dues = dues;
    }

    public int isInDebt() {
        return inDebt;
    }

    public void setInDebt(int inDebt) {
        this.inDebt = inDebt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
