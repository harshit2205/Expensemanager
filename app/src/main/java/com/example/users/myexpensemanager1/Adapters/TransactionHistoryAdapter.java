package com.example.users.myexpensemanager1.Adapters;

import android.content.Context;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Views.TransactionViewHolder;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    Context context;
    List<TransactionItem> items;
    FragmentManager manager;

    public TransactionHistoryAdapter(Context context, List<TransactionItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history_row, parent, false);
        return new TransactionViewHolder(view, manager);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getItem_name());
        holder.transactionCost.setText(Long.toString(items.get(position).getAmount()));
        holder.transactionDate.setText(getDate(items.get(position).getTimestamp()));
        holder.id = items.get(position).getId();
        Log.d("temp_Logs","object of adapter created");

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
}