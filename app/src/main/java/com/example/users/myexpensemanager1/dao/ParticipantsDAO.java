package com.example.users.myexpensemanager1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.models.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 3/19/2018.
 */

public class ParticipantsDAO {
    private static ParticipantsDAO participantsDAO;
    public String ADD_PARTIPANTS = "addParticipants";
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
    public void addparticipants(Participant participant) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_NAME, participant.getParticipantname());
        values.put(DAOFactory.COLUMN_ISINDEBT, participant.isInDebt());
        database.insert(DAOFactory.PARTICIPANT_TABLE, null, values);
        Log.d("EXPM", "Money Inserted");
    }

    public void deleteMoney(int id) throws SQLException {
        database.delete(DAOFactory.PARTICIPANT_TABLE, " _id = '" + id + "'", null);
        Log.d("EXPM", "money table item deleted");
    }

    public List<Participant> showParticipants() {
        List<Participant> participantList = new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.PARTICIPANT_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Log.d("EXPM_lendborrow", "data = " + cursor.getString(1));
                Participant item = new Participant(cursor.getString(1),
                        cursor.getInt(2));
                item.setId(cursor.getInt(0));
                participantList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return participantList;
    }
}
