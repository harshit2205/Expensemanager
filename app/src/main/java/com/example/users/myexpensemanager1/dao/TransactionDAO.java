package com.example.users.myexpensemanager1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.users.myexpensemanager1.models.TransactionItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionDAO {
    private static TransactionDAO transactionDAO;
    public String TRANSACTION_DAO = "transactionDAO";
    //initialising variables.....
    SQLiteDatabase database;
    DAOFactory daoFactory;
    Context context;

    private TransactionDAO(Context context){
        this.context = context;
        daoFactory = new DAOFactory(context, null);

        try{
            openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static TransactionDAO initialiser(Context context) {
        if (transactionDAO == null) {
            transactionDAO = new TransactionDAO(context);
        }
        return transactionDAO;
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
        database.delete(DAOFactory.TRANSACTION_TABLE, " _id = '" +id +"'", null);

        Log.d("EXPM","transaction table item deleted");
    }

    public List<TransactionItem> showTransactionTuple() {
        Log.d("EXPM_Logs","show Transaction tuple function called at "+Long.toString(System.currentTimeMillis()));
        List<TransactionItem> transactionList =  new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.TRANSACTION_TABLE + " ORDER BY " + DAOFactory.COLUMN_TIMSTAMP + " DESC;";
        Cursor cursor = database.rawQuery(query, null);
        Log.d("temp_tags","cursor count is "+cursor.getCount());
        if(cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                Log.d("EXPM_tag_temp_tags", "data = " + cursor.getLong(5));
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
        Log.d("EXPM_tag_Calender","time stamp = "+calendar.getTimeInMillis());
        long timestamp2 = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        Log.d("EXPM_tag_Calender","time stamp = "+calendar.getTimeInMillis());
        long timestamp1 = calendar.getTimeInMillis();
        String query = "SELECT SUM("+DAOFactory.COLUMN_AMOUNT+") FROM "+DAOFactory.TRANSACTION_TABLE+
                " WHERE "+DAOFactory.COLUMN_TIMSTAMP+" >= "+timestamp1+
                " AND "+DAOFactory.COLUMN_TIMSTAMP+" <= "+timestamp2 +";";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        long amount = cursor.getLong(0);
        return amount;
    }

    public void updateTransaction(TransactionItem transactionItem) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_USERNAME, transactionItem.getUserName());
        values.put(DAOFactory.COLUMN_ITEM, transactionItem.getItem_name());
        values.put(DAOFactory.COLUMN_AMOUNT, transactionItem.getAmount());
        values.put(DAOFactory.COLUMN_DESCRIPTION, transactionItem.getDescription());
        values.put(DAOFactory.COLUMN_TIMSTAMP, transactionItem.getTimestamp());
        values.put(DAOFactory.COLUMN_FILEPATH, transactionItem.getFilePath());
        values.put(DAOFactory.COLUMN_TYPE, transactionItem.getTransactionType());
        database.update(DAOFactory.TRANSACTION_TABLE, values,
                DAOFactory.COLUMN_ID + " = '" + transactionItem.getId() + "'",
                null);
        Log.d("EXPM", "transaction Inserted");
    }

    public List<TransactionItem> thisMonthTransaction(Calendar now) {

        long timeStamp1 = now.getTimeInMillis();
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        long timeStamp2 = now.getTimeInMillis();

        List<TransactionItem> transactionList = new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.TRANSACTION_TABLE + " WHERE " + DAOFactory.COLUMN_TIMSTAMP
                + " <= " + timeStamp1 + " AND " + DAOFactory.COLUMN_TIMSTAMP + " >= " + timeStamp2
                + " ORDER BY " + DAOFactory.COLUMN_TIMSTAMP + " ASC;";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
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
        return transactionList;
    }

    public List<TransactionItem> pastMonthTransaction(Calendar now) {

        now.set(Calendar.MONTH, 0);
        now.set(Calendar.DAY_OF_MONTH, 1);
        long timeStamp1 = now.getTimeInMillis();
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        now.set(Calendar.HOUR_OF_DAY, now.getActualMaximum(Calendar.HOUR_OF_DAY));
        now.set(Calendar.MINUTE, now.getActualMaximum(Calendar.MINUTE));
        now.set(Calendar.SECOND, now.getActualMaximum(Calendar.SECOND));
        long timeStamp2 = now.getTimeInMillis();

        List<TransactionItem> transactionList = new ArrayList<>();
        String query = "SELECT * FROM " + DAOFactory.TRANSACTION_TABLE + " WHERE " + DAOFactory.COLUMN_TIMSTAMP
                + " <= " + timeStamp2 + " AND " + DAOFactory.COLUMN_TIMSTAMP + " >= " + timeStamp1
                + " ORDER BY " + DAOFactory.COLUMN_TIMSTAMP + " ASC;";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
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
        return transactionList;
    }
}