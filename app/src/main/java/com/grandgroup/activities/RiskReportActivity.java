package com.grandgroup.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.grandgroup.R;
import com.grandgroup.model.RiskReportModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.CommonUtils;
import com.grandgroup.utills.PermissionUtils;
import com.grandgroup.views.CustomDateDialog;
import com.grandgroup.views.CustomTimeDialog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grandgroup.utills.AppConstant.CAMERA_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.CAMERA_REQUEST;
import static com.grandgroup.utills.AppConstant.GALLERY_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.GALLERY_REQUEST;
import static com.grandgroup.utills.AppConstant.SAVE_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.SIGNATRUE_REQUEST;
import static com.grandgroup.utills.AppConstant.WRITE_PERMISSIONS_REQUEST;

public class RiskReportActivity extends AppCompatActivity {
    private RiskReportModel riskReportObject;
    private Bitmap signBitmap = null, photoOfHazardBitmap;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_event_date)
    TextView tv_event_date;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_select_likelihood)
    TextView tvSelectedLikelihood;
    @BindView(R.id.tv_select_consq)
    TextView tv_select_consq;
    @BindView(R.id.et_control_eff)
    TextView et_control_eff;
    @BindView(R.id.lay_screenshot)
    ConstraintLayout lay_screenshot;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.my_toolbar)
    RelativeLayout myToolbar;
    @BindView(R.id.tv_report_desc_title)
    TextView tvReportDescTitle;
    @BindView(R.id.tv_report_desc)
    EditText tvReportDesc;
    @BindView(R.id.tv_report_date_time_title)
    TextView tvReportDateTimeTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.lay_photo)
    ConstraintLayout layPhoto;
    @BindView(R.id.tv_likelihood)
    TextView tvLikelihood;
    @BindView(R.id.tv_consequence)
    TextView tvConsequence;
    @BindView(R.id.tv_controls)
    TextView tvControls;
    @BindView(R.id.et_controls)
    EditText etControls;
    @BindView(R.id.tv_control_eff)
    TextView tvControlEff;
    @BindView(R.id.tv_action_plan)
    TextView tvActionPlan;
    @BindView(R.id.et_action_plan)
    EditText etActionPlan;
    @BindView(R.id.tv_reported_by)
    TextView tvReportedBy;
    @BindView(R.id.et_reported_by)
    EditText etReportedBy;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_signature)
    ImageView ivSignature;
    @BindView(R.id.lay_signature)
    ConstraintLayout laySignature;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    private AppCompatActivity mContext;
    private ArrayList<String> likeArray, consequenceArray, controlEffectivenessArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_report);
        setInitialData();
    }

    private void setInitialData() {
        mContext = RiskReportActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Risk Report");
        //Like Array
        likeArray = new ArrayList<>();
        likeArray.add("Rare");
        likeArray.add("Slight");
        likeArray.add("Possible");
        likeArray.add("Likely");
        likeArray.add("almost Certain");

        //ConsequencesArray
        consequenceArray = new ArrayList<>();
        consequenceArray.add("Insignificant");
        consequenceArray.add("Minor");
        consequenceArray.add("Moderate");
        consequenceArray.add("Major");
        consequenceArray.add("Severe");

        //controlEffectivenessArray
        controlEffectivenessArray = new ArrayList<>();
        controlEffectivenessArray.add("Poor");
        controlEffectivenessArray.add("Fair");
        controlEffectivenessArray.add("Good");
        controlEffectivenessArray.add("Effective");
        if (getIntent().getSerializableExtra("riskReportObject") != null) {
            btnSave.setVisibility(View.GONE);
            btnEmail.setVisibility(View.GONE);
            riskReportObject = (RiskReportModel) getIntent().getSerializableExtra("riskReportObject");
            tvReportDesc.setText(riskReportObject.getRisk_description());
            tv_event_date.setText(riskReportObject.getRisk_date());
            etLocation.setText(riskReportObject.getRisk_location());
            tvSelectedLikelihood.setText(riskReportObject.getRisk_likelihood());
            tv_select_consq.setText(riskReportObject.getRisk_consequence());
            etControls.setText(riskReportObject.getRisk_control());
            et_control_eff.setText(riskReportObject.getRisk_control_effectiveness());
            etActionPlan.setText(riskReportObject.getRisk_action_plan());
            etReportedBy.setText(riskReportObject.getRisk_reported_by());
            Glide.with(mContext).load(riskReportObject.getSignature_file())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.default_user)
                            .error(R.drawable.default_user)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform()).into(ivSignature);

            Glide.with(mContext).load(riskReportObject.getRisk_file())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.default_user)
                            .error(R.drawable.default_user)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform()).into(iv_image);
        }
    }

    @OnClick({R.id.btn_back, R.id.tv_event_date, R.id.lay_photo, R.id.tv_select_likelihood, R.id.tv_select_consq,
            R.id.et_control_eff, R.id.btn_email, R.id.btn_save, R.id.iv_signature})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.tv_event_date:
                CustomDateDialog.getInstance().DatePicker(mContext, new CustomDateDialog.DateDialogListener() {
                    @Override
                    public void onOkayClick(final int date, final int month, final int year) {
                        CustomTimeDialog.getInstance().timePicker(mContext, new CustomTimeDialog.TimeDialogListener() {
                            @Override
                            public void onOkayClick(String twentyFourTime, String TwelveHourTime) {
                                String dayOfMonth = "", monthOfYear = "", selectedDate, formattedDate = "";

                                monthOfYear = (month + 1 < 10) ? "0" + (month + 1) : "" + (month + 1);
                                dayOfMonth = (date < 10) ? "0" + date : "" + date;
                                selectedDate = monthOfYear + " " + dayOfMonth + ", " + year + " " + TwelveHourTime;
                                Log.e("day", selectedDate);
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                tv_event_date.setText(formattedDate);
                            }
                        });
                    }
                });
                break;
            case R.id.lay_photo:
                selectImage();
                break;
            case R.id.tv_select_likelihood:
                CommonUtils.getInstance().selectDialog(mContext, likeArray, "Likelihood", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        tvSelectedLikelihood.setText(Item);
                    }
                });
                break;
            case R.id.tv_select_consq:
                CommonUtils.getInstance().selectDialog(mContext, consequenceArray, "Select Consequence", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        tv_select_consq.setText(Item);
                    }
                });
                break;
            case R.id.et_control_eff:
                CommonUtils.getInstance().selectDialog(mContext, controlEffectivenessArray, "Controls Effectiveness", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        et_control_eff.setText(Item);
                    }
                });
                break;
            case R.id.btn_email:
                if (PermissionUtils.requestPermission(mContext, WRITE_PERMISSIONS_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    createSendForm();
                }
                break;
            case R.id.btn_save:
               /* if (PermissionUtils.requestPermission(mContext, SAVE_PERMISSIONS_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Uri uri = CommonUtils.getInstance().createPdf(lay_screenshot, "Risk_Report_Form");
                    Toast.makeText(mContext, "Form Saved Successfully", Toast.LENGTH_SHORT).show();
                }*/
                uploaddata();
                break;
            case R.id.iv_signature:
                startActivityForResult(new Intent(mContext, SignatureActivity.class), SIGNATRUE_REQUEST);
                mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
        }
    }

    private void uploaddata() {
        CallProgressWheel.showLoadingDialog(mContext);
        ParseObject riskReportObject = new ParseObject("RiskReport");
        riskReportObject.put("risk_likelihood", tvSelectedLikelihood.getText().toString());
        riskReportObject.put("risk_action_plan", etActionPlan.getText().toString());
        riskReportObject.put("risk_location", etLocation.getText().toString());
        riskReportObject.put("risk_description", tvReportDesc.getText().toString());
        riskReportObject.put("risk_control_effectiveness", et_control_eff.getText().toString());
        riskReportObject.put("risk_control", etControls.getText().toString());
        riskReportObject.put("risk_reported_by", etReportedBy.getText().toString());
        riskReportObject.put("risk_consequence", tv_select_consq.getText().toString());
        riskReportObject.put("risk_date", tv_event_date.getText().toString());
        if (signBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signBitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            byte[] image = stream.toByteArray();
            ParseFile file = new ParseFile("ile.png", image);
            riskReportObject.put("risk_file", file);
        }

        if (photoOfHazardBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoOfHazardBitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            byte[] image = stream.toByteArray();
            ParseFile file = new ParseFile("ile.png", image);
            riskReportObject.put("signature_file", file);
        }
        riskReportObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                CallProgressWheel.dismissLoadingDialog();
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Report form saved successfully!", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getApplicationContext(), "Please, Try Again", Toast.LENGTH_LONG).show();

            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                if (data != null) {
                    signBitmap = (Bitmap) data.getExtras().get("data");

                    Uri tempUri = CommonUtils.getInstance().getImageUri(mContext, signBitmap);
                    Glide.with(mContext).load(tempUri).into(iv_image);
                }
            } else if (requestCode == GALLERY_REQUEST) {
                if (data != null) {
                    try {
                        Uri tempUri = data.getData();
                        Glide.with(mContext).load(tempUri).into(iv_image);
                        signBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), tempUri);
                        //  iv_image.setImageBitmap(bitmap);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == SIGNATRUE_REQUEST) {
                byte[] byteArray = data.getByteArrayExtra("byteArray");
                photoOfHazardBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                ivSignature.setImageBitmap(photoOfHazardBitmap);
            }
        }
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
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    GalleryIntent();
                }
                break;
            case WRITE_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, WRITE_PERMISSIONS_REQUEST, grantResults)) {
                    createSendForm();
                }
                break;
            case SAVE_PERMISSIONS_REQUEST:
                Uri uri = CommonUtils.getInstance().createPdf(lay_screenshot, "Risk_Report_Form");
                Toast.makeText(mContext, "Form Saved Successfully", Toast.LENGTH_SHORT).show();
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

    private void createSendForm() {
        CallProgressWheel.showLoadingDialog(mContext);
        Uri uri = CommonUtils.getInstance().createPdf(lay_screenshot, "Risk_Report_Form");

        ShareCompat.IntentBuilder.from(mContext)
                .setType("message/rfc822")
                .setSubject("Risk Report Form")
                .setText("Risk Report Form.")
                .setStream(uri)
                .setChooserTitle("Share Form")
                .startChooser();
        CallProgressWheel.dismissLoadingDialog();
    }

}
