package com.example.users.myexpensemanager1.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.dao.TransactionDAO;
import com.example.users.myexpensemanager1.dialogs.ShowTransactionDetailsDialog;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.example.users.myexpensemanager1.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {
    public List<TransactionItem> items;
    public Context context;
    private FragmentManager manager;
    private View view;

    public TransactionHistoryAdapter(Context context, List<TransactionItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history_row, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        String transactionCost = view.getContext().getResources().getString(R.string.Rs) +
                " " + Long.toString(items.get(position).getAmount());
        holder.transactionCost.setText(transactionCost);
        holder.itemName.setText(items.get(position).getItem_name());
        holder.transactionDate.setText(getDate(items.get(position).getTimestamp()));
        holder.position = position;

        switch (items.get(position).getTransactionType()) {
            case "Food Expenses":
                holder.avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.food_expenses));
                break;
            case "Movies Expenses":
                holder.avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.movie_expenses));
                break;
            case "Household Expenses":
                holder.avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.household_expenses));
                break;
            case "Tax Expenses":
                holder.avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.tax_expenses));
                break;
            default:
                holder.avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.other_expenses));
        }

        holder.id = items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if(items == null)return 0;
        return items.size();
    }


    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }

    public void itemSetChanged(){
        items = TransactionDAO.initialiser(context).showTransactionTuple();
        notifyDataSetChanged();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView recieptImage, deleteTransaction, editTransaction;
        private TextView itemName;
        private TextView transactionCost;
        private TextView transactionDate;
        private ImageView avatarView;
        private int id, position;

        public TransactionViewHolder(final View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            avatarView = itemView.findViewById(R.id.avatarView);
            transactionCost = itemView.findViewById(R.id.transaction_cost);
            transactionDate = itemView.findViewById(R.id.transaction_date);


            transactionCost.setTypeface(CombinedChartTransaction.mTfLight);
            transactionDate.setTypeface(CombinedChartTransaction.mTfLight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("EXPM_temp_logs", "position = " + position);
                    new ShowTransactionDetailsDialog(context, manager, items.get(position));
                }
            });


        }
    }
}