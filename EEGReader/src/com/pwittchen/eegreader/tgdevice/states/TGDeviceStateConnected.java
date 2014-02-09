package com.pwittchen.eegreader.tgdevice.states;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;
import com.pwittchen.eegreader.utils.StringUtils;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceStateConnected implements Runnable {
    private static final String TAG = makeLogTag(TGDeviceStateConnected.class);

    @Override
    public void run() {
        GenericApplication.getTGDeviceUtils().getTGDevice().start();
        String message = StringUtils.getStringFromResources(R.string.connected_to_device);
        LOGD(TAG, message);
        TGDeviceHandler.getActivityViewContract().setMessageFromDevice(message);
    }
}