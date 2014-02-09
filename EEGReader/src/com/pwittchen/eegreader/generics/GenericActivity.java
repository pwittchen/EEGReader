package com.pwittchen.eegreader.generics;

import android.view.WindowManager;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.pwittchen.eegreader.preferences.SettingsSharedPreferences;

public abstract class GenericActivity extends RoboSherlockFragmentActivity {

    protected SettingsSharedPreferences settingsSharedPreferences;

    @Override
    protected void onResume() {
        super.onResume();
        settingsSharedPreferences = new SettingsSharedPreferences();
        keepScreenOnIfNecessary();
    }

    protected void keepScreenOnIfNecessary() {
        if (settingsSharedPreferences.get(SettingsSharedPreferences.keepScreenTurnedOn)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
