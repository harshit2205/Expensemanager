package com.example.users.myexpensemanager1.dialogs;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.users.myexpensemanager1.activities.RecieptImageActivity;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.utils.ConfirmationDailogFrag;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import static com.example.users.myexpensemanager1.utils.GetDateTime.getDate;
import static com.example.users.myexpensemanager1.utils.GetDateTime.getTime;

/**
 * Created by USER on 2/22/2018.
 */

public class ShowTransactionDetailsDialog {

    Context context;
    FragmentManager manager;
    DialogPlus dialog;
    TransactionItem item;
    private TextView purpose, amount, date, time, transactionType;
    private ImageView avatarView;

    public ShowTransactionDetailsDialog(Context context, FragmentManager manager, TransactionItem item) {
        this.context = context;
        this.manager = manager;
        this.item = item;

        Holder holder = new ViewHolder(R.layout.dialog_transaction_detail);

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
                .setCancelable(false)
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
        purpose.setText(item.getItem_name());
        amount.setText(Long.toString(item.getAmount()));
        date.setText(getDate(item.getTimestamp()));
        time.setText(getTime(item.getTimestamp()));
        transactionType.setText(item.getTransactionType());

        switch (item.getTransactionType()) {
            case "Food Expenses":
                avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.food_expenses));
                break;
            case "Movies Expenses":
                avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.movie_expenses));
                break;
            case "Household Expenses":
                avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.household_expenses));
                break;
            case "Tax Expenses":
                avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.tax_expenses));
                break;
            default:
                avatarView.setImageDrawable(context.getResources().getDrawable(R.drawable.other_expenses));
        }

    }

    private void initializeValue() {
        View view = dialog.getHolderView();
        avatarView = (ImageView) view.findViewById(R.id.avatar_view);
        purpose = (TextView) view.findViewById(R.id.purpose);
        amount = (TextView) view.findViewById(R.id.item_amount);
        date = (TextView) view.findViewById(R.id.transaction_date);
        time = (TextView) view.findViewById(R.id.transaction_time);
        transactionType = (TextView) view.findViewById(R.id.transaction_type);
        Button recieptImage = (Button) view.findViewById(R.id.reciept_image);
        Button delete = (Button) view.findViewById(R.id.delete_transaction);

        recieptImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = item.getFilePath();
                Intent intent = new Intent(context, RecieptImageActivity.class);
                intent.putExtra("imagePath", str);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDailogFrag frag = ConfirmationDailogFrag.getConfirmationFrag(item.getId(),
                        ConfirmationDailogFrag.TRANSACTION_DELETION);
                frag.show(manager, "confirmation");
                dialog.dismiss();
            }
        });

    }

}
