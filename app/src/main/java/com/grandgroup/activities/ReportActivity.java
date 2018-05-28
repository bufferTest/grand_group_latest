package com.grandgroup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.grandgroup.R;
import com.grandgroup.adapter.ReportAdapter;
import com.grandgroup.model.IncidentModel;
import com.grandgroup.model.RiskReportModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.GrandGroupHelper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    // Spinner Drop down elements
    List categories = new ArrayList();
    ArrayList incidentReportList;
    ArrayList riskReportList;
    String selectedCat = "Incedent Report";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.spinner_report)
    Spinner spinnerReport;
    @BindView(R.id.rv_reports)
    RecyclerView rvReports;
    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setInitialData();
    }

    private void setInitialData() {
        ButterKnife.bind(this);
        mContext = ReportActivity.this;
        tvTitle.setText("Reports");
        btnAdd.setVisibility(View.VISIBLE);
        spinnerReport.setOnItemSelectedListener(this);
        categories.add("Incident Report");
        categories.add("Risk / Hazard Report");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(mContext, R.layout.spinner_dropdown_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerReport.setAdapter(dataAdapter);
        if (selectedCat.equalsIgnoreCase("Incident Report")) {
            getReports("IncidentReport");
        } else if (selectedCat.equalsIgnoreCase("Risk / Hazard Report")) {
            getReports("RiskReport");
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;

            case R.id.btn_add:
                if (selectedCat.equalsIgnoreCase("Incident Report")) {
                    Intent intent = new Intent(mContext, IncidentReportsActivity.class);
                    startActivity(intent);
                    mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else if (selectedCat.equalsIgnoreCase("Risk / Hazard Report")) {
                    Intent intent = new Intent(mContext, RiskReportActivity.class);
                    startActivity(intent);
                    mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
                break;
        }
    }

    private void getReports(String reportType) {
        if (GrandGroupHelper.grandGroupHelper(mContext).CheckIsConnectedToInternet()) {
            CallProgressWheel.showLoadingDialog(mContext);
            ParseQuery parseQuery = new ParseQuery(reportType);
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> reports, ParseException e) {
                    if (e == null) {
                        riskReportList = new ArrayList();
                        incidentReportList = new ArrayList();
                        CallProgressWheel.dismissLoadingDialog();
                        if (reports.size() > 0) {
                            if (selectedCat.equalsIgnoreCase("Incident Report")) {
                                for (int i = 0; i < reports.size(); i++) {
                                    IncidentModel incidentModel = new IncidentModel();
                                    incidentModel.setOjectId(reports.get(i).getObjectId());
                                    incidentReportList.add(incidentModel);
                                }
                                setAdapter(0, reports);
                            } else if (selectedCat.equalsIgnoreCase("Risk / Hazard Report")) {
                                for (int i = 0; i < reports.size(); i++) {
                                    RiskReportModel riskReportModel = new RiskReportModel();
                                    riskReportModel.setObjectId(reports.get(i).getObjectId());
                                    riskReportList.add(riskReportModel);
                                }
                                setAdapter(1, reports);
                            }
                        } else {
                            CallProgressWheel.dismissLoadingDialog();
                        }
                    }
                }

            });
        } else {
            CallProgressWheel.dismissLoadingDialog();
        }
    }

    private void setAdapter(int userSelection, final List<ParseObject> reports) {
        try {
            if (userSelection == 0) {
                if (incidentReportList.size() > 0) {
                    LinearLayoutManager llm = new LinearLayoutManager(this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rvReports.setLayoutManager(llm);
                    ReportAdapter adapter = new ReportAdapter(mContext, userSelection, incidentReportList, new ReportAdapter.ItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            ParseObject incidentReportObject = reports.get(position);
                            IncidentModel incidentModel = new IncidentModel();
                            incidentModel.setOjectId(incidentReportObject.getObjectId());
                            incidentModel.setWeather_option(incidentReportObject.get("weather_option").toString());
                            incidentModel.setIncedent_option(incidentReportObject.get("incident_option").toString());
                            incidentModel.setOccourance_date(incidentReportObject.get("occourance_date").toString());
                            incidentModel.setIncedent_location(incidentReportObject.get("incident_location").toString());
                            incidentModel.setPerson_post_code(incidentReportObject.get("person_post_code").toString());
                            incidentModel.setInjury_type(incidentReportObject.get("injury_type").toString());
                            incidentModel.setThird_party_detail(incidentReportObject.get("third_party_detail").toString());
                            incidentModel.setCease_option(incidentReportObject.get("cease_option").toString());
                            incidentModel.setMedical_center(incidentReportObject.get("medical_center").toString());
                            incidentModel.setWeather_conditions(incidentReportObject.get("weather_conditions").toString());
                            incidentModel.setBreakdown_agency(incidentReportObject.get("breakdown_agency").toString());
                            incidentModel.setPerson_sur_name(incidentReportObject.get("person_sur_name").toString());
                            incidentModel.setPerson_first_name(incidentReportObject.get("person_first_name").toString());
                            incidentModel.setSame_occourance(incidentReportObject.get("same_occourance").toString());
                            incidentModel.setThird_party_option(incidentReportObject.get("third_party_option").toString());
                            incidentModel.setFirst_aid_option(incidentReportObject.get("first_aid_option").toString());
                            incidentModel.setPerson_state(incidentReportObject.get("person_state").toString());
                            incidentModel.setPerson_gender_option(incidentReportObject.get("person_gender_option").toString());
                            incidentModel.setEyewear_type(incidentReportObject.get("eyewear_type").toString());
                            incidentModel.setWitness_statement(incidentReportObject.get("witness_statement").toString());
                            incidentModel.setPerson_drug_option(incidentReportObject.get("person_drug_option").toString());
                            incidentModel.setOther_mechanism(incidentReportObject.get("other_mechanism").toString());
                            incidentModel.setPerson_address(incidentReportObject.get("person_address").toString());
                            incidentModel.setDamage_type(incidentReportObject.get("damage_type").toString());
                            incidentModel.setDate_attended(incidentReportObject.get("date_attended").toString());
                            incidentModel.setAdditional_comments(incidentReportObject.get("additional_comments").toString());
                            incidentModel.setFootwaer_type(incidentReportObject.get("footwear_type").toString());
                            incidentModel.setIncident_report_date(incidentReportObject.get("incident_report_date").toString());
                            incidentModel.setVehicle_damage_detail(incidentReportObject.get("vehicle_damage_detail").toString());
                            incidentModel.setEffected_person_detail(incidentReportObject.get("affected_person_detail").toString());
                            incidentModel.setAttendee_name(incidentReportObject.get("attendee_name").toString());
                            incidentModel.setInjury_machanism(incidentReportObject.get("injury_mechanism").toString());
                            incidentModel.setCctv_option(incidentReportObject.get("cctv_option").toString());
                            incidentModel.setOther_breakdown_agency(incidentReportObject.get("other_breakdown_agency").toString());
                            incidentModel.setBody_location(incidentReportObject.get("body_location").toString());
                            incidentModel.setCarring_type(incidentReportObject.get("carrying_type").toString());
                            incidentModel.setIncident_report_person(incidentReportObject.get("incident_report_person").toString());
                            incidentModel.setWarning_sign_option(incidentReportObject.get("warning_sign_option").toString());
                            incidentModel.setAffected_person_option(incidentReportObject.get("affected_person_option").toString());
                            incidentModel.setPerson_occupation(incidentReportObject.get("person_occupation").toString());
                            incidentModel.setInjury_mark(incidentReportObject.get("injury_mark").toString());
                            incidentModel.setAmbulance_attend_option(incidentReportObject.get("ambulance_attend_option").toString());
                            incidentModel.setInjury_illness(incidentReportObject.get("injury_illness").toString());
                            incidentModel.setPerson_home_phone(incidentReportObject.get("person_home_phone").toString());
                            incidentModel.setInjury_illness_detail(incidentReportObject.get("injury_illness_detail").toString());
                            incidentModel.setWhat_you_see(incidentReportObject.get("what_you_see").toString());
                            incidentModel.setPerson_birth_date(incidentReportObject.get("person_birth_date").toString());
                            incidentModel.setWet_weather_option(incidentReportObject.get("wet_weather_option").toString());
                            incidentModel.setAttended_person_option(incidentReportObject.get("attended_person_option").toString());
                            incidentModel.setEvent_desc_desc(incidentReportObject.get("event_desc_desc").toString());
                            incidentModel.setReported_date(incidentReportObject.get("reported_date").toString());
                            incidentModel.setPerson_mobile_phone(incidentReportObject.get("person_mobile_phone").toString());
                            incidentModel.setProperty_damage_option(incidentReportObject.get("property_damage_option").toString());
                            incidentModel.setAmbulance_who(incidentReportObject.get("ambulance_who").toString());
                            incidentModel.setCease_date(incidentReportObject.get("cease_date").toString());
                            incidentModel.setFirst_aid_name(incidentReportObject.get("first_aid_name").toString());
                            incidentModel.setPerson_phone_address(incidentReportObject.get("person_home_address").toString());
                            incidentModel.setAction_taken(incidentReportObject.get("action_taken").toString());
                            incidentModel.setIncident_desc(incidentReportObject.get("incident_desc").toString());
                            incidentModel.setPerson_workplace_name(incidentReportObject.get("person_workplace_name").toString());
                            incidentModel.setEvent_type(incidentReportObject.get("event_type").toString());
                            incidentModel.setCease_date(incidentReportObject.get("cease_date").toString());

                            ParseFile firstAidSign = incidentReportObject.getParseFile("first_aid_signature");
                            ParseFile inci_repo_person_sign = incidentReportObject.getParseFile("incident_report_person_signature");
                            ParseFile photo_option = incidentReportObject.getParseFile("photo_option");

                            if (firstAidSign != null)
                                incidentModel.setFirst_aid_signature(firstAidSign.getUrl());
                            else
                                incidentModel.setFirst_aid_signature("");

                            if (inci_repo_person_sign != null)
                                incidentModel.setIncident_report_person_signature(inci_repo_person_sign.getUrl());
                            else
                                incidentModel.setIncident_report_person_signature("");

                            if (photo_option != null)
                                incidentModel.setPhoto_option(photo_option.getUrl());
                            else
                                incidentModel.setPhoto_option("");

                            Intent intent = new Intent(mContext, IncidentReportsActivity.class);
                            intent.putExtra("incidentModel", incidentModel);
                            startActivity(intent);
                            mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                    });
                    rvReports.setHasFixedSize(true);
                    rvReports.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    rvReports.setVisibility(View.VISIBLE);
                    rvReports.setVisibility(View.GONE);
                }
            } else if (userSelection == 1) {
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvReports.setLayoutManager(llm);
                ReportAdapter adapter = new ReportAdapter(mContext, riskReportList, userSelection, new ReportAdapter.ItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        ParseObject riskReportObject = reports.get(position);
                        RiskReportModel riskReportModel = new RiskReportModel();
                        riskReportModel.setRisk_likelihood(riskReportObject.get("risk_likelihood").toString());
                        riskReportModel.setObjectId(riskReportObject.getObjectId());
                        riskReportModel.setRisk_action_plan(riskReportObject.get("risk_action_plan").toString());
                        riskReportModel.setRisk_location(riskReportObject.get("risk_location").toString());
                        riskReportModel.setRisk_description(riskReportObject.get("risk_description").toString());
                        riskReportModel.setRisk_control_effectiveness(riskReportObject.get("risk_control_effectiveness").toString());
                        riskReportModel.setRisk_control(riskReportObject.get("risk_control").toString());
                        riskReportModel.setRisk_reported_by(riskReportObject.get("risk_reported_by").toString());
                        riskReportModel.setRisk_consequence(riskReportObject.get("risk_consequence").toString());
                        riskReportModel.setRisk_date(riskReportObject.get("risk_date").toString());
                        ParseFile signImage = riskReportObject.getParseFile("signature_file");
                        ParseFile riskImage = riskReportObject.getParseFile("risk_file");
                        if (signImage != null)
                            riskReportModel.setSignature_file(signImage.getUrl());
                        else
                            riskReportModel.setSignature_file("");

                        if (riskImage != null)
                            riskReportModel.setRisk_file(riskImage.getUrl());
                        else
                            riskReportModel.setRisk_file("");
                        Intent intent = new Intent(mContext, RiskReportActivity.class);
                        intent.putExtra("riskReportObject", riskReportModel);
                        startActivity(intent);
                        mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
                rvReports.setHasFixedSize(true);
                rvReports.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                rvReports.setVisibility(View.VISIBLE);
                rvReports.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCat = parent.getItemAtPosition(position).toString();
        if (selectedCat.equalsIgnoreCase("Incident Report")) {
            getReports("IncidentReport");
        } else if (selectedCat.equalsIgnoreCase("Risk / Hazard Report")) {
            getReports("RiskReport");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
