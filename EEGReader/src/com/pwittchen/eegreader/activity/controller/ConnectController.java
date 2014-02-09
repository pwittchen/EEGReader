package com.pwittchen.eegreader.activity.controller;

import android.view.View;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivityConnectContract;
import com.pwittchen.eegreader.utils.StringUtils;

public class ConnectController {

    private ActivityConnectContract activity;

    public ConnectController(ActivityConnectContract activity) {
        setActivity(activity);
    }

    public void setActivity(ActivityConnectContract activity) {
        this.activity = activity;
    }

    private boolean isConnectedToDeviceMessageReceived(String message) {
        return message.equals(StringUtils.getStringFromResources(R.string.connected_to_device));
    }

    private boolean isConnectingToDeviceMessageReceived(String message) {
        return message.equals(StringUtils.getStringFromResources(R.string.connecting_to_device));
    }

    private boolean isConnectionErrorMessageReceived(String message) {
        return (message.equals(StringUtils.getStringFromResources(R.string.device_not_paired)) ||
                message.equals(StringUtils.getStringFromResources(R.string.device_is_idle)) ||
                message.equals(StringUtils.getStringFromResources(R.string.device_not_found)));
    }

    public void establishConnection(String message, Runnable startNextActivity) {
        if (isConnectingToDeviceMessageReceived(message)) {
            activity.getIvBluetoothConnect().setImageResource(R.drawable.activity_connect_bluetooth_yellow);
        }

        if (isConnectedToDeviceMessageReceived(message)) {
            activity.getIvBluetoothConnect().setImageResource(R.drawable.activity_connect_bluetooth_green);
            activity.getTvInitializing().setVisibility(View.VISIBLE);
            activity.getNextActivityHandler().postDelayed(startNextActivity, 2500);
        } else if (isConnectionErrorMessageReceived(message)) {
            activity.getIvBluetoothConnect().setImageResource(R.drawable.activity_connect_bluetooth_red);
        }
    }
}
