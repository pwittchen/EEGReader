package com.pwittchen.eegreader.tgdevice.signals;

import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;
import com.pwittchen.eegreader.tgdevice.GenericSignal;
import com.pwittchen.eegreader.tgdevice.SignalSettable;

public class TGDeviceStateChange extends GenericSignal implements Runnable, SignalSettable {
    @Override
    public void run() {
        if(TGDeviceHandler.getDeviceStates().containsKey(level)) {
            TGDeviceHandler.getDeviceStates().get(level).run();
        }
    }
}