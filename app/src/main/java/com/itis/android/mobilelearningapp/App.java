package com.itis.android.mobilelearningapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Washington.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
