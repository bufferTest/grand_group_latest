package com.grandgroup.utills;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormatSymbols;

public class GrandGroupHelper {

    private static GrandGroupHelper grandGroupHelper = null;
    public Context context;
    public ProgressDialog progress;

    public void setContext(Context ctx) {

        context = ctx;
    }


    private GrandGroupHelper(Context ctx) {

        context = ctx;
    }

    public static GrandGroupHelper grandGroupHelper(Context context) {
        if (grandGroupHelper == null) {
            grandGroupHelper = new GrandGroupHelper(context);
            grandGroupHelper.setContext(context);

        }
        return grandGroupHelper;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("States.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void showSimpleDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public boolean CheckIsConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public void noInternet()
    {
        Toast.makeText(context,"Sorry, Your device is not connected to internet",
                Toast.LENGTH_SHORT).show();

    }


    public void showLoader(Context context) {
        if(context!= null) {
            progress = new ProgressDialog(context);
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }
    }

    public void showLoader(Context context, String msg) {
        if(context!= null) {
            progress = new ProgressDialog(context);
            progress.setMessage(msg);
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }
    }

    public void hideKeyboard(Activity context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = context.getCurrentFocus();
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public String fetchHtmlFromAssets(Context context, String filename) {


            StringBuilder buf = new StringBuilder();
        try {
            InputStream json = context.getAssets().open(filename);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
         //  newSrng = buf.toString();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }




    public String saveToExternalStorage(Bitmap bitmapImage, String filename){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();
        try{
        OutputStream file = new FileOutputStream(new File(path + File.separator + filename));
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, file);

            file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return dir.getAbsolutePath();


    }

    private String savetoInternalStorage(Bitmap bitmapImage, String imgName)
    {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imgName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }




    public void loadImagesAsset() {

            AssetManager assetManager = context.getAssets();
            String[] files = null;
            try {
                files = assetManager.list("");
            } catch (IOException e) {
            }

            for (String filename : files) {
                if (filename.endsWith("png")) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = assetManager.open(filename);
                        File outFile = new File(context.getExternalFilesDir(null), filename);
                        System.out.println("OUTPUT PATH IS "+outFile);
                        out = new FileOutputStream(outFile);
                        copyFile(in, out);

                    } catch (IOException e) {
                        Log.e("tag", "Failed to copy asset file: " + filename, e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                // NOOP
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                // NOOP
                            }
                        }
                    }
                }
            }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);

        }
    }

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

}
