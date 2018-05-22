package com.grandgroup.utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.adapter.SelectItemAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CommonUtils {

    private static final CommonUtils ourInstance = new CommonUtils();
    String itemSelected;

    private CommonUtils() {
    }

    public static CommonUtils getInstance() {
        return ourInstance;
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public void hideKeyboard(AppCompatActivity mContext) {
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean checkAndRequestPermission(Activity activity, String permission, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    REQUEST_CODE);
            return false;
        }
    }


    public void selectDialog(final AppCompatActivity mContext,ArrayList<String> arrayList,String headingText, final OnClickItem onClickItem ) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_select_dialog);
        TextView tv_heading = dialog.findViewById(R.id.tv_heading);
        tv_heading.setText(headingText);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        RecyclerView rv_items = dialog.findViewById(R.id.rv_items);

        SelectItemAdapter adapter = new SelectItemAdapter(arrayList, new SelectItemAdapter.OnClick() {
            @Override
            public void OnClick(String item) {
                itemSelected = item;
                onClickItem.OnClickItem(itemSelected);
                dialog.cancel();
            }
        });
        rv_items.setHasFixedSize(true);
        rv_items.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_items.setLayoutManager(llm);


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });

        dialog.show();
    }

    public Uri createPdf(View view, String formName) {
        Bitmap bitmap = CommonUtils.getInstance().getBitmapFromView(view);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        String targetdir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GrandGroup";
        File file = new File(targetdir);
        if (!file.exists())
            file.mkdir();

        // write the document content
        String targetPdf = targetdir + File.separator + formName + ".pdf";
        File filePath = new File(targetPdf);

        try {
            filePath.createNewFile();
            document.writeTo(new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close the document
        document.close();
        return Uri.fromFile(filePath);
    }

    public interface OnClickItem {
        void OnClickItem(String Item);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures");
        if (!file.exists()) {
            file.mkdir();
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Contact", null);
        return Uri.parse(path);
    }

}
