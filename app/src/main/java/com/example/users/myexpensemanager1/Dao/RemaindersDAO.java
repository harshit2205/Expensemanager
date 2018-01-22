package com.example.users.myexpensemanager1.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.RemainderItem;

import java.util.ArrayList;
import java.util.List;

public class RemaindersDAO {

    public String ADD_ALARM_DAO = "addAlarmDao";

    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    private static RemaindersDAO remaindersDAO;

    //singleton class static initialiser function.....
    public static RemaindersDAO initialiser(Context context){
        if(remaindersDAO == null){
            remaindersDAO = new RemaindersDAO(context);
        }
        return remaindersDAO;
    }

    //fetching the database helper object.....
    private RemaindersDAO(Context context){
        this.context = context;
        daoFactory = new DAOFactory(context, null);

        try{
            openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
            Log.d("EXPM","sqlite exception found in opening the database");
        }
    }

    private void openDatabase() throws SQLException {
        database = daoFactory.getWritableDatabase();
    }

    public void closeDatabase() throws SQLException {
        daoFactory.close();
    }

    //function to insert values in the alarm table.....
    public void insertRemainder(RemainderItem remainderItem){
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_USERNAME, remainderItem.getUserName());
        values.put(DAOFactory.COLUMN_ITEM, remainderItem.getItemName());
        values.put(DAOFactory.COLUMN_TIMSTAMP, remainderItem.getTimestamp());
        values.put(DAOFactory.COLUMN_DESCRIPTION, remainderItem.getDescription());
        values.put(DAOFactory.COLUMN_UNIQUE_STAMP, remainderItem.getUniqueKey());
        database.insert(DAOFactory.ALARM_TABLE,null,values);
        Log.d("EXPM", "alarm Inserted");
    }

    public List<RemainderItem> showRemainderTuple() {
        List<RemainderItem> alarmList =  new ArrayList<>();
        String query = "SELECT * FROM "+DAOFactory.ALARM_TABLE ;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                RemainderItem item = new RemainderItem(cursor.getString(1)
                        , cursor.getString(2)
                        , Long.parseLong(cursor.getString(3))
                        , cursor.getString(4)
                        , Long.parseLong(cursor.getString(5)));
                Log.d("EXPMdatacheck", "itemname : " + cursor.getString(2));
                alarmList.add(item);
                item.setId(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("EXPM", "alarms list fetched");
        return alarmList;
    }

    public void deleteRemainder(int id) throws SQLException {
        database.delete(DAOFactory.ALARM_TABLE, " _id = '" +id +"'", null);
        Log.d("EXPM","alarm table item deleted");
    }

    public int getRemaindersCount() {
        Log.d("EXPM_Logs","get alarm function started at "+Long.toString(System.currentTimeMillis()));
        String countQuery = "SELECT  * FROM " + DAOFactory.ALARM_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("EXPM_Logs","get alarm function stopped at "+Long.toString(System.currentTimeMillis()));
        return cnt;
    }

}