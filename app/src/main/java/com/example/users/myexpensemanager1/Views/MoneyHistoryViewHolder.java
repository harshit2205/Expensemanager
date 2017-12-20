package com.example.users.myexpensemanager1.Views;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;

public class MoneyHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView moneyAmount;
    public TextView date;
    public TextView time;
    public int id;

    public MoneyHistoryViewHolder(View itemView, final int INTENT, final FragmentManager manager) {
        super(itemView);
        moneyAmount = (TextView)itemView.findViewById(R.id.money_amount);
        date = (TextView)itemView.findViewById(R.id.money_add_date);
        time = (TextView)itemView.findViewById(R.id.money_add_time);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(id, INTENT);
                frag.show(manager,"deletion");

                return true;
            }
        });
    }
}
