package com.pwittchen.eegreader.tgdevice.brainwaves;

import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;
import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceEEGPowerDelta extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDeviceEEGPowerDelta.class);

    @Override
    public void run() {
        TGDeviceHandler.getActivityViewContract().setDelta(level);
        LOGD(TAG, "Control signal - Delta wave: " + level);
    }
}
