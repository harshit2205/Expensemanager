package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.AlertDialogFrag;

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
        holder.transactionCost.setText(view.getContext().getResources().getString(R.string.Rs) + " " +Long.toString(items.get(position).getAmount()));
        holder.transactionDate.setText(getDate(items.get(position).getTimestamp()));
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
        public int id, position;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            transactionCost = (TextView)itemView.findViewById(R.id.transaction_cost);
            transactionDate = (TextView)itemView.findViewById(R.id.transaction_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialogFrag frag = AlertDialogFrag.getAlertDialog();
                    frag.item = items.get(position);
                    frag.show(manager, "dailog for transaction element");
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Snackbar.make(v,"this is the snackBar",Snackbar.LENGTH_LONG)
                    .setAction("UPDATE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("EXPM_Snackbar","transaction list item long press listener's first option is clicked");
                        }
                    }).show();
                    Log.d("EXPM_Temptags","data set changed at " + System.currentTimeMillis() );
                    return true;
                }
            });
        }
    }
}