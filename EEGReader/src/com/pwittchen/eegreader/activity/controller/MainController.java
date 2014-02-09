package com.pwittchen.eegreader.activity.controller;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.neurosky.thinkgear.TGDevice;
import com.pwittchen.eegreader.R;
import com.pwittchen.eegreader.activity.contract.ActivityMainContract;
import com.pwittchen.eegreader.database.DatabaseHandler;
import com.pwittchen.eegreader.database.model.Signal;
import com.pwittchen.eegreader.generics.GenericApplication;
import com.pwittchen.eegreader.menu.item.MenuItemBluetooth;
import com.pwittchen.eegreader.menu.item.MenuItemSettings;
import com.pwittchen.eegreader.preferences.SettingsSharedPreferences;
import com.pwittchen.eegreader.utils.StringUtils;

public class MainController {

    private ActivityMainContract activity;
    private SettingsSharedPreferences settingsSharedPreferences = new SettingsSharedPreferences();
    private DatabaseHandler databaseHandler;
    private static int TTSThreshold = 90;

    public MainController(ActivityMainContract activity) {
        setActivity(activity);
        setDatabaseHandler();
        setOptionsMenu();
    }

    public void setActivity(ActivityMainContract activity) {
        this.activity = activity;
    }

    private void setDatabaseHandler() {
        this.databaseHandler = new DatabaseHandler(GenericApplication.getContext());
    }

    private void setOptionsMenu() {
        activity.getOptionsMenu().put(R.id.ic_bluetooth_connect_or_disconnect, new MenuItemBluetooth());
        activity.getOptionsMenu().put(R.id.ic_settings, new MenuItemSettings());
    }

    public void connectOrDisconnectToDevice(MenuItem item) {
        if (GenericApplication.getTGDeviceUtils().getTGDevice() != null) {
            disconnectFromDevice(item);
        } else {
            connectToDevice(item);
        }
    }

    private void connectToDevice(MenuItem item) {
        GenericApplication.getTGDeviceUtils().reconnectToDevice();
        item.setIcon(R.drawable.ic_menu_device_access_bluetooth_connected);
        Toast.makeText(GenericApplication.getContext(), activity.getConnectedToDevice(), Toast.LENGTH_SHORT).show();
    }

    private void disconnectFromDevice(MenuItem item) {
        GenericApplication.getTGDeviceUtils().disconnectFromDevice();
        item.setIcon(R.drawable.ic_menu_device_access_bluetooth);
        Toast.makeText(GenericApplication.getContext(), activity.getDisconnectedFromDevice(), Toast.LENGTH_SHORT).show();
    }

    private boolean isVoiceFeedbackEnabled() {
        return settingsSharedPreferences.get(SettingsSharedPreferences.enableVoiceFeedback);
    }

    private boolean isSaveEegAndEyeBlinkingDataEnabled() {
        return settingsSharedPreferences.get(SettingsSharedPreferences.saveEegAndEyeBlinkingData);
    }

    private String getHighAttentionVoiceString() {
        return StringUtils.getStringFromResources(R.string.high_attention_acquired);
    }

    private String getHighMeditationVoiceString() {
        return StringUtils.getStringFromResources(R.string.high_meditation_acquired);
    }

    private void executeAttentionTTS(int level) {
        if (isVoiceFeedbackEnabled() && level > TTSThreshold) {
            if (!activity.getTextToSpeech().isSpeaking()) {
                activity.getTextToSpeech().speak(getHighAttentionVoiceString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    private void executeMeditationTTS(int level) {
        if (isVoiceFeedbackEnabled() && level > TTSThreshold) {
            if (!activity.getTextToSpeech().isSpeaking()) {
                activity.getTextToSpeech().speak(getHighMeditationVoiceString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    private void executeAttentionDataSave(int level) {
        if (isSaveEegAndEyeBlinkingDataEnabled()) {
            databaseHandler.getSignalTableController().add(new Signal(TGDevice.MSG_ATTENTION, level));
        }
    }

    private void executeMeditationDataSave(int level) {
        if (isSaveEegAndEyeBlinkingDataEnabled()) {
            databaseHandler.getSignalTableController().add(new Signal(TGDevice.MSG_MEDITATION, level));
        }
    }

    private void executeBlinkDataSave(int level) {
        if (isSaveEegAndEyeBlinkingDataEnabled()) {
            databaseHandler.getSignalTableController().add(new Signal(TGDevice.MSG_BLINK, level));
        }
    }

    private String getMessageForSignal(int level, int resourcesId) {
        return StringUtils.getStringFromResources(resourcesId) + ": " + String.valueOf(level);
    }

    public void setAttentionLevel(int level) {
        activity.getViewHolder().tvAttention.setText(getMessageForSignal(level,R.string.attention_level));
        executeAttentionTTS(level);
        executeAttentionDataSave(level);
    }

    public void setMeditationLevel(int level) {
        activity.getViewHolder().tvMeditation.setText(getMessageForSignal(level,R.string.meditation_level));
        executeMeditationTTS(level);
        executeMeditationDataSave(level);
    }

    public void setBlinkLevel(int level) {
        activity.getViewHolder().tvBlink.setText(getMessageForSignal(level,R.string.blink_level));
        executeBlinkDataSave(level);
    }

    public void setRawData(int level) {
        activity.getViewHolder().tvRawData.setText(getMessageForSignal(level,R.string.raw_data));
    }

    public void setWaveAlphaLow(int level) {
        activity.getViewHolder().tvWaveAlphaLow.setText(getMessageForSignal(level,R.string.wave_alpha_low));
    }

    public void setWaveAlphaHigh(int level) {
        activity.getViewHolder().tvWaveAlphaHigh.setText(getMessageForSignal(level,R.string.wave_alpha_high));
    }

    public void setWaveBetaLow(int level) {
        activity.getViewHolder().tvWaveBetaLow.setText(getMessageForSignal(level,R.string.wave_beta_low));
    }

    public void setWaveBetaHigh(int level) {
        activity.getViewHolder().tvWaveBetaHigh.setText(getMessageForSignal(level,R.string.wave_beta_high));
    }

    public void setWaveGammaLow(int level) {
        activity.getViewHolder().tvWaveGammaLow.setText(getMessageForSignal(level,R.string.wave_gamma_low));
    }

    public void setWaveGammaMid(int level) {
        activity.getViewHolder().tvWaveGammaMid.setText(getMessageForSignal(level,R.string.wave_gamma_mid));
    }

    public void setWaveDelta(int level) {
        activity.getViewHolder().tvWaveDelta.setText(getMessageForSignal(level,R.string.wave_delta));
    }

    public void setWaveTheta(int level) {
        activity.getViewHolder().tvWaveTheta.setText(getMessageForSignal(level,R.string.wave_theta));
    }
}
