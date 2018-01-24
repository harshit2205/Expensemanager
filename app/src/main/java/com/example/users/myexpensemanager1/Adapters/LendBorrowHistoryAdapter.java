package com.example.users.myexpensemanager1.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.LendBorrowItem;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LendBorrowHistoryAdapter extends RecyclerView.Adapter<LendBorrowHistoryAdapter.LendBorrowHistoryViewHolder> {
    public List<LendBorrowItem> items;
    Context context;
    FragmentManager manager;
    View view;

    public LendBorrowHistoryAdapter(Context context, List<LendBorrowItem> items, FragmentManager manager) {
        this.context = context;
        this.items = items;
        this.manager = manager;
    }

    @Override
    public LendBorrowHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lend_borrow_history_row, parent, false);
        return new LendBorrowHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LendBorrowHistoryViewHolder holder, int position) {
        LendBorrowItem lendBorrowItem = items.get(position);

        if(lendBorrowItem.getAmount() < 0){
            holder.borrowerName.setText(lendBorrowItem.getName());
            holder.lenderName.setText("You");
            holder.rootview.setCardBackgroundColor( ColorTemplate.rgb("#F44336"));
            holder.amount.setText(Long.toString(lendBorrowItem.getAmount() * -1));
            holder.avatar.setText("B");
        }else{
            holder.borrowerName.setText("You");
            holder.lenderName.setText(lendBorrowItem.getName());
            holder.rootview.setCardBackgroundColor( ColorTemplate.rgb("#009688"));
            holder.amount.setText(Long.toString(lendBorrowItem.getAmount()));
            holder.avatar.setText("L");
        }
        holder.description.setText(lendBorrowItem.getDescription());
        if(lendBorrowItem.getReminderSet() == LendBorrowDAO.REMINDER_SET){
            holder.remainderDate.setText(getDate(lendBorrowItem.getTimeStamp()));
        }else{
            holder.remainderDate.setText("remainder not set");
        }


    }

    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    @Override
    public int getItemCount() {
        if(items == null)return 0;
        return items.size();
    }

    public void itemSetChanged(){
        items = LendBorrowDAO.initialiser(context).showLendBorrowTupple();
        notifyDataSetChanged();
    }

    public class LendBorrowHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView lenderName, borrowerName, avatar, amount,remainderDate,description;
        ImageView delete;
        CardView rootview;


        public LendBorrowHistoryViewHolder(View itemView) {
            super(itemView);
            rootview = (CardView)itemView.findViewById(R.id.rootview);
            lenderName = (TextView)itemView.findViewById(R.id.lender_name);
            borrowerName = (TextView)itemView.findViewById(R.id.borrowe_name);
            avatar = (TextView)itemView.findViewById(R.id.avatarView);
            amount = (TextView)itemView.findViewById(R.id.money_amount);
            remainderDate = (TextView)itemView.findViewById(R.id.remainder_info);
            description = (TextView)itemView.findViewById(R.id.description);
            delete = (ImageView)itemView.findViewById(R.id.delete_lend_borrow);
        }
    }

}