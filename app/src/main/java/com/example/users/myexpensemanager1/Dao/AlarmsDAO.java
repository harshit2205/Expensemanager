package com.example.users.myexpensemanager1.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.AlarmItem;

import java.util.ArrayList;
import java.util.List;

public class AlarmsDAO {

    public String ADD_ALARM_DAO = "addAlarmDao";

    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    private static AlarmsDAO alarmsDAO;

    //singleton class static initialiser function.....
    public static AlarmsDAO initialiser(Context context){
        if(alarmsDAO == null){
            alarmsDAO = new AlarmsDAO(context);
        }
        return alarmsDAO;
    }

    //fetching the database helper object.....
    private AlarmsDAO(Context context){
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
    public void insertAlarm(AlarmItem alarmItem){
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_USERNAME,alarmItem.getUserName());
        values.put(DAOFactory.COLUMN_ITEM,alarmItem.getItemName());
        values.put(DAOFactory.COLUMN_TIMSTAMP,alarmItem.getTimestamp());
        values.put(DAOFactory.COLUMN_DESCRIPTION,alarmItem.getDescription());
        values.put(DAOFactory.COLUMN_UNIQUE_STAMP,alarmItem.getUniqueKey());
        database.insert(DAOFactory.ALARM_TABLE,null,values);
        Log.d("EXPM", "alarm Inserted");
    }

    public List<AlarmItem> showAlarmsTuple() {
        List<AlarmItem> alarmList =  new ArrayList<>();
        String query = "SELECT * FROM "+DAOFactory.ALARM_TABLE ;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToPosition(0);
        while(cursor.moveToNext()){
            AlarmItem item = new AlarmItem(cursor.getString(1)
                    ,cursor.getString(2)
                    ,Long.parseLong(cursor.getString(3))
                    ,cursor.getString(4)
                    ,Long.parseLong(cursor.getString(5)));
            Log.d("EXPMdatacheck","itemname : "+cursor.getString(2));
            alarmList.add(item);
        }
        cursor.close();
        Log.d("EXPM", "alarms list fetched");
        return alarmList;
    }

    public void deleteAlarm(long uniquestamp) throws SQLException {
        database.delete(daoFactory.ALARM_TABLE, " uniqueKey = '" +uniquestamp +"'", null);
        Log.d("EXPM","alarm table item deleted");
    }

    public int getAlarmsCount() {
        String countQuery = "SELECT  * FROM " + DAOFactory.ALARM_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}