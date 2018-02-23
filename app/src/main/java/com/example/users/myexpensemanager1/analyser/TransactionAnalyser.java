package com.example.users.myexpensemanager1.analyser;

import com.example.users.myexpensemanager1.dao.TransactionDAO;

public class TransactionAnalyser {

    private static TransactionAnalyser transactionAnalyser;

    private TransactionAnalyser(){}

    public static TransactionAnalyser getInstance(TransactionDAO transactionDAO){
        if(transactionAnalyser == null){
            transactionAnalyser = new TransactionAnalyser();
        }
        return transactionAnalyser;
    }

    public int getMaxChartYValue(){
        return 10;
    }

    public int getMaxChartXValue(){return 5;}

}