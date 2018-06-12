package com.grandgroup.model;


import java.io.File;
import java.io.Serializable;

public class RiskReportModel implements Serializable{
    String objectId ="";

    String risk_likelihood ="";
    String risk_action_plan ="";
    String signature_file ="";
    String risk_location ="";
    String risk_file ="";
    String risk_description ="";
    String risk_control_effectiveness ="";
    String risk_control ="";
    String risk_reported_by ="";
    String risk_consequence ="";
    String risk_date ="";

    public void setRisk_likelihood(String risk_likelihood) {
        this.risk_likelihood = risk_likelihood;
    }

    public void setRisk_action_plan(String risk_action_plan) {
        this.risk_action_plan = risk_action_plan;
    }

    public void setSignature_file(String signature_file) {
        this.signature_file = signature_file;
    }

    public void setRisk_location(String risk_location) {
        this.risk_location = risk_location;
    }

    public void setRisk_file(String risk_file) {
        this.risk_file = risk_file;
    }

    public void setRisk_description(String risk_description) {
        this.risk_description = risk_description;
    }

    public void setRisk_control_effectiveness(String risk_control_effectiveness) {
        this.risk_control_effectiveness = risk_control_effectiveness;
    }

    public void setRisk_control(String risk_control) {
        this.risk_control = risk_control;
    }

    public void setRisk_reported_by(String risk_reported_by) {
        this.risk_reported_by = risk_reported_by;
    }

    public void setRisk_consequence(String risk_consequence) {
        this.risk_consequence = risk_consequence;
    }

    public void setRisk_date(String risk_date) {
        this.risk_date = risk_date;
    }


    public String getRisk_likelihood() {
        return risk_likelihood;
    }

    public String getRisk_action_plan() {
        return risk_action_plan;
    }

    public String getSignature_file() {
        return signature_file;
    }

    public String getRisk_location() {
        return risk_location;
    }

    public String getRisk_file() {
        return risk_file;
    }

    public String getRisk_description() {
        return risk_description;
    }

    public String getRisk_control_effectiveness() {
        return risk_control_effectiveness;
    }

    public String getRisk_control() {
        return risk_control;
    }

    public String getRisk_reported_by() {
        return risk_reported_by;
    }

    public String getRisk_consequence() {
        return risk_consequence;
    }

    public String getRisk_date() {
        return risk_date;
    }


    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
}
