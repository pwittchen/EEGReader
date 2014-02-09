package com.pwittchen.eegreader.tgdevice.states;

import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.utils.StringUtils;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceStateDisconnected implements Runnable {
    private static final String TAG = makeLogTag(TGDeviceStateDisconnected.class);

    @Override
    public void run() {
        String message = StringUtils.getStringFromResources(R.string.disconnected_from_device);
        LOGD(TAG, message);
    }
}