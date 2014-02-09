package com.pwittchen.eegreader.tgdevice;

import android.os.Message;

public abstract class GenericSignal implements Runnable, SignalSettable {
    protected int level;
    protected Message message;
    protected Object object;

    @Override
    public Runnable message(Message message) {
        this.message = message;
        this.level = message.arg1;
        this.object = message.obj;
        return this;
    }

    @Override
    public void run() {
    }
}
