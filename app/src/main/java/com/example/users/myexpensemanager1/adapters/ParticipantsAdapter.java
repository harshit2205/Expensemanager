package com.example.users.myexpensemanager1.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.models.Participant;

import java.util.List;

/**
 * Created by USER on 3/19/2018.
 */

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {
    public List<Participant> items;
    Context context;
    FragmentManager manager;
    View view;

    public ParticipantsAdapter(Context context, List<Participant> items, FragmentManager manager) {
        this.items = items;
        this.context = context;
        this.manager = manager;
    }

    @Override
    public ParticipantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participants_row, parent, false);
        return new ParticipantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticipantsViewHolder holder, int position) {
        Participant participant = items.get(position);
        holder.participant.setText(participant.getParticipantname());
        if (participant.isInDebt() == Participant.IS_IN_DEBT) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape2));
            holder.isInDebt.setText("borrow");
        } else if (participant.isInDebt() == Participant.NO_DEBT) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape3));
            holder.isInDebt.setText("settled");
        } else if (participant.isInDebt() == Participant.HAS_LENDED) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape1));
            holder.isInDebt.setText("lend");
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public class ParticipantsViewHolder extends RecyclerView.ViewHolder {
        TextView participant;
        Button isInDebt;

        public ParticipantsViewHolder(View itemView) {
            super(itemView);
            participant = itemView.findViewById(R.id.participant);
            isInDebt = itemView.findViewById(R.id.isInDebt);
        }
    }
}
