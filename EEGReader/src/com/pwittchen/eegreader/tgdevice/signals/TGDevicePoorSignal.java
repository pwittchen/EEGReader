package com.pwittchen.eegreader.tgdevice.signals;

import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDevicePoorSignal extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDevicePoorSignal.class);

    @Override
    public void run() {
        LOGD(TAG, "Signal is poor.");
    }
}
