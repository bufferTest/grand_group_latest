package com.grandgroup;

import android.app.Application;
import android.content.Context;

import com.buddy.sdk.Buddy;
import com.facebook.stetho.Stetho;
import com.grandgroup.utills.TypefaceUtil;
import com.onesignal.OneSignal;
import com.parse.Parse;

/**
 * Created by hp on 1/4/2017.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Buddy.init(MyApplication.this, "55bc98e3-d134-47dd-8ee8-0e1ba73a7a69","");
        Parse.initialize(new Parse.Configuration.Builder(MyApplication.this)
                .applicationId("55bc98e3-d134-47dd-8ee8-0e1ba73a7a69")
                .clientKey("").server("https://parse.buddy.com/parse").build());

        Stetho.initializeWithDefaults(this);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/HelveticaNeue Light.ttf");

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public static Context getAppContext() {
        return instance;
    }
}
