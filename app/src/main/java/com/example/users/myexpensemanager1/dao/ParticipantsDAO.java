package com.example.users.myexpensemanager1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.models.ParticipantItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 3/19/2018.
 */

public class ParticipantsDAO {
    private static ParticipantsDAO participantsDAO;
    public String ADD_PARTIPANTS = "addParticipants";
    public long totalLend, totalBorrow;
    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    //fetching the database helper object.....
    private ParticipantsDAO(Context context) {
        this.context = context;
        daoFactory = new DAOFactory(context, null);

        try {
            openDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //singleton class static initialiser function.....
    public static ParticipantsDAO initialiser(Context context) {
        if (participantsDAO == null) {
            participantsDAO = new ParticipantsDAO(context);
        }
        return participantsDAO;
    }

    private void openDatabase() throws SQLException {
        database = daoFactory.getWritableDatabase();
    }

    public void closeDatabase() throws SQLException {
        daoFactory.close();
    }

    //function to insert values in the money table.....
    public void addparticipants(ParticipantItem participantItem) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_NAME, participantItem.getParticipantname());
        values.put(DAOFactory.COLUMN_DUES, participantItem.getDues());
        values.put(DAOFactory.COLUMN_ISINDEBT, participantItem.isInDebt());
        database.insert(DAOFactory.PARTICIPANT_TABLE, null, values);
        Log.d("EXPM", "Money Inserted");
    }

    public void deleteMoney(int id) throws SQLException {
        database.delete(DAOFactory.PARTICIPANT_TABLE, " _id = '" + id + "'", null);
        Log.d("EXPM", "money table item deleted");
    }

    public ParticipantItem getParticipant(String participantName) {
        String query = "SELECT * FROM " + DAOFactory.PARTICIPANT_TABLE + " WHERE "
                + DAOFactory.COLUMN_NAME + " = '" + participantName + "' ;";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // get the data into array, or class variable
            Log.d("EXPM_lendborrow", "data = " + cursor.getString(1));
            ParticipantItem item = new ParticipantItem(cursor.getString(1),
                    Long.parseLong(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)));
            item.setId(cursor.getInt(0));
            cursor.close();
            return item;
        }
        return null;
    }

    public List<ParticipantItem> showParticipants() {
        List<ParticipantItem> participantItemList = new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.PARTICIPANT_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        totalLend = 0;
        totalBorrow = 0;
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Log.d("EXPM_lendborrow", "data = " + cursor.getString(1));
                ParticipantItem item = new ParticipantItem(cursor.getString(1),
                        Long.parseLong(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));
                item.setId(cursor.getInt(0));
                participantItemList.add(item);

                int type = Integer.parseInt(cursor.getString(3));
                if (type == 1) {
                    totalBorrow = totalBorrow + Long.parseLong(cursor.getString(2));
                } else if (type == 2) {
                    totalLend = totalLend + Long.parseLong(cursor.getString(2));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return participantItemList;
    }

    public boolean ifParticipantExists(String participantName) {
        String query = "SELECT * FROM " + DAOFactory.PARTICIPANT_TABLE + " WHERE '" + DAOFactory.COLUMN_NAME +
                "' = '" + participantName + "' ;";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void updateParticipant(ParticipantItem participantItem, String previousName) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_NAME, participantItem.getParticipantname());
        values.put(DAOFactory.COLUMN_DUES, participantItem.getDues());
        values.put(DAOFactory.COLUMN_ISINDEBT, participantItem.isInDebt());
        database.update(DAOFactory.PARTICIPANT_TABLE, values,
                DAOFactory.COLUMN_NAME + " = '" + previousName + "'",
                null);
        Log.d("EXPM", "update done");
    }
}
