package com.example.users.myexpensemanager1.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;

public class MoneyHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView moneyAmount;
    public TextView date;
    public TextView time;

    public MoneyHistoryViewHolder(View itemView) {
        super(itemView);
        moneyAmount = (TextView)itemView.findViewById(R.id.money_amount);
        date = (TextView)itemView.findViewById(R.id.money_add_date);
        time = (TextView)itemView.findViewById(R.id.money_add_time);
    }
}
