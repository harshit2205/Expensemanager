package com.example.users.myexpensemanager1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Dao.AlarmsDAO;
import com.example.users.myexpensemanager1.Models.AlarmItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Views.MoneyHistoryViewHolder;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RemainderHistoryAdapter extends SwipeAdapter{
    Context context;
    List<AlarmItem> items;
    AlarmsDAO alarmsDAO;

    public RemainderHistoryAdapter(Context context, List<AlarmItem> items) {
        this.context = context;
        this.items = items;
        Log.d("EXPM","size of remainders table: "+items.size());
        Log.d("EXPM","size of database remainders table: "+AlarmsDAO.initialiser(context).getAlarmsCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_history, viewGroup, true);
        return new MoneyHistoryViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MoneyHistoryViewHolder viewHolder1 = (MoneyHistoryViewHolder)viewHolder;
        viewHolder1.moneyAmount.setText(items.get(i).getItemName());
        viewHolder1.date.setText(getDate(items.get(i).getTimestamp()));
        viewHolder1.time.setText(getTime(items.get(i).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return 1;
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
            alarmsDAO = AlarmsDAO.initialiser(context);
            alarmsDAO.deleteAlarm(items.get(i).getId());
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

    private String getTime(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String time = DateFormat.format("HH:mm", cal).toString();
        return time;
    }
}