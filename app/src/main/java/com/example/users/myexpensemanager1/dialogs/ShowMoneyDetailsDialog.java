package com.example.users.myexpensemanager1.dialogs;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.utils.ConfirmationDailogFrag;
import com.example.users.myexpensemanager1.utils.GetDateTime;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by USER on 2/23/2018.
 */

public class ShowMoneyDetailsDialog {

    public static int MONTHLY = 1;
    public static int ONETIME = 2;
    Context context;
    FragmentManager manager;
    DialogPlus dialog;
    MoneyItem item;
    private int repeatableint;
    private TextView purpose, amount, date, time, repeatable;
    private ImageView avatarView;

    public ShowMoneyDetailsDialog(Context context, FragmentManager manager, MoneyItem item, int repeatableint) {
        this.context = context;
        this.manager = manager;
        this.item = item;
        this.repeatableint = repeatableint;

        Holder holder = new ViewHolder(R.layout.dialog_money_detail);

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
                dialogPlus.dismiss();
            }
        };

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
            }
        };


        showCompleteDialog(holder,
                Gravity.BOTTOM,
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
        purpose.setText(item.getDescription());
        amount.setText(Long.toString(item.getAmount()));
        date.setText(GetDateTime.getDate(item.getTimestamp()));
        time.setText(GetDateTime.getTime(item.getTimestamp()));
        if (repeatableint == MONTHLY) {
            repeatable.setText("Monthly earning");
        } else if (repeatableint == ONETIME) {
            repeatable.setText("One time earning");
        }
    }

    private void initializeValue() {
        View view = dialog.getHolderView();
        avatarView = (ImageView) view.findViewById(R.id.avatar_view);
        purpose = (TextView) view.findViewById(R.id.purpose);
        amount = (TextView) view.findViewById(R.id.item_amount);
        date = (TextView) view.findViewById(R.id.transaction_date);
        time = (TextView) view.findViewById(R.id.transaction_time);
        repeatable = (TextView) view.findViewById(R.id.repeatable);
        Button delete = (Button) view.findViewById(R.id.delete_transaction);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(item.getId(),
                        ConfirmationDailogFrag.MONEY_DELETION);
                frag.setRepeated(repeatableint);
                frag.show(manager, "confirmation");
                dialog.dismiss();
            }
        });

    }
}
