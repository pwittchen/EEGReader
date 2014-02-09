package com.pwittchen.eegreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.generics.GenericActivity;
import com.pwittchen.eegreader.generics.GenericApplication;

import roboguice.inject.ContentView;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

@ContentView(R.layout.activity_splash_screen)
public class SplashScreenActivity extends GenericActivity {

    private static final String TAG = makeLogTag(SplashScreenActivity.class);
    private Handler nextActivityHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nextActivityHandler.postDelayed(startNextActivity, 2500);
    }

    private Runnable startNextActivity = new Runnable() {
        @Override
        public void run() {
            finish();
            startActivity(new Intent(GenericApplication.getContext(), ConnectActivity.class));
        }
    };
}
