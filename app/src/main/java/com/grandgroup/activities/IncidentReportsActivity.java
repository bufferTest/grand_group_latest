package com.grandgroup.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.grandgroup.R;
import com.grandgroup.model.IncidentModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.CommonUtils;
import com.grandgroup.utills.GrandGroupHelper;
import com.grandgroup.utills.PermissionUtils;
import com.grandgroup.views.CustomDateDialog;
import com.grandgroup.views.CustomEditText;
import com.grandgroup.views.CustomTextView;
import com.grandgroup.views.CustomTimeDialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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

import static com.grandgroup.utills.AppConstant.SAVE_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.SIGNATRUE_REQUEST;
import static com.grandgroup.utills.AppConstant.SIGNATRUE_REQUEST_AMB;
import static com.grandgroup.utills.AppConstant.WRITE_PERMISSIONS_REQUEST;

public class IncidentReportsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_affected)
    CustomEditText etAffected;
    @BindView(R.id.rb_contractor)
    RadioButton rbContractor;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.tv_occurence_value)
    CustomTextView tvOccurenceValue;
    @BindView(R.id.rb_ceased_yes)
    RadioButton rbCeasedYes;
    @BindView(R.id.rb_ceased_no)
    RadioButton rbCeasedNo;
    @BindView(R.id.rg_ceased)
    RadioGroup rgCeased;
    @BindView(R.id.tv_ceased_time_value)
    CustomTextView tvCeasedTimeValue;
    @BindView(R.id.tv_report_time_value)
    CustomTextView tvReportTimeValue;
    @BindView(R.id.tv_occurence_date)
    CustomTextView tvOccurenceDate;
    @BindView(R.id.rb_occ_yes)
    RadioButton rbOccYes;
    @BindView(R.id.rb_occ_no)
    RadioButton rbOccNo;
    @BindView(R.id.rg_occurence)
    RadioGroup rgOccurence;
    @BindView(R.id.et_firstname)
    CustomEditText etFirstname;
    @BindView(R.id.et_surname)
    CustomEditText etSurname;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.et_home_address)
    CustomEditText etHomeAddress;
    @BindView(R.id.et_state)
    CustomEditText etState;
    @BindView(R.id.et_postcode)
    CustomEditText etPostcode;
    @BindView(R.id.et_home_phone)
    CustomEditText etHomePhone;
    @BindView(R.id.et_mobile_no)
    CustomEditText etMobileNo;
    @BindView(R.id.et_birthday)
    CustomTextView etBirthday;
    @BindView(R.id.et_occupation)
    CustomEditText etOccupation;
    @BindView(R.id.et_workplace)
    CustomEditText etWorkplace;
    @BindView(R.id.et_incident)
    CustomEditText etIncident;
    @BindView(R.id.rb_miss)
    RadioButton rbMiss;
    @BindView(R.id.rb_incident)
    RadioButton rbIncident;
    @BindView(R.id.rb_hazard)
    RadioButton rbHazard;
    @BindView(R.id.rb_contact)
    RadioButton rbContact;
    @BindView(R.id.rb_issue)
    RadioButton rbIssue;
    @BindView(R.id.rg_event_class)
    RadioGroup rgEventClass;

    @BindView(R.id.rg_warning_sign)
    RadioGroup rgWarningSign;
    @BindView(R.id.rb_warning_yes)
    RadioButton rbWarningYes;
    @BindView(R.id.rb_warning_no)
    RadioButton rbWarningNo;
    @BindView(R.id.et_brief)
    CustomEditText etBrief;
    @BindView(R.id.et_description)
    CustomEditText etDescription;
    @BindView(R.id.et_action)
    CustomEditText etAction;
    @BindView(R.id.et_addres)
    CustomEditText etPersonAddress;
    @BindView(R.id.et_injury)
    CustomTextView etInjury;
    @BindView(R.id.et_breakdown_others)
    CustomEditText etBreakdownOther;
    @BindView(R.id.et_illness)
    CustomTextView etIllness;
    @BindView(R.id.et_bodily)
    CustomEditText etBodily;
    @BindView(R.id.et_mark)
    CustomEditText etMark;
    @BindView(R.id.et_mechanism)
    CustomTextView etMechanism;
    @BindView(R.id.et_others)
    CustomEditText etOthers;
    @BindView(R.id.et_observe)
    CustomEditText etObserve;
    @BindView(R.id.rb_third_yes)
    RadioButton rbThirdYes;
    @BindView(R.id.rb_third_no)
    RadioButton rbThirdNo;
    @BindView(R.id.rg_third_party)
    RadioGroup rgThirdParty;
    @BindView(R.id.et_third_report)
    CustomEditText etThirdReport;
    @BindView(R.id.rb_damage_yes)
    RadioButton rbDamageYes;
    @BindView(R.id.rb_damage_no)
    RadioButton rbDamageNo;
    @BindView(R.id.rg_prop_damage)
    RadioGroup rgPropDamage;
    @BindView(R.id.et_damage_adv)
    CustomEditText etDamageAdv;
    @BindView(R.id.et_damage_veh)
    CustomEditText etDamageVeh;
    @BindView(R.id.et_breakdown)
    CustomTextView etBreakdown;
    @BindView(R.id.rb_attend_yes)
    RadioButton rbAttendYes;
    @BindView(R.id.rb_attend_no)
    RadioButton rbAttendNo;
    @BindView(R.id.rg_attend_affe)
    RadioGroup rgAttendAffe;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_aid_yes)
    RadioButton rbAidYes;
    @BindView(R.id.rb_aid_no)
    RadioButton rbAidNo;
    @BindView(R.id.rg_first_aid)
    RadioGroup rgFirstAid;
    @BindView(R.id.et_aid_name)
    CustomEditText etAidName;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.et_injury_detail)
    CustomEditText etInjuryDetail;
    @BindView(R.id.et_med_center)
    TextView etMedCenter;
    @BindView(R.id.et_date_atten)
    TextView etDateAtten;
    @BindView(R.id.rb_amb_yes)
    RadioButton rbAmbYes;
    @BindView(R.id.rb_amb_no)
    RadioButton rbAmbNo;
    @BindView(R.id.rg_ambulance)
    RadioGroup rgAmbulance;
    @BindView(R.id.et_amb_req)
    CustomEditText etAmbReq;
    @BindView(R.id.et_amb_per_name)
    CustomEditText etAmbPerName;
    @BindView(R.id.iv_amb_per_sign)
    ImageView ivAmbPerSign;
    @BindView(R.id.et_amb_date)
    TextView etAmbDate;
    @BindView(R.id.rb_weather_yes)
    RadioButton rbWeatherYes;
    @BindView(R.id.rb_weather_no)
    RadioButton rbWeatherNo;
    @BindView(R.id.rg_weather)
    RadioGroup rgWeather;
    @BindView(R.id.et_weather_cond)
    CustomEditText etWeatherCond;
    @BindView(R.id.rb_drug_yes)
    RadioButton rbDrugYes;
    @BindView(R.id.rb_drug_no)
    RadioButton rbDrugNo;
    @BindView(R.id.rg_drug_affect)
    RadioGroup rgDrugAffect;
    @BindView(R.id.et_footwear)
    CustomEditText etFootwear;
    @BindView(R.id.et_eyewear)
    CustomEditText etEyewear;
    @BindView(R.id.et_carrying)
    CustomEditText etCarrying;
    @BindView(R.id.rb_cctv_yes)
    RadioButton rbCctvYes;
    @BindView(R.id.rb_cctv_no)
    RadioButton rbCctvNo;
    @BindView(R.id.rg_cctv)
    RadioGroup rgCctv;
    @BindView(R.id.rb_photos_yes)
    RadioButton rbPhotosYes;
    @BindView(R.id.rb_photos_no)
    RadioButton rbPhotosNo;
    @BindView(R.id.rg_photos)
    RadioGroup rgPhotos;
    @BindView(R.id.rb_wand_yes)
    RadioButton rbWandYes;
    @BindView(R.id.rb_wand_no)
    RadioButton rbWandNo;
    @BindView(R.id.rg_wand_report)
    RadioGroup rgWandReport;
    @BindView(R.id.rb_wet_yes)
    RadioButton rbWetYes;
    @BindView(R.id.rb_wet_no)
    RadioButton rbWetNo;
    @BindView(R.id.rg_wet_weather)
    RadioGroup rgWetWeather;
    @BindView(R.id.et_comment)
    CustomEditText etComment;
    @BindView(R.id.rb_crunches)
    RadioButton rbCrunches;
    @BindView(R.id.rb_stick)
    RadioButton rbStick;
    @BindView(R.id.rb_frame)
    RadioButton rbFrame;
    @BindView(R.id.rb_wheelchair)
    RadioButton rbWheelchair;
    @BindView(R.id.rb_motorised)
    RadioButton rbMotorised;
    @BindView(R.id.rg_incident_specs)
    RadioGroup rgIncidentSpecs;
    @BindView(R.id.et_notes)
    CustomEditText etNotes;
    @BindView(R.id.lay_screenshot)
    ConstraintLayout layScreenshot;
    private IncidentModel incidentReportObject;
    private AppCompatActivity mContext;
    private Bitmap signatureBitmap, ambPerSign;
    private ArrayList<String> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_reports);
        setInitialData();
        etMobileNo.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        itemsList = new ArrayList<>();
        itemsList.add("Option1");
        itemsList.add("Option2");
        itemsList.add("Option3");
        itemsList.add("Option4");
        itemsList.add("Option5");
        itemsList.add("Option6");

    }

    private void setInitialData() {
        mContext = IncidentReportsActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Incident Report");
        if (getIntent().getSerializableExtra("incidentModel") != null) {
            incidentReportObject = (IncidentModel) getIntent().getSerializableExtra("incidentModel");
            etAffected.setText(incidentReportObject.getEffected_person_detail());
            tvOccurenceValue.setText(incidentReportObject.getOccourance_date());

            switch (incidentReportObject.getCease_option()) {
                case "1":
                    rbCeasedYes.setChecked(true);
                    break;
                case "2":
                    rbCeasedNo.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getIncedent_option()) {
                case "1":
                    rbContractor.setChecked(true);
                    break;
                case "2":
                    rbIncident.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getSame_occourance()) {
                case "1":
                    rbOccYes.setChecked(true);
                    break;
                case "2":
                    rbOccNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getPerson_gender_option()) {
                case "1":
                    rbMale.setChecked(true);
                    break;
                case "2":
                    rbFemale.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getAttended_person_option()) {
                case "1":
                    rbAttendYes.setChecked(true);
                    break;
                case "2":
                    rbAttendNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getEvent_type()) {
                case "1":
                    rbMiss.setChecked(true);
                    break;
                case "2":
                    rbIncident.setChecked(true);
                    break;
                case "3":
                    rbHazard.setChecked(true);
                    break;
                case "4":
                    rbContact.setChecked(true);
                    break;
                case "5":
                    rbIssue.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getThird_party_option()) {
                case "1":
                    rbThirdYes.setChecked(true);
                    break;
                case "2":
                    rbThirdNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getProperty_damage_option()) {
                case "1":
                    rbDamageYes.setChecked(true);
                    break;
                case "2":
                    rbDamageNo.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getFirst_aid_option()) {
                case "1":
                    rbAidYes.setChecked(true);
                    break;
                case "2":
                    rbAidNo.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getWandOption()) {
                case "1":
                    rbWandYes.setChecked(true);
                    break;
                case "2":
                    rbWandNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getCctv_option()) {
                case "1":
                    rbCctvYes.setChecked(true);
                    break;
                case "2":
                    rbCctvNo.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getWet_weather_option()) {
                case "1":
                    rbWetYes.setChecked(true);
                    break;
                case "2":
                    rbWetNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getWeather_option()) {
                case "1":
                    rbWeatherYes.setChecked(true);
                    break;
                case "2":
                    rbWeatherNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getPerson_drug_option()) {
                case "1":
                    rbDrugYes.setChecked(true);
                    break;
                case "2":
                    rbDrugNo.setChecked(true);
                    break;
            }
            switch (incidentReportObject.getAmbulance_attend_option()) {
                case "1":
                    rbAmbYes.setChecked(true);
                    break;
                case "2":
                    rbAmbNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getPhotos_available()) {
                case "1":
                    rbPhotosYes.setChecked(true);
                    break;
                case "2":
                    rbPhotosNo.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getEvent_type()) {
                case "1":
                    rbMiss.setChecked(true);
                    break;
                case "2":
                    rbIncident.setChecked(true);
                    break;
                case "3":
                    rbHazard.setChecked(true);
                    break;
                case "4":
                    rbContact.setChecked(true);
                    break;
                case "5":
                    rbIssue.setChecked(true);
                    break;
            }

            switch (incidentReportObject.getAffected_person_option()) {
                case "1":
                    rbCrunches.setChecked(true);
                    break;
                case "2":
                    rbStick.setChecked(true);
                    break;
                case "3":
                    rbFrame.setChecked(true);
                    break;
                case "4":
                    rbWheelchair.setChecked(true);
                    break;
                case "5":
                    rbMotorised.setChecked(true);
                    break;
            }


            switch (incidentReportObject.getWarning_sign_option()) {
                case "0":
                    rbWarningNo.setChecked(true);
                    break;
                case "1":
                    rbWarningYes.setChecked(true);
                    break;

            }

            tvCeasedTimeValue.setText(incidentReportObject.getCease_date());
            tvReportTimeValue.setText(incidentReportObject.getReported_date());
            etFirstname.setText(incidentReportObject.getPerson_first_name());
            etSurname.setText(incidentReportObject.getPerson_sur_name());
            etHomeAddress.setText(incidentReportObject.getPerson_home_address());
            etState.setText(incidentReportObject.getPerson_state());
            etPostcode.setText(incidentReportObject.getPerson_post_code());
            etHomePhone.setText(incidentReportObject.getPerson_home_phone());
            etMobileNo.setText(incidentReportObject.getPerson_mobile_phone());
            etBirthday.setText(incidentReportObject.getPerson_birth_date());
            etOccupation.setText(incidentReportObject.getPerson_occupation());
            etWorkplace.setText(incidentReportObject.getPerson_workplace_name());
            etPersonAddress.setText(incidentReportObject.getPerson_address());
            etIncident.setText(incidentReportObject.getIncedent_location());
            etBrief.setText(incidentReportObject.getIncident_desc());
            etDescription.setText(incidentReportObject.getEvent_desc_desc());
            etAction.setText(incidentReportObject.getAction_taken());
            etInjury.setText(incidentReportObject.getInjury_type());
            etIllness.setText(incidentReportObject.getInjury_illness());
            etBodily.setText(incidentReportObject.getBody_location());
            etMark.setText(incidentReportObject.getInjury_mark());
            etMechanism.setText(incidentReportObject.getInjury_machanism());
            etOthers.setText(incidentReportObject.getOther_mechanism());
            etObserve.setText(incidentReportObject.getWhat_you_see());
            etThirdReport.setText(incidentReportObject.getThird_party_detail());
            etDamageAdv.setText(incidentReportObject.getDamage_type());
            etDamageVeh.setText(incidentReportObject.getVehicle_damage_detail());
            etName.setText(incidentReportObject.getAttendee_name());
            etAidName.setText(incidentReportObject.getFirst_aid_name());
            etInjuryDetail.setText(incidentReportObject.getInjury_illness_detail());
            etMedCenter.setText(incidentReportObject.getMedical_center());
            etDateAtten.setText(incidentReportObject.getDate_attended());
            etAmbReq.setText(incidentReportObject.getAmbulance_who());
            etAmbPerName.setText(incidentReportObject.getIncident_report_person());
            etAmbDate.setText(incidentReportObject.getIncident_report_date());
            etWeatherCond.setText(incidentReportObject.getWeather_conditions());
            etFootwear.setText(incidentReportObject.getFootwaer_type());
            etEyewear.setText(incidentReportObject.getEyewear_type());
            etCarrying.setText(incidentReportObject.getCarring_type());
            etComment.setText(incidentReportObject.getAdditional_comments());
            setsignatures(incidentReportObject.getFirst_aid_signature(), ivImage);
            setsignatures(incidentReportObject.getIncident_report_person_signature(), ivAmbPerSign);
            etNotes.setText(incidentReportObject.getWitness_statement());
            etBreakdown.setText(incidentReportObject.getBreakdown_agency());
            etBreakdownOther.setText(incidentReportObject.getOther_breakdown_agency());
            setsignatures(incidentReportObject.getFirst_aid_signature(), ivImage);
            setsignatures(incidentReportObject.getIncident_report_person_signature(), ivAmbPerSign);
        }
    }

    private void setsignatures(String imgUrl, ImageView signatureView) {
        Glide.with(mContext).load(imgUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform()).into(signatureView);
    }

    private void setRadioButton(RadioGroup radiogroup, String value) {
        int count = radiogroup.getChildCount();

        for (int i = 0; i < count; i++) {
            RadioButton radioButton = (RadioButton) radiogroup.getChildAt(i);
            String btn_text = radioButton.getText().toString();
            if (btn_text.equalsIgnoreCase(value)) {
                radioButton.setChecked(true);
                break;
            }

        }
    }

    @OnClick({R.id.btn_back, R.id.et_injury, R.id.et_illness, R.id.et_mechanism, R.id.rg_ceased, R.id.rg_occurence, R.id.et_breakdown, R.id.rg_third_party, R.id.rg_prop_damage, R.id.rg_attend_affe, R.id.rg_first_aid, R.id.lay_signature, R.id.lay_amb_per_sign, R.id.rg_cctv, R.id.rg_wand_report, R.id.rg_wet_weather, R.id.rg_incident_specs, R.id.lay_screenshot,
            R.id.tv_occurence_value, R.id.et_amb_date, R.id.tv_ceased_time_value, R.id.tv_report_time_value, R.id.et_birthday, R.id.et_date_atten, R.id.btn_email, R.id.btn_save, R.id.iv_image, R.id.iv_amb_per_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;

            case R.id.et_injury:
                CommonUtils.getInstance().selectDialog(mContext, itemsList, "Select Option", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        etInjury.setText(Item);
                    }
                });
                break;
            case R.id.et_illness:
                CommonUtils.getInstance().selectDialog(mContext, itemsList, "Select Option", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        etIllness.setText(Item);
                    }
                });
                break;
            case R.id.et_mechanism:
                CommonUtils.getInstance().selectDialog(mContext, itemsList, "Select Option", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        etMechanism.setText(Item);
                    }
                });
                break;
            case R.id.rg_ceased:
                break;
            case R.id.rg_occurence:
                CustomDateDialog.getInstance().DatePicker(mContext, new CustomDateDialog.DateDialogListener() {
                    @Override
                    public void onOkayClick(int date, int month, int year) {
                        CustomTimeDialog.getInstance().timePicker(mContext, new CustomTimeDialog.TimeDialogListener() {
                            @Override
                            public void onOkayClick(String twentyFourTime, String TwelveHourTime) {

                            }
                        });
                    }
                });
                break;
            case R.id.et_breakdown:
                CommonUtils.getInstance().selectDialog(mContext, itemsList, "Select Option", new CommonUtils.OnClickItem() {
                    @Override
                    public void OnClickItem(String Item) {
                        etBreakdown.setText(Item);
                    }
                });
                break;
            case R.id.rg_third_party:
                break;
            case R.id.rg_prop_damage:
                break;
            case R.id.rg_attend_affe:
                break;
            case R.id.rg_first_aid:
                break;
            case R.id.lay_signature:
                break;
            case R.id.lay_amb_per_sign:
                break;
            case R.id.rg_cctv:
                break;
            case R.id.rg_wand_report:
                break;
            case R.id.rg_wet_weather:
                break;
            case R.id.rg_incident_specs:
                break;
            case R.id.lay_screenshot:
                break;
            case R.id.tv_occurence_value:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                tvOccurenceValue.setText(formattedDate);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_ceased_time_value:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                tvCeasedTimeValue.setText(formattedDate);
                            }
                        });
                    }
                });
                break;
            case R.id.tv_report_time_value:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                tvReportTimeValue.setText(formattedDate);
                            }
                        });
                    }
                });
                break;
            case R.id.et_birthday:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                etBirthday.setText(formattedDate);
                            }
                        });
                    }
                });
                break;

            case R.id.et_date_atten:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                etDateAtten.setText(formattedDate);
                            }
                        });
                    }
                });
                break;
            case R.id.et_amb_date:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a", Locale.US);
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
                                try {
                                    Date date1 = originalFormat.parse(selectedDate);
                                    formattedDate = targetFormat.format(date1);
                                    Log.e("day1", formattedDate);

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                etAmbDate.setText(formattedDate);
                            }
                        });
                    }
                });
                break;

            case R.id.btn_email:
                if (PermissionUtils.requestPermission(mContext, WRITE_PERMISSIONS_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    createSendForm();
                }
                break;
            case R.id.btn_save:
                if (validatefields()) {
                    if (getIntent().getSerializableExtra("incidentModel") == null)
                        new AsyncTaskRunner().execute();
                    else
                        updateIncidentReport();
                }

                break;

            case R.id.iv_image:
                startActivityForResult(new Intent(mContext, SignatureActivity.class), SIGNATRUE_REQUEST);
                mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            case R.id.iv_amb_per_sign:
                startActivityForResult(new Intent(mContext, SignatureActivity.class), SIGNATRUE_REQUEST_AMB);
                mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
        }
    }

    private boolean validatefields() {
        if (etAffected.getText().toString().trim().length() == 0 &&
                tvOccurenceValue.getText().toString().trim().length() == 0 &&
                tvCeasedTimeValue.getText().toString().trim().length() == 0 &&
                tvReportTimeValue.getText().toString().trim().length() == 0 &&
                etFirstname.getText().toString().trim().length() == 0 &&
                etSurname.getText().toString().trim().length() == 0 &&
                etHomeAddress.getText().toString().trim().length() == 0 &&
                etState.getText().toString().trim().length() == 0 &&
                etPostcode.getText().toString().trim().length() == 0 &&
                etHomePhone.getText().toString().trim().length() == 0 &&
                etMobileNo.getText().toString().trim().length() == 0 &&
                etBirthday.getText().toString().trim().length() == 0 &&
                etOccupation.getText().toString().trim().length() == 0 &&
                etWorkplace.getText().toString().trim().length() == 0 &&
                etPersonAddress.getText().toString().trim().length() == 0 &&
                etIncident.getText().toString().trim().length() == 0 &&
                etBrief.getText().toString().trim().length() == 0 &&
                etDescription.getText().toString().trim().length() == 0 &&
                etAction.getText().toString().trim().length() == 0 &&
                etInjury.getText().toString().trim().length() == 0 &&
                etIllness.getText().toString().trim().length() == 0 &&
                etBodily.getText().toString().trim().length() == 0 &&
                etMark.getText().toString().trim().length() == 0 &&
                etMechanism.getText().toString().trim().length() == 0 &&
                etOthers.getText().toString().trim().length() == 0 &&
                etObserve.getText().toString().trim().length() == 0 &&
                etThirdReport.getText().toString().trim().length() == 0 &&
                etDamageAdv.getText().toString().trim().length() == 0 &&
                etDamageVeh.getText().toString().trim().length() == 0 &&
                etName.getText().toString().trim().length() == 0 &&
                etAidName.getText().toString().trim().length() == 0 &&
                etBreakdown.getText().toString().trim().length() == 0 &&
                etBreakdownOther.getText().toString().trim().length() == 0 &&
                etInjuryDetail.getText().toString().trim().length() == 0 &&
                etMedCenter.getText().toString().trim().length() == 0 &&
                etDateAtten.getText().toString().trim().length() == 0 &&
                etAmbReq.getText().toString().trim().length() == 0 &&
                etAmbPerName.getText().toString().trim().length() == 0 &&
                etAmbDate.getText().toString().trim().length() == 0 &&
                etDateAtten.getText().toString().trim().length() == 0 &&
                etAmbReq.getText().toString().trim().length() == 0 &&
                etDateAtten.getText().toString().trim().length() == 0 &&
                etWeatherCond.getText().toString().trim().length() == 0 &&
                etFootwear.getText().toString().trim().length() == 0 &&
                etEyewear.getText().toString().trim().length() == 0 &&
                etCarrying.getText().toString().trim().length() == 0 &&
                etComment.getText().toString().trim().length() == 0 &&
                etNotes.getText().toString().trim().length() == 0 &&
                rgWeather.getCheckedRadioButtonId() == -1 &&
                rgType.getCheckedRadioButtonId() == -1 &&
                rgCeased.getCheckedRadioButtonId() == -1 &&
                rgOccurence.getCheckedRadioButtonId() == -1 &&
                rgThirdParty.getCheckedRadioButtonId() == -1 &&
                rgFirstAid.getCheckedRadioButtonId() == -1 &&
                rgGender.getCheckedRadioButtonId() == -1 &&
                rgDrugAffect.getCheckedRadioButtonId() == -1 &&
                rgCctv.getCheckedRadioButtonId() == -1 &&
                rgIncidentSpecs.getCheckedRadioButtonId() == -1 &&
                rgAmbulance.getCheckedRadioButtonId() == -1 &&
                rgWetWeather.getCheckedRadioButtonId() == -1 &&
                rgAttendAffe.getCheckedRadioButtonId() == -1 &&
                rgPropDamage.getCheckedRadioButtonId() == -1 &&
                rgEventClass.getCheckedRadioButtonId() == -1 &&
                rgWarningSign.getCheckedRadioButtonId() == -1 &&
                rgPhotos.getCheckedRadioButtonId() == -1 &&
                rgWandReport.getCheckedRadioButtonId() == -1
                ) {
            Toast.makeText(mContext, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void showAlert(final int selection) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please select Occurrence Date");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int which) {
                if (selection == 0)
                    rbCeasedNo.setChecked(true);
                else if (selection == 1)
                    rbOccNo.setChecked(true);
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SIGNATRUE_REQUEST) {
                byte[] byteArray = data.getByteArrayExtra("byteArray");
                signatureBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                ivImage.setImageBitmap(signatureBitmap);
            } else if (requestCode == SIGNATRUE_REQUEST_AMB) {
                byte[] byteArray = data.getByteArrayExtra("byteArray");
                ambPerSign = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                ivAmbPerSign.setImageBitmap(ambPerSign);
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, WRITE_PERMISSIONS_REQUEST, grantResults)) {
                    createSendForm();
                }
                break;
            case SAVE_PERMISSIONS_REQUEST:
                Uri uri = CommonUtils.getInstance().createPdf(layScreenshot, "Incident_Report_Form");
                Toast.makeText(mContext, "Form Saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void createSendForm() {
        CallProgressWheel.showLoadingDialog(mContext);
        new Thread() {
            public void run() {
                Uri uri = CommonUtils.getInstance().createPdf(layScreenshot, "Incident_Report_Form");

                ShareCompat.IntentBuilder.from(mContext)
                        .setType("message/rfc822")
                        .setSubject("Incident Report Form")
                        .setText("Incident Report Form")
                        .setStream(uri)
                        .setChooserTitle("Share Form")
                        .startChooser();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CallProgressWheel.dismissLoadingDialog();
    }

    @OnClick({R.id.rb_ceased_yes, R.id.rb_ceased_no, R.id.rb_occ_yes, R.id.rb_occ_no})
    public void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.rb_ceased_yes:
                if (checked) {
                    if (!tvOccurenceValue.getText().toString().equalsIgnoreCase("")) {
                        tvCeasedTimeValue.setText(tvOccurenceValue.getText().toString());
                    } else {
                        showAlert(0);
                    }
                }
                break;
            case R.id.rb_ceased_no:
                if (checked)
                    tvCeasedTimeValue.setText("");
                break;

            case R.id.rb_occ_yes:
                if (checked) {
                    if (!tvOccurenceValue.getText().toString().equalsIgnoreCase("")) {
                        tvReportTimeValue.setText(tvOccurenceValue.getText().toString());
                    } else {
                        showAlert(1);
                    }
                }
                break;

            case R.id.rb_occ_no:
                if (checked)
                    tvCeasedTimeValue.setText("");
                break;

        }
    }

    private void updateIncidentReport() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("IncidentReport");
        query.getInBackground(incidentReportObject.getOjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject incidentReportObj, ParseException e) {
                if (e == null) {
                    String weatheroption = "0";
                    switch (rgWeather.getCheckedRadioButtonId()) {
                        case R.id.rb_weather_yes:
                            weatheroption = "1";
                            break;
                        case R.id.rb_weather_no:
                            weatheroption = "2";
                            break;
                    }
                    incidentReportObj.put("weather_option", Integer.parseInt(weatheroption));

                    String typeOption = "0";
                    switch (rgType.getCheckedRadioButtonId()) {
                        case R.id.rb_contractor:
                            typeOption = "1";
                            break;
                        case R.id.rb_member:
                            typeOption = "2";
                            break;
                    }
                    incidentReportObj.put("incident_option", Integer.parseInt(typeOption));
                    incidentReportObj.put("occourance_date", tvOccurenceValue.getText().toString());
                    incidentReportObj.put("incident_location", etIncident.getText().toString());
                    incidentReportObj.put("person_post_code", etPostcode.getText().toString());
                    incidentReportObj.put("injury_type", etInjury.getText().toString());
                    incidentReportObj.put("third_party_detail", etThirdReport.getText().toString());

                    String ceaseOption = "0";
                    switch (rgCeased.getCheckedRadioButtonId()) {
                        case R.id.rb_ceased_yes:
                            ceaseOption = "1";
                            break;
                        case R.id.rb_ceased_no:
                            ceaseOption = "2";
                            break;
                    }
                    incidentReportObj.put("cease_option", Integer.parseInt(ceaseOption));
                    incidentReportObj.put("medical_center", etMedCenter.getText().toString());
                    incidentReportObj.put("weather_conditions", etWeatherCond.getText().toString());
                    incidentReportObj.put("other_breakdown_agency", etBreakdownOther.getText().toString());
                    incidentReportObj.put("person_sur_name", etSurname.getText().toString());
                    incidentReportObj.put("person_first_name", etFirstname.getText().toString());

                    String sameOcc = "0";
                    switch (rgOccurence.getCheckedRadioButtonId()) {
                        case R.id.rb_occ_yes:
                            sameOcc = "1";
                            break;
                        case R.id.rb_occ_no:
                            sameOcc = "2";
                            break;
                    }
                    incidentReportObj.put("same_occourance", Integer.parseInt(sameOcc));

                    String thirdOption = "0";
                    switch (rgThirdParty.getCheckedRadioButtonId()) {
                        case R.id.rb_third_yes:
                            thirdOption = "1";
                            break;
                        case R.id.rb_third_no:
                            thirdOption = "2";
                            break;
                    }

                    incidentReportObj.put("third_party_option", Integer.parseInt(thirdOption));

                    String firstAidOption = null;
                    switch (rgFirstAid.getCheckedRadioButtonId()) {
                        case R.id.rb_aid_yes:
                            firstAidOption = "1";
                            break;
                        case R.id.rb_aid_no:
                            firstAidOption = "1";
                            break;
                    }
                    incidentReportObj.put("first_aid_option", Integer.parseInt(firstAidOption));
                    incidentReportObj.put("person_state", etState.getText().toString());

                    String genderOption = "0";
                    switch (rgGender.getCheckedRadioButtonId()) {
                        case R.id.rb_male:
                            genderOption = "1";
                            break;
                        case R.id.rb_female:
                            genderOption = "2";
                            break;
                    }

                    incidentReportObj.put("person_gender_option", Integer.parseInt(genderOption));
                    incidentReportObj.put("eyewear_type", etEyewear.getText().toString());
                    incidentReportObj.put("witness_statement", etNotes.getText().toString());

                    String drugOption = "0";
                    switch (rgDrugAffect.getCheckedRadioButtonId()) {
                        case R.id.rb_drug_yes:
                            drugOption = "1";
                            break;
                        case R.id.rb_drug_no:
                            drugOption = "2";
                            break;
                    }

                    incidentReportObj.put("person_drug_option", Integer.parseInt(drugOption));
                    incidentReportObj.put("other_mechanism", etOthers.getText().toString());
                    incidentReportObj.put("person_address", etPersonAddress.getText().toString());
                    incidentReportObj.put("damage_type", etDamageAdv.getText().toString());
                    incidentReportObj.put("date_attended", etDateAtten.getText().toString());
                    incidentReportObj.put("additional_comments", etComment.getText().toString());
                    incidentReportObj.put("footwear_type", etFootwear.getText().toString());
                    incidentReportObj.put("incident_report_date", etAmbDate.getText().toString());
                    incidentReportObj.put("vehicle_damage_detail", etDamageVeh.getText().toString());
                    incidentReportObj.put("affected_person_detail", etAffected.getText().toString());
                    incidentReportObj.put("attendee_name", etName.getText().toString());
                    incidentReportObj.put("injury_mechanism", etMechanism.getText().toString());

                    String cctvOption = "0";
                    switch (rgCctv.getCheckedRadioButtonId()) {
                        case R.id.rb_cctv_yes:
                            cctvOption = "1";
                            break;
                        case R.id.rb_cctv_no:
                            cctvOption = "2";
                            break;
                    }
                    incidentReportObj.put("cctv_option", Integer.parseInt(cctvOption));

                    incidentReportObj.put("breakdown_agency", etBreakdown.getText().toString());
                    incidentReportObj.put("body_location", etBodily.getText().toString());
                    incidentReportObj.put("carrying_type", etCarrying.getText().toString());
                    incidentReportObj.put("incident_report_person", etAmbPerName.getText().toString());
//        incidentReportObj.put("warning_sign_option", tvOccurenceDate.getText().toString());

                    String affectedPersonOption = "0";
                    switch (rgIncidentSpecs.getCheckedRadioButtonId()) {
                        case R.id.rb_crunches:
                            affectedPersonOption = "1";
                            break;
                        case R.id.rb_stick:
                            affectedPersonOption = "2";
                            break;
                        case R.id.rb_frame:
                            affectedPersonOption = "3";
                            break;
                        case R.id.rb_wheelchair:
                            affectedPersonOption = "4";
                            break;
                        case R.id.rb_motorised:
                            affectedPersonOption = "5";
                            break;
                    }
                    incidentReportObj.put("affected_person_option", Integer.parseInt(affectedPersonOption));
                    incidentReportObj.put("person_occupation", etOccupation.getText().toString());
                    incidentReportObj.put("injury_mark", etMark.getText().toString());

                    String ambAttendOption = null;
                    switch (rgAmbulance.getCheckedRadioButtonId()) {
                        case R.id.rb_amb_yes:
                            ambAttendOption = "1";
                            break;
                        case R.id.rb_amb_no:
                            ambAttendOption = "2";
                            break;
                    }

                    incidentReportObj.put("ambulance_attend_option", Integer.parseInt(ambAttendOption));
                    incidentReportObj.put("injury_illness", etInjuryDetail.getText().toString());
                    incidentReportObj.put("what_you_see", etObserve.getText().toString());
                    incidentReportObj.put("person_birth_date", etBirthday.getText().toString());

                    String wetWetherOption = null;
                    switch (rgWetWeather.getCheckedRadioButtonId()) {
                        case R.id.rb_wet_yes:
                            wetWetherOption = "1";
                            break;
                        case R.id.rb_wet_no:
                            wetWetherOption = "2";
                            break;
                    }

                    incidentReportObj.put("wet_weather_option", Integer.parseInt(wetWetherOption));

                    String attendPersonOption = null;
                    switch (rgAttendAffe.getCheckedRadioButtonId()) {
                        case R.id.rb_attend_yes:
                            attendPersonOption = "1";
                            break;
                        case R.id.rb_attend_no:
                            attendPersonOption = "2";
                            break;
                    }

                    incidentReportObj.put("attended_person_option", Integer.parseInt(attendPersonOption));
                    incidentReportObj.put("event_desc_desc", etDescription.getText().toString());
                    incidentReportObj.put("reported_date", tvReportTimeValue.getText().toString());
                    incidentReportObj.put("person_mobile_phone", etMobileNo.getText().toString());

                    String damageOption = null;
                    switch (rgPropDamage.getCheckedRadioButtonId()) {
                        case R.id.rb_damage_yes:
                            damageOption = "1";
                            break;
                        case R.id.rb_damage_no:
                            damageOption = "2";
                            break;
                    }

                    incidentReportObj.put("property_damage_option", Integer.parseInt(damageOption));
                    incidentReportObj.put("ambulance_who", etAmbReq.getText().toString());
                    incidentReportObj.put("cease_date", tvCeasedTimeValue.getText().toString());
                    incidentReportObj.put("first_aid_name", etAidName.getText().toString());
                    incidentReportObj.put("action_taken", etAction.getText().toString());
                    incidentReportObj.put("incident_desc", etBrief.getText().toString());
                    incidentReportObj.put("person_workplace_name", etWorkplace.getText().toString());
                    incidentReportObj.put("person_home_address", etHomeAddress.getText().toString());
                    incidentReportObj.put("person_home_phone", etHomePhone.getText().toString());
                    incidentReportObj.put("injury_illness_detail", etInjuryDetail.getText().toString());

                    String eventTypeOption = "0";
                    switch (rgEventClass.getCheckedRadioButtonId()) {
                        case R.id.rb_miss:
                            eventTypeOption = "1";
                            break;
                        case R.id.rb_incident:
                            eventTypeOption = "2";
                            break;
                        case R.id.rb_hazard:
                            eventTypeOption = "3";
                            break;
                        case R.id.rb_contact:
                            eventTypeOption = "4";
                            break;
                        case R.id.rb_issue:
                            eventTypeOption = "5";
                            break;
                    }
                    incidentReportObj.put("event_type", Integer.parseInt(eventTypeOption));

                    String photosOption = "0";
                    switch (rgPhotos.getCheckedRadioButtonId()) {
                        case R.id.rb_photos_yes:
                            photosOption = "1";
                            break;
                        case R.id.rb_photos_no:
                            photosOption = "2";
                            break;
                    }
                    incidentReportObj.put("photo_option", Integer.parseInt(photosOption));

                    String warningSignOption = "0";
                    switch (rgWarningSign.getCheckedRadioButtonId()) {
                        case R.id.rb_warning_yes:
                            warningSignOption = "1";
                            break;
                        case R.id.rb_warning_no:
                            warningSignOption = "2";
                            break;
                    }
                    incidentReportObj.put("warning_sign_option", Integer.parseInt(warningSignOption));




                    String wandOption = "0";
                    switch (rgWandReport.getCheckedRadioButtonId()) {
                        case R.id.rb_wand_yes:
                            wandOption = "1";
                            break;
                        case R.id.rb_wand_no:
                            wandOption = "2";
                            break;
                    }
                    incidentReportObj.put("web_report_option", Integer.parseInt(wandOption));


                    if (signatureBitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        signatureBitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                        byte[] image = stream.toByteArray();
                        ParseFile file = new ParseFile("ature.png", image);
                        incidentReportObj.put("incident_report_person_signature", file);
                    }

                    if (ambPerSign != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ambPerSign.compress(Bitmap.CompressFormat.PNG, 70, stream);
                        byte[] image = stream.toByteArray();
                        ParseFile file = new ParseFile("ature.png", image);
                        incidentReportObj.put("first_aid_signature", file);
                    }
                    incidentReportObj.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            CallProgressWheel.dismissLoadingDialog();
                            if (e == null)
                                Toast.makeText(getApplicationContext(), "Report form updated successfully!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    });
                } else {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        ParseObject object = new ParseObject("IncidentReport");

        @Override
        protected void onPostExecute(Void aVoid) {
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    CallProgressWheel.dismissLoadingDialog();
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Report form saved successfully!", Toast.LENGTH_LONG).show();

                    } else
                        Toast.makeText(getApplicationContext(), "Please, Try Again", Toast.LENGTH_LONG).show();

                }
            });

        }

        @Override
        protected Void doInBackground(Void... voids) {
            object = new ParseObject("IncidentReport");
            String weatheroption = "0";
            switch (rgWeather.getCheckedRadioButtonId()) {
                case R.id.rb_weather_yes:
                    weatheroption = "1";
                    break;
                case R.id.rb_weather_no:
                    weatheroption = "2";
                    break;
            }
            object.put("weather_option", Integer.parseInt(weatheroption));

            String typeOption = "0";
            switch (rgType.getCheckedRadioButtonId()) {
                case R.id.rb_contractor:
                    typeOption = "1";
                    break;
                case R.id.rb_member:
                    typeOption = "2";
                    break;
            }
            object.put("incident_option", Integer.parseInt(typeOption));
            object.put("occourance_date", tvOccurenceValue.getText().toString());
            object.put("incident_location", etIncident.getText().toString());
            object.put("person_post_code", etPostcode.getText().toString());
            object.put("injury_type", etInjury.getText().toString());
            object.put("third_party_detail", etThirdReport.getText().toString());

            String ceaseOption = "0";
            switch (rgCeased.getCheckedRadioButtonId()) {
                case R.id.rb_ceased_yes:
                    ceaseOption = "1";
                    break;
                case R.id.rb_ceased_no:
                    ceaseOption = "2";
                    break;
            }
            object.put("cease_option", Integer.parseInt(ceaseOption));
            object.put("medical_center", etMedCenter.getText().toString());
            object.put("weather_conditions", etWeatherCond.getText().toString());
//        object.put("breakdown_agency", tvOccurenceDate.getText().toString());
            object.put("person_sur_name", etSurname.getText().toString());
            object.put("person_first_name", etFirstname.getText().toString());

            String sameOcc = "0";
            switch (rgOccurence.getCheckedRadioButtonId()) {
                case R.id.rb_occ_yes:
                    sameOcc = "1";
                    break;
                case R.id.rb_occ_no:
                    sameOcc = "2";
                    break;
            }
            object.put("same_occourance", Integer.parseInt(sameOcc));

            String thirdOption = "0";
            switch (rgThirdParty.getCheckedRadioButtonId()) {
                case R.id.rb_third_yes:
                    thirdOption = "1";
                    break;
                case R.id.rb_third_no:
                    thirdOption = "2";
                    break;
            }

            object.put("third_party_option", Integer.parseInt(thirdOption));

            String firstAidOption = null;
            switch (rgFirstAid.getCheckedRadioButtonId()) {
                case R.id.rb_aid_yes:
                    firstAidOption = "1";
                    break;
                case R.id.rb_aid_no:
                    firstAidOption = "1";
                    break;
            }
            object.put("first_aid_option", Integer.parseInt(firstAidOption));
            object.put("person_state", etState.getText().toString());

            String genderOption = "0";
            switch (rgGender.getCheckedRadioButtonId()) {
                case R.id.rb_male:
                    genderOption = "1";
                    break;
                case R.id.rb_female:
                    genderOption = "2";
                    break;
            }

            object.put("person_gender_option", Integer.parseInt(genderOption));
            object.put("eyewear_type", etEyewear.getText().toString());
            object.put("witness_statement", etNotes.getText().toString());

            String drugOption = "0";
            switch (rgDrugAffect.getCheckedRadioButtonId()) {
                case R.id.rb_drug_yes:
                    drugOption = "1";
                    break;
                case R.id.rb_drug_no:
                    drugOption = "2";
                    break;
            }

            object.put("person_drug_option", Integer.parseInt(drugOption));
            object.put("other_mechanism", etOthers.getText().toString());
            object.put("person_address", etPersonAddress.getText().toString());
            object.put("damage_type", etDamageAdv.getText().toString());
            object.put("date_attended", etDateAtten.getText().toString());
            object.put("additional_comments", etComment.getText().toString());
            object.put("footwear_type", etFootwear.getText().toString());
            object.put("incident_report_date", etAmbDate.getText().toString());
            object.put("vehicle_damage_detail", etDamageVeh.getText().toString());
            object.put("affected_person_detail", etAffected.getText().toString());
            object.put("attendee_name", etName.getText().toString());
            object.put("injury_mechanism", etMechanism.getText().toString());

            String cctvOption = "0";
            switch (rgCctv.getCheckedRadioButtonId()) {
                case R.id.rb_cctv_yes:
                    cctvOption = "1";
                    break;
                case R.id.rb_cctv_no:
                    cctvOption = "2";
                    break;
            }
            object.put("cctv_option", Integer.parseInt(cctvOption));

//        object.put("other_breakdown_agency", tvOccurenceDate.getText().toString());
            object.put("body_location", etBodily.getText().toString());
            object.put("carrying_type", etCarrying.getText().toString());
            object.put("incident_report_person", etAmbPerName.getText().toString());
//        object.put("warning_sign_option", tvOccurenceDate.getText().toString());

            String affectedPersonOption = "0";
            switch (rgIncidentSpecs.getCheckedRadioButtonId()) {
                case R.id.rb_crunches:
                    affectedPersonOption = "1";
                    break;
                case R.id.rb_stick:
                    affectedPersonOption = "2";
                    break;
                case R.id.rb_frame:
                    affectedPersonOption = "3";
                    break;
                case R.id.rb_wheelchair:
                    affectedPersonOption = "4";
                    break;
                case R.id.rb_motorised:
                    affectedPersonOption = "5";
                    break;
            }
            object.put("affected_person_option", Integer.parseInt(affectedPersonOption));
            object.put("person_occupation", etOccupation.getText().toString());
            object.put("injury_mark", etMark.getText().toString());

            String ambAttendOption = null;
            switch (rgAmbulance.getCheckedRadioButtonId()) {
                case R.id.rb_amb_yes:
                    ambAttendOption = "1";
                    break;
                case R.id.rb_amb_no:
                    ambAttendOption = "2";
                    break;
            }

            object.put("ambulance_attend_option", Integer.parseInt(ambAttendOption));
            object.put("injury_illness", etInjuryDetail.getText().toString());
            object.put("what_you_see", etObserve.getText().toString());
            object.put("person_birth_date", etBirthday.getText().toString());

            String wetWetherOption = null;
            switch (rgWetWeather.getCheckedRadioButtonId()) {
                case R.id.rb_wet_yes:
                    wetWetherOption = "1";
                    break;
                case R.id.rb_wet_no:
                    wetWetherOption = "2";
                    break;
            }

            object.put("wet_weather_option", Integer.parseInt(wetWetherOption));

            String attendPersonOption = null;
            switch (rgAttendAffe.getCheckedRadioButtonId()) {
                case R.id.rb_attend_yes:
                    attendPersonOption = "1";
                    break;
                case R.id.rb_attend_no:
                    attendPersonOption = "2";
                    break;
            }

            object.put("attended_person_option", Integer.parseInt(attendPersonOption));
            object.put("event_desc_desc", etDescription.getText().toString());
            object.put("reported_date", tvReportTimeValue.getText().toString());
            object.put("person_mobile_phone", etMobileNo.getText().toString());

            String damageOption = null;
            switch (rgPropDamage.getCheckedRadioButtonId()) {
                case R.id.rb_damage_yes:
                    damageOption = "1";
                    break;
                case R.id.rb_damage_no:
                    damageOption = "2";
                    break;
            }

            object.put("property_damage_option", Integer.parseInt(damageOption));
            object.put("ambulance_who", etAmbReq.getText().toString());
            object.put("cease_date", tvCeasedTimeValue.getText().toString());
            object.put("first_aid_name", etAidName.getText().toString());
            object.put("action_taken", etAction.getText().toString());
            object.put("incident_desc", etBrief.getText().toString());
            object.put("person_workplace_name", etWorkplace.getText().toString());
            object.put("person_home_address", etHomeAddress.getText().toString());
            object.put("person_home_phone", etHomePhone.getText().toString());
            object.put("injury_illness_detail", etInjuryDetail.getText().toString());

            String eventTypeOption = "0";
            switch (rgEventClass.getCheckedRadioButtonId()) {
                case R.id.rb_miss:
                    eventTypeOption = "1";
                    break;
                case R.id.rb_incident:
                    eventTypeOption = "2";
                    break;
                case R.id.rb_hazard:
                    eventTypeOption = "3";
                    break;
                case R.id.rb_contact:
                    eventTypeOption = "4";
                    break;
                case R.id.rb_issue:
                    eventTypeOption = "5";
                    break;
            }
            object.put("event_type", Integer.parseInt(eventTypeOption));

            String photosOption = "0";
            switch (rgPhotos.getCheckedRadioButtonId()) {
                case R.id.rb_photos_yes:
                    photosOption = "1";
                    break;
                case R.id.rb_photos_no:
                    photosOption = "2";
                    break;
            }

            object.put("photo_option", Integer.parseInt(photosOption));

            String warningSignOption = "0";
            switch (rgWarningSign.getCheckedRadioButtonId()) {
                case R.id.rb_warning_yes:
                    warningSignOption = "1";
                    break;
                case R.id.rb_warning_no:
                    warningSignOption = "2";
                    break;
            }
            object.put("warning_sign_option", Integer.parseInt(warningSignOption));

            String wandOption = "0";
            switch (rgWandReport.getCheckedRadioButtonId()) {
                case R.id.rb_wand_yes:
                    wandOption = "1";
                    break;
                case R.id.rb_wand_no:
                    wandOption = "2";
                    break;
            }
            object.put("web_report_option", Integer.parseInt(wandOption));

            if (signatureBitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                byte[] image = stream.toByteArray();
                ParseFile file = new ParseFile("ature.png", image);
                object.put("incident_report_person_signature", file);
            }

            if (ambPerSign != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ambPerSign.compress(Bitmap.CompressFormat.PNG, 70, stream);
                byte[] image = stream.toByteArray();
                ParseFile file = new ParseFile("ature.png", image);
                object.put("first_aid_signature", file);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            CallProgressWheel.showLoadingDialog(mContext);
        }
    }

    private void convertAndUpdateString(){
        String formString = GrandGroupHelper.grandGroupHelper(mContext).fetchHtmlFromAssets("incedent.html");
        String formatedString = formString.replace("$$1_affectedpersondetails$$", tvSelectedLikelihood.getText().toString()).replace("$$2_type$$", tvSelectedLikelihood.getText().toString())
                .replace("$$3_occourancedate$$", tvSelectedLikelihood.getText().toString()).replace("$$4_ceasedworkdate$$", tvSelectedLikelihood.getText().toString())
                .replace("$$5_reporteddate$$", tvSelectedLikelihood.getText().toString()).replace("$$6_firstname$$", tvSelectedLikelihood.getText().toString())
                .replace("$$7_surname$$", tvSelectedLikelihood.getText().toString()).replace("$$8_gender$$", tvSelectedLikelihood.getText().toString())
                .replace("$$9_homeaddress$$", tvSelectedLikelihood.getText().toString()).replace("$$10_state$$", tvSelectedLikelihood.getText().toString())
                .replace("$$11_postcode$$", tvSelectedLikelihood.getText().toString()).replace("$$12_homephonenumber$$", tvSelectedLikelihood.getText().toString())
                .replace("$$13_mobilephonenumber$$", tvSelectedLikelihood.getText().toString()).replace("$$14_birthdate$$", tvSelectedLikelihood.getText().toString())
                .replace("$$15_occupation$$", tvSelectedLikelihood.getText().toString()).replace("$$16_workplacename$$", tvSelectedLikelihood.getText().toString())
                .replace("$$17_address$$", tvSelectedLikelihood.getText().toString()).replace("$$18_incidentlocation$$", tvSelectedLikelihood.getText().toString())
                .replace("$$19_locationofincidentcenter$$", tvSelectedLikelihood.getText().toString()).replace("$$20_eventclassification$$", tvSelectedLikelihood.getText().toString())
                .replace("$$21_descofincident$$", tvSelectedLikelihood.getText().toString()).replace("$$22_descofevent$$", tvSelectedLikelihood.getText().toString())
                .replace("$$23_immidiateactiontaken$$", tvSelectedLikelihood.getText().toString()).replace("$$24_injurytype$$", tvSelectedLikelihood.getText().toString())
                .replace("$$25_injuryconsequence$$", tvSelectedLikelihood.getText().toString()).replace("$$26_bodilylocation$$", tvSelectedLikelihood.getText().toString())
                .replace("$$27_locationonbody$$", tvSelectedLikelihood.getText().toString()).replace("$$28_mechanismofinjury$$", tvSelectedLikelihood.getText().toString())
                .replace("$$29_otherexplanation$$", tvSelectedLikelihood.getText().toString()).replace("$$30_breakdown$$", tvSelectedLikelihood.getText().toString())
                .replace("$$31_otherexplanation$$", tvSelectedLikelihood.getText().toString()).replace("$$32_whatdidyousee$$", tvSelectedLikelihood.getText().toString())
                .replace("$$33_wastherethirdparty$$", tvSelectedLikelihood.getText().toString()).replace("$$34_ifyesthirdparty$$", tvSelectedLikelihood.getText().toString())
                .replace("$$35_propertydamage$$", tvSelectedLikelihood.getText().toString()).replace("$$36typeofdamage$$", tvSelectedLikelihood.getText().toString())
                .replace("$$37_vehicledamage$$", tvSelectedLikelihood.getText().toString()).replace("$$38_attendedaffectedperson$$", tvSelectedLikelihood.getText().toString())
                .replace("$$39_name$$", tvSelectedLikelihood.getText().toString()).replace("$$40_firstaid$$", tvSelectedLikelihood.getText().toString())
                .replace("$$41_ifyes$$", tvSelectedLikelihood.getText().toString()).replace("$$42_signature$$", tvSelectedLikelihood.getText().toString())
                .replace("$$43_pleaseprovide_$$", tvSelectedLikelihood.getText().toString()).replace("$$44_dateattended$$", tvSelectedLikelihood.getText().toString())
                .replace("$$45_ambulanceattended$$", tvSelectedLikelihood.getText().toString()).replace("$$46_whorequested$$", tvSelectedLikelihood.getText().toString())
                .replace("$$47_personcompleting$$", tvSelectedLikelihood.getText().toString()).replace("$$48_signature$$", tvSelectedLikelihood.getText().toString())
                .replace("$$49_date$$", tvSelectedLikelihood.getText().toString()).replace("$$50_wasweather$$", tvSelectedLikelihood.getText().toString())
                .replace("$$51_weatherconditions$$", tvSelectedLikelihood.getText().toString()).replace("$$52_didthisperson$$", tvSelectedLikelihood.getText().toString())
                .replace("$$53_typeoffootwear$$", tvSelectedLikelihood.getText().toString()).replace("$$54_typeofeyewear$$", tvSelectedLikelihood.getText().toString())
                .replace("$$55_weretheycarrying$$", tvSelectedLikelihood.getText().toString()).replace("$$56_cctv$$", tvSelectedLikelihood.getText().toString())
                .replace("$$57_photos$$", tvSelectedLikelihood.getText().toString()).replace("$$58_wandreports$$", tvSelectedLikelihood.getText().toString())
                .replace("$$59_warningsign$$", tvSelectedLikelihood.getText().toString()).replace("$$60_wetweather$$", tvSelectedLikelihood.getText().toString())
                .replace("$$61_anyadditional$$", tvSelectedLikelihood.getText().toString()).replace("$$62_atthetimeofincident$$", tvSelectedLikelihood.getText().toString())
                .replace("$$63_notes$$", tvSelectedLikelihood.getText().toString());





        tvCeasedTimeValue.setText(incidentReportObject.getCease_date());tvReportTimeValue.setText(incidentReportObject.getReported_date());
        etFirstname.setText(incidentReportObject.getPerson_first_name());etSurname.setText(incidentReportObject.getPerson_sur_name());
        etHomeAddress.setText(incidentReportObject.getPerson_home_address());etState.setText(incidentReportObject.getPerson_state());
        etPostcode.setText(incidentReportObject.getPerson_post_code());etHomePhone.setText(incidentReportObject.getPerson_home_phone());
        etMobileNo.setText(incidentReportObject.getPerson_mobile_phone());etBirthday.setText(incidentReportObject.getPerson_birth_date());
        etOccupation.setText(incidentReportObject.getPerson_occupation());etWorkplace.setText(incidentReportObject.getPerson_workplace_name());
        etPersonAddress.setText(incidentReportObject.getPerson_address());etIncident.setText(incidentReportObject.getIncedent_location());
        etBrief.setText(incidentReportObject.getIncident_desc());etDescription.setText(incidentReportObject.getEvent_desc_desc());
        etAction.setText(incidentReportObject.getAction_taken());etInjury.setText(incidentReportObject.getInjury_type());
        etIllness.setText(incidentReportObject.getInjury_illness());etBodily.setText(incidentReportObject.getBody_location());
        etMark.setText(incidentReportObject.getInjury_mark());etMechanism.setText(incidentReportObject.getInjury_machanism());
        etOthers.setText(incidentReportObject.getOther_mechanism());etObserve.setText(incidentReportObject.getWhat_you_see());
        etThirdReport.setText(incidentReportObject.getThird_party_detail());etDamageAdv.setText(incidentReportObject.getDamage_type());
        etDamageVeh.setText(incidentReportObject.getVehicle_damage_detail());etName.setText(incidentReportObject.getAttendee_name());
        etAidName.setText(incidentReportObject.getFirst_aid_name());etInjuryDetail.setText(incidentReportObject.getInjury_illness_detail());
        etMedCenter.setText(incidentReportObject.getMedical_center());etDateAtten.setText(incidentReportObject.getDate_attended());
        etAmbReq.setText(incidentReportObject.getAmbulance_who());etAmbPerName.setText(incidentReportObject.getIncident_report_person());
        etAmbDate.setText(incidentReportObject.getIncident_report_date());etWeatherCond.setText(incidentReportObject.getWeather_conditions());
        etFootwear.setText(incidentReportObject.getFootwaer_type());etEyewear.setText(incidentReportObject.getEyewear_type());
        etCarrying.setText(incidentReportObject.getCarring_type());etComment.setText(incidentReportObject.getAdditional_comments());
        setsignatures(incidentReportObject.getFirst_aid_signature(), ivImage);setsignatures(incidentReportObject.getIncident_report_person_signature(), ivAmbPerSign);
        etNotes.setText(incidentReportObject.getWitness_statement());etBreakdown.setText(incidentReportObject.getBreakdown_agency());
        etBreakdownOther.setText(incidentReportObject.getOther_breakdown_agency());setsignatures(incidentReportObject.getFirst_aid_signature(), ivImage);
        setsignatures(incidentReportObject.getIncident_report_person_signature(), ivAmbPerSign);







        GrandGroupHelper.grandGroupHelper(mContext).generateDocFile(formatedString, "Risk Report.doc");
    }
}