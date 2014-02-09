package com.pwittchen.eegreader.utils;

import com.pwittchen.eegreader.generics.GenericApplication;

public class StringUtils {
    public static String getStringFromResources(int resourcesId) {
        return GenericApplication.getContext().getResources().getString(resourcesId);
    }
}
