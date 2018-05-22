package com.grandgroup.model;

import java.util.Date;

public class ShiftDetailModel {
    private String shift_end_date_str = "";
    private String shift_user = "";
    private String shift_assigned_by_id = "";
    private String shift_details = "";
    private String shift_name = "";
    private String shift_start_date_str = "";
    private Date shift_start_date ;
    private Date shift_end_date;

    public void setShift_start_date(Date shift_start_date) {
        this.shift_start_date = shift_start_date;
    }

    public void setShift_end_date(Date shift_end_date) {
        this.shift_end_date = shift_end_date;
    }

    public Date getShift_start_date() {
        return shift_start_date;
    }

    public Date getShift_end_date() {
        return shift_end_date;
    }


    public String getShift_end_date_str() {
        return shift_end_date_str;
    }

    public String getShift_name() {
        return shift_name;
    }

    public String getShift_start_date_str() {
        return shift_start_date_str;
    }

    public String getShift_user() {
        return shift_user;
    }

    public String getShift_assigned_by_id() {
        return shift_assigned_by_id;
    }

    public String getShift_details() {
        return shift_details;
    }


    public void setShift_end_date_str(String shift_end_date_str) {
        this.shift_end_date_str = shift_end_date_str;
    }

    public void setShift_name(String shift_name) {
        this.shift_name = shift_name;
    }

    public void setShift_start_date_str(String shift_start_date_str) {
        this.shift_start_date_str = shift_start_date_str;
    }

    public void setShift_user(String shift_user) {
        this.shift_user = shift_user;
    }

    public void setShift_assigned_by_id(String shift_assigned_by_id) {
        this.shift_assigned_by_id = shift_assigned_by_id;
    }

    public void setShift_details(String shift_details) {
        this.shift_details = shift_details;
    }

}
