package com.example.users.myexpensemanager1.adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.fragments.ParticipanthistoryFrag;
import com.example.users.myexpensemanager1.models.ParticipantItem;

import java.util.List;


public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {
    public List<ParticipantItem> items;
    Context context;
    FragmentManager manager;
    View view;

    public ParticipantsAdapter(Context context, List<ParticipantItem> items, FragmentManager manager) {
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
        ParticipantItem participantItem = items.get(position);
        holder.participant.setText(participantItem.getParticipantname());
        holder.dues = participantItem.getDues();
        if (participantItem.isInDebt() == ParticipantItem.IS_IN_DEBT) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape2));
            holder.type = "borrowed";
            holder.isInDebt.setText(holder.type);
        } else if (participantItem.isInDebt() == ParticipantItem.NO_DEBT) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape3));
            holder.type = "settled";
            holder.isInDebt.setText(holder.type);
        } else if (participantItem.isInDebt() == ParticipantItem.HAS_LENDED) {
            holder.isInDebt.setBackground(context.getResources().getDrawable(R.drawable.shape1));
            holder.type = "lended";
            holder.isInDebt.setText(holder.type);
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    private void fragmentstarter(android.app.Fragment frag, FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("All_participants");
        transaction.replace(R.id.container_main, frag);
        transaction.commit();
    }

    class ParticipantsViewHolder extends RecyclerView.ViewHolder {
        TextView participant;
        Button isInDebt;
        boolean flag = false;
        String type;
        long dues = 0;

        ParticipantsViewHolder(View itemView) {
            super(itemView);
            participant = itemView.findViewById(R.id.participant);
            isInDebt = itemView.findViewById(R.id.isInDebt);

            isInDebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!flag) {
                        String data = context.getResources().getString(R.string.Rs) + " " + Long.toString(dues);
                        isInDebt.setText(data);
                        flag = true;
                    } else {
                        isInDebt.setText(type);
                        flag = false;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParticipanthistoryFrag frag = new ParticipanthistoryFrag();
                    frag.setParticipantName(participant.getText().toString());
                    fragmentstarter(frag, manager);
                }
            });
        }
    }
}
