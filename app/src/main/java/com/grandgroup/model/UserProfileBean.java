package com.grandgroup.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.parse.ParseFile;

import java.io.Serializable;

/**
 * Created by 1234 on 2/1/2017.
 */

public class UserProfileBean implements Serializable {
    private String userFirstName = "";
    private String userLastName = "";
    private String userPhoneNumber = "";
    private  String userPassword = "";
    private String userCurrentCity = "";
    private Bitmap userImgBitmap;
    private String userProfilePicUrl;
    private  String userGender = "";
    private  boolean userAvailability = true;
    private String userEmail = "";
    private  boolean isAdmin = true;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public Bitmap getUserImgBitmap() {
        return userImgBitmap;
    }

    public void setUserImgBitmap(Bitmap userImgBitmap) {
        this.userImgBitmap = userImgBitmap;
    }


    public boolean isUserAvailability() {
        return userAvailability;
    }

    public void setUserAvailability(boolean userAvailability) {
        this.userAvailability = userAvailability;
    }


    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }


  /*  public ParseFile getUserImg() {
        return userImg;
    }

    public void setUserImg(ParseFile userImg) {
        this.userImg = userImg;
    }*/

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserCurrentCity() {
        return userCurrentCity;
    }

    public void setUserCurrentCity(String userCurrentCity) {
        this.userCurrentCity = userCurrentCity;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }


}