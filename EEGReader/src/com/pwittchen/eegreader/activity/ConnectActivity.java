package com.pwittchen.eegreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivityConnectContract;
import com.pwittchen.eegreader.activity.contract.GenericActivitySignalContract;
import com.pwittchen.eegreader.activity.controller.ConnectController;
import com.pwittchen.eegreader.generics.GenericActivity;
import com.pwittchen.eegreader.generics.GenericApplication;

import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

@ContentView(R.layout.activity_connect)
public class ConnectActivity extends GenericActivity implements GenericActivitySignalContract, ActivityConnectContract {

    private static final String TAG = makeLogTag(ConnectActivity.class);

    @InjectView(R.id.iv_bluetooth_connect)
    private ImageView ivBluetoothConnect;
    @InjectView(R.id.tv_message_from_device)
    private TextView tvMessageFromDevice;
    @InjectView(R.id.tv_initializing)
    private TextView tvInitializing;
    @InjectResource(R.string.bluetooth_is_not_available)
    private String bluetoothIsNotAvailable;
    @InjectResource(R.string.please_activate_bluetooth)
    private String pleaseTurnOnBluetooth;

    private final Handler nextActivityHandler = new Handler();
    private ConnectController connectController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.connectController = new ConnectController(this);

        if (!GenericApplication.getTGDeviceUtils().initializeBlueToothAdapter()) {
            Toast.makeText(this, bluetoothIsNotAvailable, Toast.LENGTH_LONG).show();
        } else {
            GenericApplication.getTGDeviceUtils().setActivityViewContract(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void realizeConnection(View view) {
        if (GenericApplication.getTGDeviceUtils().isBluetoothTurnedOn()) {
            GenericApplication.getTGDeviceUtils().connectToDevice();
        } else {
            Toast.makeText(this, pleaseTurnOnBluetooth, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setMessageFromDevice(String message) {
        tvMessageFromDevice.setText(message);
        connectController.establishConnection(message, startNextActivity);
    }

    private Runnable startNextActivity = new Runnable() {
        @Override
        public void run() {
            finish();
            startActivity(new Intent(GenericApplication.getContext(), MainActivity.class));
        }
    };

    public ImageView getIvBluetoothConnect() {
        return ivBluetoothConnect;
    }

    @Override
    public Handler getNextActivityHandler() {
        return nextActivityHandler;
    }

    @Override
    public TextView getTvInitializing() {
        return tvInitializing;
    }

    @Override
    public void setAttentionLevel(int level) {
    }

    @Override
    public void setMeditationLevel(int level) {
    }

    @Override
    public void setBlinkLevel(int level) {
    }

    @Override
    public void setRawData(int level) {
    }

    @Override
    public void setDelta(int level) {
    }

    @Override
    public void setTheta(int level) {
    }

    @Override
    public void setLowAlpha(int level) {
    }

    @Override
    public void setHighAlpha(int level) {
    }

    @Override
    public void setLowBeta(int level) {
    }

    @Override
    public void setHighBeta(int level) {
    }

    @Override
    public void setLowGamma(int level) {
    }

    @Override
    public void setMidGamma(int level) {
    }
}
