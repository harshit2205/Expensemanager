package com.example.users.myexpensemanager1.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.dao.MoneyDAO;
import com.example.users.myexpensemanager1.dialogs.ShowMoneyDetailsDialog;
import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MoneyHistoryAdapter extends RecyclerView.Adapter<MoneyHistoryAdapter.MoneyHistoryViewHolder> {
    public List<MoneyItem> items;
    private Context context;
    private FragmentManager manager;
    private int repeatable;

    public MoneyHistoryAdapter(Context context, List<MoneyItem> items, FragmentManager manager, int repeatable) {
        this.context = context;
        this.items = items;
        this.manager = manager;
        this.repeatable = repeatable;
        Log.d("EXPM_Logs","size of Money table: "+items.size()+ " at " +System.currentTimeMillis());
        Log.d("EXPM_Logs","size of database Money table: "+MoneyDAO.initialiser(context).getMoneyCount()+" at "+System.currentTimeMillis());
    }


    @Override
    public MoneyHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
        return new MoneyHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MoneyHistoryViewHolder holder, int position) {
        holder.moneyAmount.setText(context.getResources().getString(R.string.Rs)+" "
                +Long.toString(items.get(position).getAmount()));
        holder.date.setText(getDate(items.get(position).getTimestamp()));
        holder.id = items.get(position).getId();
        holder.position = position;
        holder.description.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private String getTime(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String time = DateFormat.format("HH:mm", cal).toString();
        return time;
    }

    public void itemSetChanged(){
        items = MoneyDAO.initialiser(context).showMoneyTuple();
        notifyDataSetChanged();
    }

    public class MoneyHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public int id;
        public int position;
        public TextView description;
        private TextView moneyAmount;

        public MoneyHistoryViewHolder(View itemView) {
            super(itemView);
            moneyAmount = (TextView)itemView.findViewById(R.id.money_amount);
            moneyAmount.setTypeface(CombinedChartTransaction.mTfLight);
            date = (TextView)itemView.findViewById(R.id.money_add_date);
            date.setTypeface(CombinedChartTransaction.mTfLight);
            description = (TextView) itemView.findViewById(R.id.item_description);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShowMoneyDetailsDialog(context, manager, items.get(position), repeatable);
                }
            });
        }
    }
}
