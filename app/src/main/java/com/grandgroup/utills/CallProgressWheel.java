package com.grandgroup.utills;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.grandgroup.R;


public class CallProgressWheel {

    private static ProgressDialog progressDialog;

    /*
      Displays custom loading dialog
     */
    public static void showLoadingDialog(Context context) {
        try {
            if (isDialogShowing()) {
                dismissLoadingDialog();
            }

            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                }
            }

            progressDialog = new ProgressDialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            progressDialog.show();
            WindowManager.LayoutParams layoutParams = progressDialog.getWindow().getAttributes();
            layoutParams.dimAmount = 0.5f;
            progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.progresswheel);

            ImageView imageView = progressDialog.findViewById(R.id.iv_image);
            Glide.with(context).load(R.drawable.loading).into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismissLoadingDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isDialogShowing() {
        try {
            return progressDialog != null && progressDialog.isShowing();
        } catch (Exception e) {
            return false;
        }
    }
}
