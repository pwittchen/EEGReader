package com.pwittchen.eegreader.utils;

import android.bluetooth.BluetoothAdapter;

import com.neurosky.thinkgear.TGDevice;
import com.pwittchen.eegreader.activity.contract.GenericActivitySignalContract;
import com.pwittchen.eegreader.tgdevice.TGDeviceHandler;

public class TGDeviceUtils {
    private BluetoothAdapter bluetoothAdapter;
    private TGDeviceHandler tgDeviceHandler;
    private static TGDevice tgDevice;
    private boolean isTGDeviceRawSignalEnabled = true;

    private boolean isDeviceConnecting() {
        return tgDevice.getState() == TGDevice.STATE_CONNECTING;
    }

    public boolean isDeviceConnected() {
        return tgDevice.getState() == TGDevice.STATE_CONNECTED;
    }

    public boolean initializeBlueToothAdapter() {
        this.tgDeviceHandler = new TGDeviceHandler();
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return false;
        } else {
            setTGDevice(new TGDevice(bluetoothAdapter, tgDeviceHandler));
            return true;
        }
    }

    public TGDevice getTGDevice() {
        return tgDevice;
    }

    public void setTGDevice(TGDevice tgDevice) {
        TGDeviceUtils.tgDevice = tgDevice;
    }

    public void reconnectToDevice() {
        initializeBlueToothAdapter();
        connectToDevice();
    }

    public void connectToDevice() {
        if (!isDeviceConnecting() && !isDeviceConnected()) {
            tgDevice.connect(isTGDeviceRawSignalEnabled);
        }
    }

    public void disconnectFromDevice() {
        if (tgDevice != null) {
            tgDevice.close();
            this.tgDevice = null;
        }
    }

    public boolean isBluetoothTurnedOn() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled());
    }

    public void setActivityViewContract(GenericActivitySignalContract activity) {
        tgDeviceHandler.setActivityViewContract(activity);
    }

}