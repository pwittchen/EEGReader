package com.pwittchen.eegreader.tgdevice;

import android.os.Handler;
import android.os.Message;

import com.neurosky.thinkgear.TGDevice;
import com.pwittchen.eegreader.activity.contract.GenericActivitySignalContract;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerDelta;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerHighAlpha;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerHighBeta;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerLowAlpha;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerLowBeta;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerLowGamma;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerMidGamma;
import com.pwittchen.eegreader.tgdevice.brainwaves.TGDeviceEEGPowerTheta;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceAttention;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceBlink;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceEegPower;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceHeartRate;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceLowBattery;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceMeditation;
import com.pwittchen.eegreader.tgdevice.signals.TGDevicePoorSignal;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceRawCount;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceRawData;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceRawMulti;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceSleepStage;
import com.pwittchen.eegreader.tgdevice.signals.TGDeviceStateChange;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateConnected;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateConnecting;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateDisconnected;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateIdle;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateNotFound;
import com.pwittchen.eegreader.tgdevice.states.TGDeviceStateNotPaired;

import java.util.HashMap;
import java.util.Map;

public final class TGDeviceHandler extends Handler {

    private static Map<Integer, Runnable> deviceStates;
    private static Map<Integer, GenericSignal> deviceSignals;
    private static Map<Integer, GenericSignal> brainWaves;
    private static GenericActivitySignalContract activityViewContract;

    public TGDeviceHandler() {
        initializeDeviceStates();
        initializeDeviceSignals();
        initializeBrainWaves();
    }

    public static Map<Integer, Runnable> getDeviceStates() {
        return deviceStates;
    }

    public static Map<Integer, GenericSignal> getBrainWaves() {
        return brainWaves;
    }

    private void initializeDeviceStates() {
        deviceStates = new HashMap<Integer, Runnable>();
        deviceStates.put(TGDevice.STATE_IDLE, new TGDeviceStateIdle());
        deviceStates.put(TGDevice.STATE_CONNECTING, new TGDeviceStateConnecting());
        deviceStates.put(TGDevice.STATE_CONNECTED, new TGDeviceStateConnected());
        deviceStates.put(TGDevice.STATE_NOT_FOUND, new TGDeviceStateNotFound());
        deviceStates.put(TGDevice.STATE_NOT_PAIRED, new TGDeviceStateNotPaired());
        deviceStates.put(TGDevice.STATE_DISCONNECTED, new TGDeviceStateDisconnected());
    }

    private void initializeDeviceSignals() {
        deviceSignals = new HashMap<Integer, GenericSignal>();
        deviceSignals.put(TGDevice.MSG_STATE_CHANGE, new TGDeviceStateChange());
        deviceSignals.put(TGDevice.MSG_POOR_SIGNAL, new TGDevicePoorSignal());
        deviceSignals.put(TGDevice.MSG_ATTENTION, new TGDeviceAttention());
        deviceSignals.put(TGDevice.MSG_MEDITATION, new TGDeviceMeditation());
        deviceSignals.put(TGDevice.MSG_BLINK, new TGDeviceBlink());
        deviceSignals.put(TGDevice.MSG_SLEEP_STAGE, new TGDeviceSleepStage());
        deviceSignals.put(TGDevice.MSG_LOW_BATTERY, new TGDeviceLowBattery());
        deviceSignals.put(TGDevice.MSG_RAW_COUNT, new TGDeviceRawCount());
        deviceSignals.put(TGDevice.MSG_RAW_DATA, new TGDeviceRawData());
        deviceSignals.put(TGDevice.MSG_HEART_RATE, new TGDeviceHeartRate());
        deviceSignals.put(TGDevice.MSG_RAW_MULTI, new TGDeviceRawMulti());
        deviceSignals.put(TGDevice.MSG_EEG_POWER, new TGDeviceEegPower());
    }

    private void initializeBrainWaves() {
        brainWaves = new HashMap<Integer, GenericSignal>();
        brainWaves.put(TGDeviceEegPower.DELTA, new TGDeviceEEGPowerDelta());
        brainWaves.put(TGDeviceEegPower.THETA, new TGDeviceEEGPowerTheta());
        brainWaves.put(TGDeviceEegPower.LOW_ALPHA, new TGDeviceEEGPowerLowAlpha());
        brainWaves.put(TGDeviceEegPower.HIGH_ALPHA, new TGDeviceEEGPowerHighAlpha());
        brainWaves.put(TGDeviceEegPower.LOW_BETA, new TGDeviceEEGPowerLowBeta());
        brainWaves.put(TGDeviceEegPower.HIGH_BETA, new TGDeviceEEGPowerHighBeta());
        brainWaves.put(TGDeviceEegPower.LOW_GAMMA, new TGDeviceEEGPowerLowGamma());
        brainWaves.put(TGDeviceEegPower.MID_GAMMA, new TGDeviceEEGPowerMidGamma());
    }

    @Override
    public void handleMessage(Message msg) {
        if (GenericApplication.getTGDeviceUtils().getTGDevice() != null) {
            if (deviceSignals.containsKey(msg.what)) {
                deviceSignals.get(msg.what).message(msg).run();
            }
        }
    }

    public void setActivityViewContract(GenericActivitySignalContract activityForUiUpdates) {
        this.activityViewContract = activityForUiUpdates;
    }

    public static GenericActivitySignalContract getActivityViewContract() {
        return activityViewContract;
    }
}
