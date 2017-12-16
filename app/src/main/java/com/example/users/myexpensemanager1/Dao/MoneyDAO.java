package com.example.users.myexpensemanager1.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.MoneyItem;

import java.util.ArrayList;
import java.util.List;

public class MoneyDAO {

    public String ADD_MONEY_DAO = "addMoneyDAO";

    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    private static MoneyDAO moneyDAO;

    //singleton class static initialiser function.....
    public static MoneyDAO initialiser(Context context){
        if(moneyDAO == null){
            moneyDAO = new MoneyDAO(context);
        }
        return moneyDAO;
    }

    //fetching the database helper object.....
    private MoneyDAO(Context context) {
        this.context = context;
        daoFactory = new DAOFactory(context, null);

        try{
            openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void openDatabase() throws SQLException {
        database = daoFactory.getWritableDatabase();
    }

    public void closeDatabase() throws SQLException {
        daoFactory.close();
    }

    //function to insert values in the money table.....
    public void insertMoney(MoneyItem moneyItem){
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_USERNAME,moneyItem.getUserName());
        values.put(DAOFactory.COLUMN_AMOUNT,moneyItem.getAmount());
        values.put(DAOFactory.COLUMN_DESCRIPTION,moneyItem.getDescription());
        values.put(DAOFactory.COLUMN_TIMSTAMP,moneyItem.getTimestamp());
        database.insert(DAOFactory.MONEY_TABLE,null,values);
        Log.d("EXPM", "Money Inserted");
    }

    public void deleteMoney(long timestamp) throws SQLException {
        database.delete(daoFactory.MONEY_TABLE, " currentstamp = '" +timestamp +"'", null);
        Log.d("EXPM","money table item deleted");
    }

    public List<MoneyItem> showMoneyTuple() {
        Log.d("EXPM_Logs","show money tuple function called at "+Long.toString(System.currentTimeMillis()));
        List<MoneyItem> moneyList =  new ArrayList<>();
        String query = "SELECT * FROM "+DAOFactory.MONEY_TABLE ;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Log.d("temp_tags","cursor count is "+cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Log.d("temp_tags","data = "+cursor.getString(2));
                MoneyItem item = new MoneyItem(cursor.getString(1)
                        ,Long.parseLong(cursor.getString(2))
                        ,Long.parseLong(cursor.getString(4))
                        ,cursor.getString(3));
                moneyList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("EXPM_Logs","show money tuple function stopped at "+Long.toString(System.currentTimeMillis()));
        Log.d("EXPM", "money list fetched");
        return moneyList;
    }

    // gives the count of the number of elements.......
    public int getMoneyCount() {
        Log.d("EXPM_Logs","get money count function called at "+Long.toString(System.currentTimeMillis()));
        String countQuery = "SELECT  * FROM " + DAOFactory.MONEY_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("EXPM_Logs","get money count function stopped at "+Long.toString(System.currentTimeMillis()));
        return cnt;
    }

}