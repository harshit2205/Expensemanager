package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Dao.AlarmsDAO;
import com.example.users.myexpensemanager1.Models.AlarmItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.ConfirmationDailogFrag;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RemainderHistoryAdapter extends RecyclerView.Adapter<RemainderHistoryAdapter.RemainderHistoryViewHolder>{
    Context context;
    List<AlarmItem> items;
    FragmentManager manager;

    public RemainderHistoryAdapter(Context context, List<AlarmItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
        Log.d("EXPM","size of remainders table: "+items.size());
        Log.d("EXPM","size of database remainders table: "+AlarmsDAO.initialiser(context).getAlarmsCount());
    }

    @Override
    public RemainderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false);
        return new RemainderHistoryViewHolder(view, ConfirmationDailogFrag.ALARM_DELETION, manager);
    }

    @Override
    public void onBindViewHolder(RemainderHistoryViewHolder holder, int position) {
        holder.moneyAmount.setText(items.get(position).getItemName());
        holder.date.setText(getDate(items.get(position).getTimestamp()));
        holder.time.setText(getTime(items.get(position).getTimestamp()));
        holder.id = items.get(position).getId();
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
        items = AlarmsDAO.initialiser(context).showAlarmsTuple();
        notifyDataSetChanged();
    }

    public class RemainderHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView moneyAmount;
        public TextView date;
        public TextView time;
        public int id;

        public RemainderHistoryViewHolder(View itemView, final int INTENT, final FragmentManager manager) {
            super(itemView);
            moneyAmount = (TextView)itemView.findViewById(R.id.money_amount);
            date = (TextView)itemView.findViewById(R.id.money_add_date);
            time = (TextView)itemView.findViewById(R.id.money_add_time);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(id, INTENT,
                            RemainderHistoryAdapter.this);
                    frag.show(manager,"deletion");

                    return true;
                }
            });
        }
    }
}