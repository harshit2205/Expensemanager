package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Models.MoneyItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;
import com.example.users.myexpensemanager1.Views.MoneyHistoryViewHolder;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MoneyHistoryAdapter extends RecyclerView.Adapter<MoneyHistoryViewHolder> {
    Context context;
    List<MoneyItem> items;
    FragmentManager manager;

    public MoneyHistoryAdapter(Context context, List<MoneyItem> items, FragmentManager manager){
        this.context = context;
        this.items = items;
        this.manager = manager;
        Log.d("EXPM_Logs","size of Money table: "+items.size()+ " at " +System.currentTimeMillis());
        Log.d("EXPM_Logs","size of database Money table: "+MoneyDAO.initialiser(context).getMoneyCount()+" at "+System.currentTimeMillis());
    }


    @Override
    public MoneyHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
        return new MoneyHistoryViewHolder(v, ConfirmationDailogFrag.MONEY_DELETION, manager);
    }

    @Override
    public void onBindViewHolder(MoneyHistoryViewHolder holder, int position) {
        holder.moneyAmount.setText(Long.toString(items.get(position).getAmount()));
        holder.date.setText(getDate(items.get(position).getTimestamp()));
        holder.time.setText(getTime(items.get(position).getTimestamp()));
        holder.id = items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private String getTime(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String time = DateFormat.format("HH:mm", cal).toString();
        return time;
    }



}
