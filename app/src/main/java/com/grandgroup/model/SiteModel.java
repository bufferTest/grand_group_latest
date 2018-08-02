package com.grandgroup.model;

import com.parse.ParseGeoPoint;

public class SiteModel {
    private ParseGeoPoint site_location ;
    private String site_address = "";
    private String site_detail = "";
    private String site_name = "";
    private String objectId = "" ;

    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseGeoPoint getSite_location() {
        return site_location;
    }

    public void setSite_location(ParseGeoPoint site_location) {
        this.site_location = site_location;
    }
    public String getSite_address() {
        return site_address;
    }

    public void setSite_address(String site_address) {
        this.site_address = site_address;
    }

    public String getSite_detail() {
        return site_detail;
    }

    public void setSite_detail(String site_detail) {
        this.site_detail = site_detail;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }



}
