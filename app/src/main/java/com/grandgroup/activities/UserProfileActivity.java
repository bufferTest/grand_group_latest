package com.grandgroup.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.grandgroup.R;
import com.grandgroup.model.UserProfileBean;
import com.grandgroup.utills.AppConstant;
import com.grandgroup.utills.AppPrefrence;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.CommonUtils;
import com.grandgroup.utills.PermissionUtils;
import com.grandgroup.views.CustomTextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grandgroup.utills.AppConstant.CAMERA_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.CAMERA_REQUEST;
import static com.grandgroup.utills.AppConstant.GALLERY_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.GALLERY_REQUEST;
import static com.grandgroup.utills.AppConstant.SAVE_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.WRITE_PERMISSIONS_REQUEST;

public class UserProfileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.iv_user_pic)
    ImageView ivUserPic;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    private AppCompatActivity mContext;
    private ParseUser parseUser;
    private UserProfileBean userProfileObj;
    private Bitmap scaled = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setInitialData();
    }

    private void setInitialData() {
        ButterKnife.bind(this);
        mContext = UserProfileActivity.this;
        tvTitle.setText("User Profile");

        Gson gson = new Gson();
        String json = AppPrefrence.init(mContext).getString(AppConstant.USER_PROFILE);
        userProfileObj = gson.fromJson(json, UserProfileBean.class);

        etFirstName.setText(userProfileObj.getUserFirstName());
        etFirstName.setSelection(etFirstName.length());

        etLastName.setText(userProfileObj.getUserLastName());
        etEmail.setText(userProfileObj.getUserEmail());

        if (userProfileObj.getUserProfilePicUrl() != null) {
            if (!userProfileObj.getUserProfilePicUrl().equals(""))
                Glide.with(mContext).load(userProfileObj.getUserProfilePicUrl())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.default_user)
                                .error(R.drawable.default_user)
                                .dontAnimate()
                                .centerCrop()
                                .dontTransform()).into(ivUserPic);
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_save, R.id.iv_user_pic})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.btn_save:
                updateProfile(etFirstName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(), scaled);
                break;
            case R.id.iv_user_pic:
                selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(options,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            if (PermissionUtils.requestPermission(mContext, CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
                                startCamera();
                            }
                        } else if (options[item].equals("Choose from Gallery")) {
                            if (PermissionUtils.requestPermission(mContext, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                GalleryIntent();
                            }

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    GalleryIntent();
                }
                break;
        }
    }

    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void GalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int nh = (int) (photo.getHeight() * (512.0 / photo.getWidth()));
                scaled = Bitmap.createScaledBitmap(photo, 512, nh, true);
                ivUserPic.setImageBitmap(scaled);
            } else if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
                scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
                ivUserPic.setImageBitmap(scaled);
            }
        }
    }


    private void updateProfile(final String userFirstName, final String userLastName, final String userEmail, final Bitmap scaled) {
        CallProgressWheel.showLoadingDialog(mContext);
        parseUser = ParseUser.getCurrentUser();
        parseUser.put(getString(R.string.userFirstName), userFirstName);
        parseUser.put(getString(R.string.userLastName), userLastName);
        parseUser.put(getString(R.string.userEmail), userEmail);
        if (scaled != null) {
            Bitmap scaledProfilePic = Bitmap.createScaledBitmap(scaled, 320, 320, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaledProfilePic.compress(Bitmap.CompressFormat.PNG, 70, stream);
            byte[] image = stream.toByteArray();
            ParseFile file = new ParseFile("ile.png", image);
            parseUser.put(getString(R.string.profilePic), file);
        }
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    CallProgressWheel.dismissLoadingDialog();
                                UserProfileBean userProfileBean = new UserProfileBean();
                                userProfileBean.setUserFirstName(userFirstName);
                                userProfileBean.setUserLastName(userLastName);
                                userProfileBean.setUserEmail(userEmail);
                                ParseFile postImage = parseUser.getParseFile(getString(R.string.profilePic));
                                if (postImage != null)
                                    userProfileBean.setUserProfilePicUrl(postImage.getUrl());
                                else
                                    userProfileBean.setUserProfilePicUrl("");
                                Gson gson = new Gson();
                                String json = gson.toJson(userProfileBean);
                                AppPrefrence.init(mContext).putString(AppConstant.USER_PROFILE, json);
                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                } else {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText(getApplicationContext(), "Please, Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
