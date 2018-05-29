package com.grandgroup.utills;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.grandgroup.MyApplication;
import com.grandgroup.R;

public class SnackbarUtil {
    public static void showSuccessLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(MyApplication.getAppContext().getResources().getColor(R.color.green));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showSuccessShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.green));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showErrorLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.rred));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showErrorShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.rred));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showWarningLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.orange));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showWarningShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, 800);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.orange));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        snackbar.show();
    }

    public static void showWarningShortSnackbarTopDialog(Dialog activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(ContextCompat.getColor(MyApplication.getAppContext(), R.color.orange));
        snackbar.show();
    }
}
