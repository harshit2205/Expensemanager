package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Activities.RecieptImageActivity;
import com.example.users.myexpensemanager1.Charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Dialogs.AddTransactionDialog;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;

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
        private ImageView recieptImage, deleteTransaction, editTransaction;
        private TextView itemName;
        private TextView transactionCost;
        private TextView transactionDate;
        private ImageView avatarView;
        private int id, position;

        public TransactionViewHolder(final View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            avatarView = (ImageView) itemView.findViewById(R.id.avatarView);
            transactionCost = (TextView)itemView.findViewById(R.id.transaction_cost);
            transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);
            recieptImage = (ImageView)itemView.findViewById(R.id.reciept_attachment);
            deleteTransaction =(ImageView)itemView.findViewById(R.id.delete_transaction);
            editTransaction = (ImageView) itemView.findViewById(R.id.edit_transaction);

            transactionCost.setTypeface(CombinedChartTransaction.mTfLight);
            transactionDate.setTypeface(CombinedChartTransaction.mTfLight);

            recieptImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = items.get(position).getFilePath();
                    Intent intent = new Intent(context, RecieptImageActivity.class);
                    intent.putExtra("imagePath",str);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            deleteTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(id,
                            ConfirmationDailogFrag.TRANSACTION_DELETION);
                    frag.show(manager, "confirmation");
                }
            });

            editTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AddTransactionDialog dialog = new AddTransactionDialog(context, manager);
                    Log.d("EXPM_temp_logs", "size of list = " + items.size() + " and item position = " + position);
                    dialog.viewElementEditor(items.get(position));
                }
            });
        }
    }
}