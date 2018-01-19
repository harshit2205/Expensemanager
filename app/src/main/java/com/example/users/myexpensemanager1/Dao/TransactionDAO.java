package com.example.users.myexpensemanager1.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.TransactionItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionDAO {
    public String TRANSACTION_DAO = "transactionDAO";

    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    private static TransactionDAO transactionDAO;

    public static TransactionDAO initialiser(Context context){
        if(transactionDAO == null){
            transactionDAO = new TransactionDAO(context);
        }
        return transactionDAO;
    }

    private TransactionDAO(Context context){
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
    public void insertTransaction(TransactionItem transactionItem){
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_USERNAME,transactionItem.getUserName());
        values.put(DAOFactory.COLUMN_ITEM,transactionItem.getItem_name());
        values.put(DAOFactory.COLUMN_AMOUNT,transactionItem.getAmount());
        values.put(DAOFactory.COLUMN_DESCRIPTION,transactionItem.getDescription());
        values.put(DAOFactory.COLUMN_TIMSTAMP,transactionItem.getTimestamp());
        values.put(DAOFactory.COLUMN_FILEPATH,transactionItem.getFilePath());
        values.put(DAOFactory.COLUMN_TYPE,transactionItem.getTransactionType());
        database.insert(DAOFactory.TRANSACTION_TABLE,null,values);
        Log.d("EXPM", "transaction Inserted");
    }

    public void deleteTransaction(int id) throws SQLException {
        database.delete(daoFactory.TRANSACTION_TABLE, " _id = '" +id +"'", null);

        Log.d("EXPM","transaction table item deleted");
    }

    public List<TransactionItem> showTransactionTuple() {
        Log.d("EXPM_Logs","show Transaction tuple function called at "+Long.toString(System.currentTimeMillis()));
        List<TransactionItem> transactionList =  new ArrayList<>();
        String query = "SELECT * FROM "+DAOFactory.TRANSACTION_TABLE+" ORDER BY "+DAOFactory.COLUMN_TIMSTAMP+" ASC;";
        Cursor cursor = database.rawQuery(query, null);
        Log.d("temp_tags","cursor count is "+cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Log.d("EXPM(tag)_temp_tags", "data = " + cursor.getLong(5));
                TransactionItem item = new TransactionItem(cursor.getString(1)
                        , cursor.getString(2)
                        , Long.parseLong(cursor.getString(3))
                        , Long.parseLong(cursor.getString(5))
                        , cursor.getString(4)
                        , cursor.getString(6)
                        , cursor.getString(7));
                transactionList.add(item);
                item.setId(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("EXPM_Logs","show transaction tuple function stopped at "+Long.toString(System.currentTimeMillis()));
        Log.d("EXPM", "money list fetched");
        return transactionList;
    }

    // gives the count of the number of elements.......
    public int getTransactionsCount() {
        Log.d("EXPM_Logs","get transactions count function called at "+Long.toString(System.currentTimeMillis()));
        String countQuery = "SELECT  * FROM " + DAOFactory.TRANSACTION_TABLE;
        Cursor cursor = database.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("EXPM_Logs","get transactions count function stopped at "+Long.toString(System.currentTimeMillis()));
        return cnt;
    }

    public long gettransactionamountByMonth(Calendar calendar){
        Log.d("EXPM(tag)_Calender","time stamp = "+calendar.getTimeInMillis());
        long timestamp2 = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        Log.d("EXPM(tag)_Calender","time stamp = "+calendar.getTimeInMillis());
        long timestamp1 = calendar.getTimeInMillis();
        String query = "SELECT SUM("+DAOFactory.COLUMN_TIMSTAMP+") FROM "+DAOFactory.TRANSACTION_TABLE+
                " WHERE '"+DAOFactory.COLUMN_TIMSTAMP+"' >= "+timestamp1+
                " AND '" +DAOFactory.COLUMN_TIMSTAMP+"' <= "+timestamp2;
//        Log.d("EXPM_Query"," getTransactionAmount by month = "+query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Log.d("EXPM(tag)_Query","total amount got = "+cursor.getLong(0));
        return cursor.getLong(0);
    }

}