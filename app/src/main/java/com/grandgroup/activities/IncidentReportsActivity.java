package com.grandgroup.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.grandgroup.R;
import com.grandgroup.model.IncidentModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.CommonUtils;
import com.grandgroup.utills.PermissionUtils;
import com.grandgroup.views.CustomDateDialog;
import com.grandgroup.views.CustomTimeDialog;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grandgroup.utills.AppConstant.SAVE_PERMISSIONS_REQUEST;
import static com.grandgroup.utills.AppConstant.SIGNATRUE_REQUEST;
import static com.grandgroup.utills.AppConstant.SIGNATRUE_REQUEST_AMB;
import static com.grandgroup.utills.AppConstant.WRITE_PERMISSIONS_REQUEST;

public class IncidentReportsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.my_toolbar)
    RelativeLayout myToolbar;
    @BindView(R.id.tv_affected)
    TextView tvAffected;
    @BindView(R.id.et_affected)
    EditText etAffected;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rb_contractor)
    RadioButton rbContractor;
    @BindView(R.id.rb_member)
    RadioButton rbMember;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.tv_occurence)
    TextView tvOccurence;
    @BindView(R.id.tv_occurence_value)
    TextView tvOccurenceValue;
    @BindView(R.id.tv_ceased)
    TextView tvCeased;
    @BindView(R.id.rb_ceased_yes)
    RadioButton rbCeasedYes;
    @BindView(R.id.rb_ceased_no)
    RadioButton rbCeasedNo;
    @BindView(R.id.rg_ceased)
    RadioGroup rgCeased;
    @BindView(R.id.tv_ceased_time)
    TextView tvCeasedTime;
    @BindView(R.id.tv_ceased_time_value)
    TextView tvCeasedTimeValue;
    @BindView(R.id.tv_report_time)
    TextView tvReportTime;
    @BindView(R.id.tv_report_time_value)
    TextView tvReportTimeValue;
    @BindView(R.id.tv_occurence_date)
    TextView tvOccurenceDate;
    @BindView(R.id.rb_occ_yes)
    RadioButton rbOccYes;
    @BindView(R.id.rb_occ_no)
    RadioButton rbOccNo;
    @BindView(R.id.rg_occurence)
    RadioGroup rgOccurence;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.et_firstname)
    EditText etFirstname;
    @BindView(R.id.tv_surname)
    TextView tvSurname;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.et_home_address)
    EditText etHomeAddress;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.tv_postcode)
    TextView tvPostcode;
    @BindView(R.id.et_postcode)
    EditText etPostcode;
    @BindView(R.id.tv_home_phone)
    TextView tvHomePhone;
    @BindView(R.id.et_home_phone)
    EditText etHomePhone;
    @BindView(R.id.tv_mobile_no)
    TextView tvMobileNo;
    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.et_birthday)
    TextView etBirthday;
    @BindView(R.id.tv_occupation)
    TextView tvOccupation;
    @BindView(R.id.et_occupation)
    TextView etOccupation;
    @BindView(R.id.tv_workplace)
    TextView tvWorkplace;
    @BindView(R.id.et_workplace)
    TextView etWorkplace;
    @BindView(R.id.tv_addres)
    TextView tvAddres;
    @BindView(R.id.et_addres)
    TextView etAddres;
    @BindView(R.id.tv_incident)
    TextView tvIncident;
    @BindView(R.id.et_incident)
    TextView etIncident;
    @BindView(R.id.tv_event_class)
    TextView tvEventClass;
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
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.et_brief)
    EditText etBrief;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.et_action)
    EditText etAction;
    @BindView(R.id.tv_injury)
    TextView tvInjury;
    @BindView(R.id.et_injury)
    EditText etInjury;
    @BindView(R.id.tv_illness)
    TextView tvIllness;
    @BindView(R.id.et_illness)
    EditText etIllness;
    @BindView(R.id.tv_bodily)
    TextView tvBodily;
    @BindView(R.id.et_bodily)
    EditText etBodily;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.et_mark)
    EditText etMark;
    @BindView(R.id.tv_mechanism)
    TextView tvMechanism;
    @BindView(R.id.et_mechanism)
    EditText etMechanism;
    @BindView(R.id.tv_others)
    TextView tvOthers;
    @BindView(R.id.et_others)
    EditText etOthers;
    @BindView(R.id.tv_observe)
    TextView tvObserve;
    @BindView(R.id.et_observe)
    EditText etObserve;
    @BindView(R.id.tv_third_party)
    TextView tvThirdParty;
    @BindView(R.id.rb_third_yes)
    RadioButton rbThirdYes;
    @BindView(R.id.rb_third_no)
    RadioButton rbThirdNo;
    @BindView(R.id.rg_third_party)
    RadioGroup rgThirdParty;
    @BindView(R.id.tv_third_report)
    TextView tvThirdReport;
    @BindView(R.id.et_third_report)
    EditText etThirdReport;
    @BindView(R.id.tv_prop_damage)
    TextView tvPropDamage;
    @BindView(R.id.rb_damage_yes)
    RadioButton rbDamageYes;
    @BindView(R.id.rb_damage_no)
    RadioButton rbDamageNo;
    @BindView(R.id.rg_prop_damage)
    RadioGroup rgPropDamage;
    @BindView(R.id.tv_damage_adv)
    TextView tvDamageAdv;
    @BindView(R.id.et_damage_adv)
    EditText etDamageAdv;
    @BindView(R.id.tv_damage_veh)
    TextView tvDamageVeh;
    @BindView(R.id.et_damage_veh)
    EditText etDamageVeh;
    @BindView(R.id.tv_attend_affe)
    TextView tvAttendAffe;
    @BindView(R.id.rb_attend_yes)
    RadioButton rbAttendYes;
    @BindView(R.id.rb_attend_no)
    RadioButton rbAttendNo;
    @BindView(R.id.rg_attend_affe)
    RadioGroup rgAttendAffe;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_first_aid)
    TextView tvFirstAid;
    @BindView(R.id.rb_aid_yes)
    RadioButton rbAidYes;
    @BindView(R.id.rb_aid_no)
    RadioButton rbAidNo;
    @BindView(R.id.rg_first_aid)
    RadioGroup rgFirstAid;
    @BindView(R.id.tv_aid_yes_admin)
    TextView tvAidYesAdmin;
    @BindView(R.id.tv_aid_admin)
    TextView tvAidAdmin;
    @BindView(R.id.et_aid_name)
    EditText etAidName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.lay_signature)
    ConstraintLayout laySignature;
    @BindView(R.id.tv_injury_det)
    TextView tvInjuryDet;
    @BindView(R.id.et_injury_detail)
    EditText etInjuryDetail;
    @BindView(R.id.tv_med_center)
    TextView tvMedCenter;
    @BindView(R.id.et_med_center)
    EditText etMedCenter;
    @BindView(R.id.tv_date_atten)
    TextView tvDateAtten;
    @BindView(R.id.et_date_atten)
    TextView etDateAtten;
    @BindView(R.id.tv_ambulance)
    TextView tvAmbulance;
    @BindView(R.id.rb_amb_yes)
    RadioButton rbAmbYes;
    @BindView(R.id.rb_amb_no)
    RadioButton rbAmbNo;
    @BindView(R.id.rg_ambulance)
    RadioGroup rgAmbulance;
    @BindView(R.id.tv_amb_req)
    TextView tvAmbReq;
    @BindView(R.id.et_amb_req)
    EditText etAmbReq;
    @BindView(R.id.tv_amb_per)
    TextView tvAmbPer;
    @BindView(R.id.tv_amb_per_name)
    TextView tvAmbPerName;
    @BindView(R.id.et_amb_per_name)
    EditText etAmbPerName;
    @BindView(R.id.tv_amb_per_sign)
    TextView tvAmbPerSign;
    @BindView(R.id.iv_amb_per_sign)
    ImageView ivAmbPerSign;
    @BindView(R.id.lay_amb_per_sign)
    ConstraintLayout layAmbPerSign;
    @BindView(R.id.tv_amb_date)
    TextView tvAmbDate;
    @BindView(R.id.et_amb_date)
    TextView etAmbDate;
    @BindView(R.id.et_complete_note)
    TextView etCompleteNote;
    @BindView(R.id.tv_weather_reas)
    TextView tvWeatherReas;
    @BindView(R.id.rb_weather_yes)
    RadioButton rbWeatherYes;
    @BindView(R.id.rb_weather_no)
    RadioButton rbWeatherNo;
    @BindView(R.id.rg_weather)
    RadioGroup rgWeather;
    @BindView(R.id.tv_weather_cond)
    TextView tvWeatherCond;
    @BindView(R.id.et_weather_cond)
    EditText etWeatherCond;
    @BindView(R.id.tv_drug_affect)
    TextView tvDrugAffect;
    @BindView(R.id.rb_drug_yes)
    RadioButton rbDrugYes;
    @BindView(R.id.rb_drug_no)
    RadioButton rbDrugNo;
    @BindView(R.id.rg_drug_affect)
    RadioGroup rgDrugAffect;
    @BindView(R.id.tv_footwear)
    TextView tvFootwear;
    @BindView(R.id.et_footwear)
    EditText etFootwear;
    @BindView(R.id.tv_eyewear)
    TextView tvEyewear;
    @BindView(R.id.et_eyewear)
    EditText etEyewear;
    @BindView(R.id.tv_carrying)
    TextView tvCarrying;
    @BindView(R.id.et_carrying)
    EditText etCarrying;
    @BindView(R.id.tv_cctv)
    TextView tvCctv;
    @BindView(R.id.rb_cctv_yes)
    RadioButton rbCctvYes;
    @BindView(R.id.rb_cctv_no)
    RadioButton rbCctvNo;
    @BindView(R.id.rg_cctv)
    RadioGroup rgCctv;
    @BindView(R.id.tv_photos)
    TextView tvPhotos;
    @BindView(R.id.rb_photos_yes)
    RadioButton rbPhotosYes;
    @BindView(R.id.rb_photos_no)
    RadioButton rbPhotosNo;
    @BindView(R.id.rg_photos)
    RadioGroup rgPhotos;
    @BindView(R.id.tv_wand_report)
    TextView tvWandReport;
    @BindView(R.id.rb_wand_yes)
    RadioButton rbWandYes;
    @BindView(R.id.rb_wand_no)
    RadioButton rbWandNo;
    @BindView(R.id.rg_wand_report)
    RadioGroup rgWandReport;
    @BindView(R.id.tv_wet_weather)
    TextView tvWetWeather;
    @BindView(R.id.rb_wet_yes)
    RadioButton rbWetYes;
    @BindView(R.id.rb_wet_no)
    RadioButton rbWetNo;
    @BindView(R.id.rg_wet_weather)
    RadioGroup rgWetWeather;
    @BindView(R.id.tv_any_com)
    TextView tvAnyCom;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_incident_specs)
    TextView tvIncidentSpecs;
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
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.tv_notes)
    TextView tvNotes;
    @BindView(R.id.et_notes)
    EditText etNotes;
    @BindView(R.id.lay_screenshot)
    ConstraintLayout layScreenshot;
    private IncidentModel incidentReportObject;
    private AppCompatActivity mContext;
    private Bitmap signatureBitmap, ambPerSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_reports);
        setInitialData();
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

            tvCeasedTimeValue.setText(incidentReportObject.getCease_date());
            tvReportTimeValue.setText(incidentReportObject.getReported_date());
            etFirstname.setText(incidentReportObject.getPerson_first_name());
            etSurname.setText(incidentReportObject.getPerson_sur_name());
            etHomeAddress.setText(incidentReportObject.getPerson_phone_address());
            etState.setText(incidentReportObject.getPerson_state());
            etPostcode.setText(incidentReportObject.getPerson_post_code());
            etHomePhone.setText(incidentReportObject.getPerson_home_phone());
            etMobileNo.setText(incidentReportObject.getPerson_mobile_phone());
            etBirthday.setText(incidentReportObject.getPerson_birth_date());
            etOccupation.setText(incidentReportObject.getPerson_occupation());
            etWorkplace.setText(incidentReportObject.getPerson_workplace_name());
            etAddres.setText(incidentReportObject.getPerson_address());
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
            setsignatures(incidentReportObject.getFirst_aid_signature(), ivImage);
            setsignatures(incidentReportObject.getIncident_report_person_signature(), ivAmbPerSign);
        } else {
            btnEmail.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }
    }

    private void setsignatures(String imgUrl, ImageView signatureView) {
        Glide.with(mContext).load(imgUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.default_user)
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

    @OnClick({R.id.btn_back, R.id.btn_add, R.id.rg_type, R.id.rg_ceased, R.id.rg_occurence, R.id.rg_gender, R.id.rg_third_party, R.id.rg_prop_damage, R.id.rg_attend_affe, R.id.rg_first_aid, R.id.lay_signature, R.id.lay_amb_per_sign, R.id.rg_cctv, R.id.rg_wand_report, R.id.rg_wet_weather, R.id.rg_incident_specs, R.id.lay_screenshot,
            R.id.tv_occurence_value, R.id.et_amb_date, R.id.tv_ceased_time_value, R.id.tv_report_time_value, R.id.et_birthday, R.id.et_date_atten, R.id.btn_email, R.id.btn_save, R.id.iv_image, R.id.iv_amb_per_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.btn_add:
                break;
            case R.id.rg_type:
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
            case R.id.rg_gender:
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                                DateFormat originalFormat = new SimpleDateFormat("MM dd, yyyy hh:mm a");
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
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
                /*if (PermissionUtils.requestPermission(mContext, SAVE_PERMISSIONS_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Uri uri = CommonUtils.getInstance().createPdf(layScreenshot, "Incident_Report_Form");
                    Toast.makeText(mContext, "Form Saved Successfully", Toast.LENGTH_SHORT).show();
                }*/
                saveDataOnParse();
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
        Uri uri = CommonUtils.getInstance().createPdf(layScreenshot, "Incident_Report_Form");

        ShareCompat.IntentBuilder.from(mContext)
                .setType("message/rfc822")
                .setSubject("Incident Report Form")
                .setText("Incident Report Form")
                .setStream(uri)
                .setChooserTitle("Share Form")
                .startChooser();
        CallProgressWheel.dismissLoadingDialog();
    }

    private void saveDataOnParse() {
        CallProgressWheel.showLoadingDialog(mContext);
        ParseObject object = new ParseObject("IncidentReport");

        String weatheroption = null;
        switch (rgWeather.getCheckedRadioButtonId()) {
            case R.id.rb_weather_yes:
                weatheroption = "1";
                break;
            case R.id.rb_weather_no:
                weatheroption = "2";
                break;
        }
        object.put("weather_option", weatheroption);

        String typeOption = null;
        switch (rgType.getCheckedRadioButtonId()) {
            case R.id.rb_contractor:
                typeOption = "1";
                break;
            case R.id.rb_member:
                typeOption = "2";
                break;
        }
        object.put("incident_option", typeOption);

        object.put("occourance_date", tvOccurenceDate.getText().toString());
        object.put("incident_location", etIncident.getText().toString());
        object.put("person_post_code", etPostcode.getText().toString());
        object.put("injury_type", etInjury.getText().toString());
        object.put("third_party_detail", etThirdReport.getText().toString());

        String ceaseOption = null;
        switch (rgCeased.getCheckedRadioButtonId()) {
            case R.id.rb_ceased_yes:
                ceaseOption = "1";
                break;
            case R.id.rb_ceased_no:
                ceaseOption = "2";
                break;
        }
        object.put("cease_option", ceaseOption);
        object.put("medical_center", etMedCenter.getText().toString());
        object.put("weather_conditions", etWeatherCond.getText().toString());
//        object.put("breakdown_agency", tvOccurenceDate.getText().toString());
        object.put("person_sur_name", etSurname.getText().toString());
        object.put("person_first_name", etFirstname.getText().toString());

        String sameOcc = null;
        switch (rgOccurence.getCheckedRadioButtonId()) {
            case R.id.rb_occ_yes:
                sameOcc = "1";
                break;
            case R.id.rb_occ_no:
                sameOcc = "2";
                break;
        }
        object.put("same_occourance", sameOcc);

        String thirdOption =  null;
        switch (rgThirdParty.getCheckedRadioButtonId()) {
            case R.id.rb_third_yes:
                thirdOption = "1";
                break;
            case R.id.rb_third_no:
                thirdOption = "2";
                break;
        }

        object.put("third_party_option", thirdOption);

        String firstAidOption = null;
        switch (rgFirstAid.getCheckedRadioButtonId()) {
            case R.id.rb_aid_yes:
                firstAidOption = "1";
                break;
            case R.id.rb_aid_no:
                firstAidOption = "1";
                break;
        }
        object.put("first_aid_option", firstAidOption);
        object.put("person_state", etState.getText().toString());

        String genderOption = null;
        switch (rgGender.getCheckedRadioButtonId()) {
            case R.id.rb_male:
                genderOption = "1";
                break;
            case R.id.rb_female:
                genderOption = "2";
                break;
        }

        object.put("person_gender_option", genderOption);
        object.put("eyewear_type", etEyewear.getText().toString());
        object.put("witness_statement", etNotes.getText().toString());

        String drugOption =  null;
        switch (rgDrugAffect.getCheckedRadioButtonId()) {
            case R.id.rb_drug_yes:
                drugOption = "1";
                break;
            case R.id.rb_drug_no:
                drugOption = "2";
                break;
        }

        object.put("person_drug_option", drugOption);
        object.put("other_mechanism", etOthers.getText().toString());
        object.put("person_address", etAddres.getText().toString());
        object.put("damage_type", etDamageAdv.getText().toString());
        object.put("date_attended", etDateAtten.getText().toString());
        object.put("additional_comments", etComment.getText().toString());
        object.put("footwear_type", etFootwear.getText().toString());
        object.put("incident_report_date", etAmbDate.getText().toString());
        object.put("vehicle_damage_detail", etDamageVeh.getText().toString());
        object.put("affected_person_detail", etAffected.getText().toString());
        object.put("attendee_name", etName.getText().toString());
        object.put("injury_mechanism", etMechanism.getText().toString());

        String cctvOption = null;
        switch (rgCctv.getCheckedRadioButtonId()) {
            case R.id.rb_cctv_yes:
                cctvOption = "1";
                break;
            case R.id.rb_cctv_no:
                cctvOption = "2";
                break;
        }
        object.put("cctv_option", cctvOption);

//        object.put("other_breakdown_agency", tvOccurenceDate.getText().toString());
        object.put("body_location", etBodily.getText().toString());
        object.put("carrying_type", etCarrying.getText().toString());
        object.put("incident_report_person", etAmbPerName.getText().toString());
//        object.put("warning_sign_option", tvOccurenceDate.getText().toString());

        String affectedPersonOption = null;
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
        object.put("affected_person_option", affectedPersonOption);
        object.put("person_occupation", etOccupation.getText().toString());
        object.put("injury_mark", etMark.getText().toString());

        String ambAttendOption = null;
        switch (rgAmbulance.getCheckedRadioButtonId()) {
            case R.id.rb_amb_yes:
                ambAttendOption ="1";
                break;
            case R.id.rb_amb_no:
                ambAttendOption ="2";
                break;
        }

        object.put("ambulance_attend_option", ambAttendOption);
        object.put("injury_illness", etInjuryDetail.getText().toString());
        object.put("what_you_see", etObserve.getText().toString());
        object.put("person_birth_date", etBirthday.getText().toString());

        String wetWetherOption = null;
        switch (rgWetWeather.getCheckedRadioButtonId()) {
            case R.id.rb_wet_yes:
                wetWetherOption ="1";
                break;
            case R.id.rb_wet_no:
                wetWetherOption ="2";
                break;
        }

        object.put("wet_weather_option", wetWetherOption);

        String attendPersonOption =  null;
        switch (rgAttendAffe.getCheckedRadioButtonId()) {
            case R.id.rb_attend_yes:
                attendPersonOption =  "1";
                break;
            case R.id.rb_attend_no:
                attendPersonOption =  "2";
                break;
        }

        object.put("attended_person_option", attendPersonOption);
        object.put("event_desc_desc", etDescription.getText().toString());
        object.put("reported_date", tvReportTimeValue.getText().toString());
        object.put("person_mobile_phone", etMobileNo.getText().toString());

        String damageOption =  null;
        switch (rgPropDamage.getCheckedRadioButtonId()) {
            case R.id.rb_damage_yes:
                damageOption = "1";
                break;
            case R.id.rb_damage_no:
                damageOption = "2";
                break;
        }

        object.put("property_damage_option", damageOption);
        object.put("ambulance_who", etAmbReq.getText().toString());
        object.put("cease_date", tvCeasedTimeValue.getText().toString());
        object.put("first_aid_name", etAidName.getText().toString());
        object.put("action_taken", etAction.getText().toString());
        object.put("incident_desc", etBrief.getText().toString());
        object.put("person_workplace_name", etWorkplace.getText().toString());

        String eventTypeOption =  null;
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
        object.put("event_type", eventTypeOption);

        String photosOption = null;
        switch (rgPhotos.getCheckedRadioButtonId()) {
            case R.id.rb_photos_yes:
                photosOption ="1";
                break;
            case R.id.rb_photos_no:
                photosOption ="2";
                break;
        }

        object.put("photo_option", photosOption);

        String wandOption = null;
        switch (rgWandReport.getCheckedRadioButtonId()) {
            case R.id.rb_wand_yes:
                wandOption = "1";
                break;
            case R.id.rb_wand_no:
                wandOption = "2";
                break;
        }
        object.put("web_report_option", wandOption);


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

    private void getData() {
        rgType.getCheckedRadioButtonId();
        tvOccurenceValue.getText().toString();
        rgCeased.getCheckedRadioButtonId();
        tvCeasedTimeValue.getText().toString();
        tvReportTimeValue.getText().toString();
        rgOccurence.getCheckedRadioButtonId();
        etFirstname.getText().toString();
        etSurname.getText().toString();
        rgGender.getCheckedRadioButtonId();
        etHomeAddress.getText().toString();
        etState.getText().toString();
        etPostcode.getText().toString();
        etHomePhone.getText().toString();
        etMobileNo.getText().toString();
        etBirthday.getText().toString();
        etOccupation.getText().toString();
        etWorkplace.getText().toString();
        etAddres.getText().toString();
        etIncident.getText().toString();
        rgEventClass.getCheckedRadioButtonId();
        etBrief.getText().toString();
        etDescription.getText().toString();
        etAction.getText().toString();
        etInjury.getText().toString();
        etIllness.getText().toString();
        etBodily.getText().toString();
        etMark.getText().toString();
        etMechanism.getText().toString();
        etOthers.getText().toString();
        etObserve.getText().toString();
        rgThirdParty.getCheckedRadioButtonId();
        etThirdReport.getText().toString();
        rgPropDamage.getCheckedRadioButtonId();
        etDamageAdv.getText().toString();
        etDamageVeh.getText().toString();
        rgAttendAffe.getCheckedRadioButtonId();
        etName.getText().toString();
        rgFirstAid.getCheckedRadioButtonId();
        etAidName.getText().toString();
        etInjuryDetail.getText().toString();
        etMedCenter.getText().toString();
        etDateAtten.getText().toString();
        rgAmbulance.getCheckedRadioButtonId();
        etAmbReq.getText().toString();
        etAmbPerName.getText().toString();
        etAmbDate.getText().toString();
        rgWeather.getCheckedRadioButtonId();
        etWeatherCond.getText().toString();
        rgDrugAffect.getCheckedRadioButtonId();
        etFootwear.getText().toString();
        etEyewear.getText().toString();
        etCarrying.getText().toString();
        rgCctv.getCheckedRadioButtonId();
        rgPhotos.getCheckedRadioButtonId();
        rgWandReport.getCheckedRadioButtonId();
        rgWetWeather.getCheckedRadioButtonId();
        etComment.getText().toString();
        rgIncidentSpecs.getCheckedRadioButtonId();
        etNotes.getText().toString();


    }
}