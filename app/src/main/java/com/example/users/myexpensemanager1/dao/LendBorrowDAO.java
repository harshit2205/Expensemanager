package com.example.users.myexpensemanager1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.models.LendBorrowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 1/24/2018.
 */

public class LendBorrowDAO {
    public static int REMINDER_SET = 1;
    public static int REMINDER_NOT_SET = 2;
    private static LendBorrowDAO lendBorrowDAO;
    //initialising variables.....
    private SQLiteDatabase database;
    private DAOFactory daoFactory;
    private Context context;
    private String LEND_BORROW_DAO = "lendBorrowDAO";
    private long oldAmount = 0;

    private LendBorrowDAO(Context context) {
        this.context = context;
        daoFactory = new DAOFactory(context, null);

        try{
            openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static LendBorrowDAO initialiser(Context context){
        if(lendBorrowDAO == null){
            lendBorrowDAO = new LendBorrowDAO(context);
        }
        return lendBorrowDAO;
    }

    private void openDatabase() throws SQLException {
        database = daoFactory.getWritableDatabase();
    }

    public void closeDatabase() throws SQLException {
        daoFactory.close();
    }

    public void insertLendBorrowItem(LendBorrowItem lendBorrowItem){
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_NAME,lendBorrowItem.getName());
        values.put(DAOFactory.COLUMN_AMOUNT,lendBorrowItem.getAmount());
        values.put(DAOFactory.COLUMN_DESCRIPTION,lendBorrowItem.getDescription());
        values.put(DAOFactory.COLUMN_REMAINDER_SET,lendBorrowItem.getReminderSet());
        values.put(DAOFactory.COLUMN_TIMSTAMP,lendBorrowItem.getTimeStamp());
        database.insert(DAOFactory.LEND_BORROW_TABLE,null,values);
        Log.d("EXPM_Participant", "lend/borrow item inserted in " + DAOFactory.LEND_BORROW_TABLE);
    }

    public void deleteLendBorrowItem(int id){
        database.delete(DAOFactory.LEND_BORROW_TABLE, " _id = '" +id +"'", null);
        Log.d("EXPM","lend/borrow table item deleted");
    }

    public List<LendBorrowItem> showLendBorrowTupple(){
        List<LendBorrowItem> lendBorrowItemList =  new ArrayList<>();
        String query = "SELECT * FROM "+DAOFactory.LEND_BORROW_TABLE ;
        Cursor cursor = database.rawQuery(query, null);
        long totalLends = 0, totalBorrows = 0;
        if(cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                LendBorrowItem item = new LendBorrowItem(cursor.getString(1)
                        , cursor.getLong(2)
                        , cursor.getString(3)
                        , cursor.getInt(4)
                        , cursor.getLong(5));
                lendBorrowItemList.add(item);
                item.setId(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lendBorrowItemList;
    }

    public int getLendBorrowCount(){
        String countQuery = "SELECT  * FROM " + DAOFactory.LEND_BORROW_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public boolean searchOldItem(String name){
        String query = "SELECT  * FROM " + DAOFactory.LEND_BORROW_TABLE + " WHERE " + DAOFactory.COLUMN_NAME+ " = '"+name+"';";
        Cursor cursor = database.rawQuery(query, null);
        int cnt = cursor.getCount();
        cursor.moveToFirst();
        if (cnt != 0) {
            oldAmount = cursor.getLong(2);
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void updateitem(LendBorrowItem lendBorrowItem){
        ContentValues values = new ContentValues();

        values.put(DAOFactory.COLUMN_NAME,lendBorrowItem.getName());
        values.put(DAOFactory.COLUMN_AMOUNT,(lendBorrowItem.getAmount()+ oldAmount));
        values.put(DAOFactory.COLUMN_DESCRIPTION,lendBorrowItem.getDescription());
        values.put(DAOFactory.COLUMN_REMAINDER_SET,lendBorrowItem.getReminderSet());
        values.put(DAOFactory.COLUMN_TIMSTAMP,lendBorrowItem.getTimeStamp());
        database.update(DAOFactory.LEND_BORROW_TABLE, values,
                DAOFactory.COLUMN_NAME +" = '"+lendBorrowItem.getName()+"'",
                null );
    }

    public List<LendBorrowItem> showTupplesByName(String participantName) {
        List<LendBorrowItem> lendBorrowItemList = new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.LEND_BORROW_TABLE +
                " WHERE " + DAOFactory.COLUMN_NAME + " = '" + participantName + "' ORDER BY " + DAOFactory.COLUMN_TIMSTAMP + " DESC ;";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            Log.d("EXPM_Participant", "cursor count is 0");
            return lendBorrowItemList;
        }
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                LendBorrowItem item = new LendBorrowItem(cursor.getString(1)
                        , cursor.getLong(2)
                        , cursor.getString(3)
                        , cursor.getInt(4)
                        , cursor.getLong(5));
                lendBorrowItemList.add(item);
                item.setId(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lendBorrowItemList;
    }

    public void updateName(String previousName, String newName) {
        ContentValues values = new ContentValues();
        Log.d("EXPM_ProgressCheck", "the old value = " + previousName
                + ", the new value = " + newName);
        values.put(DAOFactory.COLUMN_NAME, newName);
        database.update(DAOFactory.LEND_BORROW_TABLE, values,
                DAOFactory.COLUMN_NAME + " = '" + previousName + "'",
                null);
        Log.d("EXPM_update", "update done in lendBorrowTable");
    }
}
