package com.pwittchen.eegreader.tgdevice.brainwaves;

import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;
import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceEEGPowerTheta extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDeviceEEGPowerTheta.class);

    @Override
    public void run() {
        TGDeviceHandler.getActivityViewContract().setTheta(level);
        LOGD(TAG, "Control signal - Theta wave: " + level);
    }
}
