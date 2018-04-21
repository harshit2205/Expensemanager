package com.example.users.myexpensemanager1.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.activities.RecieptImageActivity;
import com.example.users.myexpensemanager1.dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.models.LendBorrowItem;
import com.example.users.myexpensemanager1.models.MessageEvent;
import com.example.users.myexpensemanager1.models.ParticipantItem;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.example.users.myexpensemanager1.utils.ConfirmationDailogFrag;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import static com.example.users.myexpensemanager1.utils.GetDateTime.getDate;
import static com.example.users.myexpensemanager1.utils.GetDateTime.getTime;

/**
 * Created by USER on 3/27/2018.
 */

public class EditParticipantDialog {
    Context context;
    FragmentManager manager;
    DialogPlus dialog;
    ParticipantItem item;
    String previousParticipantName;
    boolean toggler;
    private EditText participantName;
    private TextView amount;
    private Button cancel, done, settle;
    private View view;
    private LendBorrowItem lendBorrowItem;

    public EditParticipantDialog(final Context context, FragmentManager manager, ParticipantItem item) {
        this.context = context;
        this.manager = manager;
        this.item = item;

        Holder holder = new ViewHolder(R.layout.dialog_edit_participant);

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//            TextView textView = (TextView) view.findViewById(R.id.text_view);
//            String clickedAppName = textView.getText().toString();
                //        dialog.dismiss();
                //        Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
                //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
                //        Toast.makeText(MainActivity.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        OnBackPressListener backPressListener = new OnBackPressListener() {
            @Override
            public void onBackPressed(DialogPlus dialogPlus) {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                } else {
                    dialogPlus.dismiss();
                }
            }
        };

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
            }
        };


        showCompleteDialog(holder,
                Gravity.CENTER,
                clickListener,
                itemClickListener,
                dismissListener,
                cancelListener,
                backPressListener,
                false);
    }

    private void showCompleteDialog(Holder holder, int gravity,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    OnBackPressListener backPressListener, boolean expanded) {
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
//        .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOnBackPressListener(backPressListener)
//        .setContentBackgroundResource(R.drawable.corner_background)
                //                .setOutMostMargin(0, 100, 0, 0)
                .create();

        initializeValue();
        autofill();
        dialog.show();
    }

    private void autofill() {
        participantName.setText(previousParticipantName);
        String dues = context.getResources().getString(R.string.Rs) + " " + Long.toString(item.getDues());
        amount.setText(dues);
    }

    private void initializeValue() {
        view = dialog.getHolderView();
        participantName = view.findViewById(R.id.participant_name);
        amount = view.findViewById(R.id.amount);
        settle = view.findViewById(R.id.settle_button);
        done = view.findViewById(R.id.done_action);
        toggler = false;
        previousParticipantName = item.getParticipantname();

        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long due = item.getDues();
                if (item.isInDebt() == ParticipantItem.IS_IN_DEBT) {
                    lendBorrowItem = new LendBorrowItem(item.getParticipantname(),
                            due,
                            "Settled",
                            1,
                            System.currentTimeMillis());
                    item.setDues(0);
                    item.setInDebt(ParticipantItem.NO_DEBT);
                    String settled = "settled";
                    settle.setText(settled);
                    toggler = true;
                } else if (item.isInDebt() == ParticipantItem.HAS_LENDED) {
                    lendBorrowItem = new LendBorrowItem(item.getParticipantname(),
                            -due,
                            "Settled",
                            1,
                            System.currentTimeMillis());
                    item.setDues(0);
                    item.setInDebt(ParticipantItem.NO_DEBT);
                    String settled = "settled";
                    settle.setText(settled);
                    toggler = true;
                } else {
                    String nodue = "no due";
                    settle.setText(nodue);
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LendBorrowDAO.initialiser(context).updateName(previousParticipantName, participantName.getText().toString());
                item.setParticipantname(participantName.getText().toString());
                if (toggler) {
                    LendBorrowDAO.initialiser(context).insertLendBorrowItem(lendBorrowItem);
                }
                ParticipantsDAO.initialiser(context).updateParticipant(item, previousParticipantName);

                //updation in previous views......
                MessageEvent event = new MessageEvent("update_all_data");
                event.setParticipantsName(participantName.getText().toString());
                EventBus.getDefault().post(event);
                dialog.dismiss();
            }
        });

    }


}
