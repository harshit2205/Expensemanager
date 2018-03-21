package com.example.users.myexpensemanager1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DAOFactory extends SQLiteOpenHelper {


    //table names.....
    public static final String TRANSACTION_TABLE = "transactiontable";
    public static final String MONEY_TABLE = "MoneyTable";
    public static final String ALARM_TABLE = "AlarmTable";
    public static final String REPETATIVE_MONEY_TABLE = "RepetativeMoneyTable";
    public static final String LEND_BORROW_TABLE = "LendBorrowTable";
    public static final String PARTICIPANT_TABLE = "ParticipantsTable";
    //column names.....
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "itemname";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_TIMSTAMP = "currentstamp";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_UNIQUE_STAMP = "uniqueKey";
    public static final String COLUMN_FILEPATH = "filePath";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ISINDEBT = "debt";
    public static final String COLUMN_REMAINDER_SET = "remainderSet";
    public static final String COLUMN_DUES = "dues";
    //database names.....
    private static final String DATABASE = "expense_Manager_db.db";
    //queries for Table creation.....
    //create add Money Table.....
    String createMoneyTable =  "CREATE TABLE "+MONEY_TABLE+"( "+
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_USERNAME+ " TEXT , " +
            COLUMN_AMOUNT+ " BIGINT(15) , " +
            COLUMN_DESCRIPTION+ " TEXT , " +
            COLUMN_TIMSTAMP+" BIGINT(10) );";

    String createRepMoneyTable =  "CREATE TABLE "+REPETATIVE_MONEY_TABLE+"( "+
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_USERNAME+ " TEXT , " +
            COLUMN_AMOUNT+ " BIGINT(15) , " +
            COLUMN_DESCRIPTION+ " TEXT , " +
            COLUMN_TIMSTAMP+" BIGINT(10) );";

    //create add Transaction Table.....
    String createTransactionTable = "CREATE TABLE "+TRANSACTION_TABLE+"( " +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_USERNAME+ " TEXT , "+
            COLUMN_ITEM+ " TEXT , " +
            COLUMN_AMOUNT+ " BIGINT(10) , " +
            COLUMN_DESCRIPTION+ " TEXT , " +
            COLUMN_TIMSTAMP+ " BIGINT(15), " +
            COLUMN_FILEPATH+ " TEXT ," +
            COLUMN_TYPE+ " TEXT );";

    String createAlarmTable = "CREATE TABLE "+ ALARM_TABLE +"( " +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_USERNAME+ " TEXT , "+
            COLUMN_ITEM+ " TEXT , " +
            COLUMN_TIMSTAMP+ " BIGINT(15) , "+
            COLUMN_DESCRIPTION+ " TEXT , "+
            COLUMN_UNIQUE_STAMP + " BIGINT(15) );";

    String createLendBorrowTable = " CREATE TABLE "+LEND_BORROW_TABLE + "( " +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_NAME + " TEXT , " +
            COLUMN_AMOUNT+ " BIGINT(10) , " +
            COLUMN_DESCRIPTION + " TEXT , " +
            COLUMN_REMAINDER_SET + " BOOL , " +
            COLUMN_TIMSTAMP+ " BIGINT(15) ); ";

    String createParticipantTable = " CREATE TABLE " + PARTICIPANT_TABLE + "( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_NAME + " TEXT , " +
            COLUMN_DUES + " BIGINT(15) , " +
            COLUMN_ISINDEBT + " INTEGER ); ";


    public DAOFactory(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMoneyTable);
        db.execSQL(createTransactionTable);
        db.execSQL(createAlarmTable);
        db.execSQL(createRepMoneyTable);
        db.execSQL(createLendBorrowTable);
        db.execSQL(createParticipantTable);
        Log.d("EXPM", "all tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MONEY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REPETATIVE_MONEY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LEND_BORROW_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PARTICIPANT_TABLE);

        //database recreated.....
        onCreate(db);
        Log.d("EXPM", "database upgraded");
    }
}