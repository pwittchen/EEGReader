package com.pwittchen.eegreader.activity.contract;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public interface ActivityConnectContract {
    ImageView getIvBluetoothConnect();
    Handler getNextActivityHandler();
    TextView getTvInitializing();
}
