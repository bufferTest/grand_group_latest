package com.grandgroup.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.grandgroup.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CustomDateDialog {
    private static final CustomDateDialog ourInstance = new CustomDateDialog();
    private int day;
    private int month;
    private int year;
    /*private Boolean isTimeChanged;*/
    private DatePicker datepicker;

    private CustomDateDialog() {
    }

    public static CustomDateDialog getInstance() {
        return ourInstance;
    }

    public void DatePicker(Context mContext, final DateDialogListener dateDialogListener) {
        final Dialog dialog = new Dialog(mContext);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_date_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        datepicker = dialog.findViewById(R.id.datepicker);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datepicker.getDayOfMonth();
                int month = datepicker.getMonth();
                int year = datepicker.getYear();
                dateDialogListener.onOkayClick(day, month, year);
                dialog.dismiss();

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public interface DateDialogListener {
        void onOkayClick(int date, int month, int year);
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}

