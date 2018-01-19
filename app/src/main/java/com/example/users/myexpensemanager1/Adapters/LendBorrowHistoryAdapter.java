package com.example.users.myexpensemanager1.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.R;

public class LendBorrowHistoryAdapter extends RecyclerView.Adapter<LendBorrowHistoryAdapter.LendBorrowHistoryViewHolder> {

    @Override
    public LendBorrowHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lend_borrow_history_row, parent, false);
        return new LendBorrowHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LendBorrowHistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class LendBorrowHistoryViewHolder extends RecyclerView.ViewHolder {
        public LendBorrowHistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}