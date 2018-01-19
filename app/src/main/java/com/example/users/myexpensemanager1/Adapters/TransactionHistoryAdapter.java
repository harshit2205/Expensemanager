package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Activities.RecieptImageActivity;
import com.example.users.myexpensemanager1.Charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {
    Context context;
    public List<TransactionItem> items;
    FragmentManager manager;
    View view;

    public TransactionHistoryAdapter(Context context, List<TransactionItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(items.size()==0){

        }else{

        }
         view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history_row, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getItem_name());
        holder.transactionCost.setText(view.getContext().getResources().getString(R.string.Rs) + " "
                +Long.toString(items.get(position).getAmount()));
        holder.transactionDate.setText(getDate(items.get(position).getTimestamp()));
        holder.transactionType.setText(items.get(position).getTransactionType());
        holder.description.setText(items.get(position).getDescription());
        holder.id = items.get(position).getId();
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        if(items == null)return 0;
        return items.size();
    }


    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public void itemSetChanged(){
        items = TransactionDAO.initialiser(context).showTransactionTuple();
        notifyDataSetChanged();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView transactionCost;
        public TextView transactionDate;
        public TextView updateData;
        public TextView transactionType;
        public TextView description;
        public int id, position;
        ImageView recieptImage,deleteTransaction;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            transactionCost = (TextView)itemView.findViewById(R.id.transaction_cost);
            transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);
            recieptImage = (ImageView)itemView.findViewById(R.id.reciept_attachment);
            deleteTransaction =(ImageView)itemView.findViewById(R.id.delete_transaction);
            updateData = (TextView)itemView.findViewById(R.id.modify);
            transactionType = (TextView)itemView.findViewById(R.id.transaction_type);
            description = (TextView)itemView.findViewById(R.id.item_description);

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
                            ConfirmationDailogFrag.TRANSACTION_DELETION,
                            TransactionHistoryAdapter.this);
                    frag.show(manager, "confirmation");
                }
            });
        }
    }
}