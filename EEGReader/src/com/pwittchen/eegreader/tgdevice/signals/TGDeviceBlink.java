package com.pwittchen.eegreader.tgdevice.signals;

import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;
import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceBlink extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDeviceBlink.class);

    @Override
    public void run() {
        TGDeviceHandler.getActivityViewContract().setBlinkLevel(level);
        LOGD(TAG, "Control signal - Blink level: " + level);
    }
}