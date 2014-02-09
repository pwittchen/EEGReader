package com.pwittchen.eegreader.tgdevice.signals;

import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;
import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class TGDeviceAttention extends GenericSignal implements Runnable, SignalSettable {
    private static final String TAG = makeLogTag(TGDeviceAttention.class);

    @Override
    public void run() {
        TGDeviceHandler.getActivityViewContract().setAttentionLevel(level);
        LOGD(TAG, "Control signal - Attention level: " + level);
    }
}
