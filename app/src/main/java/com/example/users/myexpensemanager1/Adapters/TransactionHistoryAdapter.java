package com.example.users.myexpensemanager1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Views.TransactionViewHolder;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryAdapter extends SwipeAdapter {
    Context context;
    List<TransactionItem> items;
    TransactionDAO transactionDAO;

    public TransactionHistoryAdapter(Context context, List<TransactionItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_history_row, viewGroup, true);
        return new TransactionViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TransactionViewHolder holder = (TransactionViewHolder)viewHolder;
        holder.itemName.setText(items.get(i).getItem_name());
        holder.transactionCost.setText(Long.toString(items.get(i).getAmount()));
        holder.transactionDate.setText(getDate(items.get(i).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int i) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.colorPrimaryDark)
                .setRightBackgroundColorResource(R.color.mdtp_red)
                .setDrawableResource(R.drawable.ic_delete_white_24dp)
                .setRightDrawableResource(R.drawable.ic_done_white_24dp)
                .setDescriptionTextColorResource(android.R.color.white)
                .setRightUndoable(false)
                .setLeftUndoable(false)
                .setDescription("")
                .setRightDescription("")
                .setLeftDescription("")
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NO_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int i, int i1) {
        if(i1 == SWIPE_LEFT){
            transactionDAO = TransactionDAO.initialiser(context);
            transactionDAO.deleteTransaction(items.get(i).getId());
            Toast toast = Toast.makeText(context, "Deleted item at position " + i, Toast.LENGTH_SHORT);
            notifyDataSetChanged();
            toast.show();
        }
    }

    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}