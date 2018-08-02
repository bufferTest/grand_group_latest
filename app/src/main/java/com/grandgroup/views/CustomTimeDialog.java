package com.grandgroup.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;

import com.grandgroup.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomTimeDialog {
    private static final CustomTimeDialog ourInstance = new CustomTimeDialog();
    private int timeFromHour;
    private int timeFromMinute;
    private String timeFromFormat;

    private CustomTimeDialog() {
    }

    public static CustomTimeDialog getInstance() {
        return ourInstance;
    }

    public void timePicker(Context mContext, final TimeDialogListener timeDialogListener) {
        final Dialog dialog = new Dialog(mContext);
        Calendar c = Calendar.getInstance();
        timeFromHour = c.get(Calendar.HOUR_OF_DAY);
        timeFromMinute = c.get(Calendar.MINUTE);
        timeFromFormat = (c.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_time_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        final TimePicker simpleTimePicker = dialog.findViewById(R.id.timepicker);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);

        simpleTimePicker.setIs24HourView(false);
       /* simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeDialogListener.onTimeSet(hourOfDay, minute);
                timeFromHour = hourOfDay;
                timeFromMinute = minute;
            }
        });*/

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFromHour = simpleTimePicker.getCurrentHour();
                timeFromMinute = simpleTimePicker.getCurrentMinute();
                if (timeFromHour > 0 && timeFromHour < 12) {
                    timeFromFormat = "AM";
                } else if (timeFromHour > 12) {
                    timeFromHour -= 12;
                    timeFromFormat = "PM";
                } else if (timeFromHour == 12) {
                    timeFromFormat = "PM";
                } else if (timeFromHour == 0) {
                    timeFromFormat = "AM";
                    timeFromHour = 12;
                }
                DecimalFormat decimalFormat = new DecimalFormat("00");

                String twelveHourTime = new StringBuilder().append(decimalFormat.format(timeFromHour)).append(":").append(decimalFormat.format(timeFromMinute)).append(" ").append(timeFromFormat).toString();
                Log.e("twelvetime", twelveHourTime);
                String twentyFourTime = convertTo24Format(twelveHourTime);
                timeDialogListener.onOkayClick(twentyFourTime, twelveHourTime);
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

    public String convertTo24Format(String input) {
        DateFormat df = new SimpleDateFormat("hh:mm aa", Locale.US);
        DateFormat outputformat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        Date date = null;
        String output = "";
        try {
            date = df.parse(input);
            output = outputformat.format(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public interface TimeDialogListener {

        void onOkayClick(String twentyFourTime, String TwelveHourTime);
    }
}

