package com.pwittchen.eegreader.tgdevice.signals;

import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceSleepStage extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDeviceSleepStage.class);

    @Override
    public void run() {
        LOGD(TAG, "Control signal - Sleep Stage level: " + level);
    }
}
