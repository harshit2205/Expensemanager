package com.example.users.myexpensemanager1.dialogs;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.users.myexpensemanager1.activities.Main2Activity;
import com.example.users.myexpensemanager1.dao.TransactionDAO;
import com.example.users.myexpensemanager1.models.MessageEvent;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by USER on 2/19/2018.
 */

@SuppressLint("ValidFragment")
public class AddTransactionDialog extends BaseDialog {

    EditText purpose, itemcost;
    BetterSpinner transactionType;
    int itemId;
    private Context context;
    private Button date, time;
    private File imageFile;
    private String filePath;
    private FragmentManager manager;
    private DialogPlus dialog;
    private boolean isEditing;
    private String[] TRANSACTION_TYPE = new String[]{
            "Food Expenses", "Movies Expenses", "Household Expenses", "Tax Expenses", "Other Expenses"
    };


    public AddTransactionDialog(final Context context, final FragmentManager manager) {
        this.context = context;
        this.manager = manager;
        CURRENT_YEAR = now.get(Calendar.YEAR);
        CURRENT_MONTH = now.get(Calendar.MONTH);
        CURRENT_DATE = now.get(Calendar.DATE);
        CURREN_HRS = now.get(Calendar.HOUR_OF_DAY);
        CURRENT_MINS = now.get(Calendar.MINUTE);
        CURRENT_SEC = now.get(Calendar.SECOND);

        Holder holder = new ViewHolder(R.layout.dialog_add_transaction);
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
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
                switch (view.getId()) {
                    case R.id.date_input:
                        DatePickerDialog dpd = datePicker();
                        dpd.show(manager, "date_picker_dialog");
                        break;
                    case R.id.time_input:
                        TimePickerDialog tpd = timePicker();
                        tpd.show(manager, "time_picker_dialog");
                        break;
                    case R.id.take_snapshot:
                        takeSnapshot();
                        break;
                    case R.id.footer_cancel_button:
                        dialog.dismiss();
                        break;
                    case R.id.footer_add_button:
                        if (inputcheck()) {
                            addTransaction();
                        }
                        break;
                }
            }
        };


        showCompleteDialog(holder,
                Gravity.TOP,
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
                .setHeader(R.layout.header)
                .setFooter(R.layout.footer)
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

        viewElementInitialiser(dialog);
        dialog.show();
    }

    private void viewElementInitialiser(DialogPlus dialog) {
        View view = dialog.getHolderView();
        date = (Button) view.findViewById(R.id.date_input);
        time = (Button) view.findViewById(R.id.time_input);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.dropdown_item_spinner, TRANSACTION_TYPE);
        transactionType = (BetterSpinner) view.findViewById(R.id.type_chooser);
        transactionType.setAdapter(adapter);
        purpose = (EditText) view.findViewById(R.id.item_name);
        itemcost = (EditText) view.findViewById(R.id.item_cost);

        View headerView = dialog.getHeaderView();
        TextView header_textView = (TextView) headerView.findViewById(R.id.header_text);
        header_textView.setText("Add Transaction");

    }

    public void viewElementEditor(TransactionItem item) {
        purpose.setText(item.getItem_name());
        itemcost.setText(Long.toString(item.getAmount()));
        date.setText(getDate(item.getTimestamp()));
        time.setText(getTime(item.getTimestamp()));
        transactionType.setText(item.getTransactionType());
        now.setTimeInMillis(item.getTimestamp());
        itemId = item.getId();
        isEditing = true;

        CURRENT_YEAR = now.get(Calendar.YEAR);
        CURRENT_MONTH = now.get(Calendar.MONTH);
        CURRENT_DATE = now.get(Calendar.DATE);
        CURREN_HRS = now.get(Calendar.HOUR_OF_DAY);
        CURRENT_MINS = now.get(Calendar.MINUTE);
        CURRENT_SEC = now.get(Calendar.SECOND);
    }

    public boolean inputcheck() {
        return !(purpose.getText().toString().equals("") || itemcost.getText().toString().equals("")
                || transactionType.getText().toString().equals(""));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        CURRENT_DATE = dayOfMonth;
        CURRENT_MONTH = monthOfYear;
        CURRENT_YEAR = year;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time.setText(hourOfDay + ":" + minute);
        CURREN_HRS = hourOfDay;
        CURRENT_MINS = minute;
        CURRENT_SEC = second;
    }

    private void takeSnapshot() {
        imageFile = new File(Environment.getExternalStorageDirectory() + "/app.myexpensemanager1", "file" + System.currentTimeMillis() + ".jpg");
        Intent i = new Intent();
        i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        context.startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && imageFile.exists()) {
            filePath = imageFile.getAbsolutePath();
        }
    }

    private void addTransaction() {
        now.set(CURRENT_YEAR, CURRENT_MONTH, CURRENT_DATE, CURREN_HRS, CURRENT_MINS, CURRENT_SEC);
        TransactionItem transactionItem = new TransactionItem(
                Main2Activity.userName,
                purpose.getText().toString(),
                Long.parseLong(itemcost.getText().toString()),
                now.getTimeInMillis(),
                "",
                filePath,
                transactionType.getText().toString());

        if (isEditing) {
            transactionItem.setId(itemId);
            TransactionDAO.initialiser(context).updateTransaction(transactionItem);
        } else {
            TransactionDAO transactionDAO = TransactionDAO.initialiser(context);
            transactionDAO.insertTransaction(transactionItem);
        }
        EventBus.getDefault().post(new MessageEvent("transaction_update"));
        dialog.dismiss();
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

    @Override
    public void dismiss() {
        super.dismiss();
        hideKeyboard();
    }

}
