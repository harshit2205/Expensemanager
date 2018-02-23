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

import com.example.users.myexpensemanager1.dao.RemaindersDAO;
import com.example.users.myexpensemanager1.models.RemainderItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.utils.ConfirmationDailogFrag;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RemainderHistoryAdapter extends RecyclerView.Adapter<RemainderHistoryAdapter.RemainderHistoryViewHolder>{
    public List<RemainderItem> items;
    Context context;
    FragmentManager manager;

    public RemainderHistoryAdapter(Context context, List<RemainderItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
        Log.d("EXPM","size of remainders table: "+items.size());
        Log.d("EXPM","size of database remainders table: "+ RemaindersDAO.initialiser(context).getRemaindersCount());
    }

    @Override
    public RemainderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remainder_history_row, parent, false);
        return new RemainderHistoryViewHolder(view, ConfirmationDailogFrag.REMAINDER_DELETION, manager);
    }

    @Override
    public void onBindViewHolder(RemainderHistoryViewHolder holder, int position) {
        holder.purpose.setText(items.get(position).getItemName());
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
        items = RemaindersDAO.initialiser(context).showRemainderTuple();
        notifyDataSetChanged();
    }

    public class RemainderHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView purpose;
        public TextView date;
        public TextView time;
        public TextView description;
        public int id;
        ImageView deleteRemainder;

        public RemainderHistoryViewHolder(View itemView, final int INTENT, final FragmentManager manager) {
            super(itemView);
            purpose = (TextView)itemView.findViewById(R.id.purpose);
            date = (TextView)itemView.findViewById(R.id.remainder_date);
            time = (TextView)itemView.findViewById(R.id.remainder_time);
            deleteRemainder = (ImageView)itemView.findViewById(R.id.delete_remainder);

            deleteRemainder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(id,
                            ConfirmationDailogFrag.REMAINDER_DELETION);
                    frag.show(manager,"deletion");

                }
            });
        }
    }
}