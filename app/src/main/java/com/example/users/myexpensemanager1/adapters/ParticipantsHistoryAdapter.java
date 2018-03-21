package com.example.users.myexpensemanager1.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.charts.BarChartTransaction;
import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.models.LendBorrowItem;
import com.example.users.myexpensemanager1.models.ParticipantItem;
import com.example.users.myexpensemanager1.models.TransactionItem;

import java.util.List;

/**
 * Created by USER on 3/21/2018.
 */

public class ParticipantsHistoryAdapter extends RecyclerView.Adapter<ParticipantsHistoryAdapter.ParticipantsHistoryViewHolder> {
    public List<LendBorrowItem> items;
    public Context context;
    View view;

    public ParticipantsHistoryAdapter(List<LendBorrowItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ParticipantsHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_history_row, parent, false);
        return new ParticipantsHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticipantsHistoryViewHolder holder, int position) {
        LendBorrowItem lendBorrowItem = items.get(position);
        if (lendBorrowItem.getAmount() < 0) {
            String toPay = "To Pay";
            holder.debtType.setText(toPay);
            String amount = context.getResources().getString(R.string.Rs) + " " + (-lendBorrowItem.getAmount());
            holder.debtAmount.setText(amount);
            holder.debtPurpose.setText(lendBorrowItem.getDescription());
            holder.changeToRed();
        } else {
            String toTake = "To Take";
            holder.debtType.setText(toTake);
            String amount = context.getResources().getString(R.string.Rs) + " " + lendBorrowItem.getAmount();
            holder.debtAmount.setText(amount);
            holder.debtPurpose.setText(lendBorrowItem.getDescription());
            holder.changeToGreen();
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    class ParticipantsHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView debtType;
        TextView debtPurpose;
        TextView debtAmount;

        ParticipantsHistoryViewHolder(View itemView) {
            super(itemView);
            debtType = itemView.findViewById(R.id.type);
            debtPurpose = itemView.findViewById(R.id.purpose);
            debtAmount = itemView.findViewById(R.id.debtAmount);
            debtAmount.setTypeface(CombinedChartTransaction.mTfLight);
        }

        void changeToRed() {
            debtType.setTextColor(ContextCompat.getColor(context, R.color.darkred));
            debtPurpose.setTextColor(ContextCompat.getColor(context, R.color.darkred));
            debtAmount.setTextColor(ContextCompat.getColor(context, R.color.darkred));
        }

        void changeToGreen() {
            debtType.setTextColor(ContextCompat.getColor(context, R.color.darkgreen));
            debtPurpose.setTextColor(ContextCompat.getColor(context, R.color.darkgreen));
            debtAmount.setTextColor(ContextCompat.getColor(context, R.color.darkgreen));
        }
    }
}
