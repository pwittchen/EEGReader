package com.pwittchen.eegreader.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.pwittchen.eegreader.generics.GenericApplication;

public class SettingsSharedPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private final static String preferencesKey = "com.pwittchen.eegreader.settings";
    public final static String keepScreenTurnedOn = "settingsKeepScreenTurnedOn";
    public final static String saveEegAndEyeBlinkingData = "settingsSaveEegAndEyeBlinkingData";
    public final static String enableVoiceFeedback = "settingsEnableVoiceFeedback";

    public SettingsSharedPreferences() {
        this.sharedPreferences = GenericApplication.getContext().getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void put(String key, boolean value) {
        sharedPreferencesEditor.putBoolean(key, value);
        sharedPreferencesEditor.commit();
    }

    public boolean get(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
