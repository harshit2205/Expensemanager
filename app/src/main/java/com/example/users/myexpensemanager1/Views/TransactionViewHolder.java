package com.example.users.myexpensemanager1.Views;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView transactionCost;
    public TextView transactionDate;
    public int id;

    public TransactionViewHolder(View itemView, final FragmentManager manager) {
        super(itemView);
        itemName = (TextView)itemView.findViewById(R.id.item_name);
        transactionCost = (TextView)itemView.findViewById(R.id.transaction_cost);
        transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(id, ConfirmationDailogFrag.TRANSACTION_DELETION);
                frag.show(manager,"transaction_deletion");
                return true;
            }
        });
    }
}

