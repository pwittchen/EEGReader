package com.pwittchen.eegreader.generics;

import android.app.Application;
import android.content.Context;

import com.pwittchen.eegreader.utils.TGDeviceUtils;

public class GenericApplication extends Application {
    private static Application instance;
    private static TGDeviceUtils tgDeviceUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        tgDeviceUtils = new TGDeviceUtils();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static TGDeviceUtils getTGDeviceUtils() {
        return tgDeviceUtils;
    }
}
