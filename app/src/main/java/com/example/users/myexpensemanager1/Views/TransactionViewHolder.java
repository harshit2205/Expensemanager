package com.example.users.myexpensemanager1.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView transactionCost;
    public TextView transactionDate;

    public TransactionViewHolder(View itemView) {
        super(itemView);
        itemName = (TextView)itemView.findViewById(R.id.item_name);
        transactionCost = (TextView)itemView.findViewById(R.id.transaction_cost);
        transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);
    }
}